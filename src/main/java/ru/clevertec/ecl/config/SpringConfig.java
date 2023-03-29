package ru.clevertec.ecl.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan({"ru.clevertec.ecl"})
@Import( {SpringJdbcConfig.class, SpringTestJdbcConfig.class } )
@PropertySource("classpath:application.properties")
public class SpringConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
