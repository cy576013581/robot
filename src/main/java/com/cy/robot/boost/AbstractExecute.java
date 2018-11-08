package com.cy.robot.boost;

import com.cy.robot.config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

@Order(10000)
public abstract class AbstractExecute<T extends Intent> implements Execute<T> {

    protected String intent;

    protected String domain;

    @Autowired
    protected ApplicationProperties props;

    public AbstractExecute(String domain,String intent){
        this.domain = domain;
        this.intent = intent;
        this.props = props;
    }

    @Override
    public String getIntent() {
        return intent;
    }

}
