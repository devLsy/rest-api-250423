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
import org.springframework.transaction.interceptor.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Aspect
@Configuration
@RequiredArgsConstructor
public class TransactionConfig {

    private final PlatformTransactionManager txManager;

    @Bean
    public TransactionInterceptor txAdvice() {
        // 롤백 규칙: RuntimeException 발생 시 롤백
        List<RollbackRuleAttribute> rollbackRules = new ArrayList<>();
        rollbackRules.add(new RollbackRuleAttribute(RuntimeException.class));

        // 1. 기본 읽기 전용 트랜잭션 속성
        RuleBasedTransactionAttribute readOnlyAttribute = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules);
        readOnlyAttribute.setReadOnly(true);

        // 2. 쓰기 트랜잭션 속성
        RuleBasedTransactionAttribute writeAttribute = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules);
        writeAttribute.setReadOnly(false);

        // 메서드 이름 기반 트랜잭션 속성 설정
        NameMatchTransactionAttributeSource transactionAttributeSource = new NameMatchTransactionAttributeSource();

        // 기본적으로 모든 메서드는 읽기 전용 트랜잭션
        transactionAttributeSource.addTransactionalMethod("*", readOnlyAttribute);

        // CUD 메서드는 쓰기 트랜잭션
        transactionAttributeSource.addTransactionalMethod("insert*", writeAttribute);
        transactionAttributeSource.addTransactionalMethod("update*", writeAttribute);
        transactionAttributeSource.addTransactionalMethod("delete*", writeAttribute);
        transactionAttributeSource.addTransactionalMethod("create*", writeAttribute);
        transactionAttributeSource.addTransactionalMethod("save*", writeAttribute);
        transactionAttributeSource.addTransactionalMethod("add*", writeAttribute);
        transactionAttributeSource.addTransactionalMethod("modify*", writeAttribute);
        transactionAttributeSource.addTransactionalMethod("remove*", writeAttribute);
        transactionAttributeSource.addTransactionalMethod("useTransaction*", writeAttribute);

        // 트랜잭션 속성을 TransactionInterceptor에 설정
        return new TransactionInterceptor(txManager, transactionAttributeSource);

    }

    @Bean
    public DefaultPointcutAdvisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.test.lsy.restapi250423..service..*(..))");
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}