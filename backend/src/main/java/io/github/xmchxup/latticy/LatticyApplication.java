package io.github.xmchxup.latticy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pedro@TaleLin
 */
@RestController
@MapperScan(basePackages = {"io.github.xmchxup.latticy.mapper"})
@SpringBootApplication(scanBasePackages = {"io.github.xmchxup.latticy"})
public class LatticyApplication  {
//    war包需要用到
//extends SpringBootServletInitializer
//mvn clean package -Dmaven.test.skip=true
    public static void main(String[] args) {
        SpringApplication.run(LatticyApplication.class, args);
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(LatticyApplication.class);
//    }

}
