package men.ngopi.aviedb.fitnesin.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class InstructorSerializer implements JsonSerializer<Instructor>, JsonDeserializer<Instructor> {

    @Override
    public JsonElement serialize(Instructor src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.addProperty("name", src.getName());
        object.addProperty("phone", src.getPhone());
        object.addProperty("city", src.getCity());
        object.addProperty("gender", src.getGender().toString().toLowerCase());
//        object.addProperty("photo", src.getPhoto());

        return object;
    }

    @Override
    public Instructor deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();

        return new Instructor(
                obj.get("name").getAsString(),
                Gender.valueOf(obj.get("gender").getAsString().toUpperCase()),
                obj.get("city").getAsString(),
                obj.get("phone").getAsString()
                // Add photo
        );
    }
}
