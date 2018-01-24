package ru.otus.l081;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.*;

class TestSerial implements Serializable {
    public byte version = 100;
    public byte count = 0;
}

public class Main {
    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream("temp.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        TestSerial ts = new TestSerial();
        oos.writeObject(ts);
        oos.flush();
        oos.close();

        JsonObject model = Json.createObjectBuilder()
            .add("firstName", "Dike")
            .add("age", 28)
            .add("streetAddress", "100 Internet Dr")
            .add("phoneNumbers", Json.createArrayBuilder().add(
                Json.createObjectBuilder()
                    .add("type", "home")
                    .add("number", "222-222-2222")
            )).build();
        System.out.println("model = " + model);
    }
}
