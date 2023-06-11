<br/>
<div align="center"><img src="/Images/Logo.png" alt="Logo" width="400"/></div>
<br/>

## Description
> OverwatchTD is a tower defense game using objects based on Overwatch characters. Besides enjoying the thrill of a tower defense game, it also allows players to create their own level layouts with an easy-to-use interface. As of now, the game boasts 3 playable towers and 4 enemy types.
<br/>

## Dependencies Used

- javax.swing
  > an API for providing a graphical user interface for Java programs, used in this project for rendering the game window.

- java.awt
  > an API to develop Graphical User Interface (GUI) or windows-based applications in Java, used in this project for setting the window size, rendering and rotating in-game images, displaying text, projectile movement, forming object bounds using the built-in Rectangle class, and implementing input listeners for the game.

- java.io
  > a package for reading and writing data, used in this project to read, write and save level files, return buffered images from the spritesheet and throw error messages.

- java.text
  > a package that provides classes and interfaces for handling text, dates, numbers, and messages in a manner independent of natural languages, used in the project to format a timer used in the game to 1 decimal place.

- java.util
  > a package that contains the collections framework, some internationalization support classes, a service loader, properties, random number generation, string parsing and scanning classes, base64 encoding and decoding, a bit array, and several miscellaneous utility classes, this project utilizes array lists (both 1D and 2D), hash maps, maps, and scanners.
<br/>

## How to Run
1. Download the .zip file.
2. Open the `Overwatch TD` folder in your IDE.
3. You will be prompted to setup the SDK (it will show on the top-right corner in IntelliJ IDEA).
4. Run the code. Go to `src > main > Game` to do so if you are on Intellij IDEA.
5. If the `input == null!` error shows up, make sure the `assets` folder is a resource folder. If you are on Intellij IDEA, you may right-click the `assets` folder, then select `Mark Directory as > Resources Root`. It should now run smoothly.
6. You can play around with enemy attributes in the `Constants` class and enemy waves (including spawn rates, spawn amounts) in the `WaveManager` class, and create your own level layouts in the edit section!
<br/>

## Class Diagram
![ClassDiagram](/Images/ClassDiagram.png)