package com.cy.robot.business.entity;

import com.cy.robot.domain.Weather;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse implements Serializable {

    private Weather data;
    private Integer status;
    private String desc;

}
