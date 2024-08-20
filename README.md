Werewolf!

The purpose of this project is to experiment with ways WEAR OS could be used to promote healthy lifestyle choices. 
The current iteration of Werewolf! includes a virtual pet dog that changes with the phases of the moon. The application tracks
the phases of the moon as well as the user's daily average sleep, daily average steps, and steps that day since the last full 
moon. On the full moon, if the user meets a certain health threshhold, they will be rewarded by their pet. Currently, this is 
done through simple congratulatory/dissatisfied animations, but we hope to incorporate a werewolf transformation in the future.

Werewolf is programmed entirely in Kotlin using Android Studio Koala 2024.1.1. For lunar phase calculations, the 
https://github.com/yoxjames/Kastro repository is used. Android Studio's health services API is used to read in health data.
Note that this is different from health connect, which cannot be used since this is a standalone watch application. 

To install the project, clone this repository into android studio. Make sure youre using Koala 2024.1.1 or later.
From there, you can add a Wear OS emulator to the project. Make sure the emulator is running Android Studio API Level 14 or later.
Then, press the run button, and the app should open on the emulator. To see the different parts of the app, just tap the emulator 
screen with your mouse. 

This app is currently in progress. 

Werewolf! was made by Adam Saleh with the guidance of Dr. Zilu Liang. 
