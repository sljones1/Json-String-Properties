import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Optional;

/**
 *
 */
public class GsonNode extends JsonNode<JsonElement> {
    public GsonNode(String option) {
        super(option);
    }

    @Override
    JsonNode<JsonElement> get(JsonElement object) {
        Optional<Integer> array = isArray();
        if(array.isPresent()) {
            JsonArray asJsonArray = object.getAsJsonArray();
            if(asJsonArray != null) {
                try {
                    JsonElement jsonElement = asJsonArray.get(array.get());
                    if (jsonElement != null) {
                        jsonElement.getAsJsonObject();
                        return checkNode(jsonElement.getAsJsonObject());
                    }
                } catch (IndexOutOfBoundsException e) {

                }
            }
        } else {
            JsonObject asJsonObject = object.getAsJsonObject();
            if(asJsonObject != null) {
                JsonElement jsonElement = asJsonObject.get(this.option);
                if (jsonElement != null) {
                    return checkNode(jsonElement);
                }
            }
        }
        return null;
    }
}
