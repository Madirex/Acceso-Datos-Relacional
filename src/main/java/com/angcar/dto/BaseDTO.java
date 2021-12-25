package com.angcar.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class BaseDTO {

    public String toJSON() {
        final Gson prettyGson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return prettyGson.toJson(this);
    }
}
