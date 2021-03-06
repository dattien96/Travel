
package com.example.dattienbkhn.travel.model.weather.forecast;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ForecastWrap {

    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("rain")
    @Expose
    private Rain rain;
    @SerializedName("sys")
    @Expose
    private Sys sys;
    @SerializedName("dt_txt")
    @Expose
    private String dtTxt;

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public java.util.List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<Weather> weather) {
        this.weather = weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }

    public String getNameOfDayInWeek() {
        int day;
        int mon;
        int year;

        year = Integer.parseInt(dtTxt.substring(0, dtTxt.indexOf("-")));
        dtTxt = dtTxt.substring(dtTxt.indexOf("-") + 1);
        mon = Integer.parseInt(dtTxt.substring(0, dtTxt.indexOf("-")));
        dtTxt = dtTxt.substring(dtTxt.indexOf("-") + 1);
        day = Integer.parseInt(dtTxt.substring(0, dtTxt.indexOf(" ")));


        Date date = new Date(year - 1900, mon - 1, day);

        return "" + date.toString().substring(0, date.toString().indexOf(" "));
    }

    public String getStringTime() {
        String tmp = dtTxt.substring(dtTxt.indexOf(" ")+1);

        return ""+tmp.substring(0,tmp.lastIndexOf(":"));
    }
}
