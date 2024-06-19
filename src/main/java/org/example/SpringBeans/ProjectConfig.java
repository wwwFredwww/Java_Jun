package org.example.SpringBeans;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    @Bean(value = "Koko")
    public Parrot parrot1() {
        var parrot = new Parrot();
        parrot.setParrotName("Koko");
        return parrot;
    }

    @Bean(name = "Miki")
    public Parrot parrot2() {
        var parrot = new Parrot();
        parrot.setParrotName("Miki");
        return parrot;
    }

    @Bean("Kiki")
    public Parrot parrot3() {
        var parrot = new Parrot();
        parrot.setParrotName("Kiki");
        return parrot;
    }

    @Bean
    public String string1() {
        return "Hello";
    }

    @Bean
    public Integer integer1() {
        return 55;
    }
}
