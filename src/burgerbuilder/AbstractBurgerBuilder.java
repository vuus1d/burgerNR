package burgerbuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBurgerBuilder implements BurgerBuilder {

    protected String bun;
    protected String patty;
    protected String cheese;
    protected String sauce;

    protected final List<String> vegetables = new ArrayList<>();
    protected final List<String> extras = new ArrayList<>();

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

    protected Burger finishBurger() {
        if (bun == null || bun.isBlank()) {
            throw new IllegalStateException("Burger must have a bun");
        }

        if (patty == null || patty.isBlank()) {
            throw new IllegalStateException("Burger must have a patty");
        }

        return new Burger(
                bun,
                patty,
                cheese,
                sauce,
                vegetables,
                extras
        );
    }
}