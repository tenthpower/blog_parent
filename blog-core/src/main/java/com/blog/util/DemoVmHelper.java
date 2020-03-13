package com.blog.util;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

@Component
public class DemoVmHelper {

    /**
     * velocity编码
     */
    private static String VELOCITY_UTF_8 = "UTF-8";

    /**
     * vm文件根目录
     */
    private static String VM_ROOT = "vm";

    @Autowired
    private VelocityEngine velocityEngine;

    /**
     * vm文件后缀
     */
    private static String VM_SUFFIX = ".vm";

    public String execute(String tableName){
        String result = "";
        Template demoVm = velocityEngine.getTemplate(VM_ROOT + "/demo/demo" + VM_SUFFIX, VELOCITY_UTF_8);
        // 创建velocity上下文
        VelocityContext ctxA = new VelocityContext();
        ctxA.put("tableName", tableName);
        StringWriter sw = new StringWriter();
        demoVm.merge(ctxA, sw);
        result = sw.toString();
        return result;
    }
}
