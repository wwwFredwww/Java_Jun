package org.example.Lec3;

import java.io.IOException;
import java.util.ArrayList;

import static org.example.Lec3.Main3.deSerialObj;

public class Main4 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ArrayList<String> list = null;
        list = (ArrayList<String>) deSerialObj("ser");
        System.out.println(list);
    }
}
