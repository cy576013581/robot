package com.cy.robot.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.StringReader;

/**
 * @description: Xml工具
 * @author: chenyang
 * @create: 2018-11-09
 **/
public class XmlBuilder {
    /**
     * 将XML转为指定的POJO
     *
     * @param clazz
     * @param xmlStr
     * @return
     */
    public static Object xmlStrToObject(Class<?> clazz, String xmlStr) throws Exception {

        Object xmlObject = null;
        Reader reader = null;
        JAXBContext context = JAXBContext.newInstance(clazz);

        //XML转为对象的接口
        Unmarshaller unmarshaller = context.createUnmarshaller();
        reader = new StringReader(xmlStr);
        xmlObject = unmarshaller.unmarshal(reader);

        if (null != reader) {
            reader.close();
        }
        return xmlObject;
    }

}
