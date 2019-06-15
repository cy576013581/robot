package com.cy.robot.boost;

import com.cy.robot.carrier.Answer;
import com.cy.robot.carrier.Judge;
import com.cy.robot.carrier.Word;

import java.util.List;
import java.util.Optional;

public interface Execute<T extends Intent> {

    /**
     * 对应的意图
     */
    String getIntent();

    /**
     * 对应的实体
     */
    String getDomain();

    /**
     * 根据问题开始话题。
     *
     * @param text 输入的语句
     * @param words 分词结果
     */
    Optional<T> start(String text, List<Word> words);

    /**
     * 检查话题要素是否完整
     *
     * @param intent 意图
     */
    Judge validate(T intent);

    /**
     * 处理话题（执行话题对应的查询）。
     *
     * @param intent 意图
     * @return 答案
     */
    Answer apply(T intent);
}
