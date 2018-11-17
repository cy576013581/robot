package com.cy.robot.time;

import com.cy.robot.config.ApplicationProperties;
import com.cy.robot.time.nlp.TimeNormalizer;
import com.cy.robot.time.nlp.TimeUnit;
import com.cy.robot.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
public class DateService {

    @Autowired
    private ApplicationProperties props;

    private TimeNormalizer normalizer;

    @PostConstruct
    public void init() {
        normalizer = new TimeNormalizer(props.getDateModel());
    }

    public String parse(String text){
        normalizer.parse(text);// 抽取时间
        TimeUnit[] unit = normalizer.getTimeUnit();
        return DateUtil.formatDate(unit[0].getTime());
    }

}
