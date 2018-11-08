package com.cy.robot.execute;

import com.cy.robot.boost.AbstractExecute;
import com.cy.robot.carrier.Code;
import com.cy.robot.carrier.Judge;
import com.cy.robot.carrier.Answer;
import com.cy.robot.carrier.Word;
import com.cy.robot.intent.WeatherQueryIntent;
import lombok.extern.slf4j.Slf4j;
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

    public static final String domain = "WEATHER";

    public static final String intent = "WEATHER_QUERY";

    public WeatherQueryExecute() {
        super(domain, intent);
    }

    @Override
    public Optional<WeatherQueryIntent> start(String text, List<Word> words) {
        System.out.println(words);
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
        Answer answer = new Answer();
        answer.setText("今夜到明天上午有点想你,预计下午转为持续想你,受此低情绪影响,傍晚将转为大到暴想,心情降低五度");
        answer.setCode(Code.SUCCESS);
        return answer;
    }
}
