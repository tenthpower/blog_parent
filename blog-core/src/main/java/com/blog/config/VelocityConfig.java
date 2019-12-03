package com.blog.config;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述:velocity引擎初始化
 * @version:1.0.0
 * @author:和彦鹏
 * @date:2019年10月24日
 */
@Configuration
public class VelocityConfig {

    /**
     * 初始化velocity引擎
     */
    @Bean
    public VelocityEngine initVelocity() {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
        return velocityEngine;
    }
}
