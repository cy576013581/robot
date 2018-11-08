package com.cy.robot.tokenizer;

import com.cy.robot.carrier.Word;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HanlpTokenizer implements Tokenizer {

    public final Segment seg = HanLP.newSegment().enableCustomDictionary(true).enableOffset(true);

    @Override
    public List<Word> segment(String text) {
        List<Term> terms = seg.seg(text);
        return terms
                .stream()
                .map(t -> new Word(t.word, t.nature.toString(), t.offset))
                .collect(Collectors.toList());
    }

}
