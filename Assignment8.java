/*
Thomas Miller 
CSCE 4430
12/5/18
*/

import java.io.IOException;
import java.util.*;
import java.lang.*;

public class Assignment8
{
    public static TreeMap<Integer, Move> Moves = new TreeMap<Integer, Move>();
    /*Integer is the board piece, Move is where the piece goes new_spot*/

    public static void main(String args[]) throws IOException
    {
		//List of moves
        Moves.put(0, new Move(0,1,3));
        Moves.put(1, new Move(0,2,5));
        Moves.put(2 , new Move(1,3,6));
        Moves.put(3, new Move(1,4,8));
        Moves.put(4, new Move(2,4,7));
        Moves.put(5, new Move(2,5,9));
        Moves.put(6, new Move(3,6,10));
        Moves.put(7, new Move(3,7,12));
        Moves.put(8, new Move(4,7,11));
        Moves.put(9, new Move(4,8,13));
        Moves.put(10, new Move(5,8,12));
        Moves.put(11, new Move(5,9,14));
        Moves.put(12, new Move(3,4,5));
        Moves.put(13, new Move(6,7,8));
        Moves.put(14, new Move(7,8,9));
        Moves.put(15, new Move(10,11,12));
        Moves.put(16, new Move(11,12,13));
        Moves.put(17, new Move(12,13,14));

        for(int i = 0; i < 18; i++) //Sets up moves for 18-36, looping errors
        {
            Moves.put(i+18, new Move(Moves.get(i).new_spot, Moves.get(i).jump, Moves.get(i).current));
        }

        Start(0); //Original Missing Piece for puzzle
    }

    public static Board move(Board board, Move piece)
    {
        if(board.peg[piece.current] == 'x' && board.peg[piece.jump] == 'x' &&board.peg[piece.new_spot] == '.') //checks for peg to jump, and open place to go
        {
		   //new spots after moving peg, and removing jumped pegged	
           board.peg[piece.current] = '.';
           board.peg[piece.jump] = '.';
           board.peg[piece.new_spot] = 'x';
           board.piecesLeft = board.piecesLeft - 1;
		   
		   //changes to board
           board.changed = true;

           return board;
        }
        else
        {
           board.changed = false;

           return board;
        }
    }

    public static void FindSolution(Board board)
    {
        for(int i = 0; i < Moves.size(); i++)
        {
            Board newBoard = new Board(board); //creates new board to test all jumps

            Move temp = Moves.get(i);
            newBoard = move(newBoard, temp); //moves peg
			
            if(newBoard.changed)
            {
                newBoard.replay.add(temp); //adds new move
                if(newBoard.piecesLeft < 2) //when you when, 1 piece left
                {
                    Solution(newBoard); //prints solution when down to one peg
                }
				
                FindSolution(newBoard); //Board after moving a peg
            }
        }
    }

    public static void Solution(Board board)
    {
       board.resetBoard(); //go back to original board
       board.print();
        for(int i = 0; i <board.replay.size(); i++) //going through all the saved moves
        {
           board = move(board,board.replay.get(i)); //recreating moves and printing board
           board.print();
        }
        Start(board.piece + 1); //goes to new game with different missing peg
    }

    public static void Start(int start)
    {
        if(start == 5) //solves 5 boards
        {
            System.exit(1); //exit success
        }
        System.out.printf("======= %d =======\n", start);

        Board board = new Board(start); //create new board with missing peg at start
        FindSolution(board);
    }
}

class Board {
    public char peg[] = new char[15];		//Contains the gameboard
    public int piecesLeft;						//Holds the number of full peg
    public boolean changed = false;			                //Lets the FindSolution function know if the board changed
    public ArrayList<Move> replay = new ArrayList<Move>(); //Holds previous moves
    public int piece;						               //Holds the initial start state so the board can be reset once the solution is found
    
    public Board(int s)
    {
        for(int i = 0; i < 15; i++)
        {
            peg[i] = 'x'; //piece in that spot
        }

        peg[s] = '.'; //no piece in the spot, s

        piecesLeft = 14; //14 means new board starting
        piece = s;
    }
    
    public Board(Board board) 
    {
        peg = board.peg.clone(); //copies peg from board

        replay.addAll(board.replay); //copies all replays
        
        piecesLeft = board.piecesLeft;
        piece = board.piece;
        changed = false;
    }

    public void resetBoard() //resetting board before replay to print
    {
        for(int i = 0; i < 15; i++) 
        {
            peg[i] = 'x';
        }
        peg[piece] = '.';
    }

    public void print()
    {
        System.out.printf("%5c\n", peg[0]);
        System.out.printf("%4c %c\n", peg[1], peg[2]);
        System.out.printf("%3c %c %c\n", peg[3], peg[4], peg[5]);
        System.out.printf("%2c %c %c %c\n", peg[6], peg[7], peg[8], peg[9]);
        System.out.printf("%1c %c %c %c %c\n\n", peg[10], peg[11], peg[12], peg[13], peg[14]);
    }


}

class Move
{
    public int current;
    public int jump;
    public int new_spot;

    public Move(int current, int jump, int new_spot)
    {
        this.current = current;
        this.jump = jump;
        this.new_spot = new_spot;
    }
}
