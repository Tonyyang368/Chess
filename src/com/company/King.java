package com.company;

public class King extends Square {
    public char type = 'k';
    public boolean castle = true;

    public King(int x, int y, char color){
        super(x, y, color);
        super.type = 'k';
    }

    public void setCastle(boolean moved){
        this.castle = !(moved);
    }

    public boolean getCastle(){ return castle; }

    public char getAppearance(){
        return this.type;
    }
}
