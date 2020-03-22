package ru.litvinov;

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
        return new Main2(jdbcTemplate());
    }
}
