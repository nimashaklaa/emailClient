package Hello;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

public class SerializeObjects {
    public void serializingObjects(Object serObject) throws IOException {

        String fileName = "D:\\emailClient\\Hello\\SerializedFile.ser";

        File file = new File(fileName);
        //if there is no file you can create a file here
        if (!file.exists()) {
            FileOutputStream fileStream= new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);
            objectOutputStream.writeObject(serObject);//write the object
            objectOutputStream.close();
        } else {
            try {
                //deserialized the previous file
                FileOutputStream serializedFile = new FileOutputStream(fileName, true);
                ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(serializedFile) {

                    protected void writeStreamHeader() throws IOException {
                        reset();
                    }
                };

                objectOutputStream1.writeObject(serObject);

                objectOutputStream1.close();
                serializedFile.close();


            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}




