package abstractfactory;

public class SesameBun implements Bun {

    @Override
    public void prepare() {
        System.out.println("Preparing sesame bun");
    }
}