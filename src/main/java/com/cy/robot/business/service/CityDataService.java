package com.cy.robot.business.service;

import com.cy.robot.business.entity.City;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 城市服务
 * @author: chenyang
 * @create: 2018-11-09
 **/
@Service
public class CityDataService {

    public Map<String, City> mapCode;

    @PostConstruct
    private void init() throws Exception {
        //读取XML文件
        // http://mobile.weather.com.cn/js/citylist.xml
        String src = String.format("%s/support/city/city.json", System.getProperty("user.dir"));
        String text = Files.lines(Paths.get(src)).collect(Collectors.joining(""));
        ObjectMapper m = new ObjectMapper();
        mapCode = new HashMap<>();
        City[] cl =  m.readValue(text,City[].class);
        Arrays.stream(cl).forEach(c ->  mapCode.put(c.getName(), c));
    }

    // 根据城市名称获取代码
    public String getCityCode(String cityName) {
        return mapCode.get(cityName).getCode();
    }

}
