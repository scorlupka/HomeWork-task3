package partOfGame;

import playable.*;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import placable.MyObject;

import java.io.IOException;

public class MyObjectTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!MyObject.class.isAssignableFrom(type.getRawType())) {
            return null;
        }

        return (TypeAdapter<T>) new TypeAdapter<MyCharacter>() {
            @Override
            public void write(JsonWriter out, MyCharacter value) throws IOException {
                gson.toJson(value, value.getClass(), out);
            }

            @Override
            public MyCharacter read(JsonReader in) throws IOException {
                JsonElement jsonElement = gson.toJsonTree(in);
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                // Здесь определяем конкретный класс по какому-то полю
                String type = jsonObject.get("type").getAsString();

                switch (type) {
                    case "crusader":
                        return gson.fromJson(jsonElement, Crusader.class);
                    case "skeleton":
                        return gson.fromJson(jsonElement, Skeleton.class);
                    // добавьте другие подклассы по необходимости
                    default:
                        throw new JsonParseException("Unknown type: " + type);
                }
            }
        };
    }
}