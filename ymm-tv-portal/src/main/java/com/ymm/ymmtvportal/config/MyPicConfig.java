package com.ymm.ymmtvportal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyPicConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        String path1 = path.replace("target/classes/", "src/main/resources/static/img/");
        String realPath =   path1.replace("/", "\\");
        String substring = realPath.substring(1);
        registry.addResourceHandler("img/**").
                addResourceLocations("file:///"+substring);
    }//第一个是访问的路径、、、   //第二个是真实的路径。。。
}