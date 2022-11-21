package ass4;

import java.util.*;

enum PieceColor{
    WHITE, BLACK;

    public PieceColor parse(String s){
        if(s.equals("white")){
            return WHITE;
        }
        else if(s.equals("black")){
            return BLACK;
        }
        else{
            return null;
        }
    }
}


class Position {
    private int x;
    private int y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
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
    protected Position position;
    protected PieceColor color;

    public ChessPiece(Position position, PieceColor color){
        this.position = position;
        this.color = color;
    }

    public Position getPosition(){
        return position;
    }

    public PieceColor getColor(){
        return color;
    }

    public abstract int getMovesCount(Map<String, ChessPiece> positions, int boardSize);
    public abstract int getCapturesCount(Map<String, ChessPiece> positions, int boardSize);

}

class Knight extends ChessPiece {
    Knight(Position position, PieceColor color){
        super(position, color);
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize){
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        if(x + 2 < boardSize && y + 1 < boardSize){
            if(positions.get((x + 2) + " " + (y + 1)) == null){
                count++;
            }
        }

        if(x + 2 < boardSize && y - 1 >= 0){
            if(positions.get((x + 2) + " " + (y - 1)) == null){
                count++;
            }
        }

        if(x - 2 >= 0 && y + 1 < boardSize){
            if(positions.get((x - 2) + " " + (y + 1)) == null){
                count++;
            }
        }

        if(x - 2 >= 0 && y - 1 >= 0){
            if(positions.get((x - 2) + " " + (y - 1)) == null){
                count++;
            }
        }

        if(x + 1 < boardSize && y + 2 < boardSize){
            if(positions.get((x + 1) + " " + (y + 2)) == null){
                count++;
            }
        }

        if(x + 1 < boardSize && y - 2 >= 0){
            if(positions.get((x + 1) + " " + (y - 2)) == null){
                count++;
            }
        }

        if(x - 1 >= 0 && y + 2 < boardSize){
            if(positions.get((x - 1) + " " + (y + 2)) == null){
                count++;
            }
        }

        if(x - 1 >= 0 && y - 2 >= 0){
            if(positions.get((x - 1) + " " + (y - 2)) == null){
                count++;
            }
        }

        return count;
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize){
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        if(x + 2 < boardSize && y + 1 < boardSize){
            if(positions.get((x + 2) + " " + (y + 1)) != null && positions.get((x + 2) + " " + (y + 1)).getColor() != color){
                count++;
            }
        }

        if(x + 2 < boardSize && y - 1 >= 0){
            if(positions.get((x + 2) + " " + (y - 1)) != null && positions.get((x + 2) + " " + (y - 1)).getColor() != color){
                count++;
            }
        }

        if(x - 2 >= 0 && y + 1 < boardSize){
            if(positions.get((x - 2) + " " + (y + 1)) != null && positions.get((x - 2) + " " + (y + 1)).getColor() != color){
                count++;
            }
        }

        if(x - 2 >= 0 && y - 1 >= 0){
            if(positions.get((x - 2) + " " + (y - 1)) != null && positions.get((x - 2) + " " + (y - 1)).getColor() != color){
                count++;
            }
        }

        if(x + 1 < boardSize && y + 2 < boardSize){
            if(positions.get((x + 1) + " " + (y + 2)) != null && positions.get((x + 1) + " " + (y + 2)).getColor() != color){
                count++;
            }
        }

        if(x + 1 < boardSize && y - 2 >= 0){
            if(positions.get((x + 1) + " " + (y - 2)) != null && positions.get((x + 1) + " " + (y - 2)).getColor() != color){
                count++;
            }
        }

        if(x - 1 >= 0 && y + 2 < boardSize){
            if(positions.get((x - 1) + " " + (y + 2)) != null && positions.get((x - 1) + " " + (y + 2)).getColor() != color){
                count++;
            }
        }

        if(x - 1 >= 0 && y - 2 >= 0){
            if(positions.get((x - 1) + " " + (y - 2)) != null && positions.get((x - 1) + " " + (y - 2)).getColor() != color){
                count++;
            }
        }

        return count;
    }
}

