package com.hudong.b16live;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 此文件为spring启动文件
 * @Configuration：标注了 @Configuration 注解，便会处理该类中标注 @Bean 的方法，将这些方法的返回值注册为容器总的 Bean。
 * @ComponentScan：注解告诉Spring递归地搜索test.testArtifact包和其子包中直接或间接标记为@Component的类（@Controller是一类@Component注解）
 * @EnableAutoConfiguration：注解的作用在于让 Spring Boot 根据应用所声明的依赖来对 Spring 框架进行自动配置，这就减少了开发人员的工作量。
 * @PropertySource：引用属性文件
 * 通过继承WebMvcConfigurerAdapter这个适配器，然后继承该类的私有方法，从而达到配置Spring MVC的目的
 *
 *  使用@SpringbootApplication注解  可以解决根类或者配置类头上注解过多的问题，
 *  一个@SpringbootApplication相当于@Configuration,@EnableAutoConfiguration和 @ComponentScan 并具有他们的默认属性值。
 */
@ComponentScan(basePackages={"com.hudong"})
@SpringBootApplication
@EnableScheduling
@ServletComponentScan
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }

    //启动springboot
	public static void main(String[] args) {
		//SpringApplication springApplication = new SpringApplication(Application.class);
		//启动前创建监听器
        //springApplication.addListeners(new Listener());
		//springApplication.run(args);

		//也可以这样启动
		SpringApplication.run(Application.class,args);

    }
}