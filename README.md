# Java Game Engine (Version 1.0)

### Table of Contents  
[What is it?](#what_is_it) 

[Features](#features) 

[How do I compile the code to use the engine in my projects?](#compile_jar)

[How do I install it?](#installation)

[How do I use it?](#usage)


<a name="what_is_it"/>

### What is it?

This is my attempt at a *custom* game engine written in Java. It may not serve much practial use, and was written more as a project for me to understand how Game Engines work, and all of the nuances that come with creating a Developer API that would be used by somebody else to create a finished product. This engine is fully featured (or at least it will be upon completion), with many core game engine features such as the creation of windows, rendering logic, customizable game loops, image
and shape creation, audio playback, and user input (with an easy to use and rebindable control system). This is more of a learning project for me, but I've thoroughly enjoyed the work I've put into it so far, and intend to see how far I can take it.

<a name="features"/>

### What features are supported and what features are planned?

Currently in this version, the features that are supported are:
  - Window Creation (customize title, screen size, and access drawable Canvas)
  - Drawable Sprites (Images, Shapes, or Text)
  - Custom Game Loop (allows dynamic programming of game logic by the end developer to run every frame)
  - Audio Playback (Supports multiple clips playing simultaneously, and looping of clips)
  - Keyboard Input (with rebindable controls)
  - Mouse Input (track if the mouse was clicked and/or current mouse location)
  - Custom Logger (with ANSI color support, and customizable verbosity level)
  - Separation between "game object" and "UI object" so they are drawn on different layers to prevent conflict
  - Customizable "name" identifiers for objects for easy referencing in code without necessarily having access to the object

My overall plans for the engine before I consider it a "complete" product are:
  - Basic physics (gravity simulation)
  - Collision detection

<a name="installation"/>

### How do I install it?

I strived to make this engine as simple as possible to set up and get started with! 

First of all, make sure you have a compiled `.jar` file with the build of the project. For instructions on doing this, [click here](#compile_jar)

To set this up in an Eclipse project, do the following:
  **New Project**
  1. `File > New > Java Project`
  2. Enter a Project name and click `Next >`
  3. Select the `Libraries` tab
  4. `Add External JARs...`
  5. Select compiled `.jar` file
  6. `Finish`

  **Existing Project**
  1. Right click on the Project `Build Path > Configure Build Path...`
  2. In the `Libraries` tab, click on `Classpath`
  3. `Add External Jars...`
  5. Select the compiled `.jar` file
  6. `Apply and Close`

<a name="compile_jar"/>

### How do I compile the code to use the engine in my projects?

Once the project is cloned, import the project into Eclipse, and do the following:
  1. Right click on the Project `Export...`
  2. `Java > JAR file`
  3. `Next >`
  4. Choose an export destination
  5. `Finish`

<a name="usage"/>

### How do I use it?

The first thing you'll want to create is a Window, this can be done using the `Window.CreateWindow()` method.

Let's create a Window object:
```java
Window window = new Window.CreateWindow(1280, 720).title("Test Game").pack()
```
This will create a Window for us with a width of 1280, and a height of 720. The Window title will be set to "Test Game". `pack()` finalizes the settings in the window and instantiates it for us.
By default, Keyboard and Mouse input will be enabled, this can be disabled by using the `disableKeyboard()` and `disableMouse()` settings on instantiation.

There are many other Window settings which can be found at the `CreateWindow` class in [src/dev/johnnyleek/engine/window/Window.java](https://github.com/JohnnyLeek1/Java-Game-Engine/blob/master/src/dev/johnnyleek/engine/window/Window.java).

With our Window created, we can create some `Sprites` to display in our window! Create a new `Source Folder` called `images`, and place in a background image. Now we can create our sprites:
```java
try {
    // Create a new "Image" Sprite at the location (0, 0)
	Sprite background = new Image(new FileInputStream("images/background.jpg"), 0, 0);

    // Create a new Cyan Rectangle with width "100", height "100" at location (50, 50) 
	Sprite player = new Shape(ShapeType.RECTANGLE, Color.CYAN, 100, 100, 50, 50, "player");
} catch (IOException e) {
	Logger.error("Failed to load background");
	e.printStackTrace();
}
```
You'll notice that the last parameter for our `player` sprite is a String that says `player`. This is it's `name`. The `name` field can be used to find a game object within the Window (we'll use it later).

When it comes to the coordinates on where to position images, it's important to note that position (0, 0) is the TOP LEFT of the screen. So a high X coordinate would move the object to the RIGHT, and a high Y coordinate would move the object to the BOTTOM.

Now that we've instantiated our sprites, we can add them to the window. This is done very simply:
```java
try {
    ...sprite instantiation...

    // Add elements to our window
    window.addGameElements(background, player);
} catch(IOException e) {
    ...
}
```
As you can see, we can add as many elements as we want in one method call using `addGameElements(...elements)`, we can also add just 1 element at a time using `addGameElement(element)`

Let's also add some UI text to the screen! This, once again, is made quite simple:
```java
Text titleText = new Text("Test text", Color.WHITE, 1100, 100);
window.addUIElement(titleText);
```

This will create some text that says `Test text` in the upper right of the screen!

Okay, that's all well and good, but how do we actually see our sprites on the screen?

In order to accomplish this, we must create our own `Game Loop`. It's not as daunting as it sounds, the rendering logic and frame rate is all handled by the engine, we just have to instantiate this, and provide an implementation for one of its methods! Make a new class called `GameLoop` (or whatever you would like), and ensure that it `extends Game`. It would look like this:
```java
public class GameLoop extends Game {

}
```

You'll first get an error that you must implement the constructor, which will look like this:
```java
public GameLoop(Window window) {
    super(window);
}
```

Now you'll get an error now that you have to implement the `runTick()` method. `runTick` is called by the loop every frame to handle updates to the game. The most common example to compare this with would be Unity's `Update()` function, which again, it called every frame so the developer cam implement dynamic game logic. Let's implement the method as follows:
```java
@Override
public void runTick() {
}
```

We don't *have* to put anything here (and we won't, yet). We just have to provide some sort of concrete implementation for our game loop to call.

Back in our Main class, let's start up this loop!
```java
Game loop = new GameLoop(window);
loop.start();
```
This will kick our loop into gear and start rendering the sprites we place in the window! If you run the game now, you'll see your Image and your little plaer rectangle on the screen!

Okay, being able to see that is cool and all, but it's not moving. Where's the fun in that? You're right! Let's add some keyboard input!

Let's transition back to our `GameLoop` class which has our `runTick()` method. We can use this to update the Sprites position on the screen whenever we press a key.
First things first, let's find our `player` Sprite in the window (which we created earlier):
```java
Sprite playerSprite = (Sprite)super.getWindow().getGameElementByName("player");
// Make sure that the sprite has been instantiated before working with it:
if(playerSprite == null) return;
```

Okay, now that we have our Sprite, we need to know it's current position. Luckily, that's super simple!
```java
int posX = playerSprite.getPosX();
int posY = playerSprite.getPosY();
```

Okay, so all we have to do here is track our Keyboard input and see if a key is pressed, and then update the position accordingly!
```java
// KeyBinding is an enum containing key codes of common keys. This lets the user rebind their keys without you having to change your code!
// When the user rebinds the key, just update the key code for the keybindings
if(Keyboard.isPressed(KeyBinding.UP)) {
    playerSprite.setPosY( posY - 5 );
}
if(Keyboard.isPressed(KeyBinding.DOWN)) {
    playerSprite.setPosY( posY + 5 );
}
if(Keyboard.isPressed(KeyBinding.LEFT)) {
    playerSprite.setPosX( posX - 5 );
}
if(Keyboard.isPressed(KeyBinding.RIGHT)) {
    playerSprite.setPosX( posX + 5 );
}
```

With this complete, if we run our game now, we should be able to move our character using the WASD keys on the keyboard! You'll notice that if you move the character behind the text, the text will render in front of the character. This is because the character is a `Game Element` whilst the Text is a `UI Element`.

We can do this same concept with mouse input as well, instead of the Keyboard inputs, we could write:
```java
if(Mouse.isPressed()) {
    playerSprite.setPosX( Mouse.getMouseX() );
    playerSprite.setPosY( Mouse.getMouseY() );
}
```

Now, let's play some audio! The Audio System is on a **GAME** scope, *not* a Window scope. That means that you use one audio engine for *every* window. Put some audio files in a location the project can access (such as a `Source Folder` called `audio`), and create an audio clip as follows:
```java
try {
    // The true parameter in AudioClip instantiation signals this clip to loop.
    // By default a clip set to loop will loop infinitely, but we can specify a number
    // of times to loop by adding a third parameter containing an integer of how many times
    // we want this audio to loop
    AudioClip backgroundMusic = new AudioClip("audio/background_music.wav", true);
} catch(FileNotFoundException e) {
    Logger.error("Failed to load audio clip");    
}
```
From here we can access our audio system and play it in our game!
```java
try {
    AudioClip backgroundMusic = new AudioClip("audio/background_music.wav", true);
    AudioPlayer.getAudioPlayer().playAudio(backgroundMusic); // ADD THIS
} catch(FileNotFoundException e) {
    Logger.error("Failed to load audio clip");
}
```

There you have it! In ~100 lines of code, you have a simple game working with controls and audio!
