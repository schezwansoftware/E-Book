package com.codesetters.ebook.service.dto;

import java.io.Serializable;

public class StateDTO implements Serializable{
    private String statename;
    private String countrycode;

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }
}
