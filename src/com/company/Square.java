package com.company;

public class Square {
    public char color;
    public int xPos;
    public int yPos;
    public char type = ' ';
    public boolean hasMoved = false;
    //public Piece piece; //to reflect the state of the square

    public Square(int xPos, int yPos, char color){
        this.xPos = xPos;
        this.yPos = yPos;
        this.color = color;
    }

    public void setColor(char color){
        this.color = color;
    }

    public void setType(char type) { this.type = type; }

    public char getType() { return this.type; }

    public char getColor() { return this.color; }
}
