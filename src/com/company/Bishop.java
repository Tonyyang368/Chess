package com.company;

public class Bishop extends Square {
    public char type = 'b';

    public Bishop(int x, int y, char color){
        super(x, y, color);
        super.type = 'b';
    }
}
