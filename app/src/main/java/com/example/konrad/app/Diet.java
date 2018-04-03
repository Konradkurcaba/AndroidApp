package com.example.konrad.app;

public class Diet {

    private String title;
    private String summary;
    private String desctiption;

    public String getDesctiption() {
        return desctiption;
    }

    public void setDesctiption(String desctiption) {
        this.desctiption = desctiption;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Diet(String title, String summary, String desctiption) {
        this.title = title;
        this.summary = summary;
        this.desctiption = desctiption;

    }









}
