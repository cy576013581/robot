package com.cy.robot.domain;

import com.cy.robot.business.entity.Forecast;
import com.cy.robot.business.entity.Yesterday;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weather implements Serializable {

    private String city;
    private Yesterday yesterday;
    private String aqi;
    private String ganmao;
    private String wendu;
    private List<Forecast> forecast;

}
