# Tears VFX

A Java application that simulates realistic tear visual effects on a cartoon face.

![Tears VFX Demo](https://via.placeholder.com/600x400?text=Tears+VFX+Demo)

## Features

- Real-time animation of tear drops
- Physics-based motion with gravity and sway effects
- Configurable parameters for tear generation
- Smooth rendering with anti-aliasing
- Translucent tear effects with alpha blending

## Requirements

- Java Runtime Environment (JRE) 8 or higher
- Java Development Kit (JDK) 8 or higher (for compilation)

## Installation

1. Clone this repository or download the source code:
   ```
   git clone https://github.com/yourusername/tears-vfx.git
   ```
   
2. Navigate to the project directory:
   ```
   cd tears-vfx
   ```

3. Compile the Java file:
   ```
   javac TearsVFX.java
   ```

## Usage

Run the compiled program:
```
java TearsVFX
```

A window will appear showing a face with animated tears falling from the eyes.

## Customization

You can modify the following parameters in the `TearsVFX.java` file to customize the visual effect:

| Parameter | Description |
|-----------|-------------|
| `WIDTH` | Width of the application window |
| `HEIGHT` | Height of the application window |
| `TEAR_COUNT` | Number of tears generated per eye in each cycle |
| `TEAR_SPAWN_DELAY` | Time between tear generation cycles (in milliseconds) |

### Tear Properties

Each tear has the following properties that can be adjusted in the `Tear` class:

- **Speed**: Base falling speed
- **Size**: Diameter of the tear drop
- **Sway**: Horizontal movement pattern
- **Alpha**: Transparency level

## How It Works

The application uses Java Swing for the UI and custom rendering via the `Graphics2D` API:

1. A face is rendered with a sad expression
2. Tear drops are spawned at regular intervals from the eye positions
3. Each tear is an instance of the `Tear` class, which maintains its position, speed, and appearance
4. Tears accelerate as they fall and have a subtle swaying motion
5. The tear shape is created using a combination of an oval and a triangle
6. Alpha compositing is used to create a translucent effect

## Examples

### Increasing Tear Flow

To create a heavier flow of tears, modify the `TEAR_COUNT` and `TEAR_SPAWN_DELAY` constants:

```java
private static final int TEAR_COUNT = 4;        // Increase from default 2
private static final int TEAR_SPAWN_DELAY = 400; // Decrease from default 800
```

### Changing Tear Color

To change the color of the tears, modify the color in the `paintComponent` method:

```java
// Change from semi-transparent blue to semi-transparent green
g2d.setColor(new Color(120, 255, 185, 180));
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Inspired by animation techniques for liquid simulation
- Uses Java Swing for the UI framework
