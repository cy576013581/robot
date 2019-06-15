package com.cy.robot.intent;

import com.cy.robot.boost.Intent;
import com.cy.robot.carrier.Answer;
import com.cy.robot.execute.WeatherQueryExecute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WeatherQueryIntent extends Intent {

    private String city;

    private String date;

    public WeatherQueryIntent(String text, Answer answer) {
        super(WeatherQueryExecute.DOMAIN,WeatherQueryExecute.INTENT, text, answer);
    }

    public WeatherQueryIntent() {
        super(WeatherQueryExecute.DOMAIN,WeatherQueryExecute.INTENT);
    }

    public WeatherQueryIntent(String city,String date) {
        super(WeatherQueryExecute.DOMAIN,WeatherQueryExecute.INTENT);
        this.city = city;
        this.date = date;
    }
}
