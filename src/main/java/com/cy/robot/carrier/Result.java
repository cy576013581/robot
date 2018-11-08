package com.cy.robot.carrier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    //    操作代码
    Code code;

    //    返回数据
    T data;

}