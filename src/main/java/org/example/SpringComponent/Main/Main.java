package org.example.SpringComponent.Main;

import org.example.SpringComponent.Config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Parrot parrot = context.getBean(Parrot.class);

        System.out.println("parrot= " + parrot);

        System.out.println("parrot.getParrotNAme()= " + parrot.getParrotName());

    }
}
