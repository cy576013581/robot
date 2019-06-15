package com.cy.robot.boost;

import com.cy.robot.config.ApplicationProperties;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Order(10000)
public abstract class AbstractExecute<T extends Intent> implements Execute<T> {

    protected String intent;

    protected String domain;

    protected SpringTemplateEngine templateEngine;

    protected ApplicationProperties props;

    protected List<String> templates = new ArrayList<>();

    public AbstractExecute(String domain,
                           String intent,
                           SpringTemplateEngine templateEngine,
                           ApplicationProperties props) {
        this.domain = domain;
        this.intent = intent;
        this.templateEngine = templateEngine;
        this.props = props;
    }

    @Override
    public String getIntent() {
        return intent;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    /**
     * 初始化。
     */
    @PostConstruct
    public void initialize() {
        templates.add("answers/" + intent.toLowerCase() +".txt");
    }

    /**
     * 应用模版合成回答。
     *
     * @param map 数据
     * @return 回答字符串
     */
    protected String applyTemplate(Map<String, Object> map) {

        String templateName = templates.get(RandomUtils.nextInt(0, templates.size()));

        Context ctx = new Context();
        ctx.setVariables(map);
        return StringUtils.trimToEmpty(templateEngine.process(templateName, ctx));
    }
}
