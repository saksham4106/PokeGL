# PokeGL
PokeGL is a 2D game built using the Light Weight Java Gaming Library (LWJGL), a Java based wrapper for OpenGL. 

![gameplay.gif](gameplay.gif)

## Installation
Clone the project and run the gradle task
```
  gradlew run
```

## Build
Run the following gradle task to compile/build a runnable FatJar
```
  gradlew fatJar
```

## Project Structure
The repository has a lot of files and folders after setting up a workspace. This is a guide to all the files and folders.

```

      File Location                          | Description
/
├──src/main/                                 |
   ├── java                                  | Source Java Code
   |   ├── animation/                        | Animation handler
   |   ├── audio/                            | Audio handler
   |   ├── callback/                         | IO callbacks
   |   ├── collision/                        | Collision Detection
   |   ├── entity/                           | Inheritance Based Game Objects
   |   ├── events/                           | Custom and GLFW based Event Handling
   |   ├── fonts/                            | Custom Text Parsing and Rendering
   |   ├── game/                             | Core Game features
   |   |   └── components/                   | Basic Entity Component System
   |   ├── particles/                        | Particle Handling
   |   ├── pokemon/                          | Hardcoded Pokemon values folder
   |   |   ├── pokemons/                     | List of all Pokemons 
   |   |   └── properties/                   | Associated properties of Pokemon i.e Moves, Types, Abilities
   |   ├── renderer/                         | Handles core rendering 
   |   ├── scenes/                           | Stack-Based Scene manager
   |   ├── serialization/                    | Saving and loading serialized Game state
   |   ├── tiles/                            | Tiled Map parsing  
   |   ├── ui/                               | Custom UI objects
   |   ├── utils/                            | Utility classes 
   |   ├── world/                            | Procedural World Generation (Unused)
   |   └── Main.java                         | Entrypoint
   |
   ├── resources/assets/                     | Game Assets and Resource Files
   |   ├── fonts                             | Font, TTF files
   |   ├── map                               | Tiled Maps
   |   ├── shaders                           | GLSL Shaders 
   |   ├── textures                          | PNG Textures
   |   ├── sounds                            | OGG Sounds
   |   └── logback.xml                       | Customization for Logger
   |
   ├── helperScripts/                        | One-Time Web Scraping scripts for Pokemon Data from PokeAPI 
   └── data/                                 | Serialized Game State
```

## Features
 - [x] Batched Rendering
 - [x] Tiled Map Parsing
 - [x] Game State Serialization
 - [x] Font Rendering
 - [x] UI
 - [x] Stack Based Scene Managing
 - [x] Event Driven System
 - [x] Collision Detection
 - [x] Basic Animation Support
 - [x] Basic Audio Support
 - [ ] Procedural World Generation
 - [ ] Multiplayer Support
 - [ ] QuadTree based Intelligent Collision Detection
 - [ ] Fully Fledged Entity Component System
 - [ ] Instanced Rendering for Particles

   
