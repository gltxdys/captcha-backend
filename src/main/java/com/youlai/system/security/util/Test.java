package com.youlai.system.security.util;

import cn.hutool.extra.mail.MailUtil;
import com.alipay.v3.ApiClient;
import com.alipay.v3.ApiException;
import com.alipay.v3.Configuration;
import com.alipay.v3.api.AlipayTradeApi;
import com.alipay.v3.model.*;
import com.alipay.v3.util.model.AlipayConfig;
import java.util.UUID;


/**
 * <pre>
 *
 * </pre>
 *
 * @author GL
 * @date 2024/3/27 15:36
 * @since 1.0.0
 * */



public class Test {
    public static final String CALL_BACK_URL = "http://tw.2007nb.cf:9002/a/b";
    public static void main(String[] args) {

        MailUtil.send("gltxdys@gmail.com","邮件服务测试","邮件服务测试成功",false);

    }
}
