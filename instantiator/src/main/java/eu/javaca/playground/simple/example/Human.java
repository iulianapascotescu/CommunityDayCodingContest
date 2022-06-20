package eu.javaca.playground.simple.example;

public class Human extends Worker {

    private String name;

    @Override
    public void doWork() {
        throw new RuntimeException("not implemented");
    }
}
