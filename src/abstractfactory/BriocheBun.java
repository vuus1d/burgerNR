package abstractfactory;

public class BriocheBun implements Bun {

    @Override
    public void prepare() {
        System.out.println("Preparing brioche bun");
    }
}