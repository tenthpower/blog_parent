package com.blog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@ConfigurationProperties(prefix = "demo", ignoreUnknownFields = true, ignoreInvalidFields= true)
@PropertySource("classpath:demoConfig.properties")
@RefreshScope
@Data
public class DemoConfig implements Serializable {

    private static final long serialVersionUID = 8924584812503000823L;

    private String name;

    private Integer age;

}
