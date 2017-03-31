import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.Optional;

/**
 *
 */
public class Main {
    public static void main(String[] args) {
        JsonParser parser = new JsonParser();
        JsonElement o = parser.parse("{\"menu\": {\n" +
                "  \"id\": \"file\",\n" +
                "  \"value\": \"File\",\n" +
                "  \"popup\": {\n" +
                "    \"menuitem\": [\n" +
                "      {\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"},\n" +
                "      {\"value\": \"Open\", \"onclick\": \"OpenDoc()\"},\n" +
                "      {\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}\n" +
                "    ]\n" +
                "  }\n" +
                "}}").getAsJsonObject();
        GsonParser gsonParser = new GsonParser(o);
        Optional<JsonNode<JsonElement>> a = gsonParser.parse("menu.popup.menuitem[2].onclick");
        if(a.isPresent()) {
            JsonNode<JsonElement> jsonElementJsonNode = a.get();
            Optional<JsonElement> object = jsonElementJsonNode.getObject();
            if(object.isPresent()) {
                JsonElement jsonElement = object.get();
                System.out.println(jsonElement.getAsString());
            }
        } else {
            System.out.println("Error");
        }
    }
}
