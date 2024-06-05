package com.bluesky.ndls.web.service.impl;

import com.bluesky.ndls.web.RespMessage;
import com.bluesky.ndls.web.service.ProcessService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.zip.ZipInputStream;

/**
 * @ClassName ProcessServiceImpl
 * @Description TO DO
 * @Author lenovo
 * @Date 2024/5/19
 * @Version 1.0
 **/
@Slf4j
@Service
public class ProcessServiceImpl implements ProcessService {
    @Resource
    RepositoryService repositoryService;

    public RespMessage<String> processUploadAndDeploy(MultipartFile processDef, String processName) {
        RespMessage<String> resp = new RespMessage<String>();
        String originalFilename = processDef.getOriginalFilename();
        if (!originalFilename.endsWith("zip")) {
            resp.setCode("100001");
            resp.setDesc("请上传合法的zip格式的流程定义文件");
            return resp;
        }
        ProcessDefinition processDefinition = null;
        try {
            ZipInputStream zipInputStream = new ZipInputStream(processDef.getInputStream());
            Deployment deployment = repositoryService.createDeployment().addZipInputStream(zipInputStream).name(processName).deploy();
            processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        } catch (IOException e) {
            log.error("流程部署失败！原因: {}", e.getMessage(), e);
        }
        resp.setCode("000000");
        resp.setDesc(String.format(Locale.CHINESE,"流程%s-%s部署成功", processName, processDefinition.getId()));
        return resp;
    }

    public void getDefGraph(String processDefId, String processDefImg) {
        HttpServletResponse response = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();
        InputStream processStream = repositoryService.getResourceAsStream(processDefId, processDefImg);
        try {
            IOUtils.copy(processStream, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(processStream);
        }
    }
}
