package org.example.SpringComponent.Main;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
public class Parrot {

    private String parrotName;

    @PostConstruct
    public void initName() {
        this.parrotName = "Kiki";
    }

}
