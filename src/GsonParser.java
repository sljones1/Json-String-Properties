import com.google.gson.JsonElement;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 *
 */
public class GsonParser {
    private JsonElement object;
    private GsonNode jsonNode = null;

    public GsonParser(JsonElement object) {
        this.object = object;
    }

    public Optional<JsonNode<JsonElement>> parse(String check) {
        String[] split = StringUtils.split(check, '.');
        for (String index : split) {
            if (isValid(index)) {
                if (isArray(index)) {
                    String[] splitArray = StringUtils.split(index, "[");
                    if (splitArray.length > 2) {
                        return Optional.empty();
                    } else {
                        if (splitArray.length == 1) {
                            setJsonNode("[" + splitArray[0]);
                        } else {
                            setJsonNode(splitArray[0]);
                            setJsonNode("[" + splitArray[1]);
                        }
                    }
                } else {
                    setJsonNode(index);
                }
            } else {
                return Optional.empty();
            }
        }
        return (jsonNode == null) ? Optional.empty() : Optional.ofNullable(jsonNode.get(object));
    }

    private boolean isValid(String index) {
        if (StringUtils.isEmpty(index)) {
            return false;
        }
        Boolean i = isArray(index);
        return i || !(StringUtils.contains(index, "]"));
    }

    private Boolean isArray(String index) {
        if (StringUtils.contains(index, "[")) {
            int i = StringUtils.indexOf(index, "[");
            return (StringUtils.endsWith(index, "]") && i < index.length() - 2);
        }
        return false;
    }

    public void setJsonNode(String value) {
        GsonNode gsonNode = new GsonNode(value);
        if (jsonNode == null) {
            jsonNode = gsonNode;
        } else {
            jsonNode.setChild(gsonNode);
        }
    }
}
