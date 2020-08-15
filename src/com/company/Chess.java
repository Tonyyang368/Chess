package com.company;

public class Chess {

    public static void main(String[] args) {
        boolean win = false;
        boolean turn = false; //not sure if to put this here or in each player class
        Board tester = new Board();

        while (!(win)) {
            turn = !turn;
            tester.updateBoard();
            tester.printBoard();
            if (turn){
                while (true) {
                    System.out.print("white turn\n");
                    Move move = new Move('w', tester);
                    Square square = move.isMoveValid();
                    if(!(square==null)){
                        tester = move.action(square);
                        tester.setCheck(move.isCheck());
                        break;
                    }
                    System.out.print("not a valid move\n");
                }

            }
            else {
                while (true) {
                    System.out.print("black turn\n");
                    Move move = new Move('b', tester);
                    Square square = move.isMoveValid();
                    if (!(square == null)) {
                        tester = move.action(square);
                        tester.setCheck(move.isCheck());
                        break;
                    }
                    System.out.print("not a valid move\n");
                }
            }
            if (tester.getCheck()) {
                System.out.print("you are in check, my dude!");
                win =tester.isCheckmate(turn);
            }
        }
        System.out.println("Checkmate");
    }
    }

