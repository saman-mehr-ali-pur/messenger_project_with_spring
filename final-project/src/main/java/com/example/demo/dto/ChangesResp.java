package com.example.demo.dto;

import com.example.demo.model.Chat;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ChangesResp extends Chat {

    public ChangesResp(Chat chat){
        super(chat);
    }

    @JsonProperty("status")
    private boolean isChanged;

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }
}
