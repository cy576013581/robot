package com.cy.robot.service;

import com.cy.robot.config.ApplicationProperties;
import com.cy.robot.util.NormalizeUtil;
import com.hankcs.hanlp.HanLP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ApplicationProperties properties;

    /**
     * 输入处理
     */
    public String normalize(String text) {

        if (properties.getProcessor().isTransfoHalfWidth()) {
            text = NormalizeUtil.toHalfWidth(text);
        }else{
            text = NormalizeUtil.punctuatioToFullWidth(text);
        }

        if (properties.getProcessor().isIgnoreCase()) {
            text = text.toLowerCase();
        }

        if (properties.getProcessor().isTrimWhitespace()) {
            text = StringUtils.trimAllWhitespace(text);
        }

        if (properties.getProcessor().isTransfoSimplified()) {
            text = HanLP.convertToSimplifiedChinese(text);

        }

        text = NormalizeUtil.normalizePunctuation(text);

        log.debug("normalize:{}",text);

        return text;
    }
}
