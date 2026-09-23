package com.kaueadriano.novici.dto;

import lombok.Data;

@Data
public class ErrorMessage {
    private String text;

    public ErrorMessage(String text){
        this.text = text;
    }
}
