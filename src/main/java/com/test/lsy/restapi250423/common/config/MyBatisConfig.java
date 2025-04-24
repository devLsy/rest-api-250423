package com.test.lsy.restapi250423.common.config;

import com.test.lsy.restapi250423.common.bean.RefreshableSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class MyBatisConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new RefreshableSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(applicationContext.getResources("classpath:/mappers/**/*.xml"));
        sqlSessionFactory.setTypeAliasesPackage("com.test.lsy.restapi250423.*.model");
        ((RefreshableSqlSessionFactoryBean) sqlSessionFactory).setInterval(3000);
        sqlSessionFactory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactory.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        sqlSessionTemplate.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
        sqlSessionTemplate.getConfiguration().setLazyLoadingEnabled(true);
        sqlSessionTemplate.getConfiguration().setMultipleResultSetsEnabled(true);
        sqlSessionTemplate.getConfiguration().setUseColumnLabel(true);
        sqlSessionTemplate.getConfiguration().setMapUnderscoreToCamelCase(true);
        sqlSessionTemplate.getConfiguration().setDefaultStatementTimeout(25000);
        return sqlSessionTemplate;
    }

    @Bean(name="txManager")
    public PlatformTransactionManager txManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        dataSourceTransactionManager.setNestedTransactionAllowed(true);
        return dataSourceTransactionManager;
    }

}
