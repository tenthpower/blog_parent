package com.tenthpower.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.tenthpower.config.AliSmsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 短信发送的工具类
 */
public class SmsUtils {

    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(SmsUtils.class);

    //产品名称:云通信短信API产品,开发者无需替换
    private static final String PRODUCT = "Dysmsapi";

    //产品域名,开发者无需替换
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";

    @Autowired
    private AliSmsConfig config;

    private static AliSmsConfig aliSmsConfig = new AliSmsConfig();

    /**
     * 于工具类是使用静态方式调用
     */
    @PostConstruct
    public void init() {
        log.info("SmsUtils Config init..");
        aliSmsConfig = config;
    }

    /**
     * 发送短信
     */
    public static SendSmsResponse sendSms(String telNo, String code) throws Exception {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn‐hangzhou", aliSmsConfig.getAccessKeyId(), aliSmsConfig.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn‐hangzhou", "cn‐hangzhou", PRODUCT, DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象‐具体描述见控制台‐文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(telNo);
        //必填:短信签名‐可在短信控制台中找到
        request.setSignName(aliSmsConfig.getSignName());
        //必填:短信模板‐可在短信控制台中找到
        request.setTemplateCode(aliSmsConfig.getTemplateCode().replace("${code}", code));
        //选填‐上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        return sendSmsResponse;
    }

    public static QuerySendDetailsResponse querySendDetails(String telNo, String bizId) throws Exception {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout","10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn‐hangzhou", aliSmsConfig.getAccessKeyId(), aliSmsConfig.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn‐hangzhou", "cn‐hangzhou", PRODUCT, DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填‐号码
        request.setPhoneNumber(telNo);
        //可选‐流水号
        request.setBizId(bizId);
        //必填‐发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(DateUtil.getCurDate()));
        //必填‐页大小
        request.setPageSize(100L);
        //必填‐当前页码从1开始计数
        request.setCurrentPage(1L);
        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);
        return querySendDetailsResponse;
    }

}
