package com.company;

public class Pawn extends Square {
    public char type = 'p';

    public Pawn(int x, int y, char color){
        super(x, y, color);
        super.type = 'p';
    }

    public char getAppearance(){
        return this.type;
    }
}
