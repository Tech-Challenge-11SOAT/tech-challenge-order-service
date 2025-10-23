package br.com.postech.techchallange_order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TechchallangeOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechchallangeOrderApplication.class, args);
    }

}
