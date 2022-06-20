package eu.javaca.playground.simple.example;

public class Robot extends Worker {

    private String version;

    @Override
    public void doWork() {
        throw new RuntimeException("not implemented");
    }
}
