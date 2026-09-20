# Burger Design Patterns

## Project Description

This project demonstrates creational design patterns using a burger ordering system.

The project includes three design patterns:

- Builder
- Factory Method
- Abstract Factory

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
└── abstractfactory/
    ├── Bun.java
    ├── Sauce.java
    ├── BriocheBun.java
    ├── SesameBun.java
    ├── BBQSauce.java
    ├── GarlicSauce.java
    ├── BurgerIngredientFactory.java
    ├── BeefBurgerIngredientFactory.java
    ├── ChickenBurgerIngredientFactory.java
    ├── BurgerApplication.java
    └── AbstractFactoryDemo.java

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

Project Goal

The main goal of the project is to demonstrate how creational design patterns can be applied to a burger ordering system and how they separate object creation from the main application logic.


