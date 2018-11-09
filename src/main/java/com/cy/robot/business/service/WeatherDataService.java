package com.cy.robot.business.service;

import com.cy.robot.business.entity.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherDataService {
    private static final String WEATHER_URI = "http://wthrcdn.etouch.cn/weather_mini?";

    @Autowired
    private RestTemplate restTemplate;

    private static final long TIME_OUT = 10L;

    public WeatherResponse getDataByCityId(String cityId) {
        String uri = WEATHER_URI + "citykey=" + cityId;
        return this.getWeatherResponse(uri);
    }


    public WeatherResponse getDataByCityName(String cityName) {
        String uri = WEATHER_URI + "city=" + cityName;
        return this.getWeatherResponse(uri);
    }

    private WeatherResponse getWeatherResponse(String uri) {
        String key = uri;
        String strBody = null;
        WeatherResponse resp = null;
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);
        if (respString.getStatusCodeValue() == 200) {
            strBody = respString.getBody();
        }
        try {
            resp = objectMapper.readValue(strBody, WeatherResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }
}
