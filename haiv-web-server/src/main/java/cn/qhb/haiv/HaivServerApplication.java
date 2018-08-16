package cn.qhb.haiv;

import cn.qhb.haiv.core.util.ApplicationContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
@SpringBootConfiguration
//@EnableCaching
//@MapperScan("cn.qhb.haiv.persistence")
public class HaivServerApplication {

	public static void main(String[] args) throws IOException {
		ApplicationContext app=SpringApplication.run(HaivServerApplication.class, args);
		ApplicationContextUtil.setApplicationContext(app);
	}

}
