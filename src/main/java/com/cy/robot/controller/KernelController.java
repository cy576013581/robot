package com.cy.robot.controller;

import com.cy.robot.carrier.Code;
import com.cy.robot.carrier.Answer;
import com.cy.robot.carrier.Question;
import com.cy.robot.carrier.Result;
import com.cy.robot.service.KernelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 问答核心控制器
 *
 * @author cheny
 */
@Slf4j
@RestController
@RequestMapping("/sys/robot")
public class KernelController {

    @Autowired
    private KernelService kernelService;

    /*
     * 问答总入口
     */
    @RequestMapping
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Result<String> reply(@RequestBody Question question){
        log.debug("question :{}",question.getText());
        Answer answer = kernelService.reply(question);
        return new Result(answer.getCode(),answer.getText());
    }

}
