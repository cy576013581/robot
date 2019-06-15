package com.cy.robot.domain;

import com.cy.robot.business.entity.Forecast;
import com.cy.robot.business.entity.Yesterday;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Weather implements Serializable {

    private String city;

    private Yesterday yesterday;

    private String aqi;

    private String ganmao;

    private String wendu;
    
    private List<Forecast> forecast;

    private int diff;

}
