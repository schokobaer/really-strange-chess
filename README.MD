# Really Strange Chess

## Build
TODO

## TODOs
  - Play Sounds
  - Animation for player in charge
  - Display if the game is finished and say if its won, lost, patt
  - Mines
  - Custom Boards for Creation
  - Undo
  - Bauer transformation selection (Dame,Turm,Springer,Laufer,king)



## Mines
 - Mines appear after an given offset (e.g. after 10 steps the mine generation starts)
 - Mines appear in a fixed given interval (e.g. each 4 steps)
 - Mines are never generated on a field with a figure
 - Mines are generated alternating on black and white fields
 - The length of a mine is random number between 3 and 6
 - The mine value decreases each step (not round)
 - If the mine value reaches 0 it explodes
 - After a mine field exploded, it destorys the figure standing on it and the field is EMPTY afterwards
 - If a king steps on a mined field, it gets defused