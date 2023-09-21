package com.chess.backend.utils.Algorithm;

import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

import com.github.bhlangonijr.chesslib.move.Move;

public class Search {
    private static Move select_checkmate_move(List<Node> children_board) {
        Move checkmate_move = null;
        for(Node node : children_board.stream().filter(i -> "Win".equals(i.get_result())).collect(Collectors.toList())) {
            Move move = node.board.undoMove();
            node.board.doMove(move);
            checkmate_move = move;
        }
        return checkmate_move;
    }
    private static Move select_best_move(List<Node> children_board) {
        Move best_move = null;
        children_board = children_board.stream().sorted((i1, i2) -> Float.compare(i1.score, i2.score)).collect(Collectors.toList());
        Collections.reverse(children_board);
        for(int i = 0; i < Math.min(children_board.size(), 3); i++) {
            Node node = children_board.get(i);
            Move move = node.board.undoMove();
            node.board.doMove(move);
            if(best_move == null) {
                best_move = move;
            }
        }
        return best_move;
    }
    public static Move search(Node root) {
        Move checkmate_move = null;
        Move best_move = null;
        for(int depth = 1; depth <= Data.depth; depth++) {
            Thread.batch_run(root.children_board);
            //
            int cut_depth = depth - Data.leaf_depth;
            int cut_width = (int)Math.ceil(Data.width / Math.pow(2, depth - 1));
            cut_width = Math.max(cut_width, 3);
            Tree.cut_children_board(root, cut_depth, cut_width);
            //
            if(checkmate_move == null) {
                checkmate_move = select_checkmate_move(root.children_board);
            }
            best_move = select_best_move(root.children_board);
        }
        return checkmate_move != null ? checkmate_move : best_move;
    }
}
