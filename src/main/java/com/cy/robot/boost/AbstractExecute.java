package com.cy.robot.boost;

import com.cy.robot.config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

@Order(10000)
public abstract class AbstractExecute<T extends Intent> implements Execute<T> {

    protected String INTENT;

    protected String DOMAIN;

    @Autowired
    protected ApplicationProperties props;

    public AbstractExecute(String DOMAIN,String INTENT){
        this.DOMAIN = DOMAIN;
        this.INTENT = INTENT;
    }

    @Override
    public String getIntent() {
        return INTENT;
    }

}
