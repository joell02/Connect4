/*Connect Four game
  Programmed by Joell Sebastiampillai, January 7, 2018
  This program is a unique version of connect 4 where each player plays their chips until there is four in a row; each player has a power chip that gets rid of all the chips in the column it is placed in; there is also a easy and hard mode in campaign; hard mode uses rudimentery AI to make it harder for the user to win*/

//test2
import hsa.Console;
import java.awt.*;

public class ConnectFour
{
    static Console j; //to declare a console
    static final int chipSize = 80; //to make a global constant to use as the chip size
    public static void main (String[] args)
    {
	j = new Console (38, 150); //to create a new console
	logoName (); // to call logo method and display logo
	loading (); // to show the loading circle
	String[] columnNumbs = {"0", "1", "2", "3", "4", "5", "6", "7"}; //the input numbers placed at the bottom of the board
	String columnChars = "01234567"; //array used for error trapping when getting input
	Color background = new Color (0, 128, 192); //background colour
	Font winner = new Font ("Haettenschweiler", Font.PLAIN, 80);
	Font menuSubtitle = new Font ("Haettenschweiler", Font.PLAIN, 50);
	char[] row1 = new char [8]; // these five rows are used to track what colour chip is placed throughout the board and is used to check whether there is a winner
	char[] row2 = new char [8];
	char[] row3 = new char [8];
	char[] row4 = new char [8];
	char[] row5 = new char [8];
	int[] columnAreaX = {250, 335, 420, 505, 590, 675, 760, 845}; //array of locations of x values that could
	int[] columnAreaY = {460, 375, 290, 205, 120}; //array of locations of y values that could
	int[] yFilled = {0, 0, 0, 0, 0, 0, 0, 0}; //array that tracks how many chips have been placed in each column
	int turn = 0, powerUser1 = 0, powerUser2 = 0, ifConnectFour = 0, chipsErased, totalChipsErased = 0, slotNumber = 0, randomatedPowerChipUse = (int) (11 * Math.random ()) + 20; //turn tracks how many turns have gone by; powerUser1 & powerUser2 track whether the power chips have been used or not; ifConnectFour checks whether there has been four in a row;chipsErased tracks how many chips are erased after the power chip is placed; totalchipserased tracks the total number of chips erased; slot number to get slot number; randomatedPowerChipUse for the computer between turns 20 and 30
	char gameModeMain, afterWin, difficulty = 'n'; // gameModeMain gets what game mode the user would like to play or use; afterWin gets what the user wants to do after the game is over; difficulty tracks what difficulty the user wants to play campaign mode in
	gameModeMain = mainMenu (background); // to display the main menu and get the input for what game mode from the user
	while (gameModeMain != 'd') //loop that runs if the user does not choose to end the game
	{
	    if (gameModeMain == 'c')
	    {
		specialEffects (Color.black);
		rules (background);
	    } //end of if
	    else if (gameModeMain == 'a')
	    {
		if (difficulty == 'n') //if difficulty has not been asked yet then to ask for what difficulty the user would like to use
		{
		    j.setColor (background);
		    j.fillRect (300, 400, 600, 150);
		    j.setColor (Color.black);
		    j.drawLine (300, 475, 900, 475);
		    j.drawLine (585, 475, 585, 550);
		    j.setColor (Color.black);
		    j.drawString ("Choose Difficulty", 400, 450);
		    j.drawString ("[1]Beginner", 300, 525);
		    j.drawString ("[2]Advanced", 600, 525);
		    do
		    {
			difficulty = j.getChar ();
		    }
		    while (difficulty != '1' && difficulty != '2');
		} //end of inner if
		//to draw the board, the player turn boxes, and the power chips
		specialEffects (background);
		j.setColor (background);
		j.fillRect (0, 0, j.maxx (), j.maxy ());
		drawBoard ();
		columnNumbers (columnNumbs);
		drawPowerChip (1010, 530, 'y');
		drawPowerChip (80, 530, 'r');
		j.setColor (Color.black);
		j.drawString ("(p)-", 45, 575);
		j.drawString ("-(p)", 1095, 575);
		j.setFont (menuSubtitle);
		j.drawRect (955, 630, 230, 50);
		j.drawRect (955, 685, 230, 50);
		j.drawString ("(m)Main Menu", 960, 675);
		j.drawString ("(r)Restart", 960, 725);
		//loop to continuosly ask for what the user would like to do until all the slots have been filled
		while (turn != 40)
		{
		    playerTurn (turn, background, totalChipsErased);
		    slotNumber = getSlotChoice (yFilled, columnChars, columnAreaX, columnAreaY, powerUser1, background, 0);
		    if (slotNumber == 8) //to place the power chip if it is selected
		    {
			chipsErased = placePowerChip (columnAreaX, columnAreaY, yFilled, 'r', columnChars, columnNumbs, background, "user", row1, row2, row3, row4, row5);
			powerUser1++;
			totalChipsErased += chipsErased;
			turn -= chipsErased;
		    } //end of if
		    else if (slotNumber == 9) //if user chooses main menu mid-game
		    {
			j.setColor (Color.orange);
			j.fillRect (955, 630, 230, 50);
			j.setColor (Color.black);
			j.setFont (menuSubtitle);
			j.drawString ("(m)Main Menu", 960, 675);
			delay (300);
			break;
		    } //end of else if
		    else if (slotNumber == 10) //if user chooses restart mid-game
		    {
			j.setColor (Color.orange);
			j.fillRect (955, 685, 230, 50);
			j.setColor (Color.black);
			j.setFont (menuSubtitle);
			j.drawString ("(r)Restart", 960, 725);
			delay (300);
			break;
		    } //end of else if
		    else //to place the user chip and check if there is a winner
		    {
			placeChipUser (slotNumber, columnAreaX, columnAreaY, yFilled, row1, row2, row3, row4, row5, background, 'r');
			j.setColor (Color.black);
			j.setFont (winner);
			ifConnectFour = ifRowWinnerRed (row1, row2, row3, row4, row5, columnAreaX, columnAreaY);
			if (ifConnectFour != 0)
			{
			    break;
			} //end of if
			ifConnectFour = ifColumnWinnerRed (row1, row2, row3, row4, row5, columnAreaX, columnAreaY);
			if (ifConnectFour != 0)
			{
			    break;
			} //end of if
			ifConnectFour = ifDiagonalWinnerRed (row1, row2, row3, row4, row5, columnAreaX, columnAreaY);
			if (ifConnectFour != 0)
			{
			    break;
			} //end of if
		    } //end of else
		    turn++;
		    if (turn == 40) // to check whether turn has equalled to 40 at this point and exit the loop
		    {
			break;
		    } //end of if
		    playerTurn (turn, background, totalChipsErased); //to change the player turn
		    delay (1000);
		    if (difficulty == '1') //if the difficulty is 1 then it makes it easier for the user to win as the computer just places randomized chips
		    {
			do
			{
			    slotNumber = (int) (8 * Math.random ()); //to get a random slot to place the chip
			}
			while (yFilled [slotNumber] == 5);
			if (randomatedPowerChipUse == turn && powerUser2 == 0)
			{
			    chipsErased = placePowerChip (columnAreaX, columnAreaY, yFilled, 'y', columnChars, columnNumbs, background, "computer", row1, row2, row3, row4, row5);
			    powerUser2++;
			    totalChipsErased += chipsErased;
			    turn -= chipsErased;
			} //end of if
			else //to place the chip and check if there is a winner
			{
			    placeChipUser (slotNumber, columnAreaX, columnAreaY, yFilled, row1, row2, row3, row4, row5, background, 'y');
			    j.setColor (Color.black);
			    j.setFont (winner);
			    ifConnectFour = ifRowWinnerYellow (row1, row2, row3, row4, row5, columnAreaX, columnAreaY);
			    if (ifConnectFour != 0)
			    {
				break;
			    } //end of if
			    ifConnectFour = ifColumnWinnerYellow (row1, row2, row3, row4, row5, columnAreaX, columnAreaY);
			    if (ifConnectFour != 0)
			    {
				break;
			    } //end of if
			    ifConnectFour = ifDiagonalWinnerYellow (row1, row2, row3, row4, row5, columnAreaX, columnAreaY);
			    if (ifConnectFour != 0)
			    {
				break;
			    } //end of if
			} //end of else
		    } //end of outer if
		    else if (difficulty == '2') //if the user wants to play advanced he will be playing the bot that makes it much harder to win
		    {
			if (randomatedPowerChipUse == turn && powerUser2 == 0)
			{
			    chipsErased = placePowerChip (columnAreaX, columnAreaY, yFilled, 'y', columnChars, columnNumbs, background, "computer", row1, row2, row3, row4, row5);
			    powerUser2++;
			    totalChipsErased += chipsErased;
			    turn -= chipsErased;
			} //end of if
			else //to place the chip and check if thereis a winner
			{
			    placeComputerChip (row1, row2, row3, row4, row5, columnAreaX, columnAreaY, yFilled, background);
			    j.setColor (Color.black);
			    j.setFont (winner);
			    ifConnectFour = ifRowWinnerYellow (row1, row2, row3, row4, row5, columnAreaX, columnAreaY);
			    if (ifConnectFour != 0)
			    {
				break;
			    } //end of if
			    ifConnectFour = ifColumnWinnerYellow (row1, row2, row3, row4, row5, columnAreaX, columnAreaY);
			    if (ifConnectFour != 0)
			    {
				break;
			    } //end of if
			    ifConnectFour = ifDiagonalWinnerYellow (row1, row2, row3, row4, row5, columnAreaX, columnAreaY);
			    if (ifConnectFour != 0)
			    {
				break;
			    } //end of if
			} //end of else
		    } //end of else if
		    turn++;
		}

	    }
	    else if (gameModeMain == 'b') //if user selects multiplayer
	    {
		//to draw the board, the background, the player turn boxes and the power chips
		specialEffects (background);
		j.setColor (background);
		j.fillRect (0, 0, j.maxx (), j.maxy ());
		drawBoard ();
		columnNumbers (columnNumbs);
		drawPowerChip (1010, 530, 'y');
		drawPowerChip (80, 530, 'r');
		j.setColor (Color.black);
		j.drawString ("(p)-", 45, 575);
		j.drawString ("-(p)", 1095, 575);
		j.setFont (menuSubtitle);
		j.drawRect (955, 630, 230, 50);
		j.drawRect (955, 685, 230, 50);
		j.drawString ("(m)Main Menu", 960, 675);
		j.drawString ("(r)Restart", 960, 725);
		//loop that continuosly runs until the players are out of turns
		while (turn != 40)
		{
		    playerTurn (turn, background, totalChipsErased);
		    slotNumber = getSlotChoice (yFilled, columnChars, columnAreaX, columnAreaY, powerUser1, background, 0); //to get slot number from the user
		    if (slotNumber == 8) //to place power chip if it is selected
		    {
			chipsErased = placePowerChip (columnAreaX, columnAreaY, yFilled, 'r', columnChars, columnNumbs, background, "user", row1, row2, row3, row4, row5);
			powerUser1++;
			totalChipsErased += chipsErased;
			turn -= chipsErased;
		    } //end of if
		    else if (slotNumber == 9) //if user chooses main menu mid-game
		    {
			j.setColor (Color.orange);
			j.fillRect (955, 630, 230, 50);
			j.setColor (Color.black);
			j.setFont (menuSubtitle);
			j.drawString ("(m)Main Menu", 960, 675);
			delay (300);
			break;
		    } //end of else if
		    else if (slotNumber == 10) //if user chooses restart mid-game
		    {
			j.setColor (Color.orange);
			j.fillRect (955, 685, 230, 50);
			j.setColor (Color.black);
			j.setFont (menuSubtitle);
			j.drawString ("(r)Restart", 960, 725);
			delay (300);
			break;
		    } //end of else if
		    else //to place ther user chip when they select to do so and check if there is a winner after the chip is placed
		    {
			placeChipUser (slotNumber, columnAreaX, columnAreaY, yFilled, row1, row2, row3, row4, row5, background, 'r');
			j.setColor (Color.black);
			j.setFont (winner);
			ifConnectFour = ifRowWinnerRed (row1, row2, row3, row4, row5, columnAreaX, columnAreaY);
			if (ifConnectFour != 0)
			{
			    break;
			} //end of if
			ifConnectFour = ifColumnWinnerRed (row1, row2, row3, row4, row5, columnAreaX, columnAreaY);
			if (ifConnectFour != 0)
			{
			    break;
			} //end of if
			ifConnectFour = ifDiagonalWinnerRed (row1, row2, row3, row4, row5, columnAreaX, columnAreaY);
			if (ifConnectFour != 0)
			{
			    break;
			} //end of if
		    }
		    turn++;
		    if (turn == 40) // to check if the board is full after the player 1 places his chip as this could happen after someone places a power chip
		    {
			break;
		    } //end of if
		    playerTurn (turn, background, totalChipsErased);
		    slotNumber = getSlotChoice (yFilled, columnChars, columnAreaX, columnAreaY, powerUser2, background, 0); //to get slot number form the second user
		    if (slotNumber == 8) //to place the power chip if player 2 chooses to
		    {
			chipsErased = placePowerChip (columnAreaX, columnAreaY, yFilled, 'y', columnChars, columnNumbs, background, "user", row1, row2, row3, row4, row5);
			powerUser2++;
			totalChipsErased += chipsErased;
			turn -= chipsErased;
		    } //end of if
		    else if (slotNumber == 9) //if user chooses main menu mid-game
		    {
			j.setColor (Color.orange);
			j.fillRect (955, 630, 230, 50);
			j.setColor (Color.black);
			j.setFont (menuSubtitle);
			j.drawString ("(m)Main Menu", 960, 675);
			delay (300);
			break;
		    } //end of else if
		    else if (slotNumber == 10) //if user chooses restart mid-game
		    {
			j.setColor (Color.orange);
			j.fillRect (955, 685, 230, 50);
			j.setColor (Color.black);
			j.setFont (menuSubtitle);
			j.drawString ("(r)Restart", 960, 725);
			delay (300);
			break;
		    } //end of else if
		    else //to place a regular chip when player 2 chooses to do so and check if there is a winner after the chip had been placed
		    {
			placeChipUser (slotNumber, columnAreaX, columnAreaY, yFilled, row1, row2, row3, row4, row5, background, 'y');
			j.setColor (Color.black);
			j.setFont (winner);
			ifConnectFour = ifRowWinnerYellow (row1, row2, row3, row4, row5, columnAreaX, columnAreaY);
			if (ifConnectFour != 0)
			{
			    break;
			} //end of if
			ifConnectFour = ifColumnWinnerYellow (row1, row2, row3, row4, row5, columnAreaX, columnAreaY);
			if (ifConnectFour != 0)
			{
			    break;
			} //end of if
			ifConnectFour = ifDiagonalWinnerYellow (row1, row2, row3, row4, row5, columnAreaX, columnAreaY);
			if (ifConnectFour != 0)
			{
			    break;
			} //end of if
		    } //end of else
		    turn++;
		} //end of while loop
	    } //end of else if
	    if (turn == 40) //to check if there was no winner and display that it was a tie game
	    {
		j.setFont (winner);
		j.drawString ("Tie Game", 500, 70);
	    }
	    if (gameModeMain == 'a' || gameModeMain == 'b') //if the user was in either campaign mode or multiplayer computer will ask whether they want a rematch or want to go to the main menu
	    {
		for (int i = 0 ; i < 8 ; i++) //to reset everything back to 0 or n because the game had ended and the user may choose to play again
		{
		    yFilled [i] = 0;
		    row1 [i] = 'n'; //n for none
		    row2 [i] = 'n';
		    row3 [i] = 'n';
		    row4 [i] = 'n';
		    row5 [i] = 'n';
		}
		powerUser1 = 0;
		powerUser2 = 0;
		turn = 0;
		totalChipsErased = 0;
		randomatedPowerChipUse = (int) (11 * Math.random ()) + 20; //make the randomated power chip occur at a different point in the game if the user plays again
		if (slotNumber == 9)
		{
		    gameModeMain = mainMenu (background);
		    difficulty = 'n';
		}
		else if (slotNumber != 10)
		{
		    j.setFont (menuSubtitle);
		    j.setColor (Color.black);
		    j.drawString ("(a)Rematch", 350, 675);
		    j.drawRect (340, 625, 200, 75);
		    j.drawString ("(b)Main Menu", 650, 675);
		    j.drawRect (640, 625, 225, 75);
		    j.setColor (background);
		    j.fillRect (945, 630, 240, 120);
		    do
		    {
			afterWin = j.getChar (); //to get what the user wants to do after the game is done
		    }
		    while (afterWin != 'a' && afterWin != 'b');

		    if (afterWin == 'a') //rematch
		    {
			j.setColor (Color.orange);
			j.fillRect (340, 625, 200, 75);
			j.setColor (Color.black);
			j.drawString ("(a)Rematch", 350, 675);
			delay (500);
		    }
		    else if (afterWin == 'b') //mainMenu
		    {
			j.setColor (Color.orange);
			j.fillRect (640, 625, 225, 75);
			j.setColor (Color.black);
			j.drawString ("(b)Main Menu", 650, 675);
			delay (500);
			gameModeMain = mainMenu (background);
			difficulty = 'n';
		    }
		}
	    }
	    else // if the user selects to see the game rules and wants to exit
	    {
		gameModeMain = mainMenu (background);
	    }

	} //end of while loop
	gameOver ();
    } //end of main method


