# Irrgarten — Object-Oriented Labyrinth Game (Java & Ruby)

Turn-based labyrinth escape game developed in **Java** and **Ruby** to apply Object-Oriented Programming (OOP) design principles, architectural patterns, and cross-language idioms.

## Overview
Players navigate a 2D grid labyrinth containing obstacles, immobile monsters, and an exit cell. Reaching a monster triggers automatic turn-based combat using weapons and shields with limited durability. Defeated players can resurrect with a configurable probability, transforming into stochastic `FuzzyPlayer` instances.

## Key Engineering & OOP Concepts
* **Model-View-Controller (MVC):** Strict separation between game logic (`Game`, `Labyrinth`), controller (`Controller`), and views via a common `UI` interface.
* **Dual Interface (Java):** Supports both a console-based view (`TextUI`) and a **Java Swing GUI** (`JFrame` main window + modal `JDialog` directional pad).
* **Inheritance & Polymorphism:** Abstract base classes `CombatElement` (generalizing `Weapon` and `Shield`) and `LabyrinthCharacter` (generalizing `Player`, `Monster`, and `FuzzyPlayer`).
* **Generics (Java):** Parametric `CardDeck<T extends CombatElement>` for managing randomized weapon and shield decks.
* **Cross-Language Implementation:** Contrasts statically typed Java (`enum`, copy constructors, generics) with dynamically typed Ruby (symbol-based modules, copy methods, `snake_case` conventions).

## How to Run

### Java (GUI / Console)
```bash
cd java/src
javac irrgarten/*.java irrgarten/UI/*.java irrgarten/controller/*.java
java irrgarten.Main
