package com.cy.robot.service;

import com.cy.robot.carrier.DataType;
import com.cy.robot.config.ApplicationProperties;
import com.cy.robot.boost.Execute;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 判断器（用于实体识别）
 *
 * @author cheny
 */
@Slf4j
@Service
public class Identifier {

    @Autowired
    private ApplicationProperties props;

    @Autowired
    private Processor processor;

    private WordVectorModel wordVectorModel;

    private DocVectorModel docVectorModel;

    private Map<String, String> regularMap = new LinkedHashMap<>();

    private Map<String, String> questionMap = new LinkedHashMap<>();

    private Map<String, Execute> executeMap = new LinkedHashMap<>();

    private List<String> regulars = new ArrayList<>();

    private List<String> questions = new ArrayList<>();

    @Autowired
    private List<Execute> executes;

    /**
     * 初始化。
     *
     * @throws IOException 当发生IO异常时
     */
    @PostConstruct
    public void initialize() throws IOException {
        loadFiles();

        log.debug("正则匹配实体：{}", regularMap);
        regulars = new ArrayList<>(regularMap.keySet());
        questions = new ArrayList<>(questionMap.keySet());

        log.debug("start load word2vec :{} ",props.getEmbedding().getFile());
        wordVectorModel = new WordVectorModel(props.getEmbedding().getFile());

        log.debug("docVectorModel load questions");
        docVectorModel = new DocVectorModel(wordVectorModel);
        for (int i = 0; i < questions.size(); i++) {
            String q = processor.normalize(questions.get(i));
            docVectorModel.addDocument(i, q);
        }

        for (Execute e : executes) {
            executeMap.put(e.getIntent(),e);
        }
    }

    private void loadFiles() throws IOException {
        for (String filename : props.getIdentifier().getFiles()) {
            loadFile(filename);
        }
    }

    private void loadFile(String filename) throws IOException {
        if (StringUtils.isNotBlank(filename)) {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            DataType dataType = DataType.DAMAIN;
            List<String> regularList = new ArrayList<>();
            String domain = null;
            String intent = null;
            for (String line : lines) {
                line = line.trim();

                //遇见注释跳过
                if (StringUtils.startsWith(line, "#")) {
                    continue;
                }
                if (StringUtils.startsWith(line, "-")) {
                    dataType = DataType.PATTERN;
                    continue;
                }else if (StringUtils.startsWith(line, "=")) {
                    dataType = DataType.INTENT;
                    continue;
                }else if (StringUtils.startsWith(line, "@")) {
                    dataType = DataType.QUESTION;
                    continue;
                }
                switch (dataType) {
                    case PATTERN:
                        regularList.add(line);
                        break;
                    case QUESTION:
                        if(null != intent){
                            questionMap.put(line, intent);
                        }
                        break;
                    case INTENT:
                        intent = line;

                        break;
                    default:
                        domain = line;
                        break;
                }
            }
            if (null != domain) {
                final String s = domain;
                regularList.forEach(p -> regularMap.put(p, s));
            }
        }
    }

    private List<String> regularMatches(String text) {
        List<String> relList = new ArrayList<>();
        for (String r : regulars) {
            log.debug("{} 试图匹配 {}", text, r);
            if (text.matches(r)) {
                log.info("{} 匹配 {}", text, r);
                relList.add(regularMap.get(r));
            }
        }
        return relList;
    }

    public String getDomain(String text) {
        if (props.getIdentifier().isRegularMatching()) {
            List<String> s = regularMatches(text);
            String rel = reinspection(s,text);
            if (null != rel) {
                return rel;
            }
        }
        return null;
    }

    // 再次确认实体
    private String reinspection(List<String> relList,String text){
        if (null == relList || relList.size() == 0){
            return null;
        }else if (relList.size() == 1){
            return relList.get(0);
        }else{
            // 走文本分类逻辑
            // 待补充
            return relList.get(0);
        }
    }

    public String getIntent(String text,String domain) {
        if (props.getIdentifier().isRegularMatching()) {
            List<Map.Entry<Integer, Float>> list = docVectorModel.nearest(text);
            if (list.size() > 0) {
                log.debug("{} 语义匹配：", text);
                for (Map.Entry<Integer, Float> entry : list) {
                    log.debug("{} {}", questions.get(entry.getKey()), entry.getValue());
                }
                for (Map.Entry<Integer, Float> m :list){
                    if (m.getValue() > props.getIdentifier().getSimilarThreshold()
                            || domain.equals(questions.get(m.getKey()))) {
                        String q = questions.get(m.getKey());
                        log.info("{} 语义匹配 {}", text, q);
                        return questionMap.get(q);
                    }
                }
            }
        }
        return null;
    }

    public Execute getExecute(String intent){
        return executeMap.get(intent);
    }
}
