# Really Strange Chess

## Build
TODO

## TODOs
  - Animation for player in charge
  - Display if the game is finished and say if its won, lost, patt
  - Bauer transformation selection (Dame,Turm,Springer,Laufer,king)
  - step buttons
  - Logging
  - Build scripts



## Mines
 - Mines appear after an given offset (e.g. after 10 steps the mine generation starts)
 - Mines appear in a fixed given interval (e.g. each 4 steps)
 - Mines are never generated on a field with a figure
 - Mines are generated alternating on black and white fields
 - The length of a mine is random number between 3 and 9
 - The mine value decreases each step (not round)
 - If the mine value reaches 0 it explodes
 - After a mine field exploded, it destorys the figure standing on it and the field is EMPTY afterwards
 - If a king steps on a mined field, it gets defused

## Custom Board
To use a custom board select the Custom style in the creation wizard, which lets pop up a textfield. Fill the textbox with 3 blocks seperated by empty lines:
 1. The first block specifies the field colors. Each charactar specifies a field. `w` for a white field, `b` for a black field and `.` for an empty field
 2. The second block specifies the white figures. `t` for turm, `s` for springer, `l` for laufer, `d` for dame and `k` for king.
 3. The last row specifies the black figures. Same concept as the second block.

The following example shows an 6x6:
```
wbwbwb
bwbwbw
wbwbwb
bwbwbw
wbwbwb
bwbwbw

tsdkst
bbbbbb
......
......
......
......

......
......
......
......
bbbbbb
tsdkst
``` 

## Known Bugs:
 - Setup Page not saving Data in Localstore
 - Castling if the tower is next to the king (5x5Grid Board)