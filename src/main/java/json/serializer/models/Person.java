package json.serializer.models;

import json.serializer.annotations.JsonElement;
import json.serializer.annotations.JsonSerializable;

@JsonSerializable
public class Person {
    @JsonElement
    private String firstName;

    @JsonElement("familiya")
    private String lastName;

    private String age;

    @JsonElement("mass")
    private String weight;

    public Person(String firstName, String lastName, String age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}

