/* Application-6 Sudoku Puzzle
 Model Class */
package com.cosc592.sudokupuzzle;

import java.util.Random;

public class ModelActivity {

    public int[][] generate()
    {
        Random rand = new Random();

        int[][] board = new int[9][9];
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                board[i][j] = 0;

        int[][][] order = new int[9][9][9];
        for (int x = 0; x < 9; x++)
            for (int y = 0; y < 9; y++)
            {
                for (int i = 0; i < 9; i++)
                    order[x][y][i] = i+1;

                for (int i = 0; i < 9; i++)
                {
                    int j = i + rand.nextInt(9-i);
                    int temp = order[x][y][i];
                    order[x][y][i] = order[x][y][j];
                    order[x][y][j] = temp;
                }
            }

        fill(board, order, 0);

        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (rand.nextDouble() < 0.4)
                    board[i][j] = board[i][j];
                else
                    board[i][j] = 0;

        return board;
    }

    private boolean fill(int[][] board, int[][][] order, int location)
    {
        int x = location/9;
        int y = location%9;
        int k;

        if (location > 80)
            return true;
        else if (board[x][y] != 0)
            return fill(board, order, location+1);
        else
        {
            for (k = 0; k < 9; k++)
            {
                board[x][y] = order[x][y][k];
                if (check(board, x, y) && fill(board, order, location+1))
                    return true;
            }
            board[x][y] = 0;
            return false;
        }
    }

    private boolean check(int[][] board, int x, int y)
    {
        int a, b, i, j;

        for (j = 0; j < 9; j++)
            if (j != y && board[x][j] == board[x][y])
                return false;

        for (i = 0; i < 9; i++)
            if (i != x && board[i][y] == board[x][y])
                return false;

        a = (x/3)*3; b = (y/3)*3;
        for (i = 0; i < 3; i++)
            for (j = 0; j < 3; j++)
                if ((a + i != x) && (b + j != y) && board[a+i][b+j] == board[x][y])
                    return false;

        return true;
    }

    public boolean checkInput(int x, int y, int value, int[][] board)
    {
        //Input value validation with row
        for (int j = 0; j < 9; j++)
            if (j != y && board[x][j] == value){
                return false;}

        //Input value validation with column
        for (int i = 0; i < 9; i++)
            if (i != x && board[i][y] == value){
                return false;}

        //Input value validation with it's 3X3 square
        int a= x-(x%3);
        int b= y-(y%3);
        for (int i = a; i < a + 3; i++)
            for (int j = b; j < b+ 3; j++)
                if (board[i][j] == value)
                    return false;
        return true;
    }
}