class King extends ChessPiece {
    King(Position position, PieceColor color){
        super(position, color);
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize){
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        if(x + 1 < boardSize){
            if(positions.get((x + 1) + " " + y) == null){
                count++;
            }
        }

        if(x - 1 >= 0){
            if(positions.get((x - 1) + " " + y) == null){
                count++;
            }
        }

        if(y + 1 < boardSize){
            if(positions.get(x + " " + (y + 1)) == null){
                count++;
            }
        }

        if(y - 1 >= 0){
            if(positions.get(x + " " + (y - 1)) == null){
                count++;
            }
        }

        if(x + 1 < boardSize && y + 1 < boardSize){
            if(positions.get((x + 1) + " " + (y + 1)) == null){
                count++;
            }
        }

        if(x + 1 < boardSize && y - 1 >= 0){
            if(positions.get((x + 1) + " " + (y - 1)) == null){
                count++;
            }
        }

        if(x - 1 >= 0 && y + 1 < boardSize){
            if(positions.get((x - 1) + " " + (y + 1)) == null){
                count++;
            }
        }

        if(x - 1 >= 0 && y - 1 >= 0){
            if(positions.get((x - 1) + " " + (y - 1)) == null){
                count++;
            }
        }

        return count;
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize){
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        if(x + 1 < boardSize){
            if(positions.get((x + 1) + " " + y) != null && positions.get((x + 1) + " " + y).getColor() != color){
                count++;
            }
        }

        if(x - 1 >= 0){
            if(positions.get((x - 1) + " " + y) != null && positions.get((x - 1) + " " + y).getColor() != color){
                count++;
            }
        }

        if(y + 1 < boardSize){
            if(positions.get(x + " " + (y + 1)) != null && positions.get(x + " " + (y + 1)).getColor() != color){
                count++;
            }
        }

        if(y - 1 >= 0){
            if(positions.get(x + " " + (y - 1)) != null && positions.get(x + " " + (y - 1)).getColor() != color){
                count++;
            }
        }

        if(x + 1 < boardSize && y + 1 < boardSize){
            if(positions.get((x + 1) + " " + (y + 1)) != null && positions.get((x + 1) + " " + (y + 1)).getColor() != color){
                count++;
            }
        }

        if(x + 1 < boardSize && y - 1 >= 0){
            if(positions.get((x + 1) + " " + (y - 1)) != null && positions.get((x + 1) + " " + (y - 1)).getColor() != color){
                count++;
            }
        }

        if(x - 1 >= 0 && y + 1 < boardSize){
            if(positions.get((x - 1) + " " + (y + 1)) != null && positions.get((x - 1) + " " + (y + 1)).getColor() != color){
                count++;
            }
        }

        if(x - 1 >= 0 && y - 1 >= 0){
            if(positions.get((x - 1) + " " + (y - 1)) != null && positions.get((x - 1) + " " + (y - 1)).getColor() != color){
                count++;
            }
        }

        return count;
    }

}

class Pawn extends ChessPiece {
    Pawn(Position position, PieceColor color){
        super(position, color);
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize){
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        if(color == PieceColor.WHITE){
            if(x + 1 < boardSize){
                if(positions.get((x + 1) + " " + y) == null){
                    count++;
                }
            }
        } else {
            if(x - 1 >= 0){
                if(positions.get((x - 1) + " " + y) == null){
                    count++;
                }
            }
        }

        return count;
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize){
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        if(color == PieceColor.WHITE){
            if(x + 1 < boardSize && y + 1 < boardSize){
                if(positions.get((x + 1) + " " + (y + 1)) != null && positions.get((x + 1) + " " + (y + 1)).getColor() != color){
                    count++;
                }
            }

            if(x + 1 < boardSize && y - 1 >= 0){
                if(positions.get((x + 1) + " " + (y - 1)) != null && positions.get((x + 1) + " " + (y - 1)).getColor() != color){
                    count++;
                }
            }
        } else {
            if(x - 1 >= 0 && y + 1 < boardSize){
                if(positions.get((x - 1) + " " + (y + 1)) != null && positions.get((x - 1) + " " + (y + 1)).getColor() != color){
                    count++;
                }
            }

            if(x - 1 >= 0 && y - 1 >= 0){
                if(positions.get((x - 1) + " " + (y - 1)) != null && positions.get((x - 1) + " " + (y - 1)).getColor() != color){
                    count++;
                }
            }
        }

        return count;
    }
}

