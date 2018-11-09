package com.cy.robot.intent;

import com.cy.robot.boost.Intent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WeatherQueryIntent extends Intent {

    String city;

    String date;
}
