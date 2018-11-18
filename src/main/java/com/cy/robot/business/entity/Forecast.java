package com.cy.robot.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Forecast implements Serializable {

 /*   "forecast":[{
        "date":"23日星期天", "high":"高温 24℃",
        "fengli":"<![CDATA[4-5级]]>",
        "low":"低温 12℃", "fengxiang":"北风", "type":"晴"
    },{
        "date":"24日星期一", "high":"高温 23℃",
         "fengli":"<![CDATA[<3级]]>", "low":"低温 11℃",
         "fengxiang":"无持续风向", "type": "晴"
    }]*/

    private String date;
    private String high;
    private String fengli;
    private String low;
    private String fengxiang;
    private String type;

}