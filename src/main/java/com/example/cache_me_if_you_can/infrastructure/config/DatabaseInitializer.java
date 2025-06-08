package com.example.cache_me_if_you_can.infrastructure.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DatabaseInitializer implements CommandLineRunner{

    @Override
    public void run(String... args) throws Exception { 
        
    }

}
