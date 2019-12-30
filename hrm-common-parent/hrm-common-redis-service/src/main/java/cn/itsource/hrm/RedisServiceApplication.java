package cn.itsource.hrm;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
/*@MapperScan("cn.itsource.hrm.mapper")
@EnableTransactionManagement
@EnableSwagger2 //开启swagger
@EnableFeignClients("cn.itsource.hrm.client")*/
@EnableEurekaClient
public class RedisServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedisServiceApplication.class,args);
    }
}
