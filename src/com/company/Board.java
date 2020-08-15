package com.company;

import java.util.ArrayList;

public class Board {

    //variables
    public boolean check = false;
    private int size = 8;
    public ArrayList<ArrayList<Square>> pieces = new ArrayList<ArrayList<Square>>(12);
    public Square[][] board = new Square[8][8];
    public char displayBoard[][] = new char[2 * this.size + 1][2 * this.size + 1];

    //constructor
    Board() {
        makeBoard();
        makePieces();
        setBoard();
        makeDisplayBoard();
    }

    void setCheck(boolean check) {
        this.check = check;
    }

    boolean getCheck() {
        return this.check;
    }

    //functions
    void makeBoard() {
        //initialize the board
        for (int xCount = 0; xCount < this.size; xCount++) {
            for (int yCount = 0; yCount < this.size; yCount++) {
                board[xCount][yCount] = new Square(xCount + 1, yCount + 1, ' ');
            }
        }
    }

    void makeDisplayBoard() {
        //horizontal entries
        for (int i = 1; i < displayBoard.length; i += 2) {
            for (int k = 0; k < displayBoard.length; k++) {
                if (k % 2 == 1) {
                    displayBoard[i][k] = ' ';
                } else {
                    displayBoard[i][k] = '|';
                }
            }
        }
        //vertical entries
        for (int i = 0; i < displayBoard.length; i += 2) {
            for (int k = 0; k < displayBoard.length; k++) {
                if (k % 2 == 1) {
                    displayBoard[i][k] = '_';
                } else {
                    displayBoard[i][k] = '+';
                }
            }
        }
    }

    public void makePieces() {

        for (int x = 0; x < 12; x++) { //six types of pieces and two colors
            pieces.add(new ArrayList());
            for (int y = 0; y < 8; y++)
                pieces.get(x).add(null);
        }

        //white pieces
        for (int pawnNum = 0; pawnNum < 8; pawnNum++) {
            pieces.get(0).set(pawnNum, new Pawn(pawnNum + 1, 2, 'w'));
        }
        pieces.get(1).set(0, new Knight(2, 1, 'w'));
        pieces.get(1).set(1, new Knight(7, 1, 'w'));
        pieces.get(2).set(0, new Bishop(3, 1, 'w'));
        pieces.get(2).set(1, new Bishop(6, 1, 'w'));
        pieces.get(3).set(0, new Rook(1, 1, 'w'));
        pieces.get(3).set(1, new Rook(8, 1, 'w'));
        pieces.get(4).set(0, new Queen(4, 1, 'w'));
        pieces.get(5).set(0, new King(5, 1, 'w'));

        //Black pieces
        for (int pawnNum = 0; pawnNum < 8; pawnNum++) {
            pieces.get(6).set(pawnNum, new Pawn(pawnNum + 1, 7, 'b'));
        }
        pieces.get(7).set(0, new Knight(2, 8, 'b'));
        pieces.get(7).set(1, new Knight(7, 8, 'b'));
        pieces.get(8).set(0, new Bishop(3, 8, 'b'));
        pieces.get(8).set(1, new Bishop(6, 8, 'b'));
        pieces.get(9).set(0, new Rook(1, 8, 'b'));
        pieces.get(9).set(1, new Rook(8, 8, 'b'));
        pieces.get(10).set(0, new Queen(4, 8, 'b'));
        pieces.get(11).set(0, new King(5, 8, 'b'));

    }

    void setBoard() {
        for (ArrayList<Square> piece : pieces) {
            for (Square square : piece) {
                if (!(square == null)) {
                    board[square.xPos - 1][square.yPos - 1].setColor(square.color);
                    board[square.xPos - 1][square.yPos - 1].setType(square.type);
                }
            }
        }
    }

    void updateBoard(){
        for (int horCount = 0; horCount < this.size; horCount++){
            for (int verCount = 0; verCount < this.size; verCount++) {
                if (this.board[verCount][horCount].getColor() == 'w') {
                    this.displayBoard[1 + 2 * horCount][1 + 2 * verCount] =
                            Character.toUpperCase(this.board[verCount][horCount].getType());
                } else {
                    this.displayBoard[1 + 2 * horCount][1 + 2 * verCount] = this.board[verCount][horCount].getType();
                }
            }
        }
    }

