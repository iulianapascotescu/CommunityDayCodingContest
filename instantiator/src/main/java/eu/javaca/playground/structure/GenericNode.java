package eu.javaca.playground.structure;

public abstract class GenericNode {
    protected String name;

    public GenericNode() {
    }

    public GenericNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
