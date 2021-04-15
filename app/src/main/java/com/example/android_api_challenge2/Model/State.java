package com.example.android_api_challenge2.Model;

import java.io.Serializable;
import java.util.List;

public class State implements Serializable {

    private String name;
    private List<String> borders = null;
    private String nativeName;
    private String flag;

    public State (String name , String nativeName)
    {
        this.name = name;
        this.nativeName = nativeName;
    }

    public State(String name, List<String> borders, String nativeName , String flag) {
        this.name = name;
        this.borders = borders;
        this.nativeName = nativeName;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public List<String> getBorders() {
        return borders;
    }

    public String getNativeName() {
        return nativeName;
    }

    public String getFlag(){return flag;}
}
