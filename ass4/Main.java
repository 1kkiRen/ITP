package ass4;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public final class Main {
    /**
     * @param args the command line arguments
     */
    private Board chessBoard;
    /**
     * variable for k = m * 4 + 2 expresion.
     */
    private static final int M_COEF = 4;
    /**
     * variable for "x" parsing.
    */
    private static final int TWO = 2;
    /**
     * variable for "y" parsing.
    */
    private static final int THREE = 3;
    /**
     * variable for minimum n number.
     */
    private static final int MIN_N = 3;
    /**
     * variable for maximum n number.
     */
    private static final int MAX_N = 1000;

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Main main = new Main();

        String inputFile = "C://Users//dmitr//Documents//Inno//ass4//input.txt";
        String outputFile = "C://Users//dmitr//Documents//Inno//ass4//output.txt";
        // String inputFile = "input.txt";
        // String outputFile = "output.txt";

        try (FileInputStream in = new FileInputStream(inputFile);
        FileOutputStream out = new FileOutputStream(outputFile);) {
            byte[] buffer = new byte[in.available()];
            in.read(buffer, 0, buffer.length);

            String[] input = new String(buffer).split("\r\n| ");

            int n;
            try {
                n = Integer.parseInt(input[0]);
            } catch (NumberFormatException e) {
                out.write(String.valueOf(new InvalidBoardSizeException().getMessage()).getBytes());
                return;
            }

            if (n < MIN_N || n > MAX_N) {
                out.write(String.valueOf(new InvalidBoardSizeException().getMessage()).getBytes());
                return;
            }

            main.chessBoard = new Board(n);

            int m;
            try {
                m = Integer.parseInt(input[1]);
            } catch (NumberFormatException e) {
                out.write(String.valueOf(new InvalidNumerOfPiecesException().getMessage()).getBytes());
                return;
            }

            if (m < 2 || m > n * n) {
                out.write(String.valueOf(new InvalidNumerOfPiecesException().getMessage()).getBytes());
                return;
            }

            int whiteKingCount = 0;
            int blackKingCount = 0;

            int k = m * M_COEF + 2;

            for (int i = 2; i < k; i += M_COEF) {
                int x;

                int y;
                String pieceType = input[i];

                PieceName pieceName = PieceName.parse(pieceType);

                PieceColor color = PieceColor.parse(input[i + 1]);

                if (pieceName == null) {
                    out.write(String.valueOf(new InvalidPieceNameException().getMessage()).getBytes());
                    return;
                }

                if (color == null) {
                    out.write(String.valueOf(new InvalidPieceColorException().getMessage()).getBytes());
                    return;
                }

                try {
                    x = Integer.parseInt(input[i + TWO]);
                    y = Integer.parseInt(input[i + THREE]);
                } catch (NumberFormatException e) {
                    out.write(String.valueOf(new InvalidPiecePositionException().getMessage()).getBytes());
                    return;
                }

                if (x < 1 || x > n || y < 1 || y > n) {
                    out.write(String.valueOf(new InvalidPiecePositionException().getMessage()).getBytes());
                    return;
                }

                if (main.chessBoard.getPiece(new PiecePosition(x, y)) != null) {
                    out.write(String.valueOf(new InvalidPiecePositionException().getMessage()).getBytes());
                    return;
                }

                switch (pieceName) {
                    case KING:
                        main.chessBoard.addPiece(new King(new PiecePosition(x, y), color));
                        if (color == PieceColor.WHITE) {
                            whiteKingCount++;
                        } else {
                            blackKingCount++;
                        }
                        break;
                    case QUEEN:
                        main.chessBoard.addPiece(new Queen(new PiecePosition(x, y), color));
                        break;
                    case ROOK:
                        main.chessBoard.addPiece(new Rook(new PiecePosition(x, y), color));
                        break;
                    case BISHOP:
                        main.chessBoard.addPiece(new Bishop(new PiecePosition(x, y), color));
                        break;
                    case KNIGHT:
                        main.chessBoard.addPiece(new Knight(new PiecePosition(x, y), color));
                        break;
                    case PAWN:
                        main.chessBoard.addPiece(new Pawn(new PiecePosition(x, y), color));
                        break;
                    default:
                        break;
                }

                if (whiteKingCount > 1 || blackKingCount > 1) {
                    out.write(String.valueOf(new InvalidGivenKingsException().getMessage()).getBytes());
                    return;
                }

            }

            try {
                input[k] = input[k].replace("\r\n", "");
                out.write(String.valueOf(new InvalidNumerOfPiecesException().getMessage()).getBytes());
                return;
            } catch (ArrayIndexOutOfBoundsException e) {
            }

            if (whiteKingCount == 0 || blackKingCount == 0) {
                out.write(String.valueOf(new InvalidGivenKingsException().getMessage()).getBytes());
                return;
            }

            for (int i = 2; i < k; i += M_COEF) {
                int x = Integer.parseInt(input[i + TWO]);
                int y = Integer.parseInt(input[i + THREE]);

                out.write(String.valueOf(
                        main.chessBoard.getPiecePossibleMovesCount(main.chessBoard.getPiece(new PiecePosition(x, y)))
                                + " " + main.chessBoard.getPiecePossibleCapturesCount(
                                        main.chessBoard.getPiece(new PiecePosition(x, y)))
                                + "\n")
                        .getBytes());

            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
            ex.addSuppressed(ex);
        }
    }

}

