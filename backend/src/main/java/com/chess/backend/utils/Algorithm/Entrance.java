package com.chess.backend.utils.Algorithm;

//import java.util.Scanner;

import com.github.bhlangonijr.chesslib.move.Move;

public class Entrance {
    public static String fun(String args) {
        String FEN = args;

        //String FEN;
        //FEN = scanner.nextLine();
        Data.init(FEN);
        System.out.println(Data.board);
        Node root = new Node(Data.board);
        Tree.build_tree(root);
        Move best_move = Search.search(root);
        System.out.println("the best move is: " + best_move);
        return best_move.toString();
    }
}