    //method to draw the connect four board
    public static void drawBoard ()
    {
	Color board = new Color (0, 0, 128);
	j.setColor (board);
	j.fillRect (220, 100, 740, 470);
	j.setColor (Color.white);
	for (int i = 0 ; i < 8 ; i++)
	{
	    for (int k = 0 ; k < 5 ; k++)
	    {
		j.fillOval (85 * i + 250, 85 * k + 120, chipSize, chipSize);
	    }
	}
    } //end of drawSlots method


    //method to get the slot choice from the user
    public static int getSlotChoice (int[] yFilled, String columnChars, int[] columnAreaX, int[] columnAreaY, int power, Color background, int method)
    {
	Font invalid = new Font ("Impact", Font.BOLD, 40);
	char slot;
	int slotIndex;

	do
	{
	    slotIndex = -1;
	    slot = j.getChar ();
	    //to check whether the input is any of the slot numbers
	    for (int i = 0 ; i < 8 ; i++)
	    {
		if (slot == columnChars.charAt (i))
		{
		    slotIndex = i;
		    break;
		}
	    }
	    //to check whether the user wants to use the power chip
	    if (slot == 'p' && power == 0)
	    {
		power++;
		slotIndex = 8;
		break;
	    }
	    //to check whether the user wants to go to the main menu
	    else if (slot == 'm' && method == 0) //method is to check whether the user is using the power chip button or is just using a normal input
	    {
		slotIndex = 9;
		break;
	    }
	    //to check whether the user wants to restart the game
	    else if (slot == 'r' && method == 0)
	    {
		slotIndex = 10;
		break;
	    }
	}
	while (slotIndex == -1 || yFilled [slotIndex] == 5); //error trapping

	return slotIndex;
    } //end of getInput method


