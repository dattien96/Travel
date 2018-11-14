
package com.example.dattienbkhn.travel.model.weather.forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastObj {

    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private List<ForecastWrap> forecastWrap = null;
    @SerializedName("city")
    @Expose
    private CityForecast cityForecast;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public List<ForecastWrap> getForecastWrap() {
        return forecastWrap;
    }

    public void setForecastWrap(List<ForecastWrap> forecastWrap) {
        this.forecastWrap = forecastWrap;
    }

    public CityForecast getCityForecast() {
        return cityForecast;
    }

    public void setCityForecast(CityForecast cityForecast) {
        this.cityForecast = cityForecast;
    }

}
