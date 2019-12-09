package cn;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

/*
启动类测试
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ServletComponentScan
public class DemoApplication {
    public static void main(String[] args) {
        System.out.println("begin");
        SpringApplication.run(DemoApplication.class, args);
    }

}
