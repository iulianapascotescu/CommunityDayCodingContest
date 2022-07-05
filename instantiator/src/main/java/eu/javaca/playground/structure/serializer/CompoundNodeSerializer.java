package eu.javaca.playground.structure.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import eu.javaca.playground.structure.CompoundNode;
import eu.javaca.playground.structure.GenericNode;

import java.io.IOException;

public class CompoundNodeSerializer extends StdSerializer<CompoundNode> {

    public CompoundNodeSerializer() {
        this(null);
    }

    public CompoundNodeSerializer(Class<CompoundNode> t) {
        super(t);
    }

    @Override
    public void serialize(CompoundNode node, JsonGenerator generator, SerializerProvider provider) throws IOException {
        if (node.isList()) {
            generator.writeArrayFieldStart(node.getName());
            writeNodes(node, generator);
            generator.writeEndArray();
            return;
        }
        generator.writeStartObject();
        writeNodes(node, generator);
        generator.writeEndObject();
    }

    private void writeNodes(CompoundNode node, JsonGenerator generator) throws IOException {
        for (GenericNode object : node.getNodes()) {
            generator.writeObject(object);
        }
    }
}
