package men.ngopi.aviedb.fitnesin.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.internal.bind.util.ISO8601Utils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;

public class InstructorSerializer implements JsonSerializer<Instructor>, JsonDeserializer<Instructor> {

    @Override
    public JsonElement serialize(Instructor src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        Calendar birthdate = src.getBirthdate();
        String formatted = ISO8601Utils.format(birthdate.getTime());

        object.addProperty("name", src.getName());
        object.addProperty("phone", src.getPhone());
        object.addProperty("city", src.getCity());
        object.addProperty("gender", src.getGender().toString().toLowerCase());
        object.addProperty("birthdate", formatted);
//        object.addProperty("photo", src.getPhoto());

        return object;
    }

    @Override
    public Instructor deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();

        Calendar calendar = Calendar.getInstance();
        try {
            Date date = ISO8601Utils.parse(obj.get("birthdate").getAsString(), new ParsePosition(0));
            calendar.setTime(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        Instructor instructor = new Instructor(
                obj.get("name").getAsString(),
                Gender.valueOf(obj.get("gender").getAsString().toUpperCase()),
                obj.get("city").getAsString(),
                obj.get("phone").getAsString()
                // Add photo
        );

        instructor.setBirthdate(calendar);
        return instructor;
    }
}
