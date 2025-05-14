package com.test.lsy.restapi250423.config;

import com.test.lsy.restapi250423.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class JasyprtConfig {

    // vm 옵션으로 전달받은 개인키
    @Value("${jasypt.password}")
    private String jasyptPassword;

    @Bean(name="jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        // vm 옵션으로 전달받은 개인키로 암호화 후 그 객체를 반환해서 빈으로 등록
        StringEncryptor encryptor = StringUtils.getEncryptor(jasyptPassword);
        return encryptor;
    }
}
