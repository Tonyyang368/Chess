package com.company;

public class Rook extends Square {
    public int[][] movement = new int[2][4];

    public Rook(int x, int y, char color){
        super(x, y, color);
        super.type = 'r';
    }

    public char getAppearance(){
        return this.type;
    }

    //public int[][] getMovement(){//get paths to Move, Move checks if input is
        // along paths, if anything is in its way - also assume not pinned)
        //super.x =
    //}
}
