package org.master.joint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaAuditing
@ServletComponentScan
// 扫码加载@WebFilter 和 @WebServlet，Servlet、Filter、Listener可以直接通过@WebServlet、@WebFilter、@WebListener注解自动注册
public class JointJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JointJavaApplication.class, args);
    }

}
