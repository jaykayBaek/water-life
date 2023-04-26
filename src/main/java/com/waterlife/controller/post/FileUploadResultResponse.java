package com.waterlife.controller.post;


import com.waterlife.controller.ResultResponse;
import lombok.Getter;

@Getter
public class FileUploadResultResponse extends ResultResponse {
    private final String URL;

    public FileUploadResultResponse(String CODE, String MESSAGE, boolean SUCCESS, String url) {
        super(CODE, MESSAGE, SUCCESS);
        this.URL = url;
    }
}
