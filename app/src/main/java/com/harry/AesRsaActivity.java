/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.harry.rv.aesrsa.AES;
import com.harry.rv.aesrsa.EncryUtil;
import com.harry.rv.aesrsa.RSA;
import com.harry.rv.aesrsa.RandomUtil;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/2/21.
 */
public class AesRsaActivity extends Activity {
    public static String HTTP_BODY = "sessionToken=37f85bc28cebf9f8fefa294771ecb00b&serialVersionUID"
        + "=8224743709472885934&aid=27&cityid=2&loginClient=Android\n"
        + "        &numEmpno=125&uuid=-1775342599834859080&serialVersionUID=-6019263390277899035";

    @BindView(R.id.tv_body)
    TextView tvBody;
    @BindView(R.id.tv_body_decrypt)
    TextView tvBodyDecrypt;
    StringBuffer buffer = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aesrsa);
        ButterKnife.bind(this);
        tvBody.setText(HTTP_BODY);
    }

    public void encrypt(View v) {
        /**
         * map 加解密
         */
//        TreeMap<String, Object> params = new TreeMap<String, Object>();
//        params.put("userid", "152255855");
//        params.put("phone", "18965621420");
//
//        try {
//            client(params);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            server();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        /**
         * 字符串 加解密
         */
        clientEncrypt();

        try {
            serverDecrypt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final String clientPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKbNojYr8KlqKD/y" +
        "COd7QXu3e4TsrHd4sz3XgDYWEZZgYqIjVDcpcnlztwomgjMj9xSxdpyCc85GOGa0" +
        "lva1fNZpG6KXYS1xuFa9G7FRbaACoCL31TRv8t4TNkfQhQ7e2S7ZktqyUePWYLlz" +
        "u8hx5jXdriErRIx1jWK1q1NeEd3NAgMBAAECgYAws7Ob+4JeBLfRy9pbs/ovpCf1" +
        "bKEClQRIlyZBJHpoHKZPzt7k6D4bRfT4irvTMLoQmawXEGO9o3UOT8YQLHdRLitW" +
        "1CYKLy8k8ycyNpB/1L2vP+kHDzmM6Pr0IvkFgnbIFQmXeS5NBV+xOdlAYzuPFkCy" +
        "fUSOKdmt3F/Pbf9EhQJBANrF5Uaxmk7qGXfRV7tCT+f27eAWtYi2h/gJenLrmtke" +
        "Hg7SkgDiYHErJDns85va4cnhaAzAI1eSIHVaXh3JGXcCQQDDL9ns78LNDr/QuHN9" +
        "pmeDdlQfikeDKzW8dMcUIqGVX4WQJMptviZuf3cMvgm9+hDTVLvSePdTlA9YSCF4" +
        "VNPbAkEAvbe54XlpCKBIX7iiLRkPdGiV1qu614j7FqUZlAkvKrPMeywuQygNXHZ+" +
        "HuGWTIUfItQfSFdjDrEBBuPMFGZtdwJAV5N3xyyIjfMJM4AfKYhpN333HrOvhHX1" +
        "xVnsHOew8lGKnvMy9Gx11+xPISN/QYMa24dQQo5OAm0TOXwbsF73MwJAHzqaKZPs" +
        "EN08JunWDOKs3ZS+92maJIm1YGdYf5ipB8/Bm3wElnJsCiAeRqYKmPpAMlCZ5x+Z" +
        "AsuC1sjcp2r7xw==";

    public static final String clientPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmzaI2K/Cpaig/8gjne0F7t3uE" +
        "7Kx3eLM914A2FhGWYGKiI1Q3KXJ5c7cKJoIzI/cUsXacgnPORjhmtJb2tXzWaRui" +
        "l2EtcbhWvRuxUW2gAqAi99U0b/LeEzZH0IUO3tku2ZLaslHj1mC5c7vIceY13a4h" +
        "K0SMdY1itatTXhHdzQIDAQAB";

    public static final String serverPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALIZ98KqgLW8IMt4" +
        "G+N+4d3DiOiEa+5s6lCMSGE/NbU9stJEqw0EuCP54MY6JkT0HCYTCrLXqww6rSQy" +
        "WF7BNCVGssk2XDcvSKiCz1ZMgabd6XVK5kvIycySydXQ0Ky6rnfxw8w2mllHABFv" +
        "s1eamaHQozv18n/XGqemjW2BFy/jAgMBAAECgYAxT3FCi3SBXKnzy7hk/z9H6Bhi" +
        "0C8V3z/stzpe+mJDYOa+wtZdD15wT4HFQFpSIwgcHo+Kvp2UEDbZ27qN2Y43AZbF" +
        "9LOalWTRUzYtr8wL8MIbgtew/QQ9YFNWdkTZ6MxCItjD/mSz3Lrkcphvbsx4VoCV" +
        "YIJ04r+Loi0t9g0guQJBANvkpfrq0bLVRYWfaigjkx47mr0trJkB7mjADe69Iqts" +
        "M/2x5dHPpClDK78yzAWxU2BrYzOd31QIOm32iMIvRxUCQQDPWJPMOzcq8Jqs1PAM" +
        "7D0hxnvF3tSJB0CJCQWdGFkJiuIYSbrWnCVF78jJyU2AK1H3RDi9BzGPL2Z3i2Si" +
        "+9kXAkAPnKtAJl3fEY9PDmNuGCCA3AB/f/eqIV345/HVSm5kt1j1oSTNAa4JE/DO" +
        "MWAU42MlDFrNtl69y5vCZOeOyeaFAkBOJieGmWcAozDZJWTYqg2cdk/eU08t2nLj" +
        "c2gPPscIRrVSzC9EhhOyWV8HVv0D6s/471inPlfajNYFBp/Goj+/AkEAiejHX/58" +
        "Vv8+ccW22RMZmyxiHcZpTw9hz7vHUCWv03+fyVGtGMhJ4xuPt8UaZm91yHSPWWar" +
        "M8Xa7errKaXN9A==";
    public static final String serverPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyGffCqoC1vCDLeBvjfuHdw4jo" +
        "hGvubOpQjEhhPzW1PbLSRKsNBLgj+eDGOiZE9BwmEwqy16sMOq0kMlhewTQlRrLJ" +
        "Nlw3L0iogs9WTIGm3el1SuZLyMnMksnV0NCsuq538cPMNppZRwARb7NXmpmh0KM7" +
        "9fJ/1xqnpo1tgRcv4wIDAQAB";

    public void client(TreeMap<String, Object> params) {
        // 生成RSA签名
        String sign = EncryUtil.handleRSA(params, clientPrivateKey);
        params.put("sign", sign);

        String info = JSON.toJSONString(params);
        //随机生成AES密钥
        String aesKey = RandomUtil.getRandom(16);
        //AES加密数据
        String data = AES.encryptToBase64(info, aesKey);

        // 使用RSA算法将商户自己随机生成的AESkey加密
        String encryptkey = null;
        try {
            encryptkey = RSA.encrypt(aesKey, serverPublicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Req.data = data;
        Req.encryptkey = encryptkey;

        tvBody.setText("加密后的请求数据:\n" + new Req().toString());
    }

    /**
     * 未使用签名
     */
    public void clientEncrypt() {

        String aesKey = RandomUtil.getRandom(16);
        //AES加密数据
        String data = AES.encryptToBase64(HTTP_BODY, aesKey);

        // 使用RSA算法将商户自己随机生成的AESkey加密
        String encryptkey = null;
        try {
            encryptkey = RSA.encrypt(aesKey, serverPublicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Req.data = data;
        Req.encryptkey = encryptkey;

        tvBody.setText("加密后的请求数据:\n" + new Req().toString());
    }

    public void server() throws Exception {

        // 验签
        boolean passSign = EncryUtil.checkDecryptAndSign(Req.data, Req.encryptkey, clientPublicKey, serverPrivateKey);

        if (passSign) {
            // 验签通过
            String aeskey = RSA.decrypt(Req.encryptkey, serverPrivateKey);
            String data = AES.decryptFromBase64(Req.data, aeskey);

            JSONObject jsonObj = JSONObject.parseObject(data);
            String userid = jsonObj.getString("userid");
            String phone = jsonObj.getString("phone");

            tvBodyDecrypt.setText("解密后的明文:userid=" + userid + " phone:" + phone);
        }
        else {
            System.out.println("验签失败");
        }
    }

    public void serverDecrypt() throws Exception {

        if (true) {
            // 验签通过
            String aeskey = RSA.decrypt(Req.encryptkey, serverPrivateKey);
            String data = AES.decryptFromBase64(Req.data, aeskey);

            tvBodyDecrypt.setText("解密后的明文:" + data);
        }
        else {
            System.out.println("验签失败");
        }
    }

    static class Req {
        public static String data;
        public static String encryptkey;

        @Override
        public String toString() {
            return "data:" + data + "\nencryptkey:" + encryptkey;
        }
    }
}
