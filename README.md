# Burger Design Patterns

## Project Description

This project demonstrates three creational design patterns using a burger system:

- Builder
- Factory Method
- Abstract Factory

The project is implemented in Java using JDK 17.

---

# 1. Builder Pattern

The Builder Pattern is used to construct a burger step by step from different components.

The burger can contain:

- bun
- patty
- cheese
- sauce
- vegetables
- extras

## Builder Components

- `Burger` - the final product.
- `BurgerBuilder` - interface that defines the burger construction steps.
- `AbstractBurgerBuilder` - contains common construction logic and validation.
- `SmashBurgerBuilder` - creates a smash burger.
- `ChickenBurgerBuilder` - creates a chicken burger.
- `BurgerDirector` - provides predefined burger configurations.
- `Main` - demonstrates how the Builder Pattern works.

## Burger Representations

### Smash Burger

- Brioche bun
- Double beef patty
- Pepper Jack cheese
- Spicy Mayo
- Pickles
- Jalapeno
- Crispy Bacon

### Chicken Burger

- Sesame bun
- Grilled chicken
- Swiss cheese
- Garlic Mayo
- Lettuce
- Tomato
- Crispy Onions

## Method Chaining

The Builder provides a fluent API, so burger components can be configured step by step:

```java
Burger customBurger = new SmashBurgerBuilder()
        .setBun("Brioche")
        .setPatty("Double Beef")
        .setCheese("Pepper Jack")
        .setSauce("Spicy Mayo")
        .addVegetable("Pickles")
        .addVegetable("Jalapeno")
        .addExtra("Crispy Bacon")
        .build();