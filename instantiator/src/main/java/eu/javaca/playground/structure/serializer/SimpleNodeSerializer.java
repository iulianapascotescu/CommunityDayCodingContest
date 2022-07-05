package eu.javaca.playground.structure.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import eu.javaca.playground.structure.SimpleNode;

import java.io.IOException;

public class SimpleNodeSerializer extends StdSerializer<SimpleNode> {

    public SimpleNodeSerializer() {
        this(null);
    }

    public SimpleNodeSerializer(Class<SimpleNode> t) {
        super(t);
    }

    @Override
    public void serialize(SimpleNode node, JsonGenerator generator, SerializerProvider provider) throws IOException {
        if (node.isMapElement()) {
            generator.writeStartObject();
            generator.writeStringField(node.getName(), node.getValue());
            generator.writeEndObject();
            return;
        }
        if (node.isShowName()) {
            generator.writeStringField(node.getName(), node.getValue());
            return;
        }
        generator.writeString(node.getValue());
    }
}