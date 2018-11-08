package com.cy.robot.carrier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    //    操作代码
    @NonNull
    Code code;

    @NonNull
    String text;
}
