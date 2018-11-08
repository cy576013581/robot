package com.cy.robot.carrier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Judge {

    // 验证结果
    boolean flag;

    // 验证提示语言
    String text;
}
