/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.invoicemaster.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Richard Narh
 * 28/05/2022 11:42AM
 * richardnarh001@gmail.com
 */
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI().components(new Components()).info(new Info()
                .title("EAM Software - Khoders Technologies")
                .description("Swagger Configuration for Application")
                .version("v1.0.0")
        );
    }
}