    void printBoard() {
        for (int horCount = displayBoard.length-1; horCount > 0; horCount--) {
            for (int verCount = 0; verCount < displayBoard.length - 1; verCount++) {
                System.out.print(displayBoard[horCount][verCount]);
            }
            System.out.println();
        }
   }

//    public boolean vectorCheck(char type, int x, int y, char side) {
//        int[][] directions;
//        if (type == 'b') {
//            directions = new int[][]{{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
//        } else if (type == 'r') {
//            directions = new int[][]{{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
//        } else {
//            directions = new int[][]{{0, 1}, {0, -1}, {-1, 0}, {1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
//        }
//        for (int[] direction : directions) {
//            for (int magnitude = 1; magnitude < 8; magnitude++) {
//                int xMove = x + direction[0] * magnitude;
//                int yMove = y + direction[1] * magnitude;
//                if (xMove < 8 && xMove > 0 && yMove < 8 && yMove > 0 &&
//                        (this.board[xMove][yMove].getType() == 'k')
//                        && (this.board[xMove][yMove].getColor() == side)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    public boolean isCheckmate(boolean turn) {
        int x;
        int y;
        char color;
        if (turn) {//track black king
            x = this.pieces.get(11).get(0).xPos - 1;
            y = this.pieces.get(11).get(0).yPos - 1;
            color = 'w';
        } else {//track white king
            x = this.pieces.get(5).get(0).xPos - 1;
            y = this.pieces.get(5).get(0).yPos - 1;
            color = 'b';
        }
        int[][] directions = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        int magnitude = 1;
        for (int[] direction : directions) {
                int xMove = x + direction[0] * magnitude;
                int yMove = y + direction[1] * magnitude;
                if (xMove < 8 && xMove > 0 && yMove < 8 && yMove > 0) {
                    if (!(vectorMove('b', color, xMove, yMove) == null) || !(vectorMove('r', color, xMove, yMove) == null)
                            || !(vectorMove('q', color, xMove, yMove) == null) || !(knightMove(color, xMove, yMove) == null) ||
                            !((pawnCapture(color, xMove, yMove) == null))) {
                        return true;
                    }
                }
        }
        return false;
    }

    public Square pawnCapture(char color, int x, int y){
        if (!(this.board[x][y].getType() == ' ')) {
            int direction;
            if (color == 'w') {
                direction = 1;
            } else {
                direction = -1;
            }
            int[][] paths = {{x - 1, y - direction}, {x + 1, y - direction}};
            for (int[] path : paths) { //check if any pawns exist in those squares and if
                // these pawns are the right color
                if (((path[0]) < 8) && ((path[0]) >= 0) && ((path[1]) < 8) && ((path[1]) >= 0)) {
                    if ((this.board[path[0]][path[1]].getType() == 'p') &&
                            (this.board[path[0]][path[1]].getColor() == color)) {
                        return this.board[path[0]][path[1]];
                    }
                }
            }
        }
        return null;
    }


    public Square knightMove(char color, int x, int y) {
        int[][] nPaths = {{x - 1, y - 2}, {x + 1, y - 2}, {x - 1, y + 2}, {x + 1, y + 2},
                {x - 2, y - 1}, {x - 2, y + 1}, {x + 2, y - 1}, {x + 2, y + 1}};
        for (int[] nPath : nPaths) {
            if (((nPath[0]) < 8) && ((nPath[0]) >= 0) && ((nPath[1]) < 8) && ((nPath[1]) >= 0)) {
                if ((this.board[nPath[0]][nPath[1]].getType() == 'n') &&
                        (this.board[nPath[0]][nPath[1]].getColor() == color)) {
                    System.out.print("ok");
                    return this.board[nPath[0]][nPath[1]];
                }
            }
        }
        return null;
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
                    if ((this.board[xMove][yMove].getType() == type) &&
                            (this.board[xMove][yMove].getColor() == color)) {
                        return this.board[xMove][yMove];
                    } else if (!(this.board[xMove][yMove].getType() == ' ')) {
                        break;
                    }
                }
            }
        }
        return null;
    }

//    public boolean isCheck(char color) {
//        int x;
//        int y;
//        if (color == 'w') {//track black king
//            x = this.pieces.get(11).get(0).xPos - 1;
//            y = this.pieces.get(11).get(0).yPos - 1;
//        }else {//track white king
//            x = this.pieces.get(5).get(0).xPos - 1;
//            y = this.pieces.get(5).get(0).yPos - 1;
//        }
//        if (!(vectorMove('b', color, x, y) == null) || !(vectorMove('r', color, x, y) == null)
//                || !(vectorMove('q', color, x, y) == null) || !(knightMove(color, x, y) == null)||
//                !((pawnCapture(color, x, y) == null))) {
//            return true;
//        }
//        return false;
//    }

}
