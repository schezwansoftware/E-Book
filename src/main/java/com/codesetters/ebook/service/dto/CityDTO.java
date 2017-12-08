package com.codesetters.ebook.service.dto;

import java.io.Serializable;

public class CityDTO implements Serializable{
    private String cityname;

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
}
