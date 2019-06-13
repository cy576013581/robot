package com.cy.robot.service;

import com.cy.robot.config.ApplicationProperties;
import com.cy.robot.util.NormalizeUtil;
import com.hankcs.hanlp.HanLP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 字符处理器
 *
 * @author cheny
 */
@Slf4j
@Component
public class Processor {

    private final ApplicationProperties props;

    public Processor(ApplicationProperties props) {
        this.props = props;
    }

    /**
     * 输入处理
     */
    public String normalize(String text) {

        if (props.getProcessor().isTransfoHalfWidth()) {
            text = NormalizeUtil.toHalfWidth(text);
        }else{
            text = NormalizeUtil.punctuatioToFullWidth(text);
        }

        if (props.getProcessor().isIgnoreCase()) {
            text = text.toLowerCase();
        }

        if (props.getProcessor().isTrimWhitespace()) {
            text = StringUtils.trimAllWhitespace(text);
        }

        if (props.getProcessor().isTransfoSimplified()) {
            text = HanLP.convertToSimplifiedChinese(text);

        }

        text = NormalizeUtil.normalizePunctuation(text);

        log.debug("-:{}",text);

        return text;
    }
}
