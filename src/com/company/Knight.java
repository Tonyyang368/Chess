package com.company;

public class Knight extends Square {
    public char type = 'n';

    public Knight(int x, int y, char color){
        super(x, y, color);
        super.type = 'n';
    }

    public char getAppearance(){
        return this.type;
    }
}
