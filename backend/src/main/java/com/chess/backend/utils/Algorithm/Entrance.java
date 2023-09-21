package com.chess.backend.utils.Algorithm;

import com.github.bhlangonijr.chesslib.move.Move;

public class Entrance {
    public static String fun(String FEN, Integer difficulty,Integer userid) {
        // String FEN;
        // FEN = scanner.nextLine();

        int[] depths = new int[5];
        int[] widths = new int[5];
        int[] pool_sizes = new int[5];
        depths[1]=3;widths[1]=16;pool_sizes[1]=16;
        depths[2]=4;widths[2]=16;pool_sizes[2]=16;
        depths[3]=5;widths[3]=16;pool_sizes[3]=16;
        Data.depth=depths[difficulty];
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
