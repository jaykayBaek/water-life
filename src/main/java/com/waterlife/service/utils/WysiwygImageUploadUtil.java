package com.waterlife.service.utils;

import com.waterlife.consts.PathConst;
import com.waterlife.exception.post.PostException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class WysiwygImageUploadUtil {

    @Value("${file.dir}")
    private String fileDir;

    public String uploadImageReturnUrl(MultipartFile file) {
        String savedFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String savedPath = fileDir + savedFileName;
        String responseUrl = PathConst.IMAGE_PATH_RESPONSE + savedFileName;

        try {
            file.transferTo(new File(savedPath));
        } catch (IOException e) {
            throw new PostException(e.getMessage());
        }
        return responseUrl;
    }
}
