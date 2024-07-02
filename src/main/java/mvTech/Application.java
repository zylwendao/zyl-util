/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package mvTech;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author
 * @desc 程序主入口
 * @date 2020年10月27日19:09:50
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        // 禁用 headless 模式
        System.setProperty("java.awt.headless", "false");
        SpringApplication.run(Application.class, args);
    }

}