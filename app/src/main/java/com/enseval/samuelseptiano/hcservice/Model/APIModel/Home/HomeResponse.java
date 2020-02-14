package com.enseval.samuelseptiano.hcservice.Model.APIModel.Home;

import com.enseval.samuelseptiano.hcservice.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeResponse extends BaseResponse {

    public HomeResponse(List<Home> events) {
        this.events = events;
    }

    public List<Home> getEvents() {
        return events;
    }

    public void setEvents(List<Home> events) {
        this.events = events;
    }

    @SerializedName("events")
    private List<Home> events;


}
