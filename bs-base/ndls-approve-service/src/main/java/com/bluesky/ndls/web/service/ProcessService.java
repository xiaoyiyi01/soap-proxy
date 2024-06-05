package com.bluesky.ndls.web.service;

import com.bluesky.ndls.web.RespMessage;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName ProcessService
 * @Description TO DO
 * @Author lenovo
 * @Date 2024/5/19
 * @Version 1.0
 **/
public interface ProcessService {
    RespMessage<String>  processUploadAndDeploy(MultipartFile processDef, String processName);

    void getDefGraph(String processDefId,String processDefImg);
}
