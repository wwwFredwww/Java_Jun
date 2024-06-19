package org.example.Lec3;

import java.io.IOException;
import java.io.Serializable;

import static org.example.Lec3.Main3.deSerialObj;
import static org.example.Lec3.Main3.serialObj;

public class Main5 {
    public static class MyFCs implements Serializable  {
        public String lName;
        public String fName;
        public String patronymic;

        public MyFCs(String fName, String lName, String patronymic) {
            this.lName = lName;
            this.fName = fName;
            this.patronymic = patronymic;
        }

        @Override
        public String toString() {
            return String.format("%s %s.%s ",
                    fName,
                    lName.toUpperCase().charAt(0),
                    patronymic.toUpperCase().charAt(0));
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        MyFCs myFCs = new MyFCs("Ivanov", "Ivan", "Ivanovich");
        serialObj(myFCs, "ser");

//        MyFCs myFCs = (MyFCs) deSerialObj("ser");
//        System.out.println(myFCs);
    }


}
