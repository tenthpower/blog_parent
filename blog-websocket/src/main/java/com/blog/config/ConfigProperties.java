package com.blog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@ConfigurationProperties(prefix = "config",ignoreUnknownFields = true, ignoreInvalidFields= true)
@PropertySource("classpath:config.properties")
@Data
public class ConfigProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fileBaseUrl;

}
