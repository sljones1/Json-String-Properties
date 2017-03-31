import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 *
 */
public abstract class JsonNode<T> {
    protected String option;
    private JsonNode node;
    private T object;

    public JsonNode(String option) {
        this.option = option;
    }

    abstract JsonNode<T> get(T object);

    public void setChild(JsonNode node) {
        if (this.node == null) {
            this.node = node;
        } else {
            this.node.setChild(node);
        }
    }

    public Optional<T> getObject(){
        return Optional.ofNullable(object);
    }

    protected JsonNode<T> checkNode(T object) {
        if (object == null) {
            return null;
        }
        if (this.node == null) {
            this.object = object;
            return this;
        } else {
            return this.node.get(object);
        }
    }

    public Optional<Integer> isArray() {
        if (StringUtils.startsWith(option, "[") && StringUtils.endsWith(option, "]")) {
            String remove = StringUtils.remove(option, '[');
            remove = StringUtils.remove(remove, ']');
            try {
                int index = Integer.parseInt(remove);
                return Optional.ofNullable(index);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }
}