interface BishopMovement {
    public int getDiagonalMovesCount(Position position, PieceColor color, Map<String, ChessPiece> positions, int boardSize);
    public int getDiagonalCapturesCount(Position position, PieceColor color, Map<String, ChessPiece> positions, int boardSize);
}

interface RookMovement {
    public int getOrthogonalMovesCount(Position position, PieceColor color, Map<String, ChessPiece> positions, int boardSize);
    public int getOrthogonalCapturesCount(Position position, PieceColor color, Map<String, ChessPiece> positions, int boardSize);
}

class Queen extends ChessPiece implements BishopMovement, RookMovement {
    Queen(Position position, PieceColor color){
        super(position, color);
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize){
        return getDiagonalMovesCount(position, color, positions, boardSize) + getOrthogonalMovesCount(position, color, positions, boardSize);
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize){
        return getDiagonalCapturesCount(position, color, positions, boardSize) + getOrthogonalCapturesCount(position, color, positions, boardSize);
    }

    public int getDiagonalMovesCount(Position position, PieceColor color, Map<String, ChessPiece> positions, int boardSize){
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        for(int i = 1; i < boardSize; i++){
            if(x + i < boardSize && y + i < boardSize){
                if(positions.get((x + i) + " " + (y + i)) == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x + i < boardSize && y - i >= 0){
                if(positions.get((x + i) + " " + (y - i)) == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x - i >= 0 && y + i < boardSize){
                if(positions.get((x - i) + " " + (y + i)) == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x - i >= 0 && y - i >= 0){
                if(positions.get((x - i) + " " + (y - i)) == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        return count;
    }

    public int getDiagonalCapturesCount(Position position, PieceColor color, Map<String, ChessPiece> positions, int boardSize){
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        for(int i = 1; i < boardSize; i++){
            if(x + i < boardSize && y + i < boardSize){
                if(positions.get((x + i) + " " + (y + i)) != null && positions.get((x + i) + " " + (y + i)).getColor() != color){
                    count++;
                    break;
                } else if(positions.get((x + i) + " " + (y + i)) != null && positions.get((x + i) + " " + (y + i)).getColor() == color){
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x + i < boardSize && y - i >= 0){
                if(positions.get((x + i) + " " + (y - i)) != null && positions.get((x + i) + " " + (y - i)).getColor() != color){
                    count++;
                    break;
                } else if(positions.get((x + i) + " " + (y - i)) != null && positions.get((x + i) + " " + (y - i)).getColor() == color){
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x - i >= 0 && y + i < boardSize){
                if(positions.get((x - i) + " " + (y + i)) != null && positions.get((x - i) + " " + (y + i)).getColor() != color){
                    count++;
                    break;
                } else if(positions.get((x - i) + " " + (y + i)) != null && positions.get((x - i) + " " + (y + i)).getColor() == color){
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x - i >= 0 && y - i >= 0){
                if(positions.get((x - i) + " " + (y - i)) != null && positions.get((x - i) + " " + (y - i)).getColor() != color){
                    count++;
                    break;
                } else if(positions.get((x - i) + " " + (y - i)) != null && positions.get((x - i) + " " + (y - i)).getColor() == color){
                    break;
                }
            }
        }

        return count;
    }
}

class Bishop extends ChessPiece implements BishopMovement {
    Bishop(Position position, PieceColor color){
        super(position, color);
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize){
        return getDiagonalMovesCount(position, color, positions, boardSize);
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize){
        return getDiagonalCapturesCount(position, color, positions, boardSize);
    }

    public int getDiagonalMovesCount(Position position, PieceColor color, Map<String, ChessPiece> positions, int boardSize){
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        for(int i = 1; i < boardSize; i++){
            if(x + i < boardSize && y + i < boardSize){
                if(positions.get((x + i) + " " + (y + i)) == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x + i < boardSize && y - i >= 0){
                if(positions.get((x + i) + " " + (y - i)) == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x - i >= 0 && y + i < boardSize){
                if(positions.get((x - i) + " " + (y + i)) == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x - i >= 0 && y - i >= 0){
                if(positions.get((x - i) + " " + (y - i)) == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        return count;
    }

    public int getDiagonalCapturesCount(Position position, PieceColor color, Map<String, ChessPiece> positions, int boardSize){
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        for(int i = 1; i < boardSize; i++){
            if(x + i < boardSize && y + i < boardSize){
                if(positions.get((x + i) + " " + (y + i)) != null && positions.get((x + i) + " " + (y + i)).getColor() != color){
                    count++;
                    break;
                } else if(positions.get((x + i) + " " + (y + i)) != null && positions.get((x + i) + " " + (y + i)).getColor() == color){
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x + i < boardSize && y - i >= 0){
                if(positions.get((x + i) + " " + (y - i)) != null && positions.get((x + i) + " " + (y - i)).getColor() != color){
                    count++;
                    break;
                } else if(positions.get((x + i) + " " + (y - i)) != null && positions.get((x + i) + " " + (y - i)).getColor() == color){
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x - i >= 0 && y + i < boardSize){
                if(positions.get((x - i) + " " + (y + i)) != null && positions.get((x - i) + " " + (y + i)).getColor() != color){
                    count++;
                    break;
                } else if(positions.get((x - i) + " " + (y + i)) != null && positions.get((x - i) + " " + (y + i)).getColor() == color){
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x - i >= 0 && y - i >= 0){
                if(positions.get((x - i) + " " + (y - i)) != null && positions.get((x - i) + " " + (y - i)).getColor() != color){
                    count++;
                    break;
                } else if(positions.get((x - i) + " " + (y - i)) != null && positions.get((x - i) + " " + (y - i)).getColor() == color){
                    break;
                }
            }
        }

        return count;
    }
}

class Rook extends ChessPiece implements RookMovement {
    Rook(Position position, PieceColor color){
        super(position, color);
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize){
        return getLinearMovesCount(position, color, positions, boardSize);
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize){
        return getLinearCapturesCount(position, color, positions, boardSize);
    }

    public int getLinearMovesCount(Position position, PieceColor color, Map<String, ChessPiece> positions, int boardSize){
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        for(int i = 1; i < boardSize; i++){
            if(x + i < boardSize){
                if(positions.get((x + i) + " " + y) == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x - i >= 0){
                if(positions.get((x - i) + " " + y) == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(y + i < boardSize){
                if(positions.get(x + " " + (y + i)) == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(y - i >= 0){
                if(positions.get(x + " " + (y - i)) == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        return count;
    }

    public int getLinearCapturesCount(Position position, PieceColor color, Map<String, ChessPiece> positions, int boardSize){
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        for(int i = 1; i < boardSize; i++){
            if(x + i < boardSize){
                if(positions.get((x + i) + " " + y) != null && positions.get((x + i) + " " + y).getColor() != color){
                    count++;
                    break;
                } else if(positions.get((x + i) + " " + y) != null && positions.get((x + i) + " " + y).getColor() == color){
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x - i >= 0){
                if(positions.get((x - i) + " " + y) != null && positions.get((x - i) + " " + y).getColor() != color){
                    count++;
                    break;
                } else if(positions.get((x - i) + " " + y) != null && positions.get((x - i) + " " + y).getColor() == color){
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(y + i < boardSize){
                if(positions.get(x + " " + (y + i)) != null && positions.get(x + " " + (y + i)).getColor() != color){
                    count++;
                    break;
                } else if(positions.get(x + " " + (y + i)) != null && positions.get(x + " " + (y + i)).getColor() == color){
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(y - i >= 0){
                if(positions.get(x + " " + (y - i)) != null && positions.get(x + " " + (y - i)).getColor() != color){
                    count++;
                    break;
                } else if(positions.get(x + " " + (y - i)) != null && positions.get(x + " " + (y - i)).getColor() == color){
                    break;
                }
            }
        }

        return count;

    }
}


class Board {
    private Map<String, ChessPiece> piecePosition;
    private int size;

    public Board(int boardSize) {
        this.size = boardSize;
        piecePosition = new HashMap<>();
    }

    public void addPiece(ChessPiece piece) {
        piecePosition.put(piece.getPosition().toString(), piece);
    }

    public Map<String, ChessPiece> getPiece() {
        return piecePosition;
    }

    public int getPiecePossibleMovesCount(ChessPiece piece) {
        return piece.getMovesCount(piecePosition, size);
    }

    public int getPiecePossibleCapturesCount(ChessPiece piece) {
        return piece.getCapturesCount(piecePosition, size);
    }
}

public class Main {
    private Board chessBoard;
    public static void main(String[] args) {
        
    }

}