    //method for the main menu that displays the main menu and gets the input for what game mode the user would like to go into
    public static char mainMenu (Color background)
    {
	Font menuTitle = new Font ("Broadway", Font.BOLD, 130);
	Font menuSubtitle = new Font ("Comic Sans MS", Font.BOLD, 50);
	char gameMode;
	int x = 0;
	j.setFont (menuTitle);
	j.setColor (Color.black);
	for (int i = 0 ; i < 300 ; i++)
	{
	    j.fillRect (0, 0, x, j.maxy ());
	    x += 4;
	}
	background ();
	j.setFont (menuTitle);
	j.setColor (background);
	j.drawString ("CONNECT 4", 200, 150);
	j.setColor (Color.yellow);
	j.fillOval (302, 58, 100, 100);
	j.setFont (menuSubtitle);
	j.setColor (Color.black);
	j.fillRect (195, 200, 300, 80);
	j.fillRect (695, 200, 350, 80);
	j.fillRect (195, 290, 300, 80);
	j.fillRect (695, 290, 350, 80);
	j.setColor (background);
	j.drawString ("(a)Campaign", 200, 250);
	j.drawString ("(b)Two player", 700, 250);
	j.drawString ("(c)Rules", 200, 340);
	j.drawString ("(d)Exit", 700, 340);
	do
	{
	    gameMode = j.getChar (); //to get the game mode from the user
	}
	while (gameMode != 'a' && gameMode != 'b' && gameMode != 'c' && gameMode != 'd');
	return gameMode;
    } //end of mainMenu method


    //to create the background with the gradient colour and small connectFour board
    public static void background ()
    {
	int g = 0, b = 15;
	Color board = new Color (0, 0, 128);
	Color background = new Color (0, g, b);
	j.setColor (background);
	int y = 0;
	while (y < j.maxx ())
	{
	    j.fillRect (0, y, j.maxx (), 10);
	    y += 10;
	    g += 2;
	    b += 2;
	    background = new Color (0, g, b);
	    j.setColor (background);
	}
	j.setColor (board);
	j.fillRect (300, 400, 600, 300);
	j.setColor (Color.white);
	for (int i = 0 ; i < 8 ; i++)
	{
	    for (int k = 0 ; k < 4 ; k++)
	    {
		j.fillOval (70 * i + 320, 70 * k + 410, 60, 60);
	    }
	}
	j.setColor (Color.red);
	j.fillOval (390, 620, 60, 60);
	j.fillOval (530, 620, 60, 60);
	j.fillOval (530, 550, 60, 60);
	j.fillOval (600, 620, 60, 60);
	j.fillOval (670, 620, 60, 60);
	j.setColor (Color.yellow);
	j.fillOval (530, 480, 60, 60);
	j.fillOval (460, 550, 60, 60);
	j.fillOval (460, 620, 60, 60);
	j.fillOval (600, 550, 60, 60);
	j.fillOval (740, 620, 60, 60);
    } //end of background method


    //method to create special effect transition where dots appear everywhere
    public static void specialEffects (Color background)
    {
	int x, y;
	j.setColor (background);
	for (int i = 0 ; i < 20000 ; i++)
	{
	    x = (int) ((j.maxx () + 1) * Math.random ());
	    y = (int) ((j.maxy () + 1) * Math.random ());
	    j.fillOval (x, y, 20, 20);
	}
    } //end of specialEffects method


    //method to create special effect transition where lined appear everywhere
    public static void specialEffects2 ()
    {
	int x = (int) ((j.maxx () + 1) * Math.random ());
	int y = (int) ((j.maxy () + 1) * Math.random ());
	j.setColor (Color.black);
	for (int i = 0 ; i < 400 ; i++)
	{
	    j.fillRect (x, 0, 20, j.maxy ());
	    delay (5);
	    x = (int) ((j.maxx () + 1) * Math.random ());
	}


	j.fillRect (0, 0, j.maxx (), j.maxy ());
    }


    //method for delays with parameter as time in milliseconds of the delay
    public static void delay (int time)
    {
	try
	{
	    Thread.sleep (time);
	}


	catch (InterruptedException e)
	{
	}
    } //end of delay method


    //method to display the slot numbers underneath the connect four board
    public static void columnNumbers (String[] columns)
    {
	int x = 280;
	Font numbers = new Font ("Impact", Font.BOLD, 20);
	j.setFont (numbers);
	j.setColor (Color.blue);
	for (int i = 0 ; i < columns.length ; i++)
	{
	    j.drawString (columns [i], x, 600);
	    x += 85;
	}
    } //end of columnNumbers method


    //method to display the rules when the user wants to see them
    public static void rules (Color background)
    {
	Font text = new Font ("Lucida Console", Font.BOLD, 25);
	Font Title = new Font ("Forte", Font.PLAIN, 100);
	Font subTitle = new Font ("HP Simplified", Font.BOLD, 60);
	char mainMenu;
	j.setColor (Color.black);
	j.fillRect (0, 0, j.maxx (), j.maxy ());
	j.setColor (background);
	j.setFont (Title);
	j.drawString ("How To Play", 325, 100);
	j.setColor (Color.white);
	j.setFont (text);
	j.drawString ("Connect four is a game played by 2 players(player vs player, or player", 25, 140);
	j.drawString ("vs bot). Each player plays one after the other, by selecting a column to ", 25, 180);
	j.drawString ("place their chip, which will fall into the lowest possible slot of that", 25, 220);
	j.drawString ("column. The objective of the game is to be the first to connect four ", 25, 260);
	j.drawString ("chips in a row whether it is horizontally, vertically, or diagonally. ", 25, 300);
	j.drawString ("The first to do so will win the game. The board is 5x8. If no player ", 25, 340);
	j.drawString ("is to connect four chips in a row and the board is full, the game will ", 25, 380);
	j.drawString ("result in a draw. IMPORTANT: Only enter one slot number per turn.", 25, 420);
	j.setColor (background);
	j.setFont (subTitle);
	j.drawString ("Special Features", 25, 480);
	j.drawLine (25, 480, 450, 480);
	j.setFont (text);
	j.drawString ("Power Chip: ", 25, 520);
	j.setColor (Color.white);
	j.drawString ("- placing the power chip will erase all the chips in the column that you place it in", 200, 520);
	j.drawString ("that you place it in", 230, 555);
	j.drawString ("- you can place the power chip by pressing p and then choosing ", 200, 595);
	j.drawString ("which column you would like to place the power chip in", 230, 620);
	j.drawString ("- use the power chip wisely as each player only has one use ", 200, 660);
	j.setColor (background);
	j.drawString ("Press any key or enter to go back to main menu", 200, 730);
	mainMenu = j.getChar ();
    }


    //method that places the users chip in the slot they wish to place it in
    public static void placeChipUser (int slotChoice, int[] columnX, int[] columnY, int[] ySlotFilled, char[] row1, char[] row2, char[] row3, char[] row4, char[] row5, Color background, char colour)
    {
	int y = 50;
	while (y < columnY [ySlotFilled [slotChoice]])
	{
	    if (colour == 'r')
	    {
		j.setColor (Color.red);
	    }
	    else
	    {
		j.setColor (Color.yellow);
	    }
	    j.fillOval (columnX [slotChoice], y, chipSize, chipSize);
	    delay (15);
	    if (y < 100)
	    {
		j.setColor (background);
		j.fillOval (columnX [slotChoice], y, chipSize, chipSize);
	    }
	    if (y < columnY [ySlotFilled [slotChoice]])
	    {
		movingChipBackground (columnX, columnY, slotChoice, ySlotFilled);
	    }
	    y += 10;
	}

	if (colour == 'r')
	{
	    j.setColor (Color.red);
	}
	else
	{
	    j.setColor (Color.yellow);
	}
	j.fillOval (columnX [slotChoice], columnY [ySlotFilled [slotChoice]], chipSize, chipSize);
	//to keep track of where each coloured chip is
	if (ySlotFilled [slotChoice] == 0)
	{
	    row1 [slotChoice] = colour;
	}
	else if (ySlotFilled [slotChoice] == 1)
	{
	    row2 [slotChoice] = colour;
	}
	else if (ySlotFilled [slotChoice] == 2)
	{
	    row3 [slotChoice] = colour;
	}
	else if (ySlotFilled [slotChoice] == 3)
	{
	    row4 [slotChoice] = colour;
	}
	else if (ySlotFilled [slotChoice] == 4)
	{
	    row5 [slotChoice] = colour;
	}
	ySlotFilled [slotChoice]++; //to keep track of how many chips have been placed in each column

    }

