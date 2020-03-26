package ru.litvinov;

import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource("classpath:jdbc.properties")
@ComponentScan("ru.litvinov")
@EnableAspectJAutoProxy()
public class ConfigClass {

    @Autowired
    private Environment environment;

    //Создание proxy через стандартные библиотеки:
    @Bean
    public Recomend recomend(){
        return new Recomend();
    }

    @Bean
    public NameMatchMethodPointcut pc(){
        NameMatchMethodPointcut nm = new NameMatchMethodPointcut();
        nm.setMappedName("soutEvents");
        return nm;
    }

    @Bean
    public DefaultPointcutAdvisor dpa(){
        DefaultPointcutAdvisor da = new DefaultPointcutAdvisor();
        da.setPointcut(pc());
        da.setAdvice(recomend());
        return da;
    }

    @Bean
    public ProxyFactoryBean proxyFactoryBean(){
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(main2());
        //proxyFactoryBean.setInterceptorNames("recomend");
        proxyFactoryBean.setInterceptorNames("dpa");
        return proxyFactoryBean;
    }
    //конец

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
        //dataSource.setUrl("jdbc:h2:/c:/Users/Dilitand/test");
        dataSource.setUrl(environment.getProperty("jdbc.url"));
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public Main2 main2() {
        Main2 main2 = new Main2(jdbcTemplate());
        main2.jdbcTemplate = jdbcTemplate();
        return main2;
    }
}
