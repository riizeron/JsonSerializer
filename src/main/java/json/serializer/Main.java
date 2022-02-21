package json.serializer;

import json.serializer.exceptions.NoRequiredAnnotationException;
import json.serializer.models.JsonSerializer;
import json.serializer.models.Person;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
        Person person = new Person("Dead", "Inside", "13");
        JsonSerializer<Person> serializer = null;
        try {
            serializer = new JsonSerializer<>(Person.class);
        } catch (NoRequiredAnnotationException ex) {
            System.out.println(ex.getMessage());
        }
        assert serializer != null;
        JSONObject json = serializer.serialize(person);
        System.out.println(json);
    }
}
