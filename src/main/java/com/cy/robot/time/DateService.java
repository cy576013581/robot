package com.cy.robot.time;

import com.cy.robot.config.ApplicationProperties;
import com.cy.robot.time.nlp.TimeNormalizer;
import com.cy.robot.time.nlp.TimeUnit;
import com.cy.robot.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DateService {

    private final ApplicationProperties props;

    private TimeNormalizer normalizer;

    public DateService(ApplicationProperties props) {
        this.props = props;
    }

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
