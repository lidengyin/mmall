package cn.hcnet2006.mmall.mmall;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//设置Mapper扫描包以及注解过滤
@MapperScan(basePackages = "cn.hcnet2006.mmall.mmall.mapper",annotationClass = Mapper.class)
public class MmallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MmallApplication.class, args);
    }

}
