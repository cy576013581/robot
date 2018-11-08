package com.cy.robot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "robot")
public class ApplicationProperties {

    // 调试模式
    private boolean debug = true;

    // 词向量文件
    private final Embedding embedding = new Embedding();

    // 处理器配置
    private final Processor processor = new Processor();

    // 实体识别器配置
    private final Identifier identifier = new Identifier();

    @Data
    public static class Embedding {

        private String file = "support/embedding/word2vec.txt";
    }

    @Data
    public static class Processor {

        // 英文忽略大小写
        private boolean ignoreCase = true;

        // 去除中文的空格
        private boolean trimWhitespace = true;

        // 中文全部转换为简体
        private boolean transfoSimplified = true;

        // 标点符号是否是半角(false为全角)
        private boolean transfoHalfWidth = true;

    }

    @Data
    public static class Identifier {

        // 正则匹配
        private boolean regularMatching = true;

        // 相似度匹配
        private boolean similarMatching = true;

        // 相似度匹配
        private double similarThreshold = 0.8f;

        // 问题文件列表
        private List<String> files;

    }
}
