package eu.javaca.playground.structure.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import eu.javaca.playground.structure.ObjectNode;

import java.io.IOException;

public class ObjectNodeSerializer extends StdSerializer<ObjectNode> {

    public ObjectNodeSerializer() {
        this(null);
    }

    public ObjectNodeSerializer(Class<ObjectNode> t) {
        super(t);
    }

    @Override
    public void serialize(ObjectNode node, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeObjectField(node.getName(), node.getComplexObject());
    }
}