    //method to place the computer chip in advanced mode, this method will prevent the user from getting a four in a row in most cases making it harder for the user to win
    public static void placeComputerChip (char[] row1, char[] row2, char[] row3, char[] row4, char[] row5, int[] columnX, int[] columnY, int[] yFilled, Color background)
    {
	int randomSlot = (int) (8 * Math.random ());
	//to check whether there is two yellow in a row in each row and place a chip to get three in a row
	for (int i = 1 ; i < 6 ; i++)
	{
	    if (row1 [i] == 'y' && row1 [i + 1] == 'y' && yFilled [i + 2] == 0
		    || row2 [i] == 'y' && row2 [i + 1] == 'y' && yFilled [i + 2] == 1
		    || row3 [i] == 'y' && row3 [i + 1] == 'y' && yFilled [i + 2] == 2
		    || row4 [i] == 'y' && row4 [i + 1] == 'y' && yFilled [i + 2] == 3
		    || row5 [i] == 'y' && row5 [i + 1] == 'y' && yFilled [i + 2] == 4)
	    {
		randomSlot = i + 2;
		break;
	    }
	}
	//to check whether there is two red with an empty space in the middle and counter it
	for (int i = 1 ; i < 5 ; i++)
	{
	    if (row1 [i] == 'r' && row1 [i + 2] == 'r' && yFilled [i + 1] == 0
		    || row2 [i] == 'r' && row2 [i + 2] == 'r' && yFilled [i + 1] == 1
		    || row3 [i] == 'r' && row3 [i + 2] == 'r' && yFilled [i + 1] == 2
		    || row4 [i] == 'r' && row4 [i + 2] == 'r' && yFilled [i + 1] == 3
		    || row5 [i] == 'r' && row5 [i + 2] == 'r' && yFilled [i + 1] == 4)
	    {
		randomSlot = i + 1;
		break;
	    }
	}
	//to check whether there is two red in a row in each row and place a chip to counter this
	for (int i = 1 ; i < 6 ; i++)
	{
	    if (row1 [i] == 'r' && row1 [i + 1] == 'r' && yFilled [i + 2] == 0
		    || row2 [i] == 'r' && row2 [i + 1] == 'r' && yFilled [i + 2] == 1
		    || row3 [i] == 'r' && row3 [i + 1] == 'r' && yFilled [i + 2] == 2
		    || row4 [i] == 'r' && row4 [i + 1] == 'r' && yFilled [i + 2] == 3
		    || row5 [i] == 'r' && row5 [i + 1] == 'r' && yFilled [i + 2] == 4)
	    {
		randomSlot = i + 2;
		break;
	    }
	}
	//to check whether there is three in a row in each row and place a chip to counter this
	for (int i = 0 ; i < 6 ; i++)
	{
	    if (row1 [i] == 'r' && row1 [i + 1] == 'r' && row1 [i + 2] == 'r')
	    {
		if (i != 0)
		{
		    if (yFilled [i - 1] == 0)
		    {
			randomSlot = i - 1;
			break;
		    }
		}
		if (i < 5)
		{
		    if (yFilled [i + 3] == 0)
		    {
			randomSlot = i + 3;
			break;
		    }
		}
	    }
	    else if (row2 [i] == 'r' && row2 [i + 1] == 'r' && row2 [i + 2] == 'r')
	    {
		if (i != 0)
		{
		    if (yFilled [i - 1] == 1)
		    {
			randomSlot = i - 1;
			break;
		    }
		}
		if (i < 5)
		{
		    if (yFilled [i + 3] == 1)
		    {
			randomSlot = i + 3;
			break;
		    }
		}
	    }
	    else if (row3 [i] == 'r' && row3 [i + 1] == 'r' && row3 [i + 2] == 'r')
	    {
		if (i != 0)
		{
		    if (yFilled [i - 1] == 2)
		    {
			randomSlot = i - 1;
			break;
		    }
		}
		if (i < 5)
		{
		    if (yFilled [i + 3] == 2)
		    {
			randomSlot = i + 3;
			break;
		    }
		}
	    }
	    else if (row4 [i] == 'r' && row4 [i + 1] == 'r' && row4 [i + 2] == 'r')
	    {
		if (i != 0)
		{
		    if (yFilled [i - 1] == 3)
		    {
			randomSlot = i - 1;
			break;
		    }
		}
		if (i < 5)
		{
		    if (yFilled [i + 3] == 3)
		    {
			randomSlot = i + 3;
			break;
		    }
		}
	    }
	    else if (row5 [i] == 'r' && row5 [i + 1] == 'r' && row5 [i + 2] == 'r')
	    {
		if (i != 0)
		{
		    if (yFilled [i - 1] == 4)
		    {
			randomSlot = i - 1;
			break;
		    }
		}
		if (i < 5)
		{
		    if (yFilled [i + 3] == 4)
		    {
			randomSlot = i + 3;
			break;
		    }
		}
	    }
	}
	//to check check whether there is a three in a row column wise and counter it
	for (int i = 0 ; i < 8 ; i++)
	{
	    if (row1 [i] == 'r' && row2 [i] == 'r' && row3 [i] == 'r' && yFilled [i] == 3)
	    {
		randomSlot = i;
		break;
	    }
	    else if (row2 [i] == 'r' && row3 [i] == 'r' && row4 [i] == 'r' && yFilled [i] == 4)
	    {
		randomSlot = i;
		break;
	    }
	}
	//to check whether there is three in a row diagonally and counter it
	for (int i = 0 ; i < 5 ; i++)
	{
	    if (row1 [i] == 'r' && row2 [i + 1] == 'r' && row3 [i + 2] == 'r' && yFilled [i + 3] == 3)
	    {
		randomSlot = i + 3;
		break;
	    }
	    else if (row2 [i] == 'r' && row3 [i + 1] == 'r' && row4 [i + 2] == 'r')
	    {
		if (yFilled [i + 3] == 4)
		{
		    randomSlot = i + 3;
		    break;
		}
		if (i != 0)
		{
		    if (yFilled [i - 1] == 0)
		    {
			randomSlot = i - 1;
			break;
		    }
		}
	    }
	    else if (row1 [i] == 'r' && row3 [i + 2] == 'r' && row4 [i + 3] == 'r' && yFilled [i + 1] == 1
		    || row2 [i] == 'r' && row4 [i + 2] == 'r' && row5 [i + 3] == 'r' && yFilled [i + 1] == 2)
	    {
		randomSlot = i + 1;
		break;
	    }
	    else if (row1 [i] == 'r' && row2 [i + 1] == 'r' && row4 [i + 3] == 'r' && yFilled [i + 2] == 2
		    || row2 [i] == 'r' && row3 [i + 1] == 'r' && row5 [i + 3] == 'r' && yFilled [i + 2] == 3)
	    {
		randomSlot = i + 2;
		break;
	    }
	}
	//to check diagonally the opposite way and counter it
	for (int i = 7 ; i > 2 ; i--)
	{
	    if (row1 [i] == 'r' && row2 [i - 1] == 'r' && row3 [i - 2] == 'r' && yFilled [i - 3] == 3)
	    {
		randomSlot = i - 3;
		break;
	    }
	    else if (row2 [i] == 'r' && row3 [i - 1] == 'r' && row4 [i - 2] == 'r')
	    {
		if (yFilled [i - 3] == 4)
		{
		    randomSlot = i - 3;
		    break;
		}
		if (i < 7)
		{
		    if (yFilled [i + 1] == 0)
		    {
			randomSlot = i + 1;
		    }
		}
	    }
	    else if (row1 [i] == 'r' && row3 [i - 2] == 'r' && row4 [i - 3] == 'r' && yFilled [i - 1] == 1
		    || row2 [i] == 'r' && row4 [i - 2] == 'r' && row5 [i - 3] == 'r' && yFilled [i - 1] == 2)
	    {
		randomSlot = i - 1;
		break;
	    }
	    else if (row1 [i] == 'r' && row2 [i - 1] == 'r' && row4 [i - 3] == 'r' && yFilled [i - 2] == 2
		    || row2 [i] == 'r' && row3 [i - 1] == 'r' && row5 [i - 3] == 'r' && yFilled [i - 2] == 3)
	    {
		randomSlot = i - 2;
		break;
	    }
	}
	//to check whether there is an empty slot in between three slots and counter it
	for (int i = 0 ; i < 5 ; i++)
	{
	    if (row1 [i] == 'r' && row1 [i + 1] == 'r' && row1 [i + 3] == 'r' && yFilled [i + 2] == 0
		    || row2 [i] == 'r' && row2 [i + 1] == 'r' && row2 [i + 3] == 'r' && yFilled [i + 2] == 1
		    || row3 [i] == 'r' && row3 [i + 1] == 'r' && row3 [i + 3] == 'r' && yFilled [i + 2] == 2
		    || row4 [i] == 'r' && row4 [i + 1] == 'r' && row4 [i + 3] == 'r' && yFilled [i + 2] == 3
		    || row5 [i] == 'r' && row5 [i + 1] == 'r' && row5 [i + 3] == 'r' && yFilled [i + 2] == 4)
	    {
		randomSlot = i + 2;
	    }
	    else if (row1 [i] == 'r' && row1 [i + 2] == 'r' && row1 [i + 3] == 'r' && yFilled [i + 1] == 0
		    || row2 [i] == 'r' && row2 [i + 2] == 'r' && row2 [i + 3] == 'r' && yFilled [i + 1] == 1
		    || row3 [i] == 'r' && row3 [i + 2] == 'r' && row3 [i + 3] == 'r' && yFilled [i + 1] == 2
		    || row4 [i] == 'r' && row4 [i + 2] == 'r' && row4 [i + 3] == 'r' && yFilled [i + 1] == 3
		    || row5 [i] == 'r' && row5 [i + 2] == 'r' && row5 [i + 3] == 'r' && yFilled [i + 1] == 4)
	    {
		randomSlot = i + 1;
	    }
	}
	//to check whether yellow has an opportunity to win and place chips accordingly
	for (int i = 0 ; i < 6 ; i++)
	{
	    if (row1 [i] == 'y' && row1 [i + 1] == 'y' && row1 [i + 2] == 'y')
	    {
		if (i != 0)
		{
		    if (yFilled [i - 1] == 0)
		    {
			randomSlot = i - 1;
			break;
		    }
		}
		if (i < 5)
		{
		    if (yFilled [i + 3] == 0)
		    {
			randomSlot = i + 3;
			break;
		    }
		}
	    }
	    else if (row2 [i] == 'y' && row2 [i + 1] == 'y' && row2 [i + 2] == 'y')
	    {
		if (i != 0)
		{
		    if (yFilled [i - 1] == 1)
		    {
			randomSlot = i - 1;
			break;
		    }
		}
		if (i < 5)
		{
		    if (yFilled [i + 3] == 1)
		    {
			randomSlot = i + 3;
			break;
		    }
		}
	    }
	    else if (row3 [i] == 'y' && row3 [i + 1] == 'y' && row3 [i + 2] == 'y')
	    {
		if (i != 0)
		{
		    if (yFilled [i - 1] == 2)
		    {
			randomSlot = i - 1;
			break;
		    }
		}
		if (i < 5)
		{
		    if (yFilled [i + 3] == 2)
		    {
			randomSlot = i + 3;
			break;
		    }
		}
	    }
	    else if (row4 [i] == 'y' && row4 [i + 1] == 'y' && row4 [i + 2] == 'y')
	    {
		if (i != 0)
		{
		    if (yFilled [i - 1] == 3)
		    {
			randomSlot = i - 1;
			break;
		    }
		}
		if (i < 5)
		{
		    if (yFilled [i + 3] == 3)
		    {
			randomSlot = i + 3;
			break;
		    }
		}
	    }
	    else if (row5 [i] == 'y' && row5 [i + 1] == 'y' && row5 [i + 2] == 'y')
	    {
		if (i != 0)
		{
		    if (yFilled [i - 1] == 4)
		    {
			randomSlot = i - 1;
			break;
		    }
		}
		if (i < 5)
		{
		    if (yFilled [i + 3] == 4)
		    {
			randomSlot = i + 3;
			break;
		    }
		}
	    }
	}
	//to check check whether there is a three in a row column wise and win by placing another yellow chip there
	for (int i = 0 ; i < 8 ; i++)
	{
	    if (row1 [i] == 'y' && row2 [i] == 'y' && row3 [i] == 'y' && yFilled [i] == 3)
	    {
		randomSlot = i;
		break;
	    }
	    else if (row2 [i] == 'y' && row3 [i] == 'y' && row4 [i] == 'y' && yFilled [i] == 4)
	    {
		randomSlot = i;
		break;
	    }
	}
	//to check whether there is three in a row diagonally and win by placing another yellow chip there
	for (int i = 0 ; i < 5 ; i++)
	{
	    if (row1 [i] == 'y' && row2 [i + 1] == 'y' && row3 [i + 2] == 'y' && yFilled [i + 3] == 3)
	    {
		randomSlot = i + 3;
		break;
	    }
	    else if (row2 [i] == 'y' && row3 [i + 1] == 'y' && row4 [i + 2] == 'y')
	    {
		if (yFilled [i + 3] == 4)
		{
		    randomSlot = i + 3;
		    break;
		}
		if (i != 0)
		{
		    if (yFilled [i - 1] == 0)
		    {
			randomSlot = i - 1;
			break;
		    }
		}
	    }
	    else if (row1 [i] == 'y' && row3 [i + 2] == 'y' && row4 [i + 3] == 'y' && yFilled [i + 1] == 1
		    || row2 [i] == 'y' && row4 [i + 2] == 'y' && row5 [i + 3] == 'y' && yFilled [i + 1] == 2)
	    {
		randomSlot = i + 1;
		break;
	    }
	    else if (row1 [i] == 'y' && row2 [i + 1] == 'y' && row4 [i + 3] == 'y' && yFilled [i + 2] == 2
		    || row2 [i] == 'y' && row3 [i + 1] == 'y' && row5 [i + 3] == 'y' && yFilled [i + 2] == 3)
	    {
		randomSlot = i + 2;
		break;
	    }
	}
	for (int i = 7 ; i > 2 ; i--)
	{
	    if (row1 [i] == 'y' && row2 [i - 1] == 'y' && row3 [i - 2] == 'y' && yFilled [i - 3] == 3)
	    {
		randomSlot = i - 3;
		break;
	    }
	    else if (row2 [i] == 'y' && row3 [i - 1] == 'y' && row4 [i - 2] == 'y')
	    {
		if (yFilled [i - 3] == 4)
		{
		    randomSlot = i - 3;
		    break;
		}
		if (i < 7)
		{
		    if (yFilled [i + 1] == 0)
		    {
			randomSlot = i + 1;
		    }
		}
	    }
	    else if (row1 [i] == 'y' && row3 [i - 2] == 'y' && row4 [i - 3] == 'y' && yFilled [i - 1] == 1
		    || row2 [i] == 'y' && row4 [i - 2] == 'y' && row5 [i - 3] == 'y' && yFilled [i - 1] == 2)
	    {
		randomSlot = i - 1;
		break;
	    }
	    else if (row1 [i] == 'y' && row2 [i - 1] == 'y' && row4 [i - 3] == 'y' && yFilled [i - 2] == 2
		    || row2 [i] == 'y' && row3 [i - 1] == 'y' && row5 [i - 3] == 'y' && yFilled [i - 2] == 3)
	    {
		randomSlot = i - 2;
		break;
	    }
	}
	//to check whether there is another possible four in a row and win by placing another yellow chip there
	for (int i = 0 ; i < 5 ; i++)
	{
	    if (row1 [i] == 'y' && row1 [i + 1] == 'y' && row1 [i + 3] == 'y' && yFilled [i + 2] == 0
		    || row2 [i] == 'y' && row2 [i + 1] == 'y' && row2 [i + 3] == 'y' && yFilled [i + 2] == 1
		    || row3 [i] == 'y' && row3 [i + 1] == 'y' && row3 [i + 3] == 'y' && yFilled [i + 2] == 2
		    || row4 [i] == 'y' && row4 [i + 1] == 'y' && row4 [i + 3] == 'y' && yFilled [i + 2] == 3
		    || row5 [i] == 'y' && row5 [i + 1] == 'y' && row5 [i + 3] == 'y' && yFilled [i + 2] == 4)
	    {
		randomSlot = i + 2;
	    }
	    else if (row1 [i] == 'y' && row1 [i + 2] == 'y' && row1 [i + 3] == 'y' && yFilled [i + 1] == 0
		    || row2 [i] == 'y' && row2 [i + 2] == 'y' && row2 [i + 3] == 'y' && yFilled [i + 1] == 1
		    || row3 [i] == 'y' && row3 [i + 2] == 'y' && row3 [i + 3] == 'y' && yFilled [i + 1] == 2
		    || row4 [i] == 'y' && row4 [i + 2] == 'y' && row4 [i + 3] == 'y' && yFilled [i + 1] == 3
		    || row5 [i] == 'y' && row5 [i + 2] == 'y' && row5 [i + 3] == 'y' && yFilled [i + 1] == 4)
	    {
		randomSlot = i + 1;
	    }
	}
	//if the slot that is chosen is already filled to the max then it will choose another random one
	while (yFilled [randomSlot] == 5)
	{
	    randomSlot = (int) (8 * Math.random ());
	}

	//to use the slot that the algorithm chooses and pass it into the placeChipPlayer method and place the yellow chip there
	placeChipUser (randomSlot, columnX, columnY, yFilled, row1, row2, row3, row4, row5, background, 'y');
    } //end of place computer chip


