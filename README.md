## Dominos Project, Overview

Synopsis:
Each player selects 7 dominos and places them on the long edge so as to be 
able to see them without being visible to the opponent. One player starts the 
game by downing (putting down) a domino face-up on the table. The dominos will 
form two parallel rows shifted by half a domino. A blank is used as a wild card 
and thus matches any value next to it. If the player has a domino that can 
extend the line of play in either direction, they put it down and their turn 
ends. If the player does not have a domino able to extend the line of play, 
they must pick one up from the boneyard and must continue to do so until a 
matching domino is found or the boneyard is empty. The game ends when the 
boneyard is empty and either 1) The current player places their last domino 
or 2) Both players have taken a turn without placing a domino.


### Domino Game, from a Programer's Perspective
* Input is not checked if invalid (keyboard // mouse)


* Dominos game assumes two players (can be modified, but default settings are 
for 1 user and 1 computer)


* "Place Domino" menu option expects an integer to be entered. 1st domino in
  user's hand = 1, 2nd domino in user's hand = 2, etc. (not an index).


* Domino game does not have a stalement resolution, when boneyard is empty, 
but neither player can match.


* Generally, Data flows from their class to GameManager.java. This is to check 
if input should be received from keyboard // mouse, or whether to print to 
console or just update GUI, etc. GameManager knows this info, so lots of data 
flows through GameManger.java for this reason. Also, a lot of GUI logic is 
stored here, since GUI logic is mostly MouseEvent-Based. 


* Console version is slightly more polished than GUI version. 


### Console, from a Consumer's Perspective
* User's main menu consists of 3 options: "Place Domino", "Draw from Boneyard", 
and "Quit". Quit will always be shown as an option. Place Domino will only be 
shown if user can match at least 1 of their domino pieces to the board. Draw 
from boneyard will only be shown if user cannot match a domino piece.


* User can win once the Boneyard is empty and has no more dominos in 
their hand.


* Game runs slowly because there is a sleep() function that runs between turns 
and when you draw from boneyard. This makes the game have a 90's retro vibe, 
in my opinion. I like it, so I'm keeping it.


* Any "  >> text" line that prints to console is 100% intentional. This tells 
the user what's happening to their pieces, since we don't give them the 
option to say left//right or flip position, and they might not see that their 
domino was indeed placed, just differently. Sleep() function also helps with 
this.



### GUI, from a Consumer's Perspective
* Boneyard hands out dominos automatically if player cannot match to board


* Backend assumes that domino clicked can be matched to something on the board


* The ONLY clickable item in the GUI is the User's Hand. The logic of *where* to 
put the domino is handled by the backend. Dominos are NOT click-and-drag. 1 
simple click anywhere on the domino will suffice. 


* GUI Version does not allow player to quit. Player can only place a domino 
or draw from boneyard (automatic)


### Known Bugs
1. No stalement win condition (Boneyard empty, nobody can match)
2. Game does not declare winner until after everyone has taken their turn 
(is it a feature? Ooh, marketing opportunities!)
3. Player can continue placing dominos even after "Computer Won" label is shown
