package com.cy.robot.execute;

import com.cy.robot.boost.AbstractExecute;
import com.cy.robot.business.entity.WeatherResponse;
import com.cy.robot.business.service.WeatherDataService;
import com.cy.robot.carrier.Answer;
import com.cy.robot.carrier.Code;
import com.cy.robot.carrier.Judge;
import com.cy.robot.carrier.Word;
import com.cy.robot.config.ApplicationProperties;
import com.cy.robot.domain.Weather;
import com.cy.robot.intent.WeatherQueryIntent;
import com.cy.robot.time.DateService;
import com.cy.robot.util.DateUtil;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
 * 天气查询的执行器
 */
@Slf4j
@Component
public class WeatherQueryExecute extends AbstractExecute<WeatherQueryIntent> {

    public static final String DOMAIN = "WEATHER";

    public static final String INTENT = "WEATHER_QUERY";

    private final WeatherDataService weatherDataService;

    private final DateService dateService;

    public WeatherQueryExecute(WeatherDataService weatherDataService,
                               DateService dateService,
                               SpringTemplateEngine templateEngine,
                               ApplicationProperties props) {
        super(DOMAIN, INTENT,templateEngine,props);
        this.weatherDataService = weatherDataService;
        this.dateService = dateService;
    }

    @Override
    public Optional<WeatherQueryIntent> start(String text, List<Word> words) {
        WeatherQueryIntent intent = new WeatherQueryIntent("杭州",LocalDate.now().toString());
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
        weather.setDiff(diff);
        Map<String, Object> params = ImmutableMap.of("data",weather);
        return new Answer(Code.SUCCESS,DOMAIN,INTENT, applyTemplate(params));
    }
}
