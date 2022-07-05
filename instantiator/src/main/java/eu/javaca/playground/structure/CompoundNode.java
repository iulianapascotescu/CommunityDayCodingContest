package eu.javaca.playground.structure;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import eu.javaca.playground.structure.serializer.CompoundNodeSerializer;

import java.util.ArrayList;
import java.util.List;

@JsonSerialize(using = CompoundNodeSerializer.class)
public class CompoundNode extends GenericNode {
    private boolean isList = false;
    private List<GenericNode> nodes = new ArrayList<>();

    public CompoundNode() {
    }

    public boolean isList() {
        return isList;
    }

    public List<GenericNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<GenericNode> nodes) {
        this.nodes = nodes;
    }

    public static class CompoundNodeBuilder {
        private final CompoundNode compoundNode;

        public CompoundNodeBuilder() {
            compoundNode = new CompoundNode();
        }

        public CompoundNodeBuilder isList() {
            compoundNode.isList = true;
            return this;
        }

        public CompoundNodeBuilder setName(String name) {
            compoundNode.name = name;
            return this;
        }

        public CompoundNodeBuilder setNodes(List<GenericNode> nodes) {
            compoundNode.nodes = nodes;
            return this;
        }

        public CompoundNode getNode() {
            return compoundNode;
        }
    }
}
