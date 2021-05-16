/* Application-6 Sudoku Puzzle
  View Class */
package com.cosc592.sudokupuzzle;

import android.content.Context;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InterfaceActivity extends LinearLayout {

    final int DP;
    MainActivity m = new MainActivity();
    int[][] initialBoard = m.getInitialBoard();
    int[][] originalBoard = new int[9][9];
    int boardWidth;
    LinearLayout mainLayout;
    GridLayout sudokuGrid;
    EditText[][] sudokuBoardEditText = new EditText[9][9];
    TextView successText;
    Button newButton, saveButton, retrieveButton;

    public InterfaceActivity(Context context, int boardWidth, View.OnClickListener buttonHandler) {
        super(context);
        this.boardWidth = boardWidth;

        DP = (int)(getResources().getDisplayMetrics().density);

        mainLayout = new LinearLayout(context);
        mainLayout.setOrientation(VERTICAL);
        mainLayout.setBackgroundColor(getResources().getColor(R.color.appBackground));
        LayoutParams mainParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        mainLayout.setLayoutParams(mainParams);
        addView(mainLayout);

        sudokuGrid = new GridLayout(context);
        sudokuGrid.setBackgroundColor(Color.BLACK);
        sudokuGrid.setPadding(5*DP,5*DP,5*DP,5*DP);
        LayoutParams params = new LayoutParams(boardWidth, boardWidth);
        params.topMargin = 20*DP;
        sudokuGrid.setLayoutParams(params);
        mainLayout.addView(sudokuGrid);

        setBoard();

        LinearLayout buttonLayout = new LinearLayout(context);
        buttonLayout.setOrientation(HORIZONTAL);
        buttonLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        buttonLayout.setPadding(0*DP,5*DP,0*DP,5*DP);
        params = new LayoutParams(boardWidth, LayoutParams.WRAP_CONTENT);
        buttonLayout.setLayoutParams(params);
        mainLayout.addView(buttonLayout);

        newButton = new Button(context);
        newButton.setId(Button.generateViewId());
        newButton.setOnClickListener(buttonHandler);
        newButton.setTextColor(getResources().getColor(R.color.editTextBackground1));
        newButton.setBackgroundColor(getResources().getColor(R.color.editTextBackground2));
        newButton.setText("New");
        newButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        params = new LayoutParams(0*DP, LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        params.leftMargin = 5*DP;
        newButton.setLayoutParams(params);
        buttonLayout.addView(newButton);

        saveButton = new Button(context);
        saveButton.setId(Button.generateViewId());
        saveButton.setOnClickListener(buttonHandler);
        saveButton.setTextColor(getResources().getColor(R.color.editTextBackground1));
        saveButton.setBackgroundColor(getResources().getColor(R.color.editTextBackground2));
        saveButton.setText("Save");
        saveButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        params = new LayoutParams(0*DP, LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        params.leftMargin = 5*DP;
        params.rightMargin = 5*DP;
        saveButton.setLayoutParams(params);
        buttonLayout.addView(saveButton);

        retrieveButton = new Button(context);
        retrieveButton.setId(Button.generateViewId());
        retrieveButton.setOnClickListener(buttonHandler);
        retrieveButton.setTextColor(getResources().getColor(R.color.editTextBackground1));
        retrieveButton.setBackgroundColor(getResources().getColor(R.color.editTextBackground2));
        retrieveButton.setText("Retrieve");
        retrieveButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        params = new LayoutParams(0*DP, LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        params.rightMargin = 5*DP;
        retrieveButton.setLayoutParams(params);
        buttonLayout.addView(retrieveButton);
    }

    //Attached textChange Handlers to all editText
    public void setTextChangeHandler(TextWatcher textChangeHandler, int i, int j)
    {
        sudokuBoardEditText[i][j].addTextChangedListener(textChangeHandler);
    }

    public int getInput(int i, int j){
        try {
            int input = Integer.valueOf(sudokuBoardEditText[i][j].getText().toString());
            return input;
        }catch (Exception e){
            return 0;
        }
    }

    public int[][] getCurrentBoard(){
        return initialBoard;
    }

    public int[][] getOriginalBoard(){
        return originalBoard;
    }

    //Modify current board and check for success
    public boolean updateBoard(int x, int y, int input){
        initialBoard[x][y]=input;
        if (input == 0)
            sudokuBoardEditText[x][y].getText().clear();

        for (int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(initialBoard[i][j] == 0)
                    return false;
            }}
        showSuccessMessage();
        return true;
    }

    //Show success message and make all editText disable
    private void showSuccessMessage() {
        successText.setText("Congratulations!");
        for (int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                sudokuBoardEditText[i][j].setBackgroundColor(getResources().getColor(R.color.editTextBackground2));
                sudokuBoardEditText[i][j].setEnabled(false);
            }}
    }

    private int getValues(int[][] board, int i, int j) {
        int value = board[i][j];
        return value;
    }

    //Set the board
    private void setBoard(){
        for (int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                sudokuBoardEditText[i][j] = new EditText(getContext());
                sudokuBoardEditText[i][j].setTextColor(Color.BLACK);
                sudokuBoardEditText[i][j].setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                sudokuBoardEditText[i][j].setGravity(Gravity.CENTER);
                //Set the input limit to 1
                sudokuBoardEditText[i][j].setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
                //Input type is numbers only and next setting is to allow only numbers between 1to9
                sudokuBoardEditText[i][j].setInputType(InputType.TYPE_CLASS_NUMBER);
                sudokuBoardEditText[i][j].setKeyListener(DigitsKeyListener.getInstance("123456789"));
                int number = getValues(initialBoard, i, j);
                originalBoard[i][j] = number;
                if(number == 0){
                    sudokuBoardEditText[i][j].setText("");
                    sudokuBoardEditText[i][j].setBackgroundColor(getResources().getColor(R.color.editTextBackground1));
                }else {
                    sudokuBoardEditText[i][j].setText(String.valueOf(number));
                    sudokuBoardEditText[i][j].setBackgroundColor(getResources().getColor(R.color.editTextBackground2));
                    sudokuBoardEditText[i][j].setEnabled(false);
                }
                GridLayout.LayoutParams gridParams = new GridLayout.LayoutParams();
                gridParams.width = (boardWidth-50)/9;
                gridParams.height = (boardWidth-50)/9;
                gridParams.topMargin = 1*DP;
                gridParams.rightMargin = 1*DP;
                gridParams.leftMargin = 1*DP;
                gridParams.bottomMargin = 1*DP;
                gridParams.rowSpec = GridLayout.spec(i,1);
                gridParams.columnSpec = GridLayout.spec(j,1);
                sudokuBoardEditText[i][j].setLayoutParams(gridParams);
                sudokuGrid.addView(sudokuBoardEditText[i][j]);
            }
        }

        successText = new TextView(getContext());
        successText.setTextColor(getResources().getColor(R.color.editTextBackground1));
        successText.setText("Good Luck");
        successText.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        successText.setGravity(Gravity.CENTER);
        LayoutParams params1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params1.topMargin = 50*DP;
        params1.bottomMargin = 50*DP;
        successText.setLayoutParams(params1);
        mainLayout.addView(successText);
    }

    //Set the new board
    public void newBoard(){
        initialBoard = m.getInitialBoard();
        setBoard();
        m.setHandler();
    }

    //Set the stored board
    public void setStoredBoard(int[][] original, int[][] current){
        for (int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                sudokuBoardEditText[i][j] = new EditText(getContext());
                sudokuBoardEditText[i][j].setTextColor(Color.BLACK);
                sudokuBoardEditText[i][j].setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                sudokuBoardEditText[i][j].setGravity(Gravity.CENTER);
                //Set the input limit to 1
                sudokuBoardEditText[i][j].setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
                //Input type is numbers only and next setting is to allow only numbers between 1to9
                sudokuBoardEditText[i][j].setInputType(InputType.TYPE_CLASS_NUMBER);
                sudokuBoardEditText[i][j].setKeyListener(DigitsKeyListener.getInstance("123456789"));
                int originalNumber = getValues(original, i, j);
                int currentNumber = getValues(current, i , j);
                originalBoard[i][j] = originalNumber;
                initialBoard[i][j] = currentNumber;
                if(originalNumber == 0){
                    sudokuBoardEditText[i][j].setBackgroundColor(getResources().getColor(R.color.editTextBackground1));
                }else {
                    sudokuBoardEditText[i][j].setBackgroundColor(getResources().getColor(R.color.editTextBackground2));
                    sudokuBoardEditText[i][j].setEnabled(false);
                }
                if(currentNumber == 0){
                    sudokuBoardEditText[i][j].setText("");
                }else {
                    sudokuBoardEditText[i][j].setText(String.valueOf(currentNumber));
                }
                GridLayout.LayoutParams gridParams = new GridLayout.LayoutParams();
                gridParams.width = (boardWidth-50)/9;
                gridParams.height = (boardWidth-50)/9;
                gridParams.topMargin = 1*DP;
                gridParams.rightMargin = 1*DP;
                gridParams.leftMargin = 1*DP;
                gridParams.bottomMargin = 1*DP;
                gridParams.rowSpec = GridLayout.spec(i,1);
                gridParams.columnSpec = GridLayout.spec(j,1);
                sudokuBoardEditText[i][j].setLayoutParams(gridParams);
                sudokuGrid.addView(sudokuBoardEditText[i][j]);
            }
        }
        m.setHandler();
    }
}
