package com.youlai.system.security.util;

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
        try {
            ApiClient defaultClient = Configuration.getDefaultApiClient();
            String path="https://openapi-sandbox.dl.alipaydev.com";
//            String path="https://openapi.alipay.com";
            defaultClient.setBasePath(path);//setBasePath里的链接不需要修改，就这样
            // 设置alipayConfig参数（建议全局设置一次，频繁创建config会报异常）
            AlipayConfig config = new AlipayConfig();
            String appId="9021000135661208";
            String privateKey="MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC9p3yVhvqG01rsO8ITs+RDhtnyz4Eh9HMb3njZ6RlWHxfMsMCnFc85osdhwKHo63Z7uPdDVSLCR7oO5DX3fOTZtJ7bLqqB10WIfQvz2rFvAMYa/J4+wXbAMks1fBx8n6lSkBMejrl6A9XOHVZslflfKX2GGMxemXkaXq76E4fSGLqeYpeuvWNNJpzKSeZLsYIbwCKP+piQWTcB5Fbs7eVwZJ/gGHt6/r1salpWHJ715h9juHy3CEarAnoMDo1fRF4SKOBqo/wEFOQP73TztORC4klHtuesHeLgwekWhU78zl7Dt3SzmWg6YCyCJN4MYG8Y6JjNglJvX2Lb8rBU10kJAgMBAAECggEAOj6YvVdB02xPjSNhHCnwHWjpwG9HykTHoTR2yv2SgWd1KLDTybyh8qJyNywwzVabAa4xieU4ko/yrSYbCNG7erzE2K2BQfT57svwgsiUG3ISZTFV7BIARVBs1pUyLnfeSXPj20ub3MQWwoEyDJEWj8ovOaKeCSN7FMUlWbnryXK0mQ4Eht6JHh5A6SPs3E61LKHYu2ov/C0HtE4QBrrq7shJds0sK7HW+8JtR60xp1tL7U0Dg/i3CfUL2RnbTohe0v6QX/XJwXDcIotgH/7D2T7ozxX+PnlhSH2sxIDFBpJHdd7AaSk9gnrNkt7pLmc3k5hiD4Z8aQ6PonHcv4o2gQKBgQD2K7RpsGL56n9o9H+dSyGay2ZjhQBahXVySyNw/2xS0vK8CqTEDmC8WpJ+PfSt9Wwnp9sXpLOGkojSeHAGQkvXKHi2Agog0iu87A53aJk/iEvOT5na2ruRfZMrASuNRi816OBpSItoQZiJandIQtNuP+6v19G1aZqCu0Wz1IPLUQKBgQDFOhWubw64PH2feRTvMFDMHabE8Pe7SMGBG12YSs1r+lFjorG13IvboR7BmVBVYALfuZ1BCcfifhnOECtzopPmjozGq6EY2to9FqG0JhFbCJKP57VLixBvQPspqRCgiYp3Cb7wJjW9LIfJLZMNWBcVZDoVOUxi+k8zegjkn0XEOQKBgQCrtSZ9hgQz0GaYvdp5y9sfzvqXNkFd4rNeSpzpB7D2QKABm+b7vg+psf92wR53bLXJH2NKJZ73oiR/snRpWTrvVBKG2ftYk2DgwUMB9VrXnulJeeqWZnXo8M+Dcqh/PVJW5x4JMgnTadXNHA8hv0MdJskFrKNhR8z3nUrsObLIUQKBgQDCsWcrzdgTCSvdnFKNF/36sR6q9VEoIVdcEt6e8uC8QdvMZpPVekTZ58CLzsPds6zCm+HfjT8zl9XzdETgntIv2/KeQFkERy7+9PNl+dpSKjE8O7jdPQdZPCV0Z7/RKlV00zsYqC+OcoIMl5+9oYMQC+PGhcyhPIWoxEQ5I/1kCQKBgQC6btFH2tVoyiZzJAY1jZA2xGGhFciWDvoJF6AriVc4970Qc4D0dLfjvpPnklMWnR1xD9JXmBoUoihRXQ9OZoHhAjFazuJFm0a1VOM/OicH8+gDGlwb4WD7wa8cPqJw2T2Ow7l5hXgVaOYXqlW6fuSQ2+GNsLCMIrL4FDlO2kejjA==";
            String alipayPubkey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAy0QmW+f88keYruK4sAbz71pVNnSXMwH+D9TlDF29qIi2qTwMRJwcvmt/Bw3764RD/H4439oaP1aAcyCtca4CyfZeHXyHEOk7+aIqVTdQrxg0eQYzALx1xbkvrVQnv5SXARXAxt9Zcc6s9RQscmOoIkrGpwGrQzqltRbebM7gOfLWYg3oRqWe2rOpBmlNatHF/LuQ8m+IrOSa36qpfWuR0bkQSwpmASUEPNZ+6QCfV1mgWDMjzdHMLf0dNQ0p//APQ1i657Fw/WT410SSh46kpxJMHcBK16XFO/0y/7fJKER/y4U4jyq1sU/CD8o6Y1hwFEIKzQo2gvQpucLwWWcfiwIDAQAB";
            config.setAppId(appId);
            config.setPrivateKey(privateKey);
            config.setAlipayPublicKey(alipayPubkey);
            //          String path = "/Documents/xxxx/支付宝支付证书/应用2签约/应用私钥RSA2048.txt";
//            String privateKey = FileTools.readToString(path);
            // 密钥模式
//            config.setAppId("2021004136697141");//支付宝商户平台创建的应用的APPID
//            config.setPrivateKey("MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCyN5SqiHHntzLOuk4dcgAebwIXRZceNhbIVmAK6+ubpDOK00/0tQacoiNyyAqZpr0ibwSJFnLuGFKMtVNfMDQe0lMgbKrPTUJsLOrbDpFTjaJYn3rRmOUE/z2z24YqhCCvkpW/u/VPK1gLE6gRLPA+L9TESOs1sw7PoqO6Q+CdgLVe59gYCZeGwJq/c/7kfL/6BGM8fxsmiv8/APf0iPqn66EYErwbwFvUi0hbhf/YiNjFiKCzo2ScDoMkifNCHDz04io7xAZlJTR8jQ3+6RncxQjLZFXfR7hHDqXt0kZbAi2oJgKt3+v2gh4fYnTuu71WupsuokQ+ojJLf3j2o2tFAgMBAAECggEAU8LPur7aWj++P29M24YDDEq8PtfUSLy/RHVkOn3bhJFOMEdU4vxaGTATXRUr9j9p0U9AYHYOml0QH1Xx02FCfeH4c9rfBQOY3mLWFxLnts9wqBp8jRpBOXIFPA1HJ6BmBF7uxui+SPFkNgCNE1V9rJyqcX6cHecEZQ5ZyWexoBReAOxdp+xdVDJLG7IxiZhgvR0nZVAZUi1VNx08+gSS/GxEefZ0lka47opoSwcB7tAyznie0Abz+fgy0Ne8UI1qaKjQDKTdxOjTxdzQGtMi05CmLEF4RArjcrF/Aozj9Wt1YYw7HSf1J+K/uvgLZl9aaSXNa3UK7gzJOdoBHiN+GQKBgQDwJiLG/khULjpPvvO35AC7CF7LsN+W5+/DlSqMb22/cOOh+G6Ew62oO2bQMleKKDMOWpdNMTt+EjCwucw0cJC37xAPaHxwhk8YVMpqsuvkDKRI7f7rmSxgokC1zfYtQyfgc+7xvp5cji4995fAyi/UVLPsZOri9RXCENwX1/WElwKBgQC9+vcLlGCgk1qhyXyxfHAIKACtjKhrvbHtc6ln88xInvGECJhgZSCWdyfFMXtYs3WCWsHgIO9FwpBOPpI39Z4cqtY0YNgYo9J/TTSZwjaTn5hdnpmfFkIcGy47wWmmNsIoyvMGjP4neDgnHk1m2HoEoUhWDDOg5Em0tSajObw+gwKBgC6R5qqaQyJ3+HzgddjSUUXz4JY1jZZxEfDmq2KidTDrzmjpiV0bqUB3jH+RU6KKGySMcszYYEYZdvG+hhghQB87L+0dAQy9Lvdz8fDxE4ec3/r4lDfR82Yl8NCQyU2LuV7wfITKx2EBfXws2HPdgZr2dhHAnEA48Og6ZsQE8CqvAoGAUavoNJQvDhkK526FKpACYZ9A21sStG6yZRBKDaRjd/zMeyRuzVHsNoM3F7D0A+fyZn25xy6hbm38N4byuRfgRxayS4YuoDkdLxjzuf4iFEiR6f1asWeSOB7/OzUkxA3aXksROwm14SXvjfGa+NGrcv4a6NMKBurdPNB7scQKRx0CgYBPHkNKJsNrHHoXjhdL5kkcS6RWbU+sMkYbVRajd02s4DL6D64ahlmVxxV1XtqVyr62H1Z5v28597rXm8qDXlF73+9aWebfEoaDqFATYJ4bntQzDnjMyxuDQSTq9tdj+ShCMY/5t3VVWZY9k1tFtotOd7LvaIht+CnrPFg2Fl8+pw=="); //私钥，这里填写的是私钥的内容，而不是私钥文件的路径，具体那个私钥，看下文解释
//            config.setAlipayPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgnopfWBV9/JNVqm9PW6WZKRyk5Q5x1E39A8tlacf4kNsu2KKqby32hDPQecJ0ER6s3TJuilIyKHmlPfNZ/QuF2mP1GgC6Iq39M9uhtObCJmiFyizSAS0xbKGAsAQ8cyEYwgfTH1je+qCmvC3gW/aRxc6Fih8OlCwuImiNbyTYKTBt+oEqTuUWnR3mIrbQA9xMMcdsXZrX8+cSrJwPeat78SNYrMvm8oTTDEfKOsUNgtAFS1W3mgHQeHMAcvTFdwngrPh9TLyDAai8uYeZYajYcWjFOgXgBoNTq5ka0XPLrP5PnB2clSRBQibjj+WXgI059AxdzwpuesdRNWH1E0FXwIDAQAB");
//            config.setEncryptKey("ug3N0Ln4Erj4vVsEgDLlcQ==");

            // 证书模式，证书路径后有对应的模版文件名后缀，从下载好的整数中很好区分(比微信友好的多)
//            config.setAppCertPath("/Documents/xxxx/支付宝支付证书/应用2签约/appCertPublicKey_2021003143xxxx.crt");
//            config.setAlipayPublicCertPath("/Documents/xxxxx/支付宝支付证书/应用xxx约/alipayCertPublicKey_RSA2.crt");
//            config.setRootCertPath("/Documents/xxxxxxxx/支付宝支付证书/应用2签约/alipayRootCert.crt");
            //接口内容加密方式(支付宝开放平台的应用开发设置中有)
            defaultClient.setAlipayConfig(config);
            //实例化客户端
            AlipayTradeApi api = new AlipayTradeApi(defaultClient);
            String orderNo = UUID.randomUUID().toString();
            //调用 alipay.trade.pay
            AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
            model.setOutTradeNo(orderNo);
            model.setSubject("lovejj" + System.currentTimeMillis());
            model.setTotalAmount("520");
            model.setTimeoutExpress("15m");
            model.setNotifyUrl(CALL_BACK_URL); // 异步回调通知地址
            AlipayTradePrecreateResponseModel responseModel = api.precreate(model);
            //responseModel中会返回二维码的链接内容，需要第三方的二维码工具将其转成二维码
            //生成二维码，可以后端不用生成二维码，让前端根据url直接生成二维码也行，二维码的链接其实就是二维码的内容
//            QRCodeGenerator.generateQRCodeImage(responseModel.getQrCode(),二维码存放路径);
            System.out.println(responseModel.toJson());
        } catch (ApiException e) {
            AlipayTradePayDefaultResponse errorObject = (AlipayTradePayDefaultResponse) e.getErrorObject();
            if (errorObject != null && errorObject.getActualInstance() instanceof CommonErrorType) {
                //获取公共错误码
                CommonErrorType actualInstance = errorObject.getCommonErrorType();
                System.out.println("CommonErrorType:" + actualInstance.toString());
            } else if (errorObject != null && errorObject.getActualInstance() instanceof AlipayTradePayErrorResponseModel) {
                //获取业务错误码
                AlipayTradePayErrorResponseModel actualInstance = errorObject.getAlipayTradePayErrorResponseModel();
                System.out.println("AlipayTradePayErrorResponseModel:" + actualInstance.toString());
            } else {
                //获取其他报错（如加验签失败、http请求失败等）
                System.err.println("Status code: " + e.getCode());
                System.err.println("Reason: " + e.getResponseBody());
                System.err.println("Response headers: " + e.getResponseHeaders());
                System.out.println(e);
            }
        }
    }
}
