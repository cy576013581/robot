package com.cy.robot.domain;

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

    @Data
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

    @Data
    public class Forecast implements Serializable {

 /*   "forecast":[{
        "date":"23日星期天", "high":"高温 24℃",
        "fengli":"<![CDATA[4-5级]]>",
        "low":"低温 12℃", "fengxiang":"北风", "type":"晴"
    },{
        "date":"24日星期一", "high":"高温 23℃",
         "fengli":"<![CDATA[<3级]]>", "low":"低温 11℃",
         "fengxiang":"无持续风向", "type": "晴"
    },{
        "date":"25日星期二", "high":"高温 23℃",
        "fengli":"<![CDATA[<3级]]>",
        "low":"低温 13℃", "fengxiang":"南风", "type":"多云"
    },{
        "date":"26日星期三", "high":"高温 22℃",
         "fengli":"<![CDATA[<3级]]>",
          "low":"低温 14℃", "fengxiang":"南风", "type":"多云"
    },{
        "date":"27日星期四", "high":"高温 22℃",
        "fengli":"<![CDATA[3-4级]]>", "low":"低温 14℃",
        "fengxiang":"南风", "type":"阴"
    }]*/

        private String date;
        private String high;
        private String fengli;
        private String low;
        private String fengxiang;
        private String type;
    }
}
