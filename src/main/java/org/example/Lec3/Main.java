package org.example.Lec3;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        String str = "Hello, everyone!";
        FileOutputStream fileOutputStream = new FileOutputStream("ser");
        ObjectOutputStream objectOutputStream = new
                ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(str);
        objectOutputStream.close();
    }
}
