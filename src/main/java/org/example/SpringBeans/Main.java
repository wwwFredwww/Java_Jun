package org.example.SpringBeans;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Parrot parrot1 = context.getBean("Koko", Parrot.class);
        System.out.println("parrot = " + parrot1.getParrotName());

        Parrot parrot2 = context.getBean("Miki", Parrot.class);
        System.out.println("parrot = " + parrot2.getParrotName());

        Parrot parrot3 = context.getBean("Kiki", Parrot.class);
        System.out.println("parrot = " + parrot3.getParrotName());

        String string1 = context.getBean("string1", String.class);
        System.out.println("string1= " + string1);

        Integer integer1 = context.getBean("integer1", Integer.class);
        System.out.println("integer1 = " + integer1);

    }
}
