package org.example.SpringRegisterBean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        Parrot parrot = new Parrot();
        parrot.setParrotName("Kiki");

        Supplier<Parrot> parrotSupplier = () -> parrot;

        context.registerBean("parrot55", // 1- parameter beanName
                Parrot.class,  // 2 - parameter bean класс кот добавляется в контекст
                parrotSupplier,  // 3 - parameter bean Supplier возвращает объект добавляемый в контекст
                bc -> bc.setPrimary(true));  // 4 - parameter bean - делаем бин первичным, последний парам явл. varargs ...


        Parrot p = context.getBean("parrot55", Parrot.class);
        System.out.println(p.getParrotName());

    }
}
