# Burger Design Patterns

## Project Description

This project demonstrates creational, structural and behavioral design patterns using a burger ordering system.

The project includes six design patterns:

- Builder (creational)
- Factory Method (creational)
- Abstract Factory (creational)
- Bridge (structural)
- Facade (structural)
- Strategy (behavioral)

The project is implemented in Java using JDK 17.

## Project Structure

```text
src/
├── burgerbuilder/
│   ├── AbstractBurgerBuilder.java
│   ├── Burger.java
│   ├── BurgerBuilder.java
│   ├── BurgerDirector.java
│   ├── ChickenBurgerBuilder.java
│   └── SmashBurgerBuilder.java
│
├── factorymethod/
│   ├── BurgerFactory.java
│   ├── BurgerProduct.java
│   ├── ChickenBurger.java
│   ├── ChickenBurgerFactory.java
│   ├── FactoryMethodDemo.java
│   ├── SmashBurger.java
│   └── SmashBurgerFactory.java
│
├── abstractfactory/
│   ├── Bun.java
│   ├── Sauce.java
│   ├── BriocheBun.java
│   ├── SesameBun.java
│   ├── BBQSauce.java
│   ├── GarlicSauce.java
│   ├── BurgerIngredientFactory.java
│   ├── BeefBurgerIngredientFactory.java
│   ├── ChickenBurgerIngredientFactory.java
│   ├── BurgerApplication.java
│   └── AbstractFactoryDemo.java
│
├── bridge/
│   ├── CookingMethod.java
│   ├── GrillCooking.java
│   ├── DeepFryerCooking.java
│   ├── AirFryerCooking.java
│   ├── BurgerRecipe.java
│   ├── SmashBurgerRecipe.java
│   ├── ChickenBurgerRecipe.java
│   └── BridgeDemo.java
│
├── facade/
│   ├── BurgerOrderFacade.java
│   ├── OrderFailedException.java
│   ├── FacadeDemo.java
│   ├── AdvancedClientDemo.java
│   ├── model/        Money, Ingredient, BurgerType, OrderRequest, OrderConfirmation, DeliveryStatus, FailureReason
│   └── subsystem/    InventoryService, PaymentGateway, SimulatedPaymentGateway, KitchenStation,
│                     PackagingService, DeliveryDispatcher, SubsystemException and its subclasses, value types
│
└── strategy/
    ├── DiscountStrategy.java
    ├── NoDiscount.java
    ├── StudentDiscount.java
    ├── HappyHourDiscount.java
    ├── ComboDiscount.java
    ├── BurgerCheckout.java
    └── StrategyDemo.java

test/
├── boundaries/       learning tests for BigDecimal, Clock and Map/List
├── facade/           BurgerOrderFacadeTest, test language (OrderRequests, OrderAssertions), fakes
│   ├── model/
│   └── subsystem/    one test class per subsystem class
└── strategy/         one test class per discount strategy, BurgerCheckoutTest

docs/
├── bridge-uml.png
└── strategy-report.md

pom.xml               Maven build: JDK 17, JUnit 5
```

Builder Pattern

The Builder Pattern is used to construct a burger step by step.

The burger can contain:

Bun
Patty
Cheese
Sauce
Vegetables
Extras

The main Builder classes are:

Burger
BurgerBuilder
AbstractBurgerBuilder
SmashBurgerBuilder
ChickenBurgerBuilder
BurgerDirector
Factory Method Pattern

The Factory Method Pattern is used to create different burger products.

The main classes are:

BurgerProduct
SmashBurger
ChickenBurger
BurgerFactory
SmashBurgerFactory
ChickenBurgerFactory

FactoryMethodDemo demonstrates how the pattern works.

Abstract Factory Pattern

The Abstract Factory Pattern is used to create families of related burger ingredients.

The main products are:

Bun
Sauce

Concrete products include:

BriocheBun
SesameBun
BBQSauce
GarlicSauce

The factories are:

BurgerIngredientFactory
BeefBurgerIngredientFactory
ChickenBurgerIngredientFactory

AbstractFactoryDemo demonstrates how the pattern works.

## Bridge Pattern

The Bridge Pattern separates WHAT burger is made from HOW it is cooked, so both sides can change independently.

WHAT burger is made (Abstraction):

- BurgerRecipe — abstract recipe that defines the preparation steps
- SmashBurgerRecipe
- ChickenBurgerRecipe

HOW it is cooked (Implementor):

