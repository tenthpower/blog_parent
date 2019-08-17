package com.blog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@ConfigurationProperties(prefix = "email", ignoreUnknownFields = true, ignoreInvalidFields= true)
@PropertySource("classpath:emailConfig.properties")
@RefreshScope
@Data
public class EmailConfig implements Serializable {

    private static final long serialVersionUID = 8924584812503000823L;

    /**
     * 通讯协议
     */
    private String protocol;

    /**
     * EWS 配置信息
     */
    private String ewsMailServer;
    private String ewsUser;
    private String ewsPassword;
    private String ewsDomain;

    /**
     * SMTP 配置信息
     */
    // 邮件发送者账号
    private String smtpFromAccount;

    // 邮件发送者名称
    private String smtpFromUseName;

    // 发送者邮件密码
    private String smtpFromPwd;

    // 邮件发送协议host服务器地址
    private String smtpFromHost;

    // 邮件smtp协议端口
    private String smtpFromPort;

    // 邮件接收端协议(POP3或者IMAP)
    private String smtpReceiveProtocolType;

    // 邮件接收端协议host服务器地址
    private String smtpReceiveHost;

    // 邮件接受端协议对应的端口号
    private String smtpReceivePort;

    // 邮件发送端是否启动ssl，true启动--邮件接收端协议端口采用ssl的，false--邮件发送端和接收端协议端口采用无ssl端口
    private Boolean smtpIsFromSsl;

    //邮件接收端是否开启ssl，true启动--邮件接收端协议端口采用ssl的，false--邮件接收端协议采用无ssl端口的
    private Boolean smtpIsReceiveSsl;

    //是否匿名
    private Boolean smtpIsAnnoymous;

}
