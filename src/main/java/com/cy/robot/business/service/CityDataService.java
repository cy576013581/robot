package com.cy.robot.business.service;

import com.cy.robot.business.entity.City;
import com.cy.robot.business.entity.CityList;
import com.cy.robot.util.XmlBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @description: 城市服务
 * @author: chenyang
 * @create: 2018-11-09
 **/
@Service
public class CityDataService{

    public List<City> listCity() throws Exception {
        //读取XML文件
        // http://mobile.weather.com.cn/js/citylist.xml
        Resource resource = new ClassPathResource("./support/citylist.xml");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream(), "utf-8"));
        StringBuffer stringBuffer = new StringBuffer();
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }
        bufferedReader.close();
        //XML转为Java对象
        CityList cityList = (CityList) XmlBuilder.xmlStrToObject(CityList.class, stringBuffer.toString());
        return cityList.getCityList();
    }
}
