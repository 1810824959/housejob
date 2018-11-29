package com.liyang.housejob.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WebMvcConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
