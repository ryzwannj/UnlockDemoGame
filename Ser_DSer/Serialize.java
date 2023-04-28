package Ser_DSer;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Serialize {

   public Serialize(Object o, String file){
      try {
         FileOutputStream fileOut = new FileOutputStream(file);
         ObjectOutputStream out = new ObjectOutputStream(fileOut);
         out.writeObject(o);
         out.close();
         fileOut.close();
         System.out.println(o.getClass());
         System.out.println("Serialized data is saved in " + file);
      } catch (IOException i) {
         i.printStackTrace();
      }
   }

public Serialize(boolean finshed, String file) {
   try {
      FileOutputStream fileOut = new FileOutputStream(file);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(finshed);
      out.close();
      fileOut.close();
      System.out.println(finshed);
      System.out.println("Serialized data is saved in " + file);
   } catch (IOException i) {
      i.printStackTrace();
   }
}
}

