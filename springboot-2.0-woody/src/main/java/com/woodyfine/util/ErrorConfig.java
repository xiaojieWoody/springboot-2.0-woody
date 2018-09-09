package com.woodyfine.util;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorConfig {

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> containerCustomizer(){
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>(){

            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                //对应404错误码，也就是说程序如果发生500错误，就会将请求转发到/error/404这个映射来，那我们只要实现一个方法是对应这个/error/404映射即可捕获这个异常做出处理
                factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));
            }
        };
    }
}
