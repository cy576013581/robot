package com.cy.robot.intent;

import com.cy.robot.boost.Intent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WeatherQueryIntent extends Intent {

    private String city = "北京";

    private String date = LocalDate.now().toString();
}
