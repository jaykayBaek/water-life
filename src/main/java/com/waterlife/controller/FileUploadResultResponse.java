package com.waterlife.controller;


import lombok.Getter;

@Getter
public class FileUploadResultResponse extends ResultResponse {
    private final String URL;

    public FileUploadResultResponse(String CODE, String MESSAGE, boolean SUCCESS, String url) {
        super(CODE, MESSAGE, SUCCESS);
        this.URL = url;
    }
}
