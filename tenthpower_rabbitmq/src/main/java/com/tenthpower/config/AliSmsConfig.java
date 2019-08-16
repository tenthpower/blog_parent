package com.tenthpower.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@ConfigurationProperties(prefix = "sms", ignoreUnknownFields = true, ignoreInvalidFields= true)
@PropertySource("classpath:aliSmsConfig.properties")
@RefreshScope
@Data
public class AliSmsConfig implements Serializable {

    private static final long serialVersionUID = 8924584812503000823L;

    private String accessKeyId;

    private String accessKeySecret;

    private String templateCode;

    private String signName;

}
