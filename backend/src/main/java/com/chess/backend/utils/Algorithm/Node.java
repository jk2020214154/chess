package com.chess.backend.utils.Algorithm;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.PieceType;
import com.github.bhlangonijr.chesslib.Square;

public class Node {
    public Board board;
    public Float score;
    public List<Node> children_board = new ArrayList<>();
    public Node(Board board) {
        this.board = board;
    }
    public String get_result() {
        if(board.isMated()) {
            if(board.getSideToMove() == Data.side) {
                return "Loss";
            }
            else {
                return "Win";
            }
        }
        if(board.isDraw()) {
            return "Draw";
        }
        return null;
    }
    public void set_score() {

        // set score by children
        if(!children_board.isEmpty()) {
            List<Float> child_board_score = children_board.stream().map(i -> i.score).collect(Collectors.toList());
            if(board.getSideToMove() == Data.side) {
                score = child_board_score.stream().max(Float::compare).get();
            }
            else {
                score = child_board_score.stream().min(Float::compare).get();
            }
            return ;
        }

        score = 0.0F;

        // piece and cell score
        {
            for(int i = 0; i < 64; i++) {
                Square square = Square.squareAt(i);
                Piece piece = board.getPiece(square);

                // cell under control score
                if(piece == Piece.NONE) {
                    if(board.squareAttackedBy(square, Data.side) > 0L) {
                        score += 0.2F;
                    }
                    if(board.squareAttackedBy(square, Data.side.flip()) > 0L) {
                        score -= 0.2F;
                    }
                    continue;
                }
                float piece_score = Data.piece_score.get(piece.getPieceType());
                float cell_score = Data.cell_score.get(piece.getPieceSide()).get(piece.getPieceType()).get(i);
                if(piece.getPieceSide() == Data.side) {
                    score += piece_score + cell_score;
                }
                else {
                    score -= piece_score + cell_score;
                }

                // piece has root or attacking score
                if(piece.getPieceType() == PieceType.KING) {
                    continue;
                }
                if(board.squareAttackedBy(square, Data.side) > 0L) {
                    score += piece_score * 0.05F;
                }
                if(board.squareAttackedBy(square, Data.side.flip()) > 0L) {
                    score -= piece_score * 0.05F;
                }
            }
        }

        // score by result
        {
            String result = get_result();
            if("Win".equals(result)) {
                score += 10000.0F;
            }
            if("Loss".equals(result)) {
                score -= 10000.0F;
            }
            if("Draw".equals(result)) {
                if(score > 0.0F) {
                    score -= 10000.0F;
                }
                else {
                    score += 10000.0F;
                }
            }
        }

        // if check + or - score
        {
            if(board.squareAttackedBy(board.getKingSquare(Data.side.flip()), Data.side) > 0L) {
                score += 5.0F;
            }
            if(board.squareAttackedBy(board.getKingSquare(Data.side), Data.side.flip()) > 0L) {
                score -= 5.0F;
            }
        }

        // freedom score
        {
            int move_score_curr = board.legalMoves().size();
            board.setSideToMove(board.getSideToMove().flip());
            int move_score_next = board.legalMoves().size();
            board.setSideToMove(board.getSideToMove().flip());
            if(board.getSideToMove() == Data.side) {
                score += (move_score_curr - move_score_next) * 0.2F;
            }
            else {
                score -= (move_score_curr - move_score_next) * 0.2F;
            }
        }
    }
}
