package eu.javaca.playground.structure;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import eu.javaca.playground.structure.serializer.ObjectNodeSerializer;

@JsonSerialize(using = ObjectNodeSerializer.class)
public class ObjectNode extends GenericNode {
    private GenericNode genericNode;

    public ObjectNode() {
    }

    public GenericNode getComplexObject() {
        return genericNode;
    }

    public static class ObjectNodeBuilder {
        private final ObjectNode objectNode;

        public ObjectNodeBuilder() {
            objectNode = new ObjectNode();
        }

        public ObjectNodeBuilder setGenericNode(GenericNode genericNode) {
            objectNode.genericNode = genericNode;
            return this;
        }

        public ObjectNodeBuilder setName(String name) {
            objectNode.name = name;
            return this;
        }

        public ObjectNode getNode() {
            return objectNode;
        }
    }
}
