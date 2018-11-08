package com.cy.robot.service;

import com.cy.robot.boost.Intent;
import com.cy.robot.carrier.*;
import com.cy.robot.config.ApplicationProperties;
import com.cy.robot.boost.Execute;
import com.cy.robot.tokenizer.Tokenizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 问答核心服务
 *
 * @author cheny
 */
@Slf4j
@Service
public class KernelService {

    @Autowired
    ApplicationProperties prop;

    @Autowired
    private Processor processor;

    @Autowired
    private Tokenizer tokenizer;

    @Autowired
    private Identifier identifier;

    // 回答服务的总入口
    public Answer reply(Question question){
        String input = StringUtils.trimWhitespace(question.getText());

        // 预处理，规范化输入
        String text = processor.normalize(input);
        // 分词
        List<Word> words = tokenizer.segment(text);
        words.forEach(t -> {
            log.debug("{}",t);
        });

        // 处理之后的文本
        text = words.stream().map(Word::getWord).collect(Collectors.joining());
        log.info("after processing :{}",text);

        // 识别实体
        String domain = identifier.getDomain(text);
        log.info("domain: {}",domain);

        if(null != domain){
            // 识别实体下的意图
            String intent = identifier.getIntent(text,domain);
            log.info("intent: {}",intent);
            if(null != intent){
                // 获取意图对应的执行器
                Execute execute = identifier.getExecute(intent);
                if(null != execute){
                    Optional<Intent> optionalIntent = execute.start(text,words);
                    if(optionalIntent.isPresent()){
                        Judge judge = execute.validate(optionalIntent.get());
                        if(judge.isFlag()){
                            return execute.apply(optionalIntent.get());
                        }else {
                            return new Answer(Code.UNFULL,judge.getText());
                        }
                    }
                }
            }
        }
        return new Answer(Code.UNABLE,"暂时无法回答您的问题！");
    }

}
