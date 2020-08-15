package com.company;

import java.util.Map;
import java.util.Scanner;

public class Move {
    public int x;
    public int y;
    public char piece;
    public char color;
    public Board currentBoard;
    public boolean castling = false;

    public Move(char color, Board board) {
            setColor(color);
            this.currentBoard = board;
            getMove();
    }

    public void setColor(char color){
        this.color = color;
    }

    public void getMove() {
        String xConvert = "abcdefgh";
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the piece you're moving: p n b r q k castle");
        this.piece = scan.next().charAt(0);

        if (this.piece == 'c') { //no need to grab coordinates for castle
            return;
        }

        System.out.println("Enter an x value, then a y value");
        this.x = xConvert.indexOf(scan.next());
        this.y = scan.nextInt() - 1;
    }

    public Board action(Square square){//need to update the piece list as well
        if (this.piece == 'k') {
            if (this.color == 'w') {//if the king has moved, it cannot later castle
                currentBoard.pieces.get(5).get(0).hasMoved = true;
            } else {
                currentBoard.pieces.get(11).get(0).hasMoved = true;
            }
        }
        if (this.castling){
            return castleAction();
        }
        else {
            currentBoard.board[this.x][this.y].setColor(square.color);
            currentBoard.board[this.x][this.y].setType(square.type);
            currentBoard.board[square.xPos - 1][square.yPos - 1].setType(' ');
            currentBoard.board[square.xPos - 1][square.yPos - 1].setColor(' ');
        }
            return currentBoard;
    }

    public Square isMoveValid() {
        if ((this.x < 8) && (this.y < 8) &&
                !(currentBoard.board[this.x][this.y].color == this.color) &&
                !(this.piece == 'c')) {//making sure there's no piece of same
                // color on target square
                switch (this.piece) {
                    case 'p':
                        return pawnMove();
                    case 'n':
                        return knightMove();
                    case 'b':
                    case 'r':
                    case 'q':
                    case 'k':
                        return vectorMove(this.piece);
                    default:
                        System.out.print("that's not a valid move\n");
                        break;
                }
            }
        else if (this.piece == 'c'){
            return castleMove();
        }
        return null;
    }

    public Board castleAction(){
        if (this.color == 'w'){
            currentBoard.board[6][0].setType('k');
            currentBoard.board[5][0].setType('r');
            currentBoard.board[4][0].setType(' ');
            currentBoard.board[7][0].setType(' ');
        }
        else{
            currentBoard.board[6][7].setType('k');
            currentBoard.board[5][7].setType('r');
            currentBoard.board[4][7].setType(' ');
            currentBoard.board[7][7].setType(' ');
        }
        return currentBoard;
    }

    public Square castleMove() {
        if (this.color == 'w') {
            if (!currentBoard.pieces.get(5).get(0).hasMoved) {
                if (currentBoard.board[5][0].getType() == ' ' && currentBoard.board[6][0].getType() == ' ' &&
                        currentBoard.board[7][0].getType() == 'r') {
                        this.castling = true;
                        currentBoard.pieces.get(5).get(0).hasMoved = true;
                        return currentBoard.board[0][6];
                    }
                }
            } else {
            if (!currentBoard.pieces.get(11).get(0).hasMoved) {
                if (currentBoard.board[7][5].getType() == ' ' && currentBoard.board[7][6].getType() == ' ' &&
                        currentBoard.board[7][7].getType() == 'r') {
                    this.castling = true;
                    currentBoard.pieces.get(11).get(0).hasMoved = true;
                    return currentBoard.board[7][6];
                }
            }
            }
        return null;
    }

    public Square pawnMove(){
        int direction;
        if (this.color == 'w') {
            direction = 1;
        } else {
            direction = -1;
        }
        int[][] paths = {{this.x, this.y - direction}, {this.x, this.y - 2 * direction}};
        for (int[] path : paths) { //check if any pawns exist in those squares and if
            // these pawns are the right color
            if (((path[0]) < 8) && ((path[0]) >= 0) && ((path[1]) < 8) && ((path[1]) >= 0)) {
                if ((currentBoard.board[path[0]][path[1]].getType() == 'p') && //check pawn is there
                        (currentBoard.board[path[0]][path[1]].getColor() == this.color) //check pawn color
                        && currentBoard.board[this.x][this.y].getType() == ' ') {//check nothing in its path
                    //check to make sure the squares in the path of the piece are free for it to move
                    if (path[1] == this.y - 2 * direction) {
                        if ((currentBoard.board[path[0]][path[1]].yPos == 2 || currentBoard.board[path[0]][path[1]].yPos == 7)
                               && currentBoard.board[path[0]][path[1] + direction].getType() == ' ') {
                            return currentBoard.board[path[0]][path[1]];
                        }
                    }
                    else if (path[1] == this.y - direction){
                        return currentBoard.board[path[0]][path[1]];
                    }
                }
            }
        }
        return pawnCapture(this.color, this.x,this.y);
    }

