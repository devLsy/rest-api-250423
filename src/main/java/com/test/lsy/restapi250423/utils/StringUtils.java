package com.test.lsy.restapi250423.utils;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

public class StringUtils {

    // 인자값으로 받은 키값을 가진 암복호화 가능한 객체 반환
    public static StringEncryptor getEncryptor(String key) {
        // Config
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(key);                    // 암호화 키
        config.setAlgorithm("PBEWithMD5AndDES");    // 암호화 알고리즘
        config.setKeyObtentionIterations("1000");   // 반복할 해싱 횟수
        config.setPoolSize("1");                    // 인스턴스 pool
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");    // salt 생성 클래스
        config.setStringOutputType("base64");       // 인코딩 방식

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(config);
        return encryptor;
    }
}
