package com.abc.config;

import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;


import javax.sql.DataSource;
import javax.persistence.EntityManagerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
public class DatabaseConfig {
    @Autowired
    private Environment env;

            //https://github.com/stephen-masters/multids-demo/tree/now-with-spring-boot
    //https://springframework.guru/how-to-configure-multiple-data-sources-in-a-spring-boot-application/


    // For Test schema
    @Bean(name="secondaryDS")
    //@Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource getSeconadaryBatchDataSource(){
        return DataSourceBuilder.create()
                .url(env.getProperty("spring.datasource.url"))
                .driverClassName(env.getProperty("spring.datasource.driver-class-name"))
                .username(env.getProperty("spring.datasource.username"))
                .password(env.getProperty("spring.datasource.password"))
                .build();
    }


    // For "batchmetadata" tables batchdb
    @Bean(name="primaryDS")
    @Primary
    @ConfigurationProperties(prefix="spring.hello.datasource")
    public DataSource getPrimaryBatchDataSource(){
        return DataSourceBuilder.create()
                .url(env.getProperty("spring.hello.datasource.url"))
                .driverClassName(env.getProperty("spring.hello.datasource.driver-class-name"))
                .username(env.getProperty("spring.hello.datasource.username"))
                .password(env.getProperty("spring.hello.datasource.password"))
                .build();
    }

//    @Primary
//    @Bean(name = "entityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//            EntityManagerFactoryBuilder builder,
//            @Qualifier("dataSource") DataSource dataSource) {
//        return builder
//                .dataSource(dataSource)
//                .packages("com.sctrcd.multidsdemo.foo.domain")
//                .persistenceUnit("foo")
//                .build();
//    }


    @Bean(name = "primaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("spring.jpa.properties.hibernate.format_sql", "true");

//        Properties properties2 = new Properties();
//        properties2.setProperty("hibernate.hbm2ddl.auto", "update");
//
//        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//        emf.setDataSource(getSeconadaryBatchDataSource());
//       // emf.setJpaVendorAdapter(jpaVendorAdapter);
//       // emf.setPackagesToScan("com.mysource.anothersource.model");   // <- package for entities
//        emf.setPersistenceUnitName("properties");
//        emf.setJpaProperties(properties2);
//        emf.afterPropertiesSet();
//return emf;


        return builder
                //.withDataSource(getSeconadaryBatchDataSource())
                .dataSource(getSeconadaryBatchDataSource())
                .packages("com.abc.entity")
                .persistenceUnit("default")
                .properties(properties)
                .build();
    }

//    @Bean
//    public PhysicalNamingStrategy physical() {
//        return new PhysicalNamingStrategyStandardImpl();
//    }
//
//    @Bean
//    public ImplicitNamingStrategy implicit() {
//        return new ImplicitNamingStrategyLegacyJpaImpl();
//    }
}
