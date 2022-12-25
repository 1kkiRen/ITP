import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitrii Kuzmin
 */
public final class Main {
    /**
     * @param args the command line arguments
     */
    private static Board chessBoard;
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
     * Private constructor.
     */
    private Main() { }

    /**
     * This is the main method which makes central logic of the program.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String inputFile = "input.txt";
        String outputFile = "output.txt";

        try (FileInputStream in = new FileInputStream(inputFile);
        FileOutputStream out = new FileOutputStream(outputFile);) {
            // input file buffer
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

            chessBoard = new Board(n);

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

                if (chessBoard.getPiece(new PiecePosition(x, y)) != null) {
                    out.write(String.valueOf(new InvalidPiecePositionException().getMessage()).getBytes());
                    return;
                }

                switch (pieceName) {
                    case KING:
                        chessBoard.addPiece(new King(new PiecePosition(x, y), color));
                        if (color == PieceColor.WHITE) {
                            whiteKingCount++;
                        } else {
                            blackKingCount++;
                        }
                        break;
                    case QUEEN:
                        chessBoard.addPiece(new Queen(new PiecePosition(x, y), color));
                        break;
                    case ROOK:
                        chessBoard.addPiece(new Rook(new PiecePosition(x, y), color));
                        break;
                    case BISHOP:
                        chessBoard.addPiece(new Bishop(new PiecePosition(x, y), color));
                        break;
                    case KNIGHT:
                        chessBoard.addPiece(new Knight(new PiecePosition(x, y), color));
                        break;
                    case PAWN:
                        chessBoard.addPiece(new Pawn(new PiecePosition(x, y), color));
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
            } catch (ArrayIndexOutOfBoundsException e) { }

            if (whiteKingCount == 0 || blackKingCount == 0) {
                out.write(String.valueOf(new InvalidGivenKingsException().getMessage()).getBytes());
                return;
            }

            for (int i = 2; i < k; i += M_COEF) {
                /* As we have need to print all pieces' moves in order pieces were given,
                we can use the same loop and take only piece's position from buffer. */
                int x = Integer.parseInt(input[i + TWO]);
                int y = Integer.parseInt(input[i + THREE]);

                out.write(String.valueOf(
                        chessBoard.getPiecePossibleMovesCount(chessBoard.getPiece(new PiecePosition(x, y)))
                                + " " + chessBoard.getPiecePossibleCapturesCount(
                                        chessBoard.getPiece(new PiecePosition(x, y)))
                                + "\n")
                        .getBytes());

            }
        } catch (FileNotFoundException ex) {
            System.out.println("Invalid input");
            ex.addSuppressed(ex);
        }
    }

}

/** Piece color enumerator. */
enum PieceColor {
    /**
     * Enumerated options for piece color.
     */
    WHITE, BLACK;

    /**
     * This method parses string to PieceColor.
     * @param s
     * @return PieceColor
     */
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

/** Piece name enumerator. */
enum PieceName {
    /**
     * Enumerated options for piece name.
     */
    KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN;

    /**
     * This method parses string to PieceName.
     * @param s
     * @return PieceName
     */
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

    /**
     * Constructor.
     * @param onX
     * @param onY
     */
    PiecePosition(int onX, int onY) {
        this.x = onX - 1;
        this.y = onY - 1;
    }

    /**
     * Getter for x coordinate.
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for y coordinate.
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * This method connects x and y coordinate into String.
     * @return String
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

abstract class ChessPiece {
    /** Piece position. */
    protected PiecePosition position;
    /** Piece color. */
    protected PieceColor color;

    /**
     * Constructor.
     * @param piecePosition
     * @param pieceColor
     */
    ChessPiece(PiecePosition piecePosition, PieceColor pieceColor) {
        this.position = piecePosition;
        this.color = pieceColor;
    }

    /**
     * Getter for piece position.
     * @return position
     */
    public PiecePosition getPosition() {
        return position;
    }

    /**
     * Getter for piece color.
     * @return color
     */
    public PieceColor getColor() {
        return color;
    }