enum PieceColor {
    /**
     * Enumerated type for piece color.
     */
    WHITE, BLACK;

    public static PieceColor parse(String s) {
        if (s.equals("White")) {
            return WHITE;
        } else if (s.equals("Black")) {
            return BLACK;
        } else {
            return null;
        }
    }
}

enum PieceName {
    /**
     * Enumerated type for piece name.
     */
    KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN;

    public static PieceName parse(String s) {
        if (s.equals("King")) {
            return KING;
        } else if (s.equals("Queen")) {
            return QUEEN;
        } else if (s.equals("Rook")) {
            return ROOK;
        } else if (s.equals("Bishop")) {
            return BISHOP;
        } else if (s.equals("Knight")) {
            return KNIGHT;
        } else if (s.equals("Pawn")) {
            return PAWN;
        } else {
            return null;
        }
    }

}

class PiecePosition {
    /** x coordinate. */
    private int x;
    /** y coordinate. */
    private int y;

    PiecePosition(int onX, int onY) {
        this.x = onX - 1;
        this.y = onY - 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

abstract class ChessPiece {
    /** Piece position. */
    protected PiecePosition position;
    /** Piece color. */
    protected PieceColor color;

    ChessPiece(PiecePosition piecePosition, PieceColor pieceColor) {
        this.position = piecePosition;
        this.color = pieceColor;
    }

    public PiecePosition getPosition() {
        return position;
    }

    public PieceColor getColor() {
        return color;
    }

    public abstract int getMovesCount(Map<String, ChessPiece> positions, int boardSize);

    public abstract int getCapturesCount(Map<String, ChessPiece> positions, int boardSize);

}

class Knight extends ChessPiece {
    Knight(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        if (x + 2 < boardSize && y + 1 < boardSize) {
            if (positions.get("(" + (x + 2) + ", " + (y + 1) + ")") == null) {
                count++;
            }
        }

        if (x + 2 < boardSize && y - 1 >= 0) {
            if (positions.get("(" + (x + 2) + ", " + (y - 1) + ")") == null) {
                count++;
            }
        }

        if (x - 2 >= 0 && y + 1 < boardSize) {
            if (positions.get("(" + (x - 2) + ", " + (y + 1) + ")") == null) {
                count++;
            }
        }

        if (x - 2 >= 0 && y - 1 >= 0) {
            if (positions.get("(" + (x - 2) + ", " + (y - 1) + ")") == null) {
                count++;
            }
        }

        if (x + 1 < boardSize && y + 2 < boardSize) {
            if (positions.get("(" + (x + 1) + ", " + (y + 2) + ")") == null) {
                count++;
            }
        }

        if (x + 1 < boardSize && y - 2 >= 0) {
            if (positions.get("(" + (x + 1) + ", " + (y - 2) + ")") == null) {
                count++;
            }
        }

        if (x - 1 >= 0 && y + 2 < boardSize) {
            if (positions.get("(" + (x - 1) + ", " + (y + 2) + ")") == null) {
                count++;
            }
        }

        if (x - 1 >= 0 && y - 2 >= 0) {
            if (positions.get("(" + (x - 1) + ", " + (y - 2) + ")") == null) {
                count++;
            }
        }

        return count + getCapturesCount(positions, boardSize);
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        if (x + 2 < boardSize && y + 1 < boardSize) {
            if (positions.get("(" + (x + 2) + ", " + (y + 1) + ")") != null
                    && positions.get("(" + (x + 2) + ", " + (y + 1) + ")").getColor() != color) {
                count++;
            }
        }

        if (x + 2 < boardSize && y - 1 >= 0) {
            if (positions.get("(" + (x + 2) + ", " + (y - 1) + ")") != null
                    && positions.get("(" + (x + 2) + ", " + (y - 1) + ")").getColor() != color) {
                count++;
            }
        }

        if (x - 2 >= 0 && y + 1 < boardSize) {
            if (positions.get("(" + (x - 2) + ", " + (y + 1) + ")") != null
                    && positions.get("(" + (x - 2) + ", " + (y + 1) + ")").getColor() != color) {
                count++;
            }
        }

        if (x - 2 >= 0 && y - 1 >= 0) {
            if (positions.get("(" + (x - 2) + ", " + (y - 1) + ")") != null
                    && positions.get("(" + (x - 2) + ", " + (y - 1) + ")").getColor() != color) {
                count++;
            }
        }

        if (x + 1 < boardSize && y + 2 < boardSize) {
            if (positions.get("(" + (x + 1) + ", " + (y + 2) + ")") != null
                    && positions.get("(" + (x + 1) + ", " + (y + 2) + ")").getColor() != color) {
                count++;
            }
        }

        if (x + 1 < boardSize && y - 2 >= 0) {
            if (positions.get("(" + (x + 1) + ", " + (y - 2) + ")") != null
                    && positions.get("(" + (x + 1) + ", " + (y - 2) + ")").getColor() != color) {
                count++;
            }
        }

        if (x - 1 >= 0 && y + 2 < boardSize) {
            if (positions.get("(" + (x - 1) + ", " + (y + 2) + ")") != null
                    && positions.get("(" + (x - 1) + ", " + (y + 2) + ")").getColor() != color) {
                count++;
            }
        }

        if (x - 1 >= 0 && y - 2 >= 0) {
            if (positions.get("(" + (x - 1) + ", " + (y - 2) + ")") != null
                    && positions.get("(" + (x - 1) + ", " + (y - 2) + ")").getColor() != color) {
                count++;
            }
        }

        return count;
    }
}

class King extends ChessPiece {
    King(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (x + i >= 0 && x + i < boardSize && y + j >= 0 && y + j < boardSize) {
                    if (positions.get("(" + (x + i) + ", " + (y + j) + ")") == null) {
                        count++;
                    }
                }
            }
        }

        return count + getCapturesCount(positions, boardSize);
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        if (x + 1 < boardSize) {
            if (positions.get("(" + (x + 1) + ", " + y + ")") != null
                    && positions.get("(" + (x + 1) + ", " + y + ")").getColor() != super.color) {
                count++;
            }
        }

        if (x - 1 >= 0) {
            if (positions.get("(" + (x - 1) + ", " + y + ")") != null
                    && positions.get("(" + (x - 1) + ", " + y + ")").getColor() != super.color) {
                count++;
            }
        }

        if (y + 1 < boardSize) {
            if (positions.get("(" + x + ", " + (y + 1) + ")") != null
                    && positions.get("(" + x + ", " + (y + 1) + ")").getColor() != super.color) {
                count++;
            }
        }

        if (y - 1 >= 0) {
            if (positions.get("(" + x + ", " + (y - 1) + ")") != null
                    && positions.get("(" + x + ", " + (y - 1) + ")").getColor() != super.color) {
                count++;
            }
        }

        if (x + 1 < boardSize && y + 1 < boardSize) {
            if (positions.get("(" + (x + 1) + ", " + (y + 1) + ")") != null
                    && positions.get("(" + (x + 1) + ", " + (y + 1) + ")").getColor() != super.color) {
                count++;
            }
        }

        if (x + 1 < boardSize && y - 1 >= 0) {
            if (positions.get("(" + (x + 1) + ", " + (y - 1) + ")") != null
                    && positions.get("(" + (x + 1) + ", " + (y - 1) + ")").getColor() != super.color) {
                count++;
            }
        }

        if (x - 1 >= 0 && y + 1 < boardSize) {
            if (positions.get("(" + (x - 1) + ", " + (y + 1) + ")") != null
                    && positions.get("(" + (x - 1) + ", " + (y + 1) + ")").getColor() != super.color) {
                count++;
            }
        }

        if (x - 1 >= 0 && y - 1 >= 0) {
            if (positions.get("(" + (x - 1) + ", " + (y - 1) + ")") != null
                    && positions.get("(" + (x - 1) + ", " + (y - 1) + ")").getColor() != super.color) {
                count++;
            }
        }

        return count;
    }

}

