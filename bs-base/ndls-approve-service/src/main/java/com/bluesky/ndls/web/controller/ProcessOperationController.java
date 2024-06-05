package com.bluesky.ndls.web.controller;

import com.bluesky.ndls.web.RespMessage;
import com.bluesky.ndls.web.service.ProcessService;
import com.sun.istack.internal.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @ClassName ProcessOperationController
 * @Description TO DO
 * @Author lenovo
 * @Date 2024/5/19
 * @Version 1.0
 **/
@RestController
@RequestMapping("/process")
public class ProcessOperationController {
    @Resource
    ProcessService processService;

    /**
     * 定义创建相关流程
     */
    @PostMapping("/deploy")
    public RespMessage<String> deployProcessDefine(@RequestParam("processDef")MultipartFile processDef,
                                                   @RequestParam("processName") @Validated @NotNull String processName) {
        return processService.processUploadAndDeploy(processDef, processName);
    }

    /**
     * 获取流程定义图示
     *
     * @Author huangyu2019
     * @Date 21:27 2024/5/19
     * @Param
     * @return
     **/
    @GetMapping("/getDefGraph")
    public void getDefGraph(@RequestParam("processDefId") String processDefId,
                            @RequestParam("processDefImg") String processDefImg) {
        processService.getDefGraph(processDefId, processDefImg);
    }
}
