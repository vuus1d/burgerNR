package burgerbuilder;

import java.util.List;

public class Burger {
    private final String bun;
    private final String patty;
    private final String cheese;
    private final String sauce;
    private final List<String> vegetables;
    private final List<String> extras;

    public Burger(String bun, String patty, String cheese,
                  String sauce, List<String> vegetables,
                  List<String> extras) {
        this.bun = bun;
        this.patty = patty;
        this.cheese = cheese;
        this.sauce = sauce;
        this.vegetables = List.copyOf(vegetables);
        this.extras = List.copyOf(extras);
    }

    @Override
    public String toString() {
        return "Burger{" +
                "bun='" + bun + '\'' +
                ", patty='" + patty + '\'' +
                ", cheese='" + cheese + '\'' +
                ", sauce='" + sauce + '\'' +
                ", vegetables=" + vegetables +
                ", extras=" + extras +
                '}';
    }
}