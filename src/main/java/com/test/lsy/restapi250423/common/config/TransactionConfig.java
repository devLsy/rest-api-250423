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
        rollbackRules.add(new RollbackRuleAttribute(Exception.class));

        DefaultTransactionAttribute attribute = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules);
        String transactionAttributesDefinition = attribute.toString();

        Properties txAttributes = new Properties();
        txAttributes.setProperty("select*", transactionAttributesDefinition);
        txAttributes.setProperty("insert*", transactionAttributesDefinition);
        txAttributes.setProperty("update*", transactionAttributesDefinition);
        txAttributes.setProperty("delete*", transactionAttributesDefinition);
        txAttributes.setProperty("create*", transactionAttributesDefinition);
        txAttributes.setProperty("add*", transactionAttributesDefinition);
        txAttributes.setProperty("modify*", transactionAttributesDefinition);
        txAttributes.setProperty("remove*", transactionAttributesDefinition);
        txAttributes.setProperty("useTransaction*", transactionAttributesDefinition);

        txAdvice.setTransactionAttributes(txAttributes);
        txAdvice.setTransactionManager(txManager);

        return txAdvice;

    }

    @Bean
    public DefaultPointcutAdvisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.test.lsy.restapi250423.service.*service.*(..))");
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }

}