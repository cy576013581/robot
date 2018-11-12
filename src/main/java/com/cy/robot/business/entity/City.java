package com.cy.robot.business.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: ${description}
 * @author: chenyang
 * @create: 2018-11-09
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {

    private String pinyin;

    private String province;

    private String name;

    private String code;
}
