package cn.qhb.haiv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
@SpringBootConfiguration
//@EnableCaching
//@MapperScan("cn.qhb.haiv.mapper")
public class HaivConsumerApplication {

    public static void main(String[] args) throws IOException {
        System.out.println("HaivApplication used!!!!!!");
        SpringApplication.run(HaivConsumerApplication.class, args);
    }

}
