package com.cy.robot.intent;

import com.cy.robot.boost.Intent;
import lombok.Data;

@Data
public class WeatherQueryIntent extends Intent {

    String city;

    String date;
}
