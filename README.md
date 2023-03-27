# SensTranslator
A sensitivity translator to help make different first-person videogames feel the same to control.

Being a fan of competitive online video games, specifically first person shooters, I know that finding a comfortable sensitivity 
is very important. One roadblock to this comfort, however, is that most video game engines use different formulas for calculating 
sensitivity - and they're all inaccessible. 

For this project, which took about two days, I wrote an application in Java which would receive Game objects with a title and an 
engine. Then, using a method to convert from one game object to another, the program will calculate what the target sensitivity 
should be in your chosen game. This means you can play two games on different engines with the exact same feel.

The process for this program is one I am especially proud of - initially, I did some research into sensitivity formulas and found 
only one reference that revealed the sensitivity translator for the Source engine, used by VALVe. This formula calculated sensitivity as: 

Windows mouse sensitivity x game sensitivity x mouse yaw x mouse DPI

Also during my research, I found a paid tool which performs this service at mousesensitivity.com. From there, I was able to compare a 
Source game to a CryEngine game and reverse engineer the formula for the CryEngine. Since the DPI, Windows sensitivity, and game 
sensitivity couldn't be the variable which was adjusted behind the scenes, I balanced the equations to help determine what mouse 
yaw variable needed to be used in order to translate from the Source engine to the CryEngine. The project only supports three game 
engines right now, but I'm still very proud of it.
