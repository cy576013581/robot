package com.cy.robot.boost;

import com.cy.robot.carrier.Answer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public abstract class Intent {

    /**
     * 实体
     */
    protected String domain;

    /**
     * 意图
     */
    protected String intent;

    /**
     * 输入的文本
     */
    @JsonIgnore
    protected String text;

    /**
     * 答案
     */
    @JsonIgnore
    protected Answer answer;

    public Intent(){

    }

    public Intent(String intent){
        this.intent = intent;
    }

}