class Pawn extends ChessPiece {
    Pawn(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        if (color == PieceColor.WHITE) {
            if (y + 1 < boardSize) {
                if (positions.get("(" + x + ", " + (y + 1) + ")") == null) {
                    count++;
                }
            }
        } else {
            if (y - 1 >= 0) {
                if (positions.get("(" + x + ", " + (y - 1) + ")") == null) {
                    count++;
                }
            }
        }

        return count + getCapturesCount(positions, boardSize);
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        if (color == PieceColor.WHITE) {
            if (x + 1 < boardSize && y + 1 < boardSize) {
                if (positions.get("(" + (x + 1) + ", " + (y + 1) + ")") != null
                        && positions.get("(" + (x + 1) + ", " + (y + 1) + ")").getColor() != color) {
                    count++;
                }
            }

            if (x - 1 < boardSize && y + 1 >= 0) {
                if (positions.get("(" + (x - 1) + ", " + (y + 1) + ")") != null
                        && positions.get("(" + (x - 1) + ", " + (y + 1) + ")").getColor() != color) {
                    count++;
                }
            }
        } else {
            if (x - 1 >= 0 && y - 1 < boardSize) {
                if (positions.get("(" + (x - 1) + ", " + (y - 1) + ")") != null
                        && positions.get("(" + (x - 1) + ", " + (y - 1) + ")").getColor() != color) {
                    count++;
                }
            }

            if (x - 1 >= 0 && y - 1 >= 0) {
                if (positions.get("(" + (x + 1) + ", " + (y - 1) + ")") != null
                        && positions.get("(" + (x + 1) + ", " + (y - 1) + ")").getColor() != color) {
                    count++;
                }
            }
        }

        return count;
    }
}