    /**
     * This method returns all possible moves for piece.
     * @param positions
     * @param boardSize
     * @return number of possible moves
     */
    public abstract int getMovesCount(Map<String, ChessPiece> positions, int boardSize);

    /**
     * This method returns all possible captures for piece.
     * @param positions
     * @param boardSize
     * @return number of possible captures
     */
    public abstract int getCapturesCount(Map<String, ChessPiece> positions, int boardSize);

}

class Knight extends ChessPiece {
    /**
     * This constructor creates King object.
     * @param position
     * @param color
     */
    Knight(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    /**
     * This method returns all possible moves for Knight.
     * 2 forward and 1 left or right.
     * 2 backward and 1 to the left or right.
     * 2 left and 1 forward or backward.
     * 2 right and 1 forward or backward.
     * @param positions
     * @param boardSize
     * @return number of possible moves
     */
    @Override
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

    /**
     * This method returns all possible captures for Knight.
     * @param positions
     * @param boardSize
     * @return number of possible captures
     */
    @Override
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
    /**
     * This constructor creates King object.
     * @param position
     * @param color
     */
    King(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    /**
     * This method returns all possible moves for King.
     * 1 left, right, forward or backward.
     * 1 for each diagonal.
     * @param positions
     * @param boardSize
     * @return number of possible moves
     */
    @Override
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

    /**
     * This method returns all possible captures for King.
     * @param positions
     * @param boardSize
     * @return number of possible captures
     */
    @Override
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
    /**
     * This constructor creates Pawn object.
     * @param position
     * @param color
     */
    Pawn(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    /**
     * This method returns all possible moves for Pawn.
     * 1 forward.
     * 1 for each diagonal if there is an enemy piece.
     * @param positions
     * @param boardSize
     * @return number of possible moves
     */
    @Override
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

    /**
     * This method returns all possible captures for Pawn.
     * @param positions
     * @param boardSize
     * @return number of possible captures
     */
    @Override
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

/**
 * This class declares position functions to implement.
 */
interface BishopMovement {
    /**
     * This method returns all possible diagoanl moves.
     * @param position
     * @param color
     * @param positions
     * @param boardSize
     * @return number of possible moves
     */
    int getDiagonalMovesCount(PiecePosition position, PieceColor color, Map<String, ChessPiece> positions,
            int boardSize);

    /**
     * This method returns all possible diagonal captures.
     * @param position
     * @param color
     * @param positions
     * @param boardSize
     * @return number of possible captures
     */
    int getDiagonalCapturesCount(PiecePosition position, PieceColor color, Map<String, ChessPiece> positions,
            int boardSize);
}

/**
 * This class declares position functions to implement.
 */
interface RookMovement {
    /**
     * This method returns all possible horizontal moves.
     * @param position
     * @param color
     * @param positions
     * @param boardSize
     * @return number of possible moves
     */
    int getOrthogonalMovesCount(PiecePosition position, PieceColor color, Map<String, ChessPiece> positions,
            int boardSize);

    /**
     * This method returns all possible horizontal captures.
     * @param position
     * @param color
     * @param positions
     * @param boardSize
     * @return number of possible moves
     */
    int getOrthogonalCapturesCount(PiecePosition position, PieceColor color, Map<String, ChessPiece> positions,
            int boardSize);
}

class Queen extends ChessPiece implements BishopMovement, RookMovement {
    /**
     * This constructor creates Queen object.
     * @param position
     * @param color
     */
    Queen(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    /**
     * This method returns all possible moves for Queen.
     * all possible horizontal and diagonal moves.
     * @param positions
     * @param boardSize
     * @return number of possible moves
     */
    @Override
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getDiagonalMovesCount(position, color, positions, boardSize)
         + getOrthogonalMovesCount(position, color, positions, boardSize)
         + getCapturesCount(positions, boardSize);
    }

    /**
     * This method returns all possible captures for Queen.
     * @param positions
     * @param boardSize
     * @return number of possible captures
     */
    @Override
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getDiagonalCapturesCount(position, color, positions, boardSize)
         + getOrthogonalCapturesCount(position, color, positions, boardSize);
    }

    /**
     * Implementation of BishopMovement interface.
     * @param position
     * @param color
     * @param positions
     * @param boardSize
     * @return number of possible moves
     */
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

    /**
     * Implementation of BishopMovement interface.
     * @param position
     * @param color
     * @param positions
     * @param boardSize
     * @return number of possible captures
     */
    @Override
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

    /**
     * Implementation of RookMovement interface.
     * @param position
     * @param color
     * @param positions
     * @param boardSize
     * @return number of possible moves
     */
    @Override
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

    /**
     * Implementation of RookMovement interface.
     * @param position
     * @param color
     * @param positions
     * @param boardSize
     * @return number of possible captures
     */
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
    /**
     * This constructor creates Bishop object.
     * @param position
     * @param color
     */
    Bishop(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    /**
     * This method returns all possible moves for Bishop.
     * all possible diagonal moves.
     * @param positions
     * @param boardSize
     * @return number of possible moves
     */
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getDiagonalMovesCount(position, color, positions, boardSize)
        + getDiagonalCapturesCount(position, color, positions, boardSize);
    }

    /**
     * This method returns all possible captures for Bishop.
     * all possible diagonal captures.
     * @param positions
     * @param boardSize
     * @return number of possible captures
     */
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getDiagonalCapturesCount(position, color, positions, boardSize);
    }

    /**
     * Implementation of BishopMovement interface.
     * @param position
     * @param color
     * @param positions
     * @param boardSize
     * @return number of possible moves
     */
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

    /**
     * Implementation of BishopMovement interface.
     * @param position
     * @param color
     * @param positions
     * @param boardSize
     * @return number of possible captures
     */
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
    /**
     * This constructor creates Rook object.
     * @param position
     * @param color
     */
    Rook(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    /**
     * This method returns all possible moves for Rook.
     * all possible horizontal and vertical moves.
     * @param positions
     * @param boardSize
     * @return number of possible moves
     */
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getOrthogonalMovesCount(position, color, positions, boardSize)
        + getOrthogonalCapturesCount(position, color, positions, boardSize);
    }

    /**
     * This method returns all possible captures for Rook.
     * all possible horizontal and vertical captures.
     * @param positions
     * @param boardSize
     * @return number of possible captures
     */
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getOrthogonalCapturesCount(position, color, positions, boardSize);
    }

    /**
     * Implementation of RookMovement interface.
     * @param position
     * @param color
     * @param positions
     * @param boardSize
     * @return number of possible moves
     */
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

    /**
     * Implementation of RookMovement interface.
     * @param position
     * @param color
     * @param positions
     * @param boardSize
     * @return number of possible captures
     */
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
    private Map<String, ChessPiece> positionToPieces;
    /** Board size. */
    private int size;

    /**
     * This constructor creates Board object.
     * @param boardSize
     */
    Board(int boardSize) {
        this.size = boardSize;
        positionToPieces = new HashMap<>();
    }


    /**
     * This method adds piece to the board.
     * @param piece
     */
    public void addPiece(ChessPiece piece) {
        positionToPieces.put(piece.getPosition().toString(), piece);
    }

    /**
     * This method returns piece by its position.
     * @param position
     * @return piece
     */
    public ChessPiece getPiece(PiecePosition position) {
        return this.positionToPieces.get(position.toString());
    }

    /**
     * This method returns all movements of certain piece.
     * @param piece
     * @return possible moves
     */
    public int getPiecePossibleMovesCount(ChessPiece piece) {
        return piece.getMovesCount(positionToPieces, size);
    }

    /**
     * This method returns all captures of certain piece.
     * @param piece
     * @return possible moves
     */
    public int getPiecePossibleCapturesCount(ChessPiece piece) {
        return piece.getCapturesCount(positionToPieces, size);
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
