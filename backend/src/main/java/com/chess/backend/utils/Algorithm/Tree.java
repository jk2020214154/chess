package com.chess.backend.utils.Algorithm;

import java.util.Collections;
import java.util.stream.Collectors;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

public class Tree {
    public static void build_tree(Node node) {
        if(node.get_result() != null) {
            return ;
        }
        if(!node.children_board.isEmpty()) {
            for(Node i : node.children_board) {
                build_tree(i);
            }
            return ;
        }
        for(Move i : node.board.legalMoves()) {
            Board child_board = node.board.clone();
            child_board.doMove(i);
            node.children_board.add(new Node(child_board));
        }
    }
    public static void set_tree_score(Node node) {
        for(Node i : node.children_board) {
            set_tree_score(i);
        }
        node.set_score();
    }
    public static void cut_children_board(Node node, int depth, int width) {
        if(depth < 0 || node.children_board.isEmpty()) {
            return ;
        }
        node.children_board = node.children_board.stream().sorted((i1, i2) -> Float.compare(i1.score, i2.score)).collect(Collectors.toList());
        if(node.board.getSideToMove() == Data.side) {
            Collections.reverse(node.children_board);
        }
        node.children_board = node.children_board.subList(0, Math.min(width, node.children_board.size()));
        width = width * 2;
        width = Math.min(width, Data.width);
        for(Node i : node.children_board) {
            cut_children_board(i, depth + 1, width);
        }
    }
}