    //method to create a background that follows the moving chip and place a background while it is falling into its slot so that the background is still there
    public static void movingChipBackground (int[] columnX, int[] columnY, int slotChoice, int[] yFilled)
    {
	Color boardColor = new Color (0, 0, 128);
	j.setColor (boardColor);
	j.fillRect (columnX [slotChoice], 100, chipSize, 420 - yFilled [slotChoice] * chipSize);
	j.setColor (Color.white);

	for (int i = yFilled [slotChoice] ; i < columnY.length ; i++)
	{
	    j.fillOval (columnX [slotChoice], columnY [i], chipSize, chipSize);
	}
    } //end of movingChipBackground method

//these next couple of methods check whether someone has won yet using the five arrays i used to track what colour is in each slot
    //method to check if yellow has won using a row
    public static int ifRowWinnerYellow (char[] row1, char[] row2, char[] row3, char[] row4, char[] row5, int[] columnX, int[] columnY)
    {
	int connectFour = 0;
	for (int i = 0 ; i < 5 ; i++)
	{
	    if (row1 [i] == 'y' && row1 [i + 1] == 'y' && row1 [i + 2] == 'y' && row1 [i + 3] == 'y')
	    {
		for (int k = 0 ; k < 4 ; k++)
		{
		    j.fillOval (columnX [i + k], columnY [0], chipSize, chipSize);
		    delay (100);
		}
		j.setColor (Color.yellow);
		j.drawString ("Yellow wins", 470, 70);
		connectFour++;
		break;
	    }
	    else if (row2 [i] == 'y' && row2 [i + 1] == 'y' && row2 [i + 2] == 'y' && row2 [i + 3] == 'y')
	    {
		for (int k = 0 ; k < 4 ; k++)
		{
		    j.fillOval (columnX [i + k], columnY [1], chipSize, chipSize);
		    delay (100);
		}
		j.setColor (Color.yellow);
		j.drawString ("Yellow wins", 470, 70);
		connectFour++;
		break;
	    }
	    else if (row3 [i] == 'y' && row3 [i + 1] == 'y' && row3 [i + 2] == 'y' && row3 [i + 3] == 'y')
	    {
		for (int k = 0 ; k < 4 ; k++)
		{
		    j.fillOval (columnX [i + k], columnY [2], chipSize, chipSize);
		    delay (100);
		}
		j.setColor (Color.yellow);
		j.drawString ("Yellow wins", 470, 70);
		connectFour++;
		break;
	    }
	    else if (row4 [i] == 'y' && row4 [i + 1] == 'y' && row4 [i + 2] == 'y' && row4 [i + 3] == 'y')
	    {
		for (int k = 0 ; k < 4 ; k++)
		{
		    j.fillOval (columnX [i + k], columnY [3], chipSize, chipSize);
		    delay (100);
		}
		j.setColor (Color.yellow);
		j.drawString ("Yellow wins", 470, 70);
		connectFour++;
		break;
	    }
	    else if (row5 [i] == 'y' && row5 [i + 1] == 'y' && row5 [i + 2] == 'y' && row5 [i + 3] == 'y')
	    {
		for (int k = 0 ; k < 4 ; k++)
		{
		    j.fillOval (columnX [i + k], columnY [4], chipSize, chipSize);
		    delay (100);
		}
		j.setColor (Color.yellow);
		j.drawString ("Yellow wins", 470, 70);
		connectFour++;
		break;
	    }
	}


	return connectFour;
    } // end of ifRowWinnerYellow method


