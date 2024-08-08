
# Tic-Tac-Toe Android Game

Welcome to the Tic-Tac-Toe Android App, a modern implementation of the classic Tic-Tac-Toe game using Kotlin for Android development. This application features a clean and intuitive user interface, robust game logic, and support for multiple rounds of play.

## Features

- **Custom Player Names**: Allow users to input names for Player X and Player O.
- **Configurable Rounds**: Set the number of rounds to be played.
- **Score Tracking**: Automatically tracks and displays scores for both players.
- **Game Outcome Detection**: Identifies and announces win or tie conditions.
- **User Interface**: Designed for ease of use with custom views and dialogs.
- **Splash Screen**: Initial screen displayed upon starting the app.
- **Game Refresh**: Automatically refreshes the game state after each round or when no rounds remain.

## Installation

1. **Clone the Repository**
    ```sh
    git clone https://github.com/yourusername/tictactoe-app.git
    ```

2. **Open the Project**
    - Open the project in Android Studio.

3. **Build and Run**
    - Click `Run` to build and execute the application on an Android device or emulator.

## Features and Usage

### MainActivity

- **Gameplay Management**: Controls game logic, player turns, and updates scores.
- **GridAdapter**: Handles the Tic-Tac-Toe grid, including button states and interactions.
- **Win Detection**: Evaluates rows, columns, and diagonals to determine game outcomes.
- **Round Management**: Updates the round count and scores, and triggers the refresh mechanism.
- **Refresh Functionality**: Automatically resets the game state after each round or when all rounds are completed.

### GridAdapter

- **Button State Management**: Tracks and updates the state of each grid button (empty, "X", or "O").
- **View Rendering**: Adjusts button visuals based on game state.

### WinnerActivity (DialogFragment)

- **Result Dialog**: Presents the game results, including scores and winner, and provides an option to reset the game.

### SplashScreen

- **Initial Display**: Shows a splash screen for 3 seconds before transitioning to the main game screen.

### homeActivity

- **Setup Screen**: Allows users to enter player names and set the number of rounds. Launches the game by passing these parameters to `MainActivity`.

## Refresh Feature

The refresh feature ensures a seamless gaming experience:

- **Post-Round Refresh**: Automatically updates scores, decrements the round count, and resets the grid after each round.
- **Final Round Reset**: Resets the game state when no rounds remain, including scores and round count, preparing for a new game if desired.

## Code Structure

- **MainActivity**: Manages core gameplay logic and user interface interactions.
- **GridAdapter**: Manages the visual and interactive aspects of the Tic-Tac-Toe grid.
- **WinnerActivity**: Displays the results of the game and handles game reset operations.
- **SplashScreen**: Provides an introductory screen upon app launch.
- **homeActivity**: Facilitates initial setup and game start.

## Dependencies

- **Kotlin**: Utilized as the primary programming language.
- **AndroidX Libraries**: Used for modern Android development and compatibility.

## How to Use

1. **Start the Game**:
    - Enter player names for Player X and Player O.
    - Specify the number of rounds.
    - Tap "Start Game" to begin.

2. **Gameplay**:
    - Players alternate turns by tapping on the grid cells.
    - The app automatically detects wins and ties, and updates scores accordingly.

3. **Rounds**:
    - The game progresses through the specified number of rounds.
    - The app refreshes automatically after each round or when all rounds are completed.

4. **Dialogs**:
    - Results are presented in a dialog after each round.
    - The dialog provides an option to reset the game or view the final result.

## Contributing

Contributions are welcome! To contribute to this project, please fork the repository and submit a pull request. Ensure that your code adheres to the existing style and passes all tests.
