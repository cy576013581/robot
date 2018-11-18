package com.cy.robot.execute;

import com.cy.robot.boost.AbstractExecute;
import com.cy.robot.business.entity.Forecast;
import com.cy.robot.business.entity.WeatherResponse;
import com.cy.robot.business.entity.Yesterday;
import com.cy.robot.business.service.WeatherDataService;
import com.cy.robot.carrier.Answer;
import com.cy.robot.carrier.Code;
import com.cy.robot.carrier.Judge;
import com.cy.robot.carrier.Word;
import com.cy.robot.domain.Weather;
import com.cy.robot.intent.WeatherQueryIntent;
import com.cy.robot.time.DateService;
import com.cy.robot.time.nlp.TimeUnit;
import com.cy.robot.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/*
 * 天气查询的执行器
 */
@Slf4j
@Component
public class WeatherQueryExecute extends AbstractExecute<WeatherQueryIntent> {

    private static final String domain = "WEATHER";

    private static final String intent = "WEATHER_QUERY";

    public WeatherQueryExecute() {
        super(domain, intent);
    }

    @Autowired
    private WeatherDataService weatherDataService;

    @Autowired
    private DateService dateService;

    @Override
    public Optional<WeatherQueryIntent> start(String text, List<Word> words) {
        WeatherQueryIntent intent = new WeatherQueryIntent();
        intent.setCity("北京");
        for(Word w : words){
            if("t".equals(w.getNature())){
                String date = dateService.parse(w.getWord());// 抽取时间
                if(null != date && !"".equals(date)){
                    intent.setDate(date);
                }
            }else if("ns".equals(w.getNature())){
                intent.setCity(w.getWord());
            }
        }
        if(null == intent.getDate() || "".equals(intent.getDate())){
            intent.setDate(LocalDate.now().toString());
        }
        return Optional.of(intent);
    }

    @Override
    public Judge validate(WeatherQueryIntent intent) {
        return new Judge(true,null);
    }

    @Override
    public Answer apply(WeatherQueryIntent intent) {
        /*
        "date":"27日星期四", "high":"高温 22℃",
        "fengli":"<![CDATA[3-4级]]>", "low":"低温 14℃",
        "fengxiang":"南风", "type":"阴"
        */
//        String cityCode = cityDataService.getCityCode(intent.getCity());

        WeatherResponse response = weatherDataService.getDataByCityName(intent.getCity());
        Weather weather = response.getData();
        int diff = DateUtil.getDiff(DateUtil.parseDate(DateUtil.formatDate(new Date()))
                ,DateUtil.parseDate(intent.getDate()));
        StringBuilder sb = new StringBuilder();
        if(diff == -1){
            Yesterday yesterday = weather.getYesterday();
            sb.append(yesterday.getDate())
                    .append(weather.getCity())
                    .append("的天气：").append(yesterday.getType())
                    .append("，").append(yesterday.getHigh())
                    .append("，").append(yesterday.getLow())

                    .append("，").append(weather.getGanmao())
                    .append(props.getName()).append("祝您生活愉快！");
        }else{
            Forecast forecast = weather.getForecast().get(diff);
            sb.append(forecast.getDate())
                    .append(weather.getCity())
                    .append("的天气：").append(forecast.getType())
                    .append("，").append(forecast.getHigh())
                    .append("，").append(forecast.getLow())

                    .append("，").append(weather.getGanmao())
                    .append(props.getName()).append("祝您生活愉快！");
        }

        Answer answer = new Answer();
        answer.setText(sb.toString());
        answer.setCode(Code.SUCCESS);
        return answer;
    }
}
