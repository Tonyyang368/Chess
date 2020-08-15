package com.company;

public abstract class Piece {
    public char color;
    public int x;
    public int y;


    public Piece(int x, int y, char color){
       // this.x = x;
       // this.y = y;
        this.color = color;
    }

    public char isCaptured(int x, int y){
        return ' ';
    }

    //public boolean isPinned(){ - inputs would be queen, rook, king and bishop positions
        //boolean pinStatus = false;
        //if ()
        //return pinStatus;
   // }
}
