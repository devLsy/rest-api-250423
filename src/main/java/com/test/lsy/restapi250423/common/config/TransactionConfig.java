package com.test.lsy.restapi250423.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
@Aspect
@Configuration
@RequiredArgsConstructor
public class TransactionConfig {

    private final PlatformTransactionManager txManager;

    @Bean
    public TransactionInterceptor txAdvice() {

        TransactionInterceptor txAdvice = new TransactionInterceptor();

        List<RollbackRuleAttribute> rollbackRules = new ArrayList<>();
        rollbackRules.add(new RollbackRuleAttribute(RuntimeException.class));

        // 1. 기본 읽기 전용 트랜잭션 속성
        DefaultTransactionAttribute readOnlyAttribute = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules);
        readOnlyAttribute.setReadOnly(true);

        // 2. CUD용 쓰기 트랜잭션 속성
        RuleBasedTransactionAttribute writeAttribute = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules);
        writeAttribute.setReadOnly(false);

        Properties txAttributes = new Properties();

        // 기본으로 전체를 읽기 전용 트랜잭션 처리
        txAttributes.setProperty("*", readOnlyAttribute.toString());

        // CUD 메서드 이름 패턴은 쓰기 트랜잭션 처리
        txAttributes.setProperty("insert*", writeAttribute.toString());
        txAttributes.setProperty("update*", writeAttribute.toString());
        txAttributes.setProperty("delete*", writeAttribute.toString());
        txAttributes.setProperty("create*", writeAttribute.toString());
        txAttributes.setProperty("add*", writeAttribute.toString());
        txAttributes.setProperty("modify*", writeAttribute.toString());
        txAttributes.setProperty("remove*", writeAttribute.toString());
        txAttributes.setProperty("useTransaction*", writeAttribute.toString());

        txAdvice.setTransactionAttributes(txAttributes);
        txAdvice.setTransactionManager(txManager);

        return txAdvice;

    }

    @Bean
    public DefaultPointcutAdvisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.test.lsy.restapi250423..service..*(..))");
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}