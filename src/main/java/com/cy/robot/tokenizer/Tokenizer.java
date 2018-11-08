package com.cy.robot.tokenizer;

import com.cy.robot.carrier.Word;

import java.util.List;

/**
 * 分词器
 *
 * @author cheny
 */
public interface Tokenizer {
    List<Word> segment(String text);
}
