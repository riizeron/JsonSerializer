package json.serializer.models;

import json.serializer.annotations.IgnoreNull;
import json.serializer.annotations.JsonElement;
import json.serializer.annotations.JsonSerializable;
import json.serializer.exceptions.NoRequiredAnnotationException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonSerializerTest {

    @Test
    public void serializationOnNotAnnotatedObjectShouldThrowNoRequiredAnnotationException() {
        class NotAnnotated { }
        assertThrows(NoRequiredAnnotationException.class,
                () -> new JsonSerializer<>(NotAnnotated.class));
    }

    @Test
    public void nullFieldsShouldBeReplacedIfClassDoesNotAnnotatedWithIgnoreNull() throws NoRequiredAnnotationException {
        @JsonSerializable
        class NotIgnoreNullClass {
            @JsonElement
            public String nullstr;
        }
        JsonSerializer<NotIgnoreNullClass> serializer = new JsonSerializer<>(NotIgnoreNullClass.class);
        JSONObject res = serializer.serialize(new NotIgnoreNullClass());
        assertEquals(res.get("nullstr"), "null");
    }

    @Test
    public void nullFieldsShouldBeSkippedIfClassAnnotatedWithIgnoreNull() throws NoRequiredAnnotationException {
        @JsonSerializable
        @IgnoreNull
        class IgnoreNullClass {
            @JsonElement
            public String nullstr;
        }
        JsonSerializer<IgnoreNullClass> serializer = new JsonSerializer<>(IgnoreNullClass.class);
        JSONObject res = serializer.serialize(new IgnoreNullClass());
        assertFalse(res.has("nullstr"));
    }

    @Test
    public void onlyAnnotatedWithJsonElementFieldsShouldBeSerialized() throws NoRequiredAnnotationException {
        @JsonSerializable
        class SomeClass {
            @JsonElement
            String yes;

            String not;
        }
        JsonSerializer<SomeClass> serializer = new JsonSerializer<>(SomeClass.class);
        JSONObject res = serializer.serialize(new SomeClass());
        assertTrue(res.has("yes"));
        assertFalse(res.has("not"));
    }

    @Test
    public void serializedFieldsNamesShouldBeSameAsJsonElementValueParamIfItWasPoint() throws NoRequiredAnnotationException {
        @JsonSerializable
        class SomeClass {
            @JsonElement("YEAAHHH")
            String yes;
        }
        JsonSerializer<SomeClass> serializer = new JsonSerializer<>(SomeClass.class);
        JSONObject res = serializer.serialize(new SomeClass());
        assertTrue(res.has("YEAAHHH"));
        assertFalse(res.has("yes"));
    }
}