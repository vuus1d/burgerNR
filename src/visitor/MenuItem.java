package visitor;

public interface MenuItem {
    void accept(OrderVisitor visitor);
}