interface BishopMovement {
    int getDiagonalMovesCount(PiecePosition position, PieceColor color, Map<String, ChessPiece> positions,
            int boardSize);

    int getDiagonalCapturesCount(PiecePosition position, PieceColor color, Map<String, ChessPiece> positions,
            int boardSize);
}

interface RookMovement {
    int getOrthogonalMovesCount(PiecePosition position, PieceColor color, Map<String, ChessPiece> positions,
            int boardSize);

    int getOrthogonalCapturesCount(PiecePosition position, PieceColor color, Map<String, ChessPiece> positions,
            int boardSize);
}

class Queen extends ChessPiece implements BishopMovement, RookMovement {
    Queen(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getDiagonalMovesCount(position, color, positions, boardSize)
         + getOrthogonalMovesCount(position, color, positions, boardSize)
         + getCapturesCount(positions, boardSize);
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getDiagonalCapturesCount(position, color, positions, boardSize)
         + getOrthogonalCapturesCount(position, color, positions, boardSize);
    }

    public int getDiagonalMovesCount(PiecePosition position, PieceColor color,
     Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        int x = position.getX();
        int y = position.getY();


        for (int i = 1; i < boardSize; i++) {
            if (x + i < boardSize && y + i < boardSize) {
                if (positions.get("(" + (x + i) + ", " + (y + i) + ")") == null) {
                    count++;
                } else {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (x + i < boardSize && y - i >= 0) {
                if (positions.get("(" + (x + i) + ", " + (y - i) + ")") == null) {
                    count++;
                } else {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (x - i >= 0 && y + i < boardSize) {
                if (positions.get("(" + (x - i) + ", " + (y + i) + ")") == null) {
                    count++;
                } else {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (x - i >= 0 && y - i >= 0) {
                if (positions.get("(" + (x - i) + ", " + (y - i) + ")") == null) {
                    count++;
                } else {
                    break;
                }
            }
        }

        return count;
    }

    public int getDiagonalCapturesCount(PiecePosition position, PieceColor color,
    Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        for (int i = 1; i < boardSize; i++) {
            if (x + i < boardSize && y + i < boardSize) {
                if (positions.get("(" + (x + i) + ", " + (y + i) + ")") != null
                && positions.get("(" + (x + i) + ", " + (y + i) + ")").getColor() != color) {
                    count++;
                    break;
                } else if (positions.get("(" + (x + i) + ", " + (y + i) + ")") != null
                && positions.get("(" + (x + i) + ", " + (y + i) + ")").getColor() == color) {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (x + i < boardSize && y - i >= 0) {
                if (positions.get("(" + (x + i) + ", " + (y - i) + ")") != null
                && positions.get("(" + (x + i) + ", " + (y - i) + ")").getColor() != color) {
                    count++;
                    break;
                } else if (positions.get("(" + (x + i) + ", " + (y - i) + ")") != null
                && positions.get("(" + (x + i) + ", " + (y - i) + ")").getColor() == color) {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (x - i >= 0 && y + i < boardSize) {
                if (positions.get("(" + (x - i) + ", " + (y + i) + ")") != null
                && positions.get("(" + (x - i) + ", " + (y + i) + ")").getColor() != color) {
                    count++;
                    break;
                } else if (positions.get("(" + (x - i) + ", " + (y + i) + ")") != null
                && positions.get("(" + (x - i) + ", " + (y + i) + ")").getColor() == color) {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (x - i >= 0 && y - i >= 0) {
                if (positions.get("(" + (x - i) + ", " + (y - i) + ")") != null
                && positions.get("(" + (x - i) + ", " + (y - i) + ")").getColor() != color) {
                    count++;
                    break;
                } else if (positions.get("(" + (x - i) + ", " + (y - i) + ")") != null
                && positions.get("(" + (x - i) + ", " + (y - i) + ")").getColor() == color) {
                    break;
                }
            }
        }

        return count;
    }

    public int getOrthogonalMovesCount(PiecePosition position, PieceColor color,
    Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        for (int i = 1; i < boardSize; i++) {
            if (x + i < boardSize) {
                if (positions.get("(" + (x + i) + ", " + y + ")") == null) {
                    count++;
                } else {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (x - i >= 0) {
                if (positions.get("(" + (x - i) + ", " + y + ")") == null) {
                    count++;
                } else {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (y + i < boardSize) {
                if (positions.get("(" + x + ", " + (y + i) + ")") == null) {
                    count++;
                } else {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (y - i >= 0) {
                if (positions.get("(" + x + ", " + (y - i) + ")") == null) {
                    count++;
                } else {
                    break;
                }
            }
        }

        return count;
    }

    public int getOrthogonalCapturesCount(PiecePosition position, PieceColor color,
    Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        for (int i = 1; i < boardSize; i++) {
            if (x + i < boardSize) {
                if (positions.get("(" + (x + i) + ", " + y + ")") != null
                && positions.get("(" + (x + i) + ", " + y + ")").getColor() != color) {
                    count++;
                    break;
                } else if (positions.get("(" + (x + i) + ", " + y + ")") != null
                && positions.get("(" + (x + i) + ", " + y + ")").getColor() == color) {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (x - i >= 0) {
                if (positions.get("(" + (x - i) + ", " + y + ")") != null
                && positions.get("(" + (x - i) + ", " + y + ")").getColor() != color) {
                    count++;
                    break;
                } else if (positions.get("(" + (x - i) + ", " + y + ")") != null
                && positions.get("(" + (x - i) + ", " + y + ")").getColor() == color) {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (y + i < boardSize) {
                if (positions.get("(" + x + ", " + (y + i) + ")") != null
                && positions.get("(" + x + ", " + (y + i) + ")").getColor() != color) {
                    count++;
                    break;
                } else if (positions.get("(" + x + ", " + (y + i) + ")") != null
                && positions.get("(" + x + ", " + (y + i) + ")").getColor() == color) {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (y - i >= 0) {
                if (positions.get("(" + x + ", " + (y - i) + ")") != null
                && positions.get("(" + x + ", " + (y - i) + ")").getColor() != color) {
                    count++;
                    break;
                } else if (positions.get("(" + x + ", " + (y - i) + ")") != null
                && positions.get("(" + x + ", " + (y - i) + ")").getColor() == color) {
                    break;
                }
            }
        }

        return count;
    }
}

class Bishop extends ChessPiece implements BishopMovement {
    Bishop(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getDiagonalMovesCount(position, color, positions, boardSize)
        + getDiagonalCapturesCount(position, color, positions, boardSize);
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getDiagonalCapturesCount(position, color, positions, boardSize);
    }

    public int getDiagonalMovesCount(PiecePosition position, PieceColor color,
     Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        int x = position.getX();
        int y = position.getY();


        for (int i = 1; i < boardSize; i++) {
            if (x + i < boardSize && y + i < boardSize) {
                if (positions.get("(" + (x + i) + ", " + (y + i) + ")") == null) {
                    count++;
                } else {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (x + i < boardSize && y - i >= 0) {
                if (positions.get("(" + (x + i) + ", " + (y - i) + ")") == null) {
                    count++;
                } else {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (x - i >= 0 && y + i < boardSize) {
                if (positions.get("(" + (x - i) + ", " + (y + i) + ")") == null) {
                    count++;
                } else {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (x - i >= 0 && y - i >= 0) {
                if (positions.get("(" + (x - i) + ", " + (y - i) + ")") == null) {
                    count++;
                } else {
                    break;
                }
            }
        }

        return count;
    }

    public int getDiagonalCapturesCount(PiecePosition position, PieceColor color,
    Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        for (int i = 1; i < boardSize; i++) {
            if (x + i < boardSize && y + i < boardSize) {
                if (positions.get("(" + (x + i) + ", " + (y + i) + ")") != null
                && positions.get("(" + (x + i) + ", " + (y + i) + ")").getColor() != color) {
                    count++;
                    break;
                } else if (positions.get("(" + (x + i) + ", " + (y + i) + ")") != null
                && positions.get("(" + (x + i) + ", " + (y + i) + ")").getColor() == color) {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (x + i < boardSize && y - i >= 0) {
                if (positions.get("(" + (x + i) + ", " + (y - i) + ")") != null
                && positions.get("(" + (x + i) + ", " + (y - i) + ")").getColor() != color) {
                    count++;
                    break;
                } else if (positions.get("(" + (x + i) + ", " + (y - i) + ")") != null
                && positions.get("(" + (x + i) + ", " + (y - i) + ")").getColor() == color) {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (x - i >= 0 && y + i < boardSize) {
                if (positions.get("(" + (x - i) + ", " + (y + i) + ")") != null
                && positions.get("(" + (x - i) + ", " + (y + i) + ")").getColor() != color) {
                    count++;
                    break;
                } else if (positions.get("(" + (x - i) + ", " + (y + i) + ")") != null
                && positions.get("(" + (x - i) + ", " + (y + i) + ")").getColor() == color) {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (x - i >= 0 && y - i >= 0) {
                if (positions.get("(" + (x - i) + ", " + (y - i) + ")") != null
                && positions.get("(" + (x - i) + ", " + (y - i) + ")").getColor() != color) {
                    count++;
                    break;
                } else if (positions.get("(" + (x - i) + ", " + (y - i) + ")") != null
                && positions.get("(" + (x - i) + ", " + (y - i) + ")").getColor() == color) {
                    break;
                }
            }
        }

        return count;
    }
}

class Rook extends ChessPiece implements RookMovement {
    Rook(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getOrthogonalMovesCount(position, color, positions, boardSize)
        + getOrthogonalCapturesCount(position, color, positions, boardSize);
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getOrthogonalCapturesCount(position, color, positions, boardSize);
    }

    public int getOrthogonalMovesCount(PiecePosition position, PieceColor color,
    Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        for (int i = 1; i < boardSize; i++) {
            if (x + i < boardSize) {
                if (positions.get("(" + (x + i) + ", " + y + ")") == null) {
                    count++;
                } else {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (x - i >= 0) {
                if (positions.get("(" + (x - i) + ", " + y + ")") == null) {
                    count++;
                } else {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (y + i < boardSize) {
                if (positions.get("(" + x + ", " + (y + i) + ")") == null) {
                    count++;
                } else {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (y - i >= 0) {
                if (positions.get("(" + x + ", " + (y - i) + ")") == null) {
                    count++;
                } else {
                    break;
                }
            }
        }

        return count;
    }

    public int getOrthogonalCapturesCount(PiecePosition position, PieceColor color,
    Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        for (int i = 1; i < boardSize; i++) {
            if (x + i < boardSize) {
                if (positions.get("(" + (x + i) + ", " + y + ")") != null
                && positions.get("(" + (x + i) + ", " + y + ")").getColor() != color) {
                    count++;
                    break;
                } else if (positions.get("(" + (x + i) + ", " + y + ")") != null
                && positions.get("(" + (x + i) + ", " + y + ")").getColor() == color) {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (x - i >= 0) {
                if (positions.get("(" + (x - i) + ", " + y + ")") != null
                && positions.get("(" + (x - i) + ", " + y + ")").getColor() != color) {
                    count++;
                    break;
                } else if (positions.get("(" + (x - i) + ", " + y + ")") != null
                && positions.get("(" + (x - i) + ", " + y + ")").getColor() == color) {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (y + i < boardSize) {
                if (positions.get("(" + x + ", " + (y + i) + ")") != null
                && positions.get("(" + x + ", " + (y + i) + ")").getColor() != color) {
                    count++;
                    break;
                } else if (positions.get("(" + x + ", " + (y + i) + ")") != null
                && positions.get("(" + x + ", " + (y + i) + ")").getColor() == color) {
                    break;
                }
            }
        }

        for (int i = 1; i < boardSize; i++) {
            if (y - i >= 0) {
                if (positions.get("(" + x + ", " + (y - i) + ")") != null
                && positions.get("(" + x + ", " + (y - i) + ")").getColor() != color) {
                    count++;
                    break;
                } else if (positions.get("(" + x + ", " + (y - i) + ")") != null
                && positions.get("(" + x + ", " + (y - i) + ")").getColor() == color) {
                    break;
                }
            }
        }

        return count;
    }
}


class Board {
    /** Map of all pieces positions. */
    private Map<String, ChessPiece> piecePosition;
    /** Board size. */
    private int size;

    Board(int boardSize) {
        this.size = boardSize;
        piecePosition = new HashMap<>();
    }

    public String returnPiecePositions() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ChessPiece> entry : piecePosition.entrySet()) {
            sb.append(entry.getKey() + " " + entry.getValue().getColor()
            + " " + entry.getValue().getClass().getSimpleName() + "\n");
        }
        return sb.toString();
    }

    public void addPiece(ChessPiece piece) {
        piecePosition.put(piece.getPosition().toString(), piece);
    }

    public ChessPiece getPiece(PiecePosition position) {
        return this.piecePosition.get(piecePosition.toString());
    }

    public int getPiecePossibleMovesCount(ChessPiece piece) {
        return piece.getMovesCount(piecePosition, size);
    }

    public int getPiecePossibleCapturesCount(ChessPiece piece) {
        return piece.getCapturesCount(piecePosition, size);
    }
}

class Exception {
    public String getMessage() {
        return "Exception";
    }
}

class InvalidBoardSizeException extends Exception {
    public String getMessage() {
        return "Invalid board size";
    }
}

class InvalidNumerOfPiecesException extends Exception {
    public String getMessage() {
        return "Invalid number of pieces";
    }
}

class InvalidPieceNameException extends Exception {
    public String getMessage() {
        return "Invalid piece name";
    }
}

class InvalidPieceColorException extends Exception {
    public String getMessage() {
        return "Invalid piece color";
    }
}

class InvalidPiecePositionException extends Exception {
    public String getMessage() {
        return "Invalid piece position";
    }
}

class InvalidGivenKingsException extends Exception {
    public String getMessage() {
        return "Invalid given Kings";
    }
}

class InvalidInputException extends Exception {
    public String getMessage() {
        return "Invalid input";
    }
}