    //method to check whether red has won in a row
    public static int ifRowWinnerRed (char[] row1, char[] row2, char[] row3, char[] row4, char[] row5, int[] columnX, int[] columnY)
    {
	int connectFour = 0;
	for (int i = 0 ; i < 5 ; i++)
	{
	    if (row1 [i] == 'r' && row1 [i + 1] == 'r' && row1 [i + 2] == 'r' && row1 [i + 3] == 'r')
	    {
		for (int k = 0 ; k < 4 ; k++)
		{
		    j.fillOval (columnX [i + k], columnY [0], chipSize, chipSize);
		    delay (100);
		}
		j.setColor (Color.red);
		j.drawString ("Red Wins", 500, 70);
		connectFour++;
		break;
	    }
	    else if (row2 [i] == 'r' && row2 [i + 1] == 'r' && row2 [i + 2] == 'r' && row2 [i + 3] == 'r')
	    {
		for (int k = 0 ; k < 4 ; k++)
		{
		    j.fillOval (columnX [i + k], columnY [1], chipSize, chipSize);
		    delay (100);
		}
		j.setColor (Color.red);
		j.drawString ("Red Wins", 500, 70);
		connectFour++;
		break;
	    }
	    else if (row3 [i] == 'r' && row3 [i + 1] == 'r' && row3 [i + 2] == 'r' && row3 [i + 3] == 'r')
	    {
		for (int k = 0 ; k < 4 ; k++)
		{
		    j.fillOval (columnX [i + k], columnY [2], chipSize, chipSize);
		    delay (100);
		}
		j.setColor (Color.red);
		j.drawString ("Red Wins", 500, 70);
		connectFour++;
		break;
	    }
	    else if (row4 [i] == 'r' && row4 [i + 1] == 'r' && row4 [i + 2] == 'r' && row4 [i + 3] == 'r')
	    {
		for (int k = 0 ; k < 4 ; k++)
		{
		    j.fillOval (columnX [i + k], columnY [3], chipSize, chipSize);
		    delay (100);
		}
		j.setColor (Color.red);
		j.drawString ("Red Wins", 500, 70);
		connectFour++;
		break;
	    }
	    else if (row5 [i] == 'r' && row5 [i + 1] == 'r' && row5 [i + 2] == 'r' && row5 [i + 3] == 'r')
	    {
		for (int k = 0 ; k < 4 ; k++)
		{
		    j.fillOval (columnX [i + k], columnY [4], chipSize, chipSize);
		    delay (100);
		}
		j.setColor (Color.red);
		j.drawString ("Red Wins", 500, 70);
		connectFour++;
		break;
	    }
	}


	return connectFour;
    } //end of ifRowWinnerRed method


    //method to check whether yellow has won using a column
    public static int ifColumnWinnerYellow (char[] row1, char[] row2, char[] row3, char[] row4, char[] row5, int[] columnX, int[] columnY)
    {
	int connectFour = 0;
	for (int i = 0 ; i < 8 ; i++)
	{
	    if (row1 [i] == 'y' && row2 [i] == 'y' && row3 [i] == 'y' && row4 [i] == 'y')
	    {
		for (int k = 0 ; k < 4 ; k++)
		{
		    j.fillOval (columnX [i], columnY [k], chipSize, chipSize);
		    delay (100);
		}
		j.setColor (Color.yellow);
		j.drawString ("Yellow wins", 470, 70);
		connectFour++;
		break;
	    }
	    else if (row2 [i] == 'y' && row3 [i] == 'y' && row4 [i] == 'y' && row5 [i] == 'y')
	    {
		for (int k = 1 ; k < 5 ; k++)
		{
		    j.fillOval (columnX [i], columnY [k], chipSize, chipSize);
		    delay (100);
		}
		j.setColor (Color.yellow);
		j.drawString ("Yellow wins", 470, 70);
		connectFour++;
		break;
	    }
	}


	return connectFour;
    } //end of ifColumnWinnerYellow method


    //method to check whether red has won using a column
    public static int ifColumnWinnerRed (char[] row1, char[] row2, char[] row3, char[] row4, char[] row5, int[] columnX, int[] columnY)
    {
	int connectFour = 0;
	for (int i = 0 ; i < 8 ; i++)
	{
	    if (row1 [i] == 'r' && row2 [i] == 'r' && row3 [i] == 'r' && row4 [i] == 'r')
	    {
		for (int k = 0 ; k < 4 ; k++)
		{
		    j.fillOval (columnX [i], columnY [k], chipSize, chipSize);
		    delay (100);
		}
		j.setColor (Color.red);
		j.drawString ("Red Wins", 500, 70);
		connectFour++;
		break;
	    }
	    else if (row2 [i] == 'r' && row3 [i] == 'r' && row4 [i] == 'r' && row5 [i] == 'r')
	    {
		for (int k = 1 ; k < 5 ; k++)
		{
		    j.fillOval (columnX [i], columnY [k], chipSize, chipSize);
		    delay (100);
		}
		j.setColor (Color.red);
		j.drawString ("Red Wins", 500, 70);
		connectFour++;
		break;
	    }
	}


	return connectFour;
    } //end of ifColumnWinnerRed method


    //method to check whether yellow has won diagonally
    public static int ifDiagonalWinnerYellow (char[] row1, char[] row2, char[] row3, char[] row4, char[] row5, int[] columnX, int[] columnY)
    {
	int connectFour = 0;
	for (int i = 0 ; i < 5 ; i++)
	{
	    if (row1 [i] == 'y' && row2 [i + 1] == 'y' && row3 [i + 2] == 'y' && row4 [i + 3] == 'y')
	    {
		for (int k = 0 ; k < 4 ; k++)
		{
		    j.fillOval (columnX [i + k], columnY [k], chipSize, chipSize);
		    delay (100);
		}
		j.setColor (Color.yellow);
		j.drawString ("Yellow wins", 470, 70);
		connectFour++;
		break;
	    }
	    else if (row2 [i] == 'y' && row3 [i + 1] == 'y' && row4 [i + 2] == 'y' && row5 [i + 3] == 'y')
	    {
		for (int k = 0 ; k < 4 ; k++)
		{
		    j.fillOval (columnX [i + k], columnY [k + 1], chipSize, chipSize);
		    delay (100);
		}
		j.setColor (Color.yellow);
		j.drawString ("Yellow wins", 470, 70);
		connectFour++;
		break;
	    }
	}


	for (int i = 7 ; i > 2 ; i--)
	{
	    if (row1 [i] == 'y' && row2 [i - 1] == 'y' && row3 [i - 2] == 'y' && row4 [i - 3] == 'y')
	    {
		for (int k = 0 ; k < 4 ; k++)
		{
		    j.fillOval (columnX [i - k], columnY [k], chipSize, chipSize);
		    delay (100);
		}
		j.setColor (Color.yellow);
		j.drawString ("Yellow wins", 470, 70);
		connectFour++;
		break;
	    }
	    else if (row2 [i] == 'y' && row3 [i - 1] == 'y' && row4 [i - 2] == 'y' && row5 [i - 3] == 'y')
	    {
		for (int k = 0 ; k < 4 ; k++)
		{
		    j.fillOval (columnX [i - k], columnY [k + 1], chipSize, chipSize);
		    delay (100);
		}
		j.setColor (Color.yellow);
		j.drawString ("Yellow wins", 470, 70);
		connectFour++;
		break;
	    }
	}


	return connectFour;
    } //end of ifDiagonalWinnerYellow method


