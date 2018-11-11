package com.cy.robot.business.service;

import com.cy.robot.business.entity.City;
import com.cy.robot.business.entity.CityList;
import com.cy.robot.util.XmlBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * @description: 城市服务
 * @author: chenyang
 * @create: 2018-11-09
 **/
@Service
public class CityDataService {

    public Map<String, String> mapCode;

    @PostConstruct
    private void init() throws Exception {
        //读取XML文件
        // http://mobile.weather.com.cn/js/citylist.xml
        String src = String.format("%s/support/city/citylist.xml", System.getProperty("user.dir"));
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(src), "utf-8"));
        StringBuffer stringBuffer = new StringBuffer();
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }
        bufferedReader.close();
        //XML转为Java对象
        CityList cl = (CityList) XmlBuilder.xmlStrToObject(CityList.class, stringBuffer.toString());
        cl.getCityList().forEach(c -> mapCode.put(c.getCityName(), c.getCityCode()));
    }

    // 根据城市名称获取代码
    public String getCityCode(String cityName) {
        return mapCode.get(cityName);
    }

}
