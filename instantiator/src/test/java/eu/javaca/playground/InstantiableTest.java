package eu.javaca.playground;

import eu.javaca.playground.simple.example.SimpleTestClass;
import org.junit.jupiter.api.Test;
import schemabindings31._int.icao.iwxxm._3.AIRMETType;

import static org.junit.jupiter.api.Assertions.*;

class InstantiableTest {

    @Test
    void instantiateComplexRealWorldXmlExample() {
        Instantiable i = (any, target) -> {
           throw new RuntimeException("not implemented");
        };
        fail(i.instantiate(AIRMETType.class, Target.XML));
    }


    @Test
    void instantiateSimpleJsonExample() {
        Instantiable i = (any, target) -> {
            throw new RuntimeException("not implemented");
        };
        fail(i.instantiate(SimpleTestClass.class, Target.JSON));
    }
}