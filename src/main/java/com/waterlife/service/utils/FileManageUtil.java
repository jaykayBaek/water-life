package com.waterlife.service.utils;

import com.waterlife.consts.PathConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class FileManageUtil {
    
    public boolean deleteImageInContent(String content){
        List<String> images = extractImageFileName(content);
        if(images.size() == 0){
            return false;
        }

        for (String image : images) {
            File deleteFile = new File(PathConst.IMAGE_SAVE_PATH+image);
            deleteFile.delete();
        }
        return true;
    }
    
    public boolean deleteImagesAccordingToComparison(String existContent, String newContent) {
        List<String> existImages = extractImageFileName(existContent);
        List<String> newImages = extractImageFileName(newContent);

        if(existImages.size() == 0){
            return false;
        }

        for (String existImage : existImages) {
            if (!newImages.contains(existImage)) {
                File deleteFile = new File(PathConst.IMAGE_SAVE_PATH+existImage);
                deleteFile.delete();
            }
        }
        return true;
    }
    private static List<String> extractImageFileName(String content) {
        List<String> fileNames = new ArrayList<>();
        String regex = "<img\\s+[^>]*src=\"\\/posts\\/image\\/([^_]+_[^\\.]+\\.\\w+)\"[^>]*>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            fileNames.add(matcher.group(1));
        }

        return fileNames;
    }
}
