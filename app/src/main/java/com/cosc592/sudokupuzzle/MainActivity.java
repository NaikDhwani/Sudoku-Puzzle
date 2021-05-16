package com.cosc592.sudokupuzzle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ModelActivity MA = new ModelActivity();
    public static InterfaceActivity IA;
    private final String CURRENT_FILE_NAME = "CurrentSudoku";
    private final String ORIGINAL_FILE_NAME = "OriginalSudoku";
    public int[][] originalBoard,currentBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        int boardWidth = screenSize.x;

        ButtonHandler handler = new ButtonHandler();
        IA = new InterfaceActivity(this, boardWidth, handler);
        setContentView(IA);
        setHandler();
    }

    public void setHandler(){
        //Creates EditText handler
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
            {
                TextChangeHandler temp = new TextChangeHandler(i, j);
                IA.setTextChangeHandler(temp, i, j);
            }
    }

    private class ButtonHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int id = v.getId();

            if(id == IA.newButton.getId()){
                try {
                    IA.newBoard();
                    Toast.makeText(getApplicationContext(),"New Board",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Start Again",Toast.LENGTH_SHORT).show();
                }
            }else if (id == IA.saveButton.getId()){

                try {
                    Save();
                    Toast.makeText(getApplicationContext(),"Successfully Saved Board",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Board Not Saved Successfully",Toast.LENGTH_SHORT).show();
                }
            }else{
                try {
                    Show();
                    IA.setStoredBoard(originalBoard,currentBoard);
                    Toast.makeText(getApplicationContext(),"Your Saved Board",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"No Saved Board",Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    private void Show() {
        try {
            FileInputStream originalIn = openFileInput(ORIGINAL_FILE_NAME);
            InputStreamReader originalISR = new InputStreamReader(originalIn);
            BufferedReader originalBufferReader = new BufferedReader(originalISR);

            FileInputStream currentIn = openFileInput(CURRENT_FILE_NAME);
            InputStreamReader currentISR = new InputStreamReader(currentIn);
            BufferedReader currentBufferReader = new BufferedReader(currentISR);

            String line;
            originalBoard = new int[9][9];
            String originalValue = "";
            currentBoard = new int[9][9];
            String currentValue = "";

            while ((line = originalBufferReader.readLine()) != null){
                originalValue = line;
            }
            while ((line = currentBufferReader.readLine()) != null){
                currentValue = line;
            }
            String[] originalVS = originalValue.split("\\s+");
            String[] currentVS = currentValue.split("\\s+");
            Log.d("Original Values", Arrays.toString(originalVS));

            int k = 0;
            for (int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    originalBoard[i][j] = Integer.parseInt(originalVS[k]);
                    currentBoard[i][j] = Integer.parseInt(currentVS[k]);
                    Log.d("Original", originalBoard[i][j]+"");
                    k ++;
                }
            }
            originalIn.close();
            currentIn.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Can not Retrieve",Toast.LENGTH_SHORT).show();
        }
    }

    private void Save() {
        try {
            FileOutputStream originalOut = openFileOutput(ORIGINAL_FILE_NAME, Context.MODE_PRIVATE);
            FileOutputStream currentOut = openFileOutput(CURRENT_FILE_NAME, Context.MODE_PRIVATE);
            int[][] currentBoard = IA.getCurrentBoard();
            int[][] originalBoard = IA.getOriginalBoard();
            String current ="", original="";

            for (int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    current += currentBoard[i][j]+" ";
                    original += originalBoard[i][j]+" ";
                }
            }
            originalOut.write(original.getBytes());
            originalOut.close();
            currentOut.write(current.getBytes());
            currentOut.close();
            Log.d("Original",original);
            Log.d("Current",current);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Can not Save",Toast.LENGTH_SHORT).show();
        }
    }

    public int[][] getInitialBoard() {
        return MA.generate();
    }

    private class TextChangeHandler implements TextWatcher
    {
        private int x;
        private int y;
        public TextChangeHandler(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
        public void afterTextChanged(Editable e)
        {
            int[][] board = IA.getCurrentBoard();
            int input = IA.getInput(x, y);

            //Input go for validation as per Sudoku rules
            boolean check = MA.checkInput(x, y, input, board);
            if (check) {
                IA.updateBoard(x, y, input);
            } else {
                IA.updateBoard(x, y, 0);
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        public void onTextChanged(CharSequence s, int start, int before, int after) { }
    }
}

