package com.company;

public class Queen extends Square {
    char appearance = 'q';
    public int[][] movement = new int[2][8];

    public Queen(int x, int y, char color){
        super(x, y, color);
        super.type = 'q';
    }

    public char getAppearance(){
        return this.appearance;
    }
}
