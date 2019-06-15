package com.cy.robot.carrier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    private Code code;

    /**
     * 实体
     */
    private String domain;

    /**
     * 意图
     */
    private String intent;

    /**
     * 答案文本
     */
    private String text;
}