    //method to check whether red has won diagonally
    public static int ifDiagonalWinnerRed (char[] row1, char[] row2, char[] row3, char[] row4, char[] row5, int[] columnX, int[] columnY)
    {
	int connectFour = 0;
	for (int i = 0 ; i < 5 ; i++)
	{
	    if (row1 [i] == 'r' && row2 [i + 1] == 'r' && row3 [i + 2] == 'r' && row4 [i + 3] == 'r')
	    {
		for (int k = 0 ; k < 4 ; k++)
		{
		    j.fillOval (columnX [i + k], columnY [k], chipSize, chipSize);
		    delay (100);
		}
		j.setColor (Color.red);
		j.drawString ("Red Wins", 500, 70);
		connectFour++;
		break;
	    }
	    if (row2 [i] == 'r' && row3 [i + 1] == 'r' && row4 [i + 2] == 'r' && row5 [i + 3] == 'r')
	    {
		for (int k = 0 ; k < 4 ; k++)
		{
		    j.fillOval (columnX [i + k], columnY [k + 1], chipSize, chipSize);
		    delay (100);
		}
		j.setColor (Color.red);
		j.drawString ("Red Wins", 500, 70);
		connectFour++;
		break;
	    }
	}


	for (int i = 7 ; i > 2 ; i--)
	{
	    if (row1 [i] == 'r' && row2 [i - 1] == 'r' && row3 [i - 2] == 'r' && row4 [i - 3] == 'r')
	    {
		for (int k = 0 ; k < 4 ; k++)
		{
		    j.fillOval (columnX [i - k], columnY [k], chipSize, chipSize);
		    delay (100);
		}
		j.setColor (Color.red);
		j.drawString ("Red Wins", 500, 70);
		connectFour++;
		break;
	    }
	    else if (row2 [i] == 'r' && row3 [i - 1] == 'r' && row4 [i - 2] == 'r' && row5 [i - 3] == 'r')
	    {
		for (int k = 0 ; k < 4 ; k++)
		{
		    j.fillOval (columnX [i - k], columnY [k + 1], chipSize, chipSize);
		    delay (100);
		}
		j.setColor (Color.red);
		j.drawString ("Red Wins", 500, 70);
		connectFour++;
		break;
	    }
	}


	return connectFour;
    } //end of ifDiagonalWinnerRed method


    //method to display the game over message when the user chooses to exit the game
    public static void gameOver ()
    {
	Font result = new Font ("Impact", Font.ITALIC, 100);
	specialEffects2 ();
	j.setFont (result);
	delay (500);
	for (int i = 0 ; i < 3 ; i++)
	{
	    j.setColor (Color.green);
	    j.drawString ("GAME OVER", 400, 400);
	    delay (500);
	    j.setColor (Color.black);
	    j.drawString ("GAME OVER", 400, 400);
	    delay (500);
	}


	j.setColor (Color.green);
	j.drawString ("GAME OVER", 400, 400);
    } //end of gameOver method


    //to draw the power chip to start off the game
    public static void drawPowerChip (int x, int y, char colour)
    {
	j.setColor (Color.black);
	j.fillOval (x, y, chipSize, chipSize);
	if (colour == 'y')
	{
	    j.setColor (Color.yellow);
	}


	else
	{
	    j.setColor (Color.red);
	}


	int[] x1 = {x + 50, x + 37, x + 15};
	int[] y1 = {y, y + 30, y + 30};
	int[] x2 = {x + 38, x + 55, x + 42, x + 26};
	int[] y2 = {y + 25, y + 25, y + 50, y + 50};
	int[] x3 = {x + 30, x + 60, x + 40};
	int[] y3 = {y + 80, y + 45, y + 45};
	j.fillPolygon (x1, y1, 3);
	j.fillPolygon (x2, y2, 4);
	j.fillPolygon (x3, y3, 3);
    } //end of drawPowerChip method


    //to draw the logo of the company name jozzila inc along with the lightning
    public static void logoName ()
    {
	Font logo = new Font ("Bauhaus 93", Font.BOLD, 100);
	Color logoColor = new Color (192, 192, 192);
	Color lightningColor = new Color (0, 0, 255);
	j.setColor (Color.black);
	j.fillRect (0, 0, j.maxx (), j.maxy ());
	j.setColor (lightningColor);
	int[] x1 = {710, 600, 500};
	int[] y1 = {50, 200, 200};
	int[] x2 = {627, 700, 600, 529};
	int[] y2 = {160, 160, 300, 300};
	int[] x3 = {480, 750, 635};
	int[] y3 = {450, 250, 250};
	j.fillPolygon (x1, y1, 3);
	j.fillPolygon (x2, y2, 4);
	j.fillPolygon (x3, y3, 3);
	j.setColor (logoColor);
	j.setFont (logo);
	j.setColor (logoColor);
	j.drawString ("JOZILLA INC.", 350, 280);
    } //end of logoName method


    //to show loading sign in the beginning to show that the game is loading
    public static void loading ()
    {
	Font loading = new Font ("Impact", Font.ITALIC, 40);
	j.setFont (loading);
	int angleStart = 0;
	j.setColor (Color.white);
	j.drawString ("Loading", 550, 525);
	for (int i = 0 ; i < 300 ; i++)
	{
	    j.setColor (Color.white);
	    j.drawArc (700, 500, 25, 25, angleStart, 330);
	    delay (5);
	    j.setColor (Color.black);
	    j.drawArc (700, 500, 25, 25, angleStart, 330);
	    angleStart--;

	}
    } //end of loading method


    //to remove the power chip from the screen when they choose to use it
    public static void removePowerChip (int x, int y, Color background)
    {
	j.setColor (background);
	x += 40;
	y += 40;
	int l = 1, w = 1;
	while (l != 85)
	{
	    j.fillOval (x, y, l, w);
	    x--;
	    y--;
	    l += 2;
	    w += 2;
	    delay (20);
	}
    } //end of removePowerChip method


    //to place the power chip when the user or computer chooses to
    public static int placePowerChip (int[] columnX, int[] columnY, int[] yFilled, char colour, String columnChars, String[] columnNumbs, Color background, String player, char[] row1, char[] row2, char[] row3, char[] row4, char[] row5)
    {
	Font powerChip = new Font ("Anything", Font.BOLD, 40);
	int x, y = 50;
	int powerChipLocation, chipsErased;
	j.setFont (powerChip);
	j.setColor (Color.black);
	j.drawString ("Power Chip Selected!", 100, 690); //to display that the power chip has been selected
	delay (500);
	if (player.equals ("user"))
	{
	    j.drawString ("Enter column to place power chip: ", 100, 730);
	    powerChipLocation = getSlotChoice (yFilled, columnChars, columnX, columnY, 1, background, 1); //to get the location of the power chip if the user is the one that selected the powerchip
	}
	else
	{
	    do
	    {
		powerChipLocation = (int) (8 * Math.random ()); //to get the location of the power chip if the computer is using the power chip
	    }
	    while (yFilled [powerChipLocation] == 5);
	}
	j.setColor (background);
	j.fillRect (100, 620, 650, 200); //to cover the power chip message
	if (colour == 'y') //to remove the power chips after they are selected
	{
	    j.fillRect (1095, 558, 32, 32);
	    removePowerChip (1010, 530, background);
	}
	else
	{
	    j.fillRect (45, 558, 32, 32);
	    removePowerChip (80, 530, background);
	}

	while (y < columnY [yFilled [powerChipLocation]])
	{
	    drawPowerChip (columnX [powerChipLocation], y, colour);
	    delay (25);
	    if (y < 100) //to redraw the background colour every time the chip moves down until it reaches the board
	    {
		j.setColor (background);
		j.fillRect (columnX [powerChipLocation], y, chipSize, chipSize);
	    }
	    if (y < columnY [yFilled [powerChipLocation]]) //to redraw the board every time the chip moves down
	    {
		movingChipBackground (columnX, columnY, powerChipLocation, yFilled);
	    }
	    y += 10;

	}


	drawPowerChip (columnX [powerChipLocation], columnY [yFilled [powerChipLocation]], colour); // to redraw the chip to make sure it is in the right spot
	row1 [powerChipLocation] = 'n'; //to make the rows tracking what colour chip is in each column know that the chips in the column are gone
	row2 [powerChipLocation] = 'n';
	row3 [powerChipLocation] = 'n';
	row4 [powerChipLocation] = 'n';
	row5 [powerChipLocation] = 'n';
	chipsErased = yFilled [powerChipLocation] + 1; //to track how many chips have been erased to keep track of how many turns are left
	yFilled [powerChipLocation] = 0; // to set the column as empty
	shooter (powerChipLocation, columnX, columnY, yFilled, columnNumbs, background); //to call the shooter method to use the shooter to shoot the laser into the column
	movingChipBackground (columnX, columnY, powerChipLocation, yFilled); //to redraw the board in that column once the laser is done
	return chipsErased;
    } //end of placePowerChip method


