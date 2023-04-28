package Ser_DSer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Deserialize {
    Object e;
    public Deserialize(String file){
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.e = (Object) in.readObject();
            in.close();
            fileIn.close();
         } catch (IOException i) {
            i.printStackTrace();
         } catch (ClassNotFoundException c) {
            c.printStackTrace();
         }
    }

    public Object getObject(){
        return this.e;
    }
}
