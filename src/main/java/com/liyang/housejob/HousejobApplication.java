package com.liyang.housejob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;



//@EnableAutoConfiguration(exclude = {
//        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
//})
@ComponentScan("com.liyang.housejob")
@SpringBootApplication
@EnableTransactionManagement
public class HousejobApplication {

    public static void main(String[] args) {
        SpringApplication.run(HousejobApplication.class, args);
    }
}
