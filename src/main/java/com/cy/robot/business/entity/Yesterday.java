package com.cy.robot.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Yesterday implements Serializable {
   /* "yesterday":{
        "date":"22日星期六", "high":"高温 24℃",
        "fx":"西北风", "low":"低温 13℃",
        "fl":"<![CDATA[3-4级]]>", "type":"晴"
    },*/

    private String date;
    private String high;
    private String fx;
    private String low;
    private String fl;
    private String type;
}