package Read_Write_file;

import Product.Customer;

import java.io.*;
import java.util.ArrayList;

public class IO_Read_Write_File<E> {
    public void writerFile(ArrayList<E> arrayData, String pathname) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(pathname)));
            objectOutputStream.writeObject(arrayData);
            objectOutputStream.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    public ArrayList<E> readFile(String pathname) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(pathname));
            return (ArrayList<E>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println();
        }
        return null;
    }
}