- CookingMethod — interface with low-level kitchen operations
- GrillCooking
- DeepFryerCooking
- AirFryerCooking

| Role | Class |
|------|-------|
| Abstraction | BurgerRecipe |
| Refined Abstraction | SmashBurgerRecipe, ChickenBurgerRecipe |
| Implementor | CookingMethod |
| Concrete Implementor | GrillCooking, DeepFryerCooking, AirFryerCooking |
| Client | BridgeDemo |

The private field `cookingMethod` in BurgerRecipe is the bridge. It links the two hierarchies through composition, not inheritance.

2 recipes × 3 cooking methods = 6 combinations, built from only 5 concrete classes. Without the Bridge, every combination would need its own subclass (N × M classes, e.g. GrilledSmashBurger, DeepFriedChickenBurger, ...).

AirFryerCooking was added as a new Implementor without changing any recipe class.

The cooking method of the same burger can be switched at runtime with `changeCookingMethod(...)`.

UML diagram: [docs/bridge-uml.png](docs/bridge-uml.png)

![Bridge UML](docs/bridge-uml.png)

BridgeDemo demonstrates how the pattern works.

## Facade Pattern

The Facade Pattern gives one simple entry point to a complex subsystem. Placing a burger order needs five collaborating classes called in the right order: reserve ingredients, charge the payment, cook, pack and schedule delivery. If a later step fails, the earlier ones must be undone (refund, release the ingredients).

`BurgerOrderFacade` hides all of this behind two methods:

- `placeOrder(OrderRequest)` returns an `OrderConfirmation` or throws a single `OrderFailedException` with a `FailureReason`
- `trackOrder(orderId)` returns a `DeliveryStatus` (`UNKNOWN` for an unknown order)

| Role | Class |
|------|-------|
| Facade | BurgerOrderFacade |
| Subsystem classes | InventoryService, PaymentGateway / SimulatedPaymentGateway, KitchenStation, PackagingService, DeliveryDispatcher |
| Client | FacadeDemo |

The facade hides the order of the steps, the rollback and the subsystem exceptions. No subsystem type appears in its public methods. The subsystem classes do not know the facade exists and stay public: `AdvancedClientDemo` deliberately bypasses the facade and orchestrates them by hand, including the manual rollback.

## Strategy Pattern

The Strategy Pattern defines a family of algorithms, puts each one in its own class and makes them interchangeable. Here the algorithms are discounts on a burger order.

- DiscountStrategy — interface with `applyDiscount(priceInTenge)` and `getName()`
- NoDiscount — the price does not change
- StudentDiscount — 10% off
- HappyHourDiscount — 20% off
- ComboDiscount — 500 tenge off, but the price never goes below 0

| Role | Class |
|------|-------|
| Strategy | DiscountStrategy |
| Concrete Strategy | NoDiscount, StudentDiscount, HappyHourDiscount, ComboDiscount |
| Context | BurgerCheckout |
| Client | StrategyDemo |

`BurgerCheckout` receives a `DiscountStrategy` through its constructor and simply delegates `calculateTotal(...)` to it. It has no `if`/`switch` on the discount type and does not know any concrete strategy class.

The strategy of the same checkout can be switched at runtime with `setDiscountStrategy(...)`.

To add a new discount, create one new class that implements `DiscountStrategy` and pass it to the checkout. The interface, `BurgerCheckout` and the other strategies do not change. ComboDiscount was added this way.

Report with the UML diagram: [docs/strategy-report.md](docs/strategy-report.md)

StrategyDemo demonstrates how the pattern works.

The tests use JUnit 5 and are run with Maven. In IntelliJ IDEA, right-click `pom.xml` and choose "Add as Maven Project" so that `test/` is recognised as a test folder.

Technologies
Java
JDK 17
IntelliJ IDEA
Git
GitHub
How to Run

Run Main to test the Builder Pattern.

Run FactoryMethodDemo to test the Factory Method Pattern.

Run AbstractFactoryDemo to test the Abstract Factory Pattern.

Run bridge.BridgeDemo to test the Bridge Pattern.

Run facade.FacadeDemo to test the Facade Pattern, and facade.AdvancedClientDemo to see the same subsystem used without the facade.

Run strategy.StrategyDemo to test the Strategy Pattern.

Run the unit tests with `mvn test`, or from IntelliJ IDEA after adding `pom.xml` as a Maven project.

Project Goal

The main goal of the project is to demonstrate how creational design patterns can be applied to a burger ordering system and how they separate object creation from the main application logic.


