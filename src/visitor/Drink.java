package visitor;

public final class Drink implements MenuItem {
    private final String name;
    private final int volumeInMl;
    private final int priceInTenge;
    private final int calories;

    public Drink(String name, int volumeInMl, int priceInTenge, int calories) {
        this.name = name;
        this.volumeInMl = volumeInMl;
        this.priceInTenge = priceInTenge;
        this.calories = calories;
    }

    @Override
    public void accept(OrderVisitor visitor) {
        visitor.visitDrink(this);
    }

    public String getName() {
        return name;
    }

    public int getVolumeInMl() {
        return volumeInMl;
    }

    public int getPriceInTenge() {
        return priceInTenge;
    }

    public int getCalories() {
        return calories;
    }
}
