package visitor;

public final class Side implements MenuItem {
    private final String name;
    private final int priceInTenge;
    private final int calories;

    public Side(String name, int priceInTenge, int calories) {
        this.name = name;
        this.priceInTenge = priceInTenge;
        this.calories = calories;
    }

    @Override
    public void accept(OrderVisitor visitor) {
        visitor.visitSide(this);
    }

    public String getName() {
        return name;
    }

    public int getPriceInTenge() {
        return priceInTenge;
    }

    public int getCalories() {
        return calories;
    }
}
