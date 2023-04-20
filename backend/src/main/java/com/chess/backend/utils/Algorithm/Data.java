package com.chess.backend.utils.Algorithm;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.PieceType;

public class Data {
    public static Board board;
    public static Side side;
    public static int depth = 4;
    public static int width = 16;
    public static int pool_size = 8;
    public static Map<PieceType, Float> piece_score = new HashMap<>();
    static {
        piece_score.put(PieceType.PAWN, 10.0F);
        piece_score.put(PieceType.KNIGHT, 30.0F);
        piece_score.put(PieceType.BISHOP, 30.0F);
        piece_score.put(PieceType.ROOK, 50.0F);
        piece_score.put(PieceType.QUEEN, 90.0F);
        piece_score.put(PieceType.KING, 900.0F);
    }
    public static Map<Side, Map<PieceType, List<Float>>> cell_score = new HashMap<>();
    static {
        Map<PieceType, List<Float>> black = new HashMap<>();
        Map<PieceType, List<Float>> white = new HashMap<>();
        cell_score.put(Side.BLACK, black);
        cell_score.put(Side.WHITE, white);

        black.put(PieceType.PAWN, Arrays.asList( 0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,
                                                 5.0F,  5.0F,  5.0F,  5.0F,  5.0F,  5.0F,  5.0F,  5.0F,
                                                 1.0F,  1.0F,  2.0F,  3.0F,  3.0F,  2.0F,  1.0F,  1.0F,
                                                 0.5F,  0.5F,  1.0F,  2.5F,  2.5F,  1.0F,  0.5F,  0.5F,
                                                 0.0F,  0.0F,  0.0F,  2.0F,  2.0F,  0.0F,  0.0F,  0.0F,
                                                 0.5F, -0.5F, -1.0F,  0.0F,  0.0F, -1.0F, -0.5F,  0.5F,
                                                 0.5F,  1.0F,  1.0F, -2.0F, -2.0F,  1.0F,  1.0F,  0.5F,
                                                 0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F));

        black.put(PieceType.KNIGHT, Arrays.asList(-5.0F, -4.0F, -3.0F, -3.0F, -3.0F, -3.0F, -4.0F, -5.0F,
                                                  -4.0F, -2.0F,  0.0F,  0.0F,  0.0F,  0.0F, -2.0F, -4.0F,
                                                  -3.0F,  0.0F,  1.0F,  1.5F,  1.5F,  1.0F,  0.0F, -3.0F,
                                                  -3.0F,  0.5F,  1.5F,  2.0F,  2.0F,  1.5F,  0.5F, -3.0F,
                                                  -3.0F,  0.0F,  1.5F,  2.0F,  2.0F,  1.5F,  0.0F, -3.0F,
                                                  -3.0F,  0.5F,  1.0F,  1.5F,  1.5F,  1.0F,  0.5F, -3.0F,
                                                  -4.0F, -2.0F,  0.0F,  0.5F,  0.5F,  0.0F, -2.0F, -4.0F,
                                                  -5.0F, -4.0F, -3.0F, -3.0F, -3.0F, -3.0F, -4.0F, -5.0F));

        black.put(PieceType.BISHOP, Arrays.asList(-2.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F, -2.0F,
                                                  -1.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F, -1.0F,
                                                  -1.0F,  0.0F,  0.5F,  1.0F,  1.0F,  0.5F,  0.0F, -1.0F,
                                                  -1.0F,  0.5F,  0.5F,  1.0F,  1.0F,  0.5F,  0.5F, -1.0F,
                                                  -1.0F,  0.0F,  1.0F,  1.0F,  1.0F,  1.0F,  0.0F, -1.0F,
                                                  -1.0F,  1.0F,  1.0F,  1.0F,  1.0F,  1.0F,  1.0F, -1.0F,
                                                  -1.0F,  0.5F,  0.0F,  0.0F,  0.0F,  0.0F,  0.5F, -1.0F,
                                                  -2.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F, -2.0F));

        black.put(PieceType.ROOK, Arrays.asList( 0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,
                                                 0.5F,  1.0F,  1.0F,  1.0F,  1.0F,  1.0F,  1.0F,  0.5F,
                                                -0.5F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F, -0.5F,
                                                -0.5F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F, -0.5F,
                                                -0.5F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F, -0.5F,
                                                -0.5F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F, -0.5F,
                                                -0.5F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F, -0.5F,
                                                 0.0F,  0.0F,  0.0F,  0.5F,  0.5F,  0.0F,  0.0F,  0.0F));

        black.put(PieceType.QUEEN, Arrays.asList(-2.0F, -1.0F, -1.0F, -0.5F, -0.5F, -1.0F, -1.0F, -2.0F,
                                                 -1.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F, -1.0F,
                                                 -1.0F,  0.0F,  0.5F,  0.5F,  0.5F,  0.5F,  0.0F, -1.0F,
                                                 -0.5F,  0.0F,  0.5F,  0.5F,  0.5F,  0.5F,  0.0F, -0.5F,
                                                  0.0F,  0.0F,  0.5F,  0.5F,  0.5F,  0.5F,  0.0F, -0.5F,
                                                 -1.0F,  0.5F,  0.5F,  0.5F,  0.5F,  0.5F,  0.0F, -1.0F,
                                                 -1.0F,  0.0F,  0.5F,  0.0F,  0.0F,  0.0F,  0.0F, -1.0F,
                                                 -2.0F, -1.0F, -1.0F, -0.5F, -0.5F, -1.0F, -1.0F, -2.0F));

        black.put(PieceType.KING, Arrays.asList(-3.0F, -4.0F, -4.0F, -5.0F, -5.0F, -4.0F, -4.0F, -3.0F,
                                                -3.0F, -4.0F, -4.0F, -5.0F, -5.0F, -4.0F, -4.0F, -3.0F,
                                                -3.0F, -4.0F, -4.0F, -5.0F, -5.0F, -4.0F, -4.0F, -3.0F,
                                                -3.0F, -4.0F, -4.0F, -5.0F, -5.0F, -4.0F, -4.0F, -3.0F,
                                                -2.0F, -3.0F, -3.0F, -4.0F, -4.0F, -3.0F, -3.0F, -2.0F,
                                                -1.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, -1.0F,
                                                 2.0F,  2.0F,  0.0F,  0.0F,  0.0F,  0.0F,  2.0F,  2.0F,
                                                 2.0F,  3.0F,  1.0F,  0.0F,  0.0F,  1.0F,  3.0F,  2.0F));

        white.put(PieceType.PAWN, Arrays.asList( 0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,
                                                 0.5F,  1.0F,  1.0F, -2.0F, -2.0F,  1.0F,  1.0F,  0.5F,
                                                 0.5F, -0.5F, -1.0F,  0.0F,  0.0F, -1.0F, -0.5F,  0.5F,
                                                 0.0F,  0.0F,  0.0F,  2.0F,  2.0F,  0.0F,  0.0F,  0.0F,
                                                 0.5F,  0.5F,  1.0F,  2.5F,  2.5F,  1.0F,  0.5F,  0.5F,
                                                 1.0F,  1.0F,  2.0F,  3.0F,  3.0F,  2.0F,  1.0F,  1.0F,
                                                 5.0F,  5.0F,  5.0F,  5.0F,  5.0F,  5.0F,  5.0F,  5.0F,
                                                 0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F));

        white.put(PieceType.KNIGHT, Arrays.asList(-5.0F, -4.0F, -3.0F, -3.0F, -3.0F, -3.0F, -4.0F, -5.0F,
                                                  -4.0F, -2.0F,  0.0F,  0.5F,  0.5F,  0.0F, -2.0F, -4.0F,
                                                  -3.0F,  0.5F,  1.0F,  1.5F,  1.5F,  1.0F,  0.5F, -3.0F,
                                                  -3.0F,  0.0F,  1.5F,  2.0F,  2.0F,  1.5F,  0.0F, -3.0F,
                                                  -3.0F,  0.5F,  1.5F,  2.0F,  2.0F,  1.5F,  0.5F, -3.0F,
                                                  -3.0F,  0.0F,  1.0F,  1.5F,  1.5F,  1.0F,  0.0F, -3.0F,
                                                  -4.0F, -2.0F,  0.0F,  0.0F,  0.0F,  0.0F, -2.0F, -4.0F,
                                                  -5.0F, -4.0F, -3.0F, -3.0F, -3.0F, -3.0F, -4.0F, -5.0F));

        white.put(PieceType.BISHOP, Arrays.asList(-2.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F, -2.0F,
                                                  -1.0F,  0.5F,  0.0F,  0.0F,  0.0F,  0.0F,  0.5F, -1.0F,
                                                  -1.0F,  1.0F,  1.0F,  1.0F,  1.0F,  1.0F,  1.0F, -1.0F,
                                                  -1.0F,  0.0F,  1.0F,  1.0F,  1.0F,  1.0F,  0.0F, -1.0F,
                                                  -1.0F,  0.5F,  0.5F,  1.0F,  1.0F,  0.5F,  0.5F, -1.0F,
                                                  -1.0F,  0.0F,  0.5F,  1.0F,  1.0F,  0.5F,  0.0F, -1.0F,
                                                  -1.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F, -1.0F,
                                                  -2.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F, -2.0F));

        white.put(PieceType.ROOK, Arrays.asList( 0.0F,  0.0F,  0.0F,  0.5F,  0.5F,  0.0F,  0.0F,  0.0F,
                                                -0.5F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F, -0.5F,
                                                -0.5F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F, -0.5F,
                                                -0.5F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F, -0.5F,
                                                -0.5F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F, -0.5F,
                                                -0.5F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F, -0.5F,
                                                 0.5F,  1.0F,  1.0F,  1.0F,  1.0F,  1.0F,  1.0F,  0.5F,
                                                 0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F));

        white.put(PieceType.QUEEN, Arrays.asList(-2.0F, -1.0F, -1.0F, -0.5F, -0.5F, -1.0F, -1.0F, -2.0F,
                                                 -1.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.5F,  0.0F, -1.0F,
                                                 -1.0F,  0.0F,  0.5F,  0.5F,  0.5F,  0.5F,  0.5F, -1.0F,
                                                 -0.5F,  0.0F,  0.5F,  0.5F,  0.5F,  0.5F,  0.0F,  0.0F,
                                                 -0.5F,  0.0F,  0.5F,  0.5F,  0.5F,  0.5F,  0.0F, -0.5F,
                                                 -1.0F,  0.0F,  0.5F,  0.5F,  0.5F,  0.5F,  0.0F, -1.0F,
                                                 -1.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F,  0.0F, -1.0F,
                                                 -2.0F, -1.0F, -1.0F, -0.5F, -0.5F, -1.0F, -1.0F, -2.0F));

        white.put(PieceType.KING, Arrays.asList( 2.0F,  3.0F,  1.0F,  0.0F,  0.0F,  1.0F,  3.0F,  2.0F,
                                                 2.0F,  2.0F,  0.0F,  0.0F,  0.0F,  0.0F,  2.0F,  2.0F,
                                                -1.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, -1.0F,
                                                -2.0F, -3.0F, -3.0F, -4.0F, -4.0F, -3.0F, -3.0F, -2.0F,
                                                -3.0F, -4.0F, -4.0F, -5.0F, -5.0F, -4.0F, -4.0F, -3.0F,
                                                -3.0F, -4.0F, -4.0F, -5.0F, -5.0F, -4.0F, -4.0F, -3.0F,
                                                -3.0F, -4.0F, -4.0F, -5.0F, -5.0F, -4.0F, -4.0F, -3.0F,
                                                -3.0F, -4.0F, -4.0F, -5.0F, -5.0F, -4.0F, -4.0F, -3.0F));
    }
    public static void init(String FEN) {
        board = new Board();
        board.loadFromFen(FEN);
        side = board.getSideToMove();
    }
}