    //to draw the laser that shoots the column that the power chip was placed in
    public static void laser (int slotChoice)
    {
	Color board = new Color (0, 0, 128);
	int[] laserPosition = {290, 375, 460, 545, 630, 715, 800, 885}; // array of locations the laser could appear in
	Color laser = new Color ((int) (256 * Math.random ()), (int) (256 * Math.random ()), (int) (256 * Math.random ()));
	int l = 1, w = 1, x = laserPosition [slotChoice], y = 520;
	while (l != 81)//to make the laser ball grow
	{
	    j.setColor (laser);
	    j.fillOval (x, y, l, w);
	    j.setColor (laser);
	    j.fillOval (x + 5, y + 5, l - 10, w - 10);
	    x--;
	    y--;
	    l += 2;
	    w += 2;
	    laser = new Color ((int) (256 * Math.random ()), (int) (256 * Math.random ()), (int) (256 * Math.random ()));
	    delay (50);
	}
	delay (100);
	for (int i = 0 ; i < 1000 ; i++)//to shoot the laser
	{
	    j.setColor (laser);
	    j.fillOval (x, y, l, w);
	    delay (4);
	    j.setColor (board);
	    j.fillOval (x, y, l, w);
	    y--;
	    w++;
	    laser = new Color ((int) (256 * Math.random ()), (int) (256 * Math.random ()), (int) (256 * Math.random ()));
	}
    } //end of laser method


    //to draw the shooter that comes out on the screen and shoots the column that the power chip was placed in
    public static void shooter (int xSlot, int[] columnX, int[] columnY, int[] yFilled, String[] columnNumbs, Color background)
    {
	Color grey = new Color (128, 128, 128);
	int[] shooterX = {150, 235, 320, 405, 490, 575, 660, 745, 830}; //array of locations the shooter could appear in
	int y = 401;
	int x = shooterX [xSlot];
	int[] y1 = {410, 400, 480, 480};
	int[] x1 = {x + 90, x + 100, x + 100, x + 90};
	int[] x2 = {x + 180, x + 170, x + 170, x + 180};
	int[] x3 = {x + 130, x + 120, x + 120, x + 130};
	int[] x4 = {x + 140, x + 150, x + 150, x + 140};
	int[] x5 = {x + 90, x + 180, x + 200, x + 200, x + 180, x + 90, x + 70, x + 70};
	int[] y2 = {480, 480, 500, 560, 580, 580, 560, 500};
	int[] x6 = {x + 100, x + 170, x + 190, x + 190, x + 170, x + 100, x + 80, x + 80};
	int[] y3 = {490, 490, 510, 550, 570, 570, 550, 510};

	//to bring the shooter up
	j.setColor (background);
	j.fillRect (220, 570, 740, 30);
	while (y1 [1] <= j.maxy ())
	{
	    for (int i = 0 ; i < 4 ; i++)
	    {
		y1 [i]++;
	    }
	    for (int i = 0 ; i < 8 ; i++)
	    {
		y2 [i]++;
		y3 [i]++;
	    }
	    y++;
	}
	while (y3 [4] != j.maxy () - 20)
	{
	    for (int i = 0 ; i < 4 ; i++)
	    {
		y1 [i]--;
	    }
	    for (int i = 0 ; i < 8 ; i++)
	    {
		y2 [i]--;
		y3 [i]--;
	    }
	    y--;
	    j.setColor (Color.black);
	    j.fillRect (x + 100, y, 20, 80);
	    j.fillRect (x + 150, y, 20, 80);
	    j.setColor (grey);
	    j.fillPolygon (x1, y1, 4);
	    j.fillPolygon (x2, y1, 4);
	    j.fillPolygon (x3, y1, 4);
	    j.fillPolygon (x4, y1, 4);
	    j.fillPolygon (x5, y2, 8);
	    j.setColor (Color.black);
	    j.fillPolygon (x5, y2, 8); //outside octagon
	    j.setColor (grey);
	    j.fillPolygon (x6, y3, 8); //insode octagon
	    j.setColor (Color.black);
	    j.fillOval (x + 123, y + 115, 25, 25);
	    delay (5);
	    if (y3 [4] >= j.maxy () - 19)
	    {
		j.setColor (background);
		j.fillRect (x + 70, y, 140, 200);
	    }
	}
	laser (xSlot); //to shoot the laser into the column
	j.setColor (background);
	j.fillRect (0, 0, j.maxx (), 100);
	movingChipBackground (columnX, columnY, xSlot, yFilled);
	delay (100);

	//to bring the shooter back down
	j.setColor (background);
	j.fillRect (x + 70, y, 140, 200);
	while (y1 [1] <= j.maxy ())
	{
	    for (int i = 0 ; i < 4 ; i++)
	    {
		y1 [i]++;
	    }
	    for (int i = 0 ; i < 8 ; i++)
	    {
		y2 [i]++;
		y3 [i]++;
	    }
	    y++;
	    j.setColor (background);
	    j.fillRect (x + 70, y, 140, 200);
	    j.setColor (Color.black);
	    j.fillRect (x + 100, y, 20, 80);
	    j.fillRect (x + 150, y, 20, 80);
	    j.setColor (grey);
	    j.fillPolygon (x1, y1, 4);
	    j.fillPolygon (x2, y1, 4);
	    j.fillPolygon (x3, y1, 4);
	    j.fillPolygon (x4, y1, 4);
	    j.fillPolygon (x5, y2, 8);
	    j.setColor (Color.black);
	    j.fillPolygon (x5, y2, 8); //outside octagon
	    j.setColor (grey);
	    j.fillPolygon (x6, y3, 8); //insode octagon
	    j.setColor (Color.black);
	    j.fillOval (x + 123, y + 115, 25, 25);
	    delay (5);
	    j.setColor (background);
	    j.fillRect (x + 70, y, 140, 200);
	}


	columnNumbers (columnNumbs);
    } //end of shooter method


    //to show whose turn it is and to tell the user to select a slot
    public static void playerTurn (int turn, Color background, int chipsErased)
    {
	Font playerTurn = new Font ("Impact", Font.PLAIN, 20);
	Color grey = new Color (25, 25, 25);
	j.setFont (playerTurn);
	turn += chipsErased; //to not effect the turns when the power chip is placed
	if (turn % 2 == 0) //to make it red players turn
	{
	    j.setColor (background);
	    j.drawString ("Player 2 turn", 1000, 480);
	    j.drawString ("Select a Slot: ", 1000, 510);
	    j.setColor (Color.red);
	    j.drawString ("Player 1 turn", 70, 480);
	    j.drawString ("Select a Slot: ", 70, 510);

	    j.fillRect (50, 300, 140, 140);
	    j.setColor (grey);
	    j.drawRect (50, 300, 140, 140);
	    j.fillOval (60, 310, 120, 120);
	    j.fillRect (105, 400, 30, 40);
	    j.setColor (Color.white);
	    j.fillOval (85, 350, 30, 30);
	    j.fillOval (125, 350, 30, 30);
	    j.drawLine (100, 400, 140, 400);

	    j.setColor (Color.white);

	    j.fillRect (980, 300, 140, 140);
	    j.setColor (grey);
	    j.drawRect (980, 300, 140, 140);
	    j.fillOval (990, 310, 120, 120);
	    j.fillRect (1035, 400, 30, 40);
	    j.setColor (Color.white);
	    j.fillOval (1015, 350, 30, 30);
	    j.fillOval (1055, 350, 30, 30);
	    j.drawLine (1030, 400, 1070, 400);
	}
	else //to make it yellow players turn
	{
	    j.setColor (background);
	    j.drawString ("Player 1 turn", 70, 480);
	    j.drawString ("Select a Slot: ", 70, 510);
	    j.setColor (Color.yellow);
	    j.drawString ("Player 2 turn", 1000, 480);
	    j.drawString ("Select a Slot: ", 1000, 510);

	    j.fillRect (980, 300, 140, 140);
	    j.setColor (grey);
	    j.drawRect (980, 300, 140, 140);
	    j.fillOval (990, 310, 120, 120);
	    j.fillRect (1035, 400, 30, 40);
	    j.setColor (Color.white);
	    j.fillOval (1015, 350, 30, 30);
	    j.fillOval (1055, 350, 30, 30);
	    j.drawLine (1030, 400, 1070, 400);

	    j.setColor (Color.white);

	    j.fillRect (50, 300, 140, 140);
	    j.setColor (grey);
	    j.drawRect (50, 300, 140, 140);
	    j.fillOval (60, 310, 120, 120);
	    j.fillRect (105, 400, 30, 40);
	    j.setColor (Color.white);
	    j.fillOval (85, 350, 30, 30);
	    j.fillOval (125, 350, 30, 30);
	    j.drawLine (100, 400, 140, 400);
	}
    } //end of playerTurn method
} //end of class
