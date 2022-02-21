package json.serializer.models;

import json.serializer.annotations.IgnoreNull;
import json.serializer.annotations.JsonElement;
import json.serializer.annotations.JsonSerializable;
import json.serializer.exceptions.NoRequiredAnnotationException;
import org.json.JSONObject;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Set;

public class JsonSerializer<T> {
    private final Set<Field> publishedFields;
    private final boolean ignoreNull;

    public JsonSerializer(Class<T> serializedClass) throws NoRequiredAnnotationException {
        if (!serializedClass.isAnnotationPresent(JsonSerializable.class)) {
            throw new NoRequiredAnnotationException("@JsonSerializable annotation not declared");
        }
        publishedFields = ReflectionUtils.getAllFields(serializedClass,
                ReflectionUtils.withAnnotation(JsonElement.class));
        publishedFields.forEach(f -> f.setAccessible(true));
        ignoreNull = serializedClass.isAnnotationPresent(IgnoreNull.class);
    }

    public JSONObject serialize(T obj) {
        JSONObject res = new JSONObject();
        Object fieldVal = null;
        String name;
        for (Field f : publishedFields) {
            try {
                fieldVal = f.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            name = f.getAnnotation(JsonElement.class).value().isEmpty() ? f.getName()
                    : f.getAnnotation(JsonElement.class).value();
            if (!ignoreNull && fieldVal == null) {
                res.put(name, "null");
            } else if (fieldVal != null) {
                res.put(name, fieldVal);
            }
        }
        return res;
    }
}
