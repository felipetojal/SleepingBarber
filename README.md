# Sleeping Barber Problem - Java Multithreading

This repository contains a Java implementation of the classic **Sleeping Barber Problem**, an inter-process communication and synchronization problem.

This project serves as an educational demonstration of concurrent programming, race conditions prevention, and resource management using Java Threads.

## 💈 The Scenario

This implementation simulates a barbershop with the following specific rules and constraints:
* **2 Barbers:** Working simultaneously to serve customers.
* **10 Waiting Chairs:** A waiting room with a strict maximum capacity of 10 customers.
* **Customer Arrival:** A new customer arrives randomly every **4 to 6 seconds**.
* **Haircut Duration:** A haircut takes a random amount of time between **5 and 15 seconds**.

### Behaviors
* If a barber has no customers, they go to sleep.
* When a customer arrives:
    * If a barber is sleeping, the customer wakes them up and gets a haircut.
    * If both barbers are busy, the customer sits in the waiting room.
    * If the waiting room is full (10 customers already waiting), the new customer leaves frustrated.

## 🛠️ Technology Stack

* **Language:** Java (JDK 8+)