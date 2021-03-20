# 🏡 DDD Home

## On what I decided to ~~waste~~ dedicate my time? 🤔

DDD Home is a project about modeling the Home Domain using __plain [Kotlin](https://kotlinlang.org/) and [JUnit5](https://junit.org/junit5/) only__.

See what's already modeled at [doc/Domain.md](./doc/Domain.md) 😄

### What will I model?

Stuff I might model:

- Different users of the home like:
    - 👩‍💼 Leaseholder
    - 🧑 Lessor
    - 🧟 Guests
    - ...
    
- Home Rooms:
    - 💡 Living Room
    - 🛌 Bed Room
    - ...
    
- Utilities
    - 🧻 Toiled paper
    - 💧 Water
    - 🥖 Food
    

and how everything interacts between them.

I would like to focus on:

- Encapsulation (that's what we discover in the IT industry every 5 years 😂)
- SOLID
- Ubiquitous Language
- Proper Testing
- __Incremental modelling__

What you won't find here:

- CQRS
- Event Sourcing
- High Availability System

## How could you be involved?

- Creating a new area to be modeled as an issue, and then I will try to model it. Example:

> As person, I want to put the food I bought in the freezer, so that I consume it later without rotting.

I might need to model various things from there, freezer (full/empty), food that can get rotted after X days when inside the freezer or after X-N days if it's outside it.

Cool, isn't it?

## License

Copyright (c) 2021 Aleix Morgadas Licensed under the [MIT License](./LICENSE)