    public Square pawnCapture(char color, int x, int y){
        if (!(currentBoard.board[x][y].getType() == ' ')) {
            int direction;
            if (this.color == 'w') {
                direction = 1;
            } else {
                direction = -1;
            }
            int[][] paths = {{x - 1, y - direction}, {x + 1, y - direction}};
            for (int[] path : paths) { //check if any pawns exist in those squares and if
                // these pawns are the right color
                if (((path[0]) < 8) && ((path[0]) >= 0) && ((path[1]) < 8) && ((path[1]) >= 0)) {
                    if ((currentBoard.board[path[0]][path[1]].getType() == 'p') &&
                            (currentBoard.board[path[0]][path[1]].getColor() == color)) {
                        return currentBoard.board[path[0]][path[1]];
                    }
                }
            }
        }
            return null;
    }

    public Square knightMove(){
        return knightMove(this.color, this.x, this.y);
    }

    public Square knightMove(char color, int x, int y) {
        int[][] nPaths = {{x - 1, y - 2}, {x + 1, y - 2}, {x - 1, y + 2}, {x + 1, y + 2},
                {x - 2, y - 1}, {x - 2, y + 1}, {x + 2, y - 1}, {x + 2, y + 1}};
        for (int[] nPath : nPaths) {
            if (((nPath[0]) < 8) && ((nPath[0]) >= 0) && ((nPath[1]) < 8) && ((nPath[1]) >= 0)) {
                if ((currentBoard.board[nPath[0]][nPath[1]].getType() == 'n') &&
                        (currentBoard.board[nPath[0]][nPath[1]].getColor() == color)) {
                    System.out.print("ok");
                    return currentBoard.board[nPath[0]][nPath[1]];
                }
            }
        }
        return null;
    }

    public Square vectorMove(char type){//for moving pieces...also input should just be a Square
        return vectorMove(type, this.color, this.x, this.y);
    }

    public Square vectorMove(char type, char color, int x, int y) { //should be part of square :|...for checks
        int[][] directions;
        int max = 8;
        if (type == 'b') {
            directions = new int[][]{{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        } else if (type == 'r') {
            directions = new int[][]{{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
        } else if (type == 'k') {
            directions = new int[][]{{0, 1}, {0, -1}, {-1, 0}, {1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
            max = 2;
        } else if (type == 'q') {
            directions = new int[][]{{0, 1}, {0, -1}, {-1, 0}, {1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        }
        else {
            return null;
        }
        for (int[] direction : directions) {
            for (int magnitude = 1; magnitude < max; magnitude++) {
                int xMove = x + direction[0]*magnitude;
                int yMove = y + direction[1]*magnitude;
                if (xMove < 8 && xMove >= 0 && yMove < 8 && yMove >= 0) {
                    if ((currentBoard.board[xMove][yMove].getType() == type) &&
                            (currentBoard.board[xMove][yMove].getColor() == color)) {
                        return currentBoard.board[xMove][yMove];
                    } else if (!(currentBoard.board[xMove][yMove].getType() == ' ')) {
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }
        return null;
    }

    public boolean checkMate(){
        int[][] directions = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        int max = 2;
        for (int[] direction : directions) {
            for (int magnitude = 1; magnitude < max; magnitude++) {
                int xMove = this.x + direction[0]*magnitude;
                int yMove = this.y + direction[1]*magnitude;
                if (xMove < 8 && xMove > 0 && yMove < 8 && yMove > 0) {
                    if(!((vectorMove('q') == null) && (vectorMove('k') == null) && (vectorMove('r')==null) && (vectorMove('b') == null))){
                        return true;
                    }
                    }
                }
            }
        return false;
    }

    public boolean isCheck() {
        int x;
        int y;
        if (this.color == 'w') {//track black king
            x = currentBoard.pieces.get(11).get(0).xPos - 1;
            y = currentBoard.pieces.get(11).get(0).yPos - 1;
        }else {//track white king
                x = currentBoard.pieces.get(5).get(0).xPos - 1;
                y = currentBoard.pieces.get(5).get(0).yPos - 1;
        }
        if (!(vectorMove('b', this.color, x, y) == null) || !(vectorMove('r', this.color, x, y) == null)
                || !(vectorMove('q',this.color, x, y) == null) || !(knightMove(this.color, x, y) == null)||
                !((pawnCapture(this.color, x, y) == null))) {
            return true;
        }
        return false;
    }
}


