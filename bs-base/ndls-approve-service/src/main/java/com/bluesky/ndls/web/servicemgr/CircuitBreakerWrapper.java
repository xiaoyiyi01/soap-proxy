package com.bluesky.ndls.web.servicemgr;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 动态设置熔断规则
 * 根据每个请求中的请求头获取熔断规则动态的开启熔断并设置熔断
 * 返回特定的降级
 * 调用resilience的熔断设置接口动态设置
 *
 * @Date 2024/6/1
 **/
@Slf4j
@Component
public class CircuitBreakerWrapper {
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @PostConstruct
    public void setCircuitBreakerRegistry(){
        /**
         * 如果每个熔断实例的熔断配置都不一样时，可以把配置放到数据库或者其他持久化中间件里，
         * 然后在下面进行 for 循环创建CircuitBreakerConfig实例
         * 再放到 HashMap中，CircuitBreakerRegistry可以使用配置map进行注册
         * configMap中的key为每个熔断实例对应的名字即可区分
         **/
        Map<String, CircuitBreakerConfig> configMap = new HashMap<>();
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig
                .custom()
                .automaticTransitionFromOpenToHalfOpenEnabled(false) // 默认就是false
                .failureRateThreshold(80) // 异常熔断百分比阈值
                .minimumNumberOfCalls(10) // 熔断的最小请求数，即请求数小于这个值时，即使之前的所有请求都异常也不会熔断
                .slidingWindowSize(20) // 默认是COUNT_BASE 模式， 滑动窗口大小
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED) // 还有TIME_BASE
                .recordExceptions(Exception.class) // 统计熔断的异常类型
                .permittedNumberOfCallsInHalfOpenState(2) // 熔断状态【半开】时允许通过的请求数，如果通过的请求都成功了则恢复关闭状态，否则继续打开
                .waitDurationInOpenState(Duration.ofMillis(3000)) // 熔断器由打开状态转半开状态的时间间隔
                .build();
        configMap.put("interface-1", circuitBreakerConfig);
        circuitBreakerRegistry = CircuitBreakerRegistry.of(configMap);
    }

    public Optional<Object> execute(String circuitBreakerkey, CheckedFunction0<Object> biz) {
        try {
            CheckedFunction0<Object> checkedFunction0 = circuitBreakerRegistry
                    .circuitBreaker(circuitBreakerkey)
                    .decorateCheckedSupplier(biz);
            // 熔断需要记录的异常都在mapTry中做了捕获处理，记录完之后会再抛出Throwable
            return Optional.ofNullable(Try.of(checkedFunction0).mapTry(bizRes -> bizRes).get());
        } catch (Throwable e) {
            log.error("execute biz error ", e);
        }
        return Optional.empty();
    }
}
