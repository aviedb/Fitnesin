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

public class MemberSerializer implements JsonSerializer<Member>, JsonDeserializer<Member> {

    @Override
    public JsonElement serialize(Member src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        Calendar birthdate = src.getBirthdate();
        String formatted = ISO8601Utils.format(birthdate.getTime());

        object.addProperty("name", src.getName());
        object.addProperty("phone", src.getPhone());
        object.addProperty("birthdate", formatted);
        object.addProperty("weight", src.getWeight());
        object.addProperty("height", src.getHeight());
        object.addProperty("gender", src.getGender().toString().toLowerCase());

        return object;
    }

    @Override
    public Member deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = ISO8601Utils.parse(obj.get("birthdate").getAsString(), new ParsePosition(0));
            calendar.setTime(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        Member member = new Member(
                obj.get("name").getAsString(),
                obj.get("phone").getAsString(),
                calendar,
                obj.get("weight").getAsDouble(),
                obj.get("height").getAsDouble(),
                Gender.valueOf(obj.get("gender").getAsString().toUpperCase())
        );

        return member;
    }
}
