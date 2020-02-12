package com.feathercompany.www;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Boletim {
    private String label;
    private int ano;
    private int boletimId;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getBoletimId() {
        return boletimId;
    }

    public void setBoletimId(int boletimId) {
        this.boletimId = boletimId;
    }
}
