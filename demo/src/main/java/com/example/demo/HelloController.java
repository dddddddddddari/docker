package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.redis.core.StringRedisTemplate;

@RestController
public class HelloController {

    private final static Logger LOGGER = LoggerFactory.getLogger(HelloController.class);
    int counter=0;
    private String keyCount="count";
    private Long count=0L;

    @Autowired
    private StringRedisTemplate template;

    @GetMapping("/helloworld")
    public String helloworld() {
        try {
            ValueOperations<String, String> ops = this.template.opsForValue();
            count = ops.increment(keyCount);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return count.toString();
    }

    @GetMapping("/hello")
    int hello() throws InterruptedException {
        Thread.sleep(200);
        ++counter;
         LOGGER.info("Counter: {}", counter);
         return counter;
    }
}

