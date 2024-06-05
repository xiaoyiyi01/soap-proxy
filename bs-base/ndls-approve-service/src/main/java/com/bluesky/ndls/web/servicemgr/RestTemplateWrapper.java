package com.bluesky.ndls.web.servicemgr;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * <p>
 *  RestTemplate 增强，包装CircuitBreaker, 日志记录等功能
 * </p>
 * @author lenovo
 * @since 2024/6/5
 **/
@Slf4j
public class RestTemplateWrapper {
    /**
     * recordFunc回调中的Map参数中可以获取的key——startAt
     */
    public static final String START_AT = "startAt";

    /**
     * recordFunc回调中的Map参数中可以获取的key——endAt
     */
    public static final String END_AT = "endAt";

    /**
     * recordFunc回调中的Map参数中可以获取的key——totalCost
     */
    public static final String TOTAL_COST = "totalCost";

    /**
     * recordFunc回调中的Map参数中可以获取的key——respErrMsg
     */
    public static final String RESP_ERR_MSG = "respErrMsg";

    /**
     * recordFunc回调中的Map参数中可以获取的key——defaultResp
     */
    public static final String DEFAULT_RESPONSE_ENTITY = "defaultResp";

    private boolean isCircuitBreak = false;

    private String circuitBreakEntryName;

    private RestTemplate restTemplate;

    private Consumer<Map<String, Object>> recordFunc;

    private RestTemplateWrapper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static RestTemplateWrapper getInstance(RestTemplate restTemplate) {
        return new RestTemplateWrapper(restTemplate);
    }

    public void setCircuitBreakEntryName(String circuitBreakEntryName) {
        this.circuitBreakEntryName = circuitBreakEntryName;
    }

    public void setCircuitBreak(boolean isCircuitBreak) {
        this.isCircuitBreak = isCircuitBreak;
    }

    private <T> ResponseEntity<T> circuitBreakPostEntity(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables)
            throws BlockException, RestClientException {
        Entry entry = null;
        try{
            entry = SphU.entry(circuitBreakEntryName);
            log.debug("circuitBreak wrapper has been executed.");
            return restTemplate.postForEntity(url, request, responseType, uriVariables);
        } catch (RestClientException e) {
            // 此处只捕获业务异常进行熔断统计,统计后继续往外抛出异常由上层进行处理
            Tracer.traceEntry(e, entry);
            throw e;
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }

    public <T> ResponseEntity<T> recordLogPostEntity(T defaultRsp, String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) {
        // additional log msg for record
        Map<String, Object> additionalInfo = new HashMap<>();

        // return empty body ResponseEntity by default
        ResponseEntity<T> defaultResp = new ResponseEntity<>(defaultRsp, HttpStatus.OK);
        long start = System.currentTimeMillis();
        try {
            if (isCircuitBreak) {
                defaultResp = circuitBreakPostEntity(url, request, responseType, uriVariables);
            } else {
                defaultResp = restTemplate.postForEntity(url, request, responseType, uriVariables);
            }
        } catch (Throwable e) {
            log.error("post request {} error", url, e);
            additionalInfo.put(RESP_ERR_MSG, e.getMessage());
        } finally {
            long end = System.currentTimeMillis();
            long cost = end - start;
            log.info("recordLogPostEntity cost [{}] ms", cost);
            additionalInfo.put(START_AT, start);
            additionalInfo.put(END_AT, end);
            additionalInfo.put(TOTAL_COST, cost);
            additionalInfo.put(DEFAULT_RESPONSE_ENTITY, defaultResp);
            try {
                recordFunc.accept(additionalInfo);
            } catch (Throwable e) {
                log.error("execute record log callback error: ", e);
            }
        }

        return defaultResp;
    }

    public void registerRecordLogCallBack(Consumer<Map<String, Object>> recordFunc) {
        this.recordFunc = recordFunc;
    }
}
