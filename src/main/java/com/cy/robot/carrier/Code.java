package com.cy.robot.carrier;

public enum Code {
    SUCCESS("100"),
    UNFULL("200"),
    UNABLE("300"),
    ERROR("400");

    private String code;
    // 构造方法
    private Code(String code) {
        this.code = code;
    }

}
