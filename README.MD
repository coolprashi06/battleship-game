Run this java application by executing following command on terminal:

- cd to the directory where this project is located
- issue following command: 

java -jar battleship-game.jar ~/battleship-game/input.txt

in above command provide the path to the file where the input to run the program is provided.

Sample input:

`5 E` <br/>
`2`<br/>
`Q 1 1 A1 B2`<br/>
`P 2 1 D4 C3`<br/>
`A1 B2 B2 B3`<br/>
`A1 B2 B3 A1 D1 E1 D4 D4 D5 D5`<br/>

execution of above will result in player2 winning the battle

`5 E` <br/>
`2`<br/>
`Q 1 1 A1 B2`<br/>
`P 2 1 D4 C3`<br/>
`A1 B2 B2 C3 C4`<br/>
`A1 B2 B3 A1 D1 E1 D4 D4 D5 D5`<br/>

execution of above will result in player2 winning the battle