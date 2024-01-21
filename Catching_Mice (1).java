/*Dane Davies
 * Catching Mice
 * 9/26/23
 * The program generates a 8x8 board and then populates it 
 * randomly with 5 mice and a cat, the mice move first and in a random
 * direction, the cat chases the closest mouse, and catches it if it
 * is within two moves. The program ends after all mice are caught.
 * */

import java.util.Random;

public class Catching_Mice {
    private static final int boardSize = 8;
    private static final int miceNum = 5;

    public static void main(String[] args) {
        char[][] board = new char[boardSize][boardSize];
        Random rand = new Random();

        // initialize the board with empty cells
        for(int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = '.';
            }
        }

        // populate the cat randomly on the board
        int catX, catY;
        do{
            catX = rand.nextInt(boardSize);
            catY = rand.nextInt(boardSize);
        } 
        while (board[catX][catY] != '.');
        board[catX][catY] = 'C';

        // populate the mice randomly on the board
        for(int i=0; i<miceNum; i++) {
            int mouseX, mouseY;
            do{
                mouseX = rand.nextInt(boardSize);
                mouseY = rand.nextInt(boardSize);
            } 
            //
            while (board[mouseX][mouseY] != '.');
            board[mouseX][mouseY] = 'M';
        }

       
        boolean game = true;
        // game loop
        while (game = true) {
            printBoard(board);
            

            //moves the mice
            moveMice(board, rand);

            // moves the cat
            moveCat(board);

            // checks if all mice have been caught
            if (!boardHasMice(board)) {
                break;
            }
        }

    }
    
    private static void printBoard(char[][] board) {
        for(int i=0; i<boardSize; i++) {
            for(int j=0; j < boardSize; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        
        }
        System.out.println();
    }

    
    private static void moveMice(char[][] board, Random rand) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == 'M') {
                    int newX, newY;
                    do {
                    	// 0=up, 1=down, 2=left, 3=right
                        int direction = rand.nextInt(4); 
                        newX = i;
                        newY = j;
                        if (direction == 0 && i > 0) {
                            newX--;
                        } else if (direction == 1 && i < boardSize - 1) {
                            newX++;
                        } else if (direction == 2 && j > 0) {
                            newY--;
                        } else if (direction == 3 && j < boardSize - 1) {
                            newY++;
                        }
                    } while (canMove(newX, newY) && board[newX][newY] != '.');
                    if (canMove(newX, newY)) {
                        board[i][j] = '.';
                        board[newX][newY] = 'M';
                    }
               
                
                }
            
            	}
        }
    }

    private static void moveCat(char[][] board) {
        int catX = -1, catY = -1;
        int minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == 'C') {
                    catX = i;
                    catY = j;
                    break;
                }
            }
        	}
        //tracking for cat
        int targetX = -1, targetY = -1;
       
        for(int i=0; i <boardSize; i++) {
            for(int j=0; j < boardSize; j++) {
                if(board[i][j] == 'M') {
                    int distanceX = i - catX;
                    int distanceY = j - catY;
                    int distance = distanceX * distanceX + distanceY * distanceY;

                    if (distance < minDistance) {
                        minDistance = distance;
                        targetX = i;
                        targetY = j;
                    	}
            
                	}
            }
            
        }
        

        if(minDistance > 0) {
            int moveX = Integer.compare(targetX, catX);
            int moveY = Integer.compare(targetY, catY);

            if(Math.abs(moveX) + Math.abs(moveY) > 2) {
                if(Math.abs(moveX) >= Math.abs(moveY)) {
                    moveX = 2 * moveX / Math.abs(moveX);
                    moveY = 0;
                } 
               else {
                    moveX = 0;
                    moveY = 2 * moveY / Math.abs(moveY);
               		}
            }

            int newX = catX + moveX;
            int newY = catY + moveY;

            if (board[newX][newY] == 'M') {
                board[newX][newY] = 'C';
                board[catX][catY] = '.';
            } 
           else {
                board[catX][catY] = '.';
                board[newX][newY] = 'C';
            	}
        }
   }
    
    private static boolean canMove(int x, int y) {
        return x >= 0 && x < boardSize && y >= 0 && y < boardSize;
    }
    
    private static boolean boardHasMice(char[][] board) {
        for (int i=0; i<boardSize; i++) {
            for(int j=0; j<boardSize; j++) {
                if(board[i][j] == 'M') {
                    return true;
                }
            	}
        }
        return false;
    }
}