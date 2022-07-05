package eu.javaca.playground;

import eu.javaca.playground.simple.example.SimpleTestClass;

public class App {
    public static void main(String[] args) {
        Instantiable instantiable = new InstantiableImpl();
        System.out.println(instantiable.instantiate(SimpleTestClass.class, Target.XML));
    }
}
