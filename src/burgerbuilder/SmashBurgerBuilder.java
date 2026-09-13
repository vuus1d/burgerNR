package burgerbuilder;

import java.util.ArrayList;
import java.util.List;

public class SmashBurgerBuilder implements BurgerBuilder {

    private String bun;
    private String patty;
    private String cheese;
    private String sauce;
    private final List<String> vegetables = new ArrayList<>();
    private final List<String> extras = new ArrayList<>();

    @Override
    public BurgerBuilder setBun(String bun) {
        this.bun = bun;
        return this;
    }

    @Override
    public BurgerBuilder setPatty(String patty) {
        this.patty = patty;
        return this;
    }

    @Override
    public BurgerBuilder setCheese(String cheese) {
        this.cheese = cheese;
        return this;
    }

    @Override
    public BurgerBuilder setSauce(String sauce) {
        this.sauce = sauce;
        return this;
    }

    @Override
    public BurgerBuilder addVegetable(String vegetable) {
        vegetables.add(vegetable);
        return this;
    }

    @Override
    public BurgerBuilder addExtra(String extra) {
        extras.add(extra);
        return this;
    }

    @Override
    public Burger build() {
        return new Burger(bun, patty, cheese, sauce, vegetables, extras);
    }
}