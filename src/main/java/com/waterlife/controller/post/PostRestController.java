package com.waterlife.controller.post;

import com.waterlife.service.utils.WysiwygImageUploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostRestController {
    private final WysiwygImageUploadUtil uploadUtil;

    @ResponseBody
    @PostMapping("/upload-image")
    public ResponseEntity<FileUploadResultResponse> uploadImage(@RequestParam(name = "file") MultipartFile file,
                                                                HttpServletRequest request) {

        String responseUrl = uploadUtil.uploadImageReturnUrl(file);

        FileUploadResultResponse resultResponse = new FileUploadResultResponse(
                HttpStatus.OK.toString(), "파일 업로드 완료", true,
                responseUrl
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultResponse);
    }

}
