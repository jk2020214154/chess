package com.chess.backend.utils.Algorithm;

// import java.util.Scanner;

import com.github.bhlangonijr.chesslib.move.Move;

public class Entrance {
    public static String fun(String FEN, Integer difficulty) {
        // String FEN;
        // FEN = scanner.nextLine();
        int[] widths = new int[5];
        int[] pool_sizes = new int[5];
        widths[1]=4;pool_sizes[1]=4;
        widths[2]=8;pool_sizes[2]=8;
        widths[3]=16;pool_sizes[3]=16;
        widths[4]=32;pool_sizes[4]=32;

        Data.width=widths[difficulty];
        Data.pool_size=pool_sizes[difficulty];
        System.out.println("width=" + Data.width + ' ' + Data.pool_size);
        Data.init(FEN);
        System.out.println(Data.board);

        Node root = new Node(Data.board);
        Tree.build_tree(root);
        Move best_move = Search.search(root);
        System.out.println("the best move is: " + best_move);
        return best_move.toString();
    }
}
