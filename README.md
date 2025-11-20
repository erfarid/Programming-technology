# Capitaly Game Simulation

This project simulates a simplified version of the **Capitaly board game** using Java and Swing. The program demonstrates **object-oriented programming (OOP) concepts** such as inheritance, polymorphism, encapsulation, and strategy patterns.  

> **Note:** This repository contains multiple Java projects. The Capitaly game is implemented as one of the projects to showcase OOP and game simulation concepts.

---

## Game Overview

In this simulation:

- Players move around a cyclical board based on dice rolls.
- The board consists of different types of fields:
  - **Property Field**: Can be bought for 1000. A house can be built for 4000. Players pay rent to owners (500 without a house, 2000 with a house).
  - **Service Field**: Players pay an amount to the bank (defined per field).
  - **Lucky Field**: Players receive money (defined per field).
- Players start with a balance of 10000.
- Three types of player strategies exist:
  - **Greedy**: Buys unowned properties or own properties without a house if enough money is available.
  - **Careful**: Buys properties but only spends at most half of their balance in a round.
  - **Tactical**: Skips every second buying opportunity.
- Players who run out of money during payments lose, and their properties become available again.

---

## Features

- Supports multiple player strategies.
- Reads **game configuration** (fields, costs, number of players, player strategies) from a text file.
- Supports **predefined dice rolls** for testing purposes.
- Prints player status after each round (balance, owned properties).
- Object-oriented design allows easy extension for new field types, strategies, or rules.
- Includes **white-box tests** to verify logic and gameplay scenarios.

---





