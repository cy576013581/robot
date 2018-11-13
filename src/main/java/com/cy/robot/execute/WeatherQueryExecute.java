package com.cy.robot.execute;

import com.cy.robot.boost.AbstractExecute;
import com.cy.robot.business.entity.Forecast;
import com.cy.robot.business.entity.WeatherResponse;
import com.cy.robot.business.service.CityDataService;
import com.cy.robot.business.service.WeatherDataService;
import com.cy.robot.carrier.Code;
import com.cy.robot.carrier.Judge;
import com.cy.robot.carrier.Answer;
import com.cy.robot.carrier.Word;
import com.cy.robot.domain.Weather;
import com.cy.robot.intent.WeatherQueryIntent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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
    private CityDataService cityDataService;

    @Override
    public Optional<WeatherQueryIntent> start(String text, List<Word> words) {
        WeatherQueryIntent intent = new WeatherQueryIntent();
        intent.setCity("北京");
        for(Word w : words){
            if("t".equals(w.getNature())){
                intent.setDate(LocalDate.now().toString());
            }else if("ns".equals(w.getNature())){
                intent.setCity(w.getWord());
            }
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
        Forecast forecast = weather.getForecast().get(0);
        StringBuilder sb = new StringBuilder();
        sb.append(forecast.getDate())
                .append(weather.getCity())
                .append("的天气：").append(forecast.getType())
                .append(",").append(forecast.getHigh())
                .append(",").append(forecast.getLow())

                .append(",").append(weather.getGanmao())
                .append("小y").append("祝您生活愉快！");
        Answer answer = new Answer();
        answer.setText(sb.toString());
        answer.setCode(Code.SUCCESS);
        return answer;
    }
}
