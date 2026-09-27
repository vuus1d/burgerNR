# Burger Design Patterns

## Project Description

This project demonstrates creational and structural design patterns using a burger ordering system.

The project includes four design patterns:

- Builder (creational)
- Factory Method (creational)
- Abstract Factory (creational)
- Bridge (structural)

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
└── bridge/
    ├── CookingMethod.java
    ├── GrillCooking.java
    ├── DeepFryerCooking.java
    ├── AirFryerCooking.java
    ├── BurgerRecipe.java
    ├── SmashBurgerRecipe.java
    ├── ChickenBurgerRecipe.java
    └── BridgeDemo.java

docs/
└── bridge-uml.png
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

Project Goal

The main goal of the project is to demonstrate how creational design patterns can be applied to a burger ordering system and how they separate object creation from the main application logic.


