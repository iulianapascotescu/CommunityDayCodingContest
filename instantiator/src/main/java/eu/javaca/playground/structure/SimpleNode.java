package eu.javaca.playground.structure;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import eu.javaca.playground.structure.serializer.SimpleNodeSerializer;

@JsonSerialize(using = SimpleNodeSerializer.class)
public class SimpleNode extends GenericNode {
    private boolean showName = false;
    private boolean isMapElement = false;
    private String value;

    public SimpleNode() {
    }

    public boolean isShowName() {
        return showName;
    }

    public String getValue() {
        return value;
    }

    public boolean isMapElement() {
        return isMapElement;
    }

    public static class SimpleNodeBuilder {
        private final SimpleNode simpleNode;

        public SimpleNodeBuilder() {
            simpleNode = new SimpleNode();
        }

        public SimpleNodeBuilder setValue(String value) {
            simpleNode.value = value;
            return this;
        }

        public SimpleNodeBuilder setAsMapElement() {
            simpleNode.isMapElement = true;
            return this;
        }

        public SimpleNodeBuilder setName(String name) {
            simpleNode.name = name;
            simpleNode.showName = true;
            return this;
        }

        public SimpleNode getNode() {
            return simpleNode;
        }
    }
}
