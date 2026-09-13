# Burger Builder

## Project Description

This project demonstrates the Builder Design Pattern using a burger as a complex product.

The burger is built step by step from different components such as bun, patty, cheese, sauce, vegetables and extras.

## Project Structure

- `Burger` - the final product.
- `BurgerBuilder` - interface that defines the burger construction steps.
- `AbstractBurgerBuilder` - contains common construction logic and validation.
- `SmashBurgerBuilder` - creates a spicy smash burger.
- `ChickenBurgerBuilder` - creates a chicken burger.
- `BurgerDirector` - provides predefined burger configurations.
- `Main` - demonstrates how the builders work.

## Burger Representations

### Smash Burger

- Brioche bun
- Double beef patty
- Cheddar cheese
- Smoky BBQ sauce
- Pickles
- Red onion
- Crispy bacon

### Chicken Burger

- Sesame bun
- Grilled chicken
- Swiss cheese
- Garlic mayo
- Lettuce
- Tomato
- Crispy onions

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