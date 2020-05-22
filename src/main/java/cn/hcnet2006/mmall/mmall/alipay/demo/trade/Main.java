package cn.hcnet2006.mmall.mmall.alipay.demo.trade;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.BaseClient.Config;
import com.alipay.easysdk.payment.common.models.AlipayTradeCreateResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;

public class Main {
    public static void main(String[] args) {
        // 1. 设置参数（全局只需设置一次）
        Factory.setOptions(getOptions());
        try {
            // 2. 发起API调用（以支付能力下的统一收单交易创建接口为例）


            AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace().preCreate ("Apple iPhone11 128G",
                    "2234567892", "5799.00");
            // 3. 处理响应或异常
            if ("10000".equals(response.code)) {
                System.out.println("调用成功:二维码流："+response.qrCode);
            } else {
                System.err.println("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static Config getOptions() {
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipaydev.com";
        config.signType = "RSA2";

        config.appId = "2016102200740135";

        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCVsVGQtOQGNMdBI33MhlmgfC+OAq18QPWSVOcL6CuZP69f+Y5b1PpXyLI2LzjceqrEu8U6plkko9yr+iFH4JsLH9N56dyjNXN2sKQ9GyP6jWslbzTFzYka29t02OKSZECVl/s0WtwOeM1uj5iZiBBb2yE1RAIZxSWEClevw1CD7mxX1tv3JHmjxKd+vZNOnWX5KK1bd8BXWuwQ1tpGVQdOA2TNm4iINegDbYW82FUNft37Pl2YWzFJVuIz5kUTZu/2XuDiTMx2Vt2y9NLJof3iRghksrzXKVO/T/rVUC2Qbk9g6/1fC3jfvP6w1LGCxiP/lEO3V6CBdmxypuT95ZOjAgMBAAECggEAPQNBE5InPsBUVqB4rdw0u8ziNSeyPmrbrQEeCyReYOAsQxochCGSyCTtPliQ/Iyi/fzqrrUOsHh5gT86QWmyyB34vEAtxfgLT90/lAhz6bOqcF+bSuQzddcW49Fix/hEurRvly9RcjSaxu2QbUOgJ1BxgJxvnsFUQTVV+DXLo3nyyk7RhfwqjW/7ff1fM3vdBhkZLrtsmCLpC2P2dRjPzQQEtWYiwBVmGjxeGFJnglA2NMpFfQbteIcHGI4My/1MtqOMg1etrxK2aRmjMKsKkUWsmCmkO5WqVFsCQ6Uq38Stt+Y7WC6kCCSzWyP+tFXW5ypzPQoiLEZ7lnF2sTVRwQKBgQDLtNCZ1Dmx94wDoDLOqzxF+QW88zgS4TP/ZIN4z7QLgLBKuZ7W0DkvOvixn34y2i6+cH8+kv5ah6Mwd2TyJwj2LDdTKXJYL8ols4NmeFoU2sdnzUpWVYR9zVM/jL42mTkHoAHKE3odI3X4PW3ZfSlHANjJC3bE+YnCiZSjFcQorwKBgQC8HtSdyYrcb1I9JsGwxcZS95FGDIH3B8Cix6YW7inyAlfqYD8DFRAPWLmZHFnAemNwYWrDQQvGbmVDy8ove31ADtkXH+8RiXp+16aq25wjo+X/mxrYYzAWe1P+sGLnWrrJSAushm5bSX715EQEo/f+EYDhaeQV0K19PdHOQ2fZTQKBgGk1YeeRLJs19yKIQrVqyx54lvYXyAY4OdrV1vE2lQ670FFHvTnFIUO7eWaDsXIiLbI99/ohgSzQkQAfKgvHDWHXRFu3NE9Hn2gJZvwtfJj4xbRe2t3sHRm8ShqwXi1kxYeipEnx9UfHYM5tK6cAdakuTsjHGvhwyS2oURRJ0Z4RAoGASm2EP11C5E3i4rDXxyGogqDSSD2gmeTmUUDeNIpL7CUDNYaYx5ZowTmgTLSLyfxiJU9VlyTADOUWiYkyWDDqcBKvz1diFbUn0tFtboHut6cb01JBf8VWfD6VD9ond1NNPV+UxtB1Dwb4BT8nrhJVhp7UN9rWU885JKj1vj6q8q0CgYBqF0amA1uify47HfE4+N+kY2Sz1sarudQWsUfMnOIHK2QiVpMEBEC8Ys8wKCtx4ZLNYOKlcdc+S+LbKXT5NizwOWp4o7Z/LqGmQo7Z+MPnFNnSNaEX8o2ohxmsM2x8RLdh2NkgFF5oHoQ4j6Sk73MkfiuQUlZ+Dje5XkmJFU4KlA==";


        //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
        config.alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmIOZT9rGLvtBMx1HLa9d14CIr44r51LCVUa5Ioxw30nlm5ElHuodacOSeskGm0C/TT4qgQHqOICHMBadMe6ceihbbDjEAKor919KQgWUlae1NAsRPqMlV2hinAUd+cy7Xp/pZ7BSeLz5BTFuiKfoG7XxOOy3ourLjvDQERJI0/FFKn6IcOrf1SSb5ypJTWjvOZ9cvTJzJ1gcATTcmxjYN8Hcwr8hl4LTA6+PLsVWtZXUjgrjKaE1ekxW1mbVMGeYKCxvaMowAoW1T8rAvyhtbEL0R0QFRWzwwphU3d/NKG+osiHWPkLVEXMjHvNSL+PO54L/NFYyWBkHPXGxOljKZQIDAQAB";

        //可设置异步通知接收服务地址（可选）
        //config.notifyUrl = "<-- 请填写您的支付类接口异步通知接收服务地址，例如：https://www.test.com/callback -->";

        //可设置AES密钥，调用AES加解密相关接口时需要（可选）
        //config.encryptKey = "<-- 请填写您的AES密钥，例如：aa4BtZ4tspm2wnXLb1ThQA== -->";

        return config;
    }
}