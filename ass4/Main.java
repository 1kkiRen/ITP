package ass4;


import java.util.*;
import java.io.*;
public final class Main {
    private Board chessBoard;
    public static void main(String[] args) throws Exception, IOException {
        Main main = new Main();




        // String inputFile = "input.txt";
        // String outputFile = "output.txt";

        String inputFile = "C://Users//dmitr//Documents//Inno//ass4//input.txt";
        String outputFile = "C://Users//dmitr//Documents//Inno//ass4//output.txt";

        try(FileInputStream in = new FileInputStream(inputFile);
        FileOutputStream out = new FileOutputStream(outputFile))
        {
            byte[] buffer = new byte[in.available()];
            in.read(buffer, 0, buffer.length);


            String [] input = new String(buffer).split("\r\n| ");

            int n;
            try{
                n = Integer.parseInt(input[0]);
            } catch (NumberFormatException e){
                e.addSuppressed(e);
                throw new InvalidBoardSizeException();
            }

            if(n < 3 || n > 1000){
                throw new InvalidBoardSizeException();
            }

            main.chessBoard = new Board(n);

            int m;
            try{
                m = Integer.parseInt(input[1]);
            } catch (NumberFormatException e){
                throw new InvalidNumerOfPiecesException();
            }

            if (m < 2 || m > n * n){
                throw new InvalidNumerOfPiecesException();
            }

            int whiteKingCount = 0, blackKingCount = 0;

            for(int i = 2; i < m * 4 + 2; i += 4){
                int x, y;

                String pieceType = input[i];

                String colorIn = input[i + 1];
                PieceColor color = PieceColor.parse(colorIn);

                // if (color == null){
                //     throw new InvalidPieceColorException();
                // }

                try{
                    x = Integer.parseInt(input[i + 2]);
                    y = Integer.parseInt(input[i + 3]);
                } catch (NumberFormatException e){
                    throw new InvalidPiecePositionException();
                }

                

                if(x < 1 || x > n || y < 1 || y > n){
                    throw new InvalidPiecePositionException();
                }

                switch(pieceType){
                    case "King":
                        main.chessBoard.addPiece(new King(new Position(x, y), color));
                        if(color == PieceColor.WHITE){
                            whiteKingCount++;
                        } else {
                            blackKingCount++;
                        }
                        break;
                    case "Queen":
                        main.chessBoard.addPiece(new Queen(new Position(x, y), color));
                        break;
                    case "Rook":
                        main.chessBoard.addPiece(new Rook(new Position(x, y), color));
                        break;
                    case "Bishop":
                        main.chessBoard.addPiece(new Bishop(new Position(x, y), color));
                        break;
                    case "Knight":
                        main.chessBoard.addPiece(new Knight(new Position(x, y), color));
                        break;
                    case "Pawn":
                        main.chessBoard.addPiece(new Pawn(new Position(x, y), color));
                        break;
                    default:
                        out.write(String.valueOf(new InvalidPieceNameException().getMessage()).getBytes());
                        return;

                }

                if (whiteKingCount > 1 || blackKingCount > 1){
                    throw new InvalidGivenKingsException();
                }
            }

            // System.out.println(main.chessBoard.returnPiecePositions());
            

            int k = m * 4 + 2;

            for(int i = 2; i < k; i += 4){
                int x = Integer.parseInt(input[i + 2]);
                int y = Integer.parseInt(input[i + 3]);

                out.write(String.valueOf(main.chessBoard.getPiecePossibleMovesCount(main.chessBoard.getPiece(new Position(x, y))) + " " + main.chessBoard.getPiecePossibleCapturesCount(main.chessBoard.getPiece(new Position(x, y))) + "\n").getBytes());

                // System.out.println(main.chessBoard.getPiecePossibleMovesCount(main.chessBoard.getPiece(new Position(x, y))) + " " + main.chessBoard.getPiecePossibleCapturesCount(main.chessBoard.getPiece(new Position(x, y))));
                // System.out.println(main.chessBoard.getPiecePossibleCapturesCount(main.chessBoard.getPiece(new Position(x, y))));
            }
            
        }
        catch(FileNotFoundException ex){
            System.out.println("File not found");
            ex.addSuppressed(ex);
        }
        
        

    }

}
enum PieceColor{
    WHITE, BLACK;

    public static PieceColor parse(String s){
        if(s.equals("White")){
            return WHITE;
        }
        else if(s.equals("Black")){
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
        this.x = x - 1;
        this.y = y - 1;
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
            if(positions.get("(" +(x + 2) + ", " + (y + 1) + ")") == null){
                count++;
            }
        }

        if(x + 2 < boardSize && y - 1 >= 0){
            if(positions.get("(" +(x + 2) + ", " + (y - 1) + ")") == null){
                count++;
            }
        }

        if(x - 2 >= 0 && y + 1 < boardSize){
            if(positions.get("(" +(x - 2) + ", " + (y + 1) + ")") == null){
                count++;
            }
        }

        if(x - 2 >= 0 && y - 1 >= 0){
            if(positions.get("(" +(x - 2) + ", " + (y - 1) + ")") == null){
                count++;
            }
        }

        if(x + 1 < boardSize && y + 2 < boardSize){
            if(positions.get("(" +(x + 1) + ", " + (y + 2) + ")") == null){
                count++;
            }
        }

        if(x + 1 < boardSize && y - 2 >= 0){
            if(positions.get("(" +(x + 1) + ", " + (y - 2) + ")") == null){
                count++;
            }
        }

        if(x - 1 >= 0 && y + 2 < boardSize){
            if(positions.get("(" +(x - 1) + ", " + (y + 2) + ")") == null){
                count++;
            }
        }

        if(x - 1 >= 0 && y - 2 >= 0){
            if(positions.get("(" +(x - 1) + ", " + (y - 2) + ")") == null){
                count++;
            }
        }

        return count + getCapturesCount(positions, boardSize);
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize){
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        if(x + 2 < boardSize && y + 1 < boardSize){
            if(positions.get("(" +(x + 2) + ", " + (y + 1) + ")") != null && positions.get("(" +(x + 2) + ", " + (y + 1) + ")").getColor() != color){
                count++;
            }
        }

        if(x + 2 < boardSize && y - 1 >= 0){
            if(positions.get("(" +(x + 2) + ", " + (y - 1) + ")") != null && positions.get("(" +(x + 2) + ", " + (y - 1) + ")").getColor() != color){
                count++;
            }
        }

        if(x - 2 >= 0 && y + 1 < boardSize){
            if(positions.get("(" +(x - 2) + ", " + (y + 1) + ")") != null && positions.get("(" +(x - 2) + ", " + (y + 1) + ")").getColor() != color){
                count++;
            }
        }

        if(x - 2 >= 0 && y - 1 >= 0){
            if(positions.get("(" +(x - 2) + ", " + (y - 1) + ")") != null && positions.get("(" +(x - 2) + ", " + (y - 1) + ")").getColor() != color){
                count++;
            }
        }

        if(x + 1 < boardSize && y + 2 < boardSize){
            if(positions.get("(" +(x + 1) + ", " + (y + 2) + ")") != null && positions.get("(" +(x + 1) + ", " + (y + 2) + ")").getColor() != color){
                count++;
            }
        }

        if(x + 1 < boardSize && y - 2 >= 0){
            if(positions.get("(" +(x + 1) + ", " + (y - 2) + ")") != null && positions.get("(" +(x + 1) + ", " + (y - 2) + ")").getColor() != color){
                count++;
            }
        }

        if(x - 1 >= 0 && y + 2 < boardSize){
            if(positions.get("(" +(x - 1) + ", " + (y + 2) + ")") != null && positions.get("(" +(x - 1) + ", " + (y + 2) + ")").getColor() != color){
                count++;
            }
        }

        if(x - 1 >= 0 && y - 2 >= 0){
            if(positions.get("(" +(x - 1) + ", " + (y - 2) + ")") != null && positions.get("(" +(x - 1) + ", " + (y - 2) + ")").getColor() != color){
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
            if(positions.get("(" +(x + 1) + ", " + y + ")") == null){
                count++;
            }
        }

        if(x - 1 >= 0){
            if(positions.get("(" +(x - 1) + ", " + y + ")") == null){
                count++;
            }
        }

        if(y + 1 < boardSize){
            if(positions.get("(" +x + ", " + (y + 1) + ")") == null){
                count++;
            }
        }

        if(y - 1 >= 0){
            if(positions.get("(" +x + ", " + (y - 1) + ")") == null){
                count++;
            }
        }

        if(x + 1 < boardSize && y + 1 < boardSize){
            if(positions.get("(" +(x + 1) + ", " + (y + 1) + ")") == null){
                count++;
            }
        }

        if(x + 1 < boardSize && y - 1 >= 0){
            if(positions.get("(" +(x + 1) + ", " + (y - 1) + ")") == null){
                count++;
            }
        }

        if(x - 1 >= 0 && y + 1 < boardSize){
            if(positions.get("(" +(x - 1) + ", " + (y + 1) + ")") == null){
                count++;
            }
        }

        if(x - 1 >= 0 && y - 1 >= 0){
            if(positions.get("(" +(x - 1) + ", " + (y - 1) + ")") == null){
                count++;
            }
        }

        return count + getCapturesCount(positions, boardSize);
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize){
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        if(x + 1 < boardSize){
            if(positions.get("(" +(x + 1) + ", " + y + ")") != null && positions.get("(" +(x + 1) + ", " + y + ")").getColor() != super.color){
                count++;
            }
        }

        if(x - 1 >= 0){
            if(positions.get("(" +(x - 1) + ", " + y + ")") != null && positions.get("(" +(x - 1) + ", " + y + ")").getColor() != super.color){
                count++;
            }
        }

        if(y + 1 < boardSize){
            if(positions.get("(" +x + ", " + (y + 1) + ")") != null && positions.get("(" +x + ", " + (y + 1) + ")").getColor() != super.color){
                count++;
            }
        }

        if(y - 1 >= 0){
            if(positions.get("(" +x + ", " + (y - 1) + ")") != null && positions.get("(" +x + ", " + (y - 1) + ")").getColor() != super.color){
                count++;
            }
        }

        if(x + 1 < boardSize && y + 1 < boardSize){
            if(positions.get("(" +(x + 1) + ", " + (y + 1) + ")") != null && positions.get("(" +(x + 1) + ", " + (y + 1) + ")").getColor() != super.color){
                count++;
            }
        }

        if(x + 1 < boardSize && y - 1 >= 0){
            if(positions.get("(" +(x + 1) + ", " + (y - 1) + ")") != null && positions.get("(" +(x + 1) + ", " + (y - 1) + ")").getColor() != super.color){
                count++;
            }
        }

        if(x - 1 >= 0 && y + 1 < boardSize){
            if(positions.get("(" +(x - 1) + ", " + (y + 1) + ")") != null && positions.get("(" +(x - 1) + ", " + (y + 1) + ")").getColor() != super.color){
                count++;
            }
        }

        if(x - 1 >= 0 && y - 1 >= 0){
            if(positions.get("(" +(x - 1) + ", " + (y - 1) + ")") != null && positions.get("(" +(x - 1) + ", " + (y - 1) + ")").getColor() != super.color){
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
            if(y + 1 < boardSize){
                if(positions.get("(" +x + ", " + (y + 1) + ")") == null){
                    count++;
                }
            }
        } else {
            if(y - 1 >= 0){
                if(positions.get("(" +x + ", " + (y - 1) + ")") == null){
                    count++;
                }
            }
        }

        return count + getCapturesCount(positions, boardSize);
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize){
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        if(color == PieceColor.WHITE){
            if(x + 1 < boardSize && y + 1 < boardSize){
                if(positions.get("(" +(x + 1) + ", " + (y + 1) + ")") != null && positions.get("(" +(x + 1) + ", " + (y + 1) + ")").getColor() != color){
                    count++;
                }
            }

            if(x - 1 < boardSize && y + 1 >= 0){
                if(positions.get("(" +(x - 1) + ", " + (y + 1) + ")") != null && positions.get("(" +(x - 1) + ", " + (y + 1) + ")").getColor() != color){
                    count++;
                }
            }
        } else {
            if(x - 1 >= 0 && y - 1 < boardSize){
                if(positions.get("(" +(x - 1) + ", " + (y - 1) + ")") != null && positions.get("(" +(x - 1) + ", " + (y - 1) + ")").getColor() != color){
                    count++;
                }
            }

            if(x - 1 >= 0 && y - 1 >= 0){
                if(positions.get("(" +(x + 1) + ", " + (y - 1) + ")") != null && positions.get("(" +(x + 1) + ", " + (y - 1) + ")").getColor() != color){
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
        return getDiagonalMovesCount(position, color, positions, boardSize) + getOrthogonalMovesCount(position, color, positions, boardSize) + getCapturesCount(positions, boardSize);
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
                if(positions.get("(" + (x + i) + ", " + (y + i) + ")" + ")") == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x + i < boardSize && y - i >= 0){
                if(positions.get("(" + (x + i) + ", " + (y - i) + ")") == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x - i >= 0 && y + i < boardSize){
                if(positions.get("(" + (x - i) + ", " + (y + i) + ")") == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x - i >= 0 && y - i >= 0){
                if(positions.get("(" + (x - i) + ", " + (y - i) + ")") == null){
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
                if(positions.get("(" + (x + i) + ", " + (y + i) + ")") != null && positions.get("(" + (x + i) + ", " + (y + i) + ")").getColor() != color){
                    count++;
                    break;
                } else if(positions.get("(" + (x + i) + ", " + (y + i) + ")") != null && positions.get("(" + (x + i) + ", " + (y + i) + ")").getColor() == color){
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x + i < boardSize && y - i >= 0){
                if(positions.get("(" + (x + i) + ", " + (y - i) + ")") != null && positions.get("(" +(x + i) + ", " + (y - i) + ")").getColor() != color){
                    count++;
                    break;
                } else if(positions.get("(" + (x + i) + ", " + (y - i) + ")") != null && positions.get("(" +(x + i) + ", " + (y - i) + ")").getColor() == color){
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x - i >= 0 && y + i < boardSize){
                if(positions.get("(" + (x - i) + ", " + (y + i) + ")") != null && positions.get("(" +(x - i) + ", " + (y + i) + ")").getColor() != color){
                    count++;
                    break;
                } else if(positions.get("(" + (x - i) + ", " + (y + i) + ")") != null && positions.get("(" +(x - i) + ", " + (y + i) + ")").getColor() == color){
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x - i >= 0 && y - i >= 0){
                if(positions.get("(" + (x - i) + ", " + (y - i) + ")") != null && positions.get("(" +(x - i) + ", " + (y - i) + ")").getColor() != color){
                    count++;
                    break;
                } else if(positions.get("(" + (x - i) + ", " + (y - i) + ")") != null && positions.get("(" +(x - i) + ", " + (y - i) + ")").getColor() == color){
                    break;
                }
            }
        }

        return count;
    }

    public int getOrthogonalMovesCount(Position position, PieceColor color, Map<String, ChessPiece> positions, int boardSize){
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        for(int i = 1; i < boardSize; i++){
            if(x + i < boardSize){
                if(positions.get("(" + (x + i) + ", " + y + ")") == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x - i >= 0){
                if(positions.get("(" + (x - i) + ", " + y + ")") == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(y + i < boardSize){
                if(positions.get("(" + x + ", " + (y + i) + ")") == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(y - i >= 0){
                if(positions.get("(" + x + ", " + (y - i) + ")") == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        return count;
    }

    public int getOrthogonalCapturesCount(Position position, PieceColor color, Map<String, ChessPiece> positions, int boardSize){
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        for(int i = 1; i < boardSize; i++){
            if(x + i < boardSize){
                if(positions.get("(" + (x + i) + ", " + y + ")") != null && positions.get("(" +(x + i) + ", " + y + ")").getColor() != color){
                    count++;
                    break;
                } else if(positions.get("(" + (x + i) + ", " + y + ")") != null && positions.get("(" +(x + i) + ", " + y + ")").getColor() == color){
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x - i >= 0){
                if(positions.get("(" + (x - i) + ", " + y + ")") != null && positions.get("(" +(x - i) + ", " + y + ")").getColor() != color){
                    count++;
                    break;
                } else if(positions.get("(" + (x - i) + ", " + y + ")") != null && positions.get("(" +(x - i) + ", " + y + ")").getColor() == color){
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(y + i < boardSize){
                if(positions.get("(" + x + ", " + (y + i) + ")") != null && positions.get("(" +x + ", " + (y + i) + ")").getColor() != color){
                    count++;
                    break;
                } else if(positions.get("(" + x + ", " + (y + i) + ")") != null && positions.get("(" +x + ", " + (y + i) + ")").getColor() == color){
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(y - i >= 0){
                if(positions.get("(" + x + ", " + (y - i) + ")") != null && positions.get("(" +x + ", " + (y - i) + ")").getColor() != color){
                    count++;
                    break;
                } else if(positions.get("(" + x + ", " + (y - i) + ")") != null && positions.get("(" +x + ", " + (y - i) + ")").getColor() == color){
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
        return getDiagonalMovesCount(position, color, positions, boardSize) + getDiagonalCapturesCount(position, color, positions, boardSize);
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
                if(positions.get("(" + (x + i) + ", " + (y + i) + ")") == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x + i < boardSize && y - i >= 0){
                if(positions.get("(" + (x + i) + ", " + (y - i) + ")") == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x - i >= 0 && y + i < boardSize){
                if(positions.get("(" + (x - i) + ", " + (y + i) + ")") == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x - i >= 0 && y - i >= 0){
                if(positions.get("(" + (x - i) + ", " + (y - i) + ")") == null){
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
                if(positions.get("(" + (x + i) + ", " + (y + i) + ")") != null && positions.get("(" +(x + i) + ", " + (y + i) + ")").getColor() != color){
                    count++;
                    break;
                } else if(positions.get("(" + (x + i) + ", " + (y + i) + ")") != null && positions.get("(" +(x + i) + ", " + (y + i) + ")").getColor() == color){
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x + i < boardSize && y - i >= 0){
                if(positions.get("(" + (x + i) + ", " + (y - i) + ")") != null && positions.get("(" +(x + i) + ", " + (y - i) + ")").getColor() != color){
                    count++;
                    break;
                } else if(positions.get("(" + (x + i) + ", " + (y - i) + ")") != null && positions.get("(" +(x + i) + ", " + (y - i) + ")").getColor() == color){
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x - i >= 0 && y + i < boardSize){
                if(positions.get("(" + (x - i) + ", " + (y + i) + ")") != null && positions.get("(" +(x - i) + ", " + (y + i) + ")").getColor() != color){
                    count++;
                    break;
                } else if(positions.get("(" + (x - i) + ", " + (y + i) + ")") != null && positions.get("(" +(x - i) + ", " + (y + i) + ")").getColor() == color){
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x - i >= 0 && y - i >= 0){
                if(positions.get("(" + (x - i) + ", " + (y - i) + ")") != null && positions.get("(" +(x - i) + ", " + (y - i) + ")").getColor() != color){
                    count++;
                    break;
                } else if(positions.get("(" + (x - i) + ", " + (y - i) + ")") != null && positions.get("(" +(x - i) + ", " + (y - i) + ")").getColor() == color){
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
        return getOrthogonalMovesCount(position, color, positions, boardSize) + getOrthogonalCapturesCount(position, color, positions, boardSize);
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize){
        return getOrthogonalCapturesCount(position, color, positions, boardSize);
    }

    public int getOrthogonalMovesCount(Position position, PieceColor color, Map<String, ChessPiece> positions, int boardSize){
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        for(int i = 1; i < boardSize; i++){
            if(x + i < boardSize){
                if(positions.get("(" + (x + i) + ", " + y + ")") == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x - i >= 0){
                if(positions.get("(" + (x - i) + ", " + y + ")") == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(y + i < boardSize){
                if(positions.get("(" + x + ", " + (y + i) + ")") == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(y - i >= 0){
                if(positions.get("(" + x + ", " + (y - i) + ")") == null){
                    count++;
                } else {
                    break;
                }
            }
        }

        return count;
    }


    public int getOrthogonalCapturesCount(Position position, PieceColor color, Map<String, ChessPiece> positions, int boardSize){
        int count = 0;
        int x = position.getX();
        int y = position.getY();

        for(int i = 1; i < boardSize; i++){
            if(x + i < boardSize){
                if(positions.get("(" + (x + i) + ", " + y + ")") != null && positions.get("(" + (x + i) + ", " + y + ")").getColor() != color){
                    count++;
                    break;
                } else if(positions.get("(" + (x + i) + ", " + y + ")") != null && positions.get("(" + (x + i) + ", " + y + ")").getColor() == color){
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(x - i >= 0){
                if(positions.get("(" + (x - i) + ", " + y + ")") != null && positions.get("(" + (x - i) + ", " + y + ")").getColor() != color){
                    count++;
                    break;
                } else if(positions.get("(" + (x - i) + ", " + y + ")") != null && positions.get("(" + (x - i) + ", " + y + ")").getColor() == color){
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(y + i < boardSize){
                if(positions.get("(" +"(" + x + ", " + (y + i) + ")") != null && positions.get("(" +x + ", " + (y + i) + ")").getColor() != color){
                    count++;
                    break;
                } else if(positions.get("(" +"(" + x + ", " + (y + i) + ")") != null && positions.get("(" +x + ", " + (y + i) + ")").getColor() == color){
                    break;
                }
            }
        }

        for(int i = 1; i < boardSize; i++){
            if(y - i >= 0){
                if(positions.get("(" +x + ", " + (y - i) + ")") != null && positions.get("(" +x + ", " + (y - i) + ")").getColor() != color){
                    count++;
                    break;
                } else if(positions.get("(" +x + ", " + (y - i) + ")") != null && positions.get("(" +x + ", " + (y - i) + ")").getColor() == color){
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

    public String returnPiecePositions() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ChessPiece> entry : piecePosition.entrySet()) {
            sb.append(entry.getKey() + " " + entry.getValue().getColor() + " " + entry.getValue().getClass().getSimpleName() + "\n");
        }
        return sb.toString();
    }

    public void addPiece(ChessPiece piece) {
        piecePosition.put(piece.getPosition().toString(), piece);
    }

    public ChessPiece getPiece(Position piecePosition) {
        return this.piecePosition.get(piecePosition.toString());
    }
    
    
    public int getPiecePossibleMovesCount(ChessPiece piece) {
        return piece.getMovesCount(piecePosition, size);
    }

    public int getPiecePossibleCapturesCount(ChessPiece piece) {
        return piece.getCapturesCount(piecePosition, size);
    }
}





class Exception extends Throwable {
    public String getMessage() {
        return "Exception";
    }
}

class InvalidBoardSizeException extends Exception {
    public String getMessage() {
        String outputFile = "C://Users//dmitr//Documents//Inno//ass4//output.txt";
        // String outputFile = "output.txt";
        try(FileOutputStream out = new FileOutputStream(outputFile))
        {
            out.write("Invalid board size".getBytes());
            return " ";
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
            ex.addSuppressed(ex);
        }

        return "H p s";
    }
}

class InvalidNumerOfPiecesException extends Exception {
    public String getMessage() {
        String outputFile = "C://Users//dmitr//Documents//Inno//ass4//output.txt";
        // String outputFile = "output.txt";
        try(FileOutputStream out = new FileOutputStream(outputFile))
        {
            out.write("Invalid number of pieces".getBytes());
            return " ";
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
            ex.addSuppressed(ex);
        }

        return "H p s";
   
    }
}

class InvalidPieceNameException extends Exception {
    public String getMessage() {
        // String outputFile = "C://Users//dmitr//Documents//Inno//ass4//output.txt";
        // // String outputFile = "output.txt";
        // try(FileOutputStream out = new FileOutputStream(outputFile))
        // {
        //     out.write("Invalid piece name".getBytes());
        //     return " ";
        // }
        // catch(IOException ex){
        //     System.out.println(ex.getMessage());
        //     ex.addSuppressed(ex);
        // }

        return "Invalid piece name";
    }
}

class InvalidPieceColorException extends Exception {
    public String getMessage() {
        String outputFile = "C://Users//dmitr//Documents//Inno//ass4//output.txt";
        // String outputFile = "output.txt";
        try(FileOutputStream out = new FileOutputStream(outputFile))
        {
            out.write("Invalid piece color".getBytes());
            return " ";
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
            ex.addSuppressed(ex);
        }

        return "H p s";
    }
}

class InvalidPiecePositionException extends Exception {
    public String getMessage() {
        String outputFile = "C://Users//dmitr//Documents//Inno//ass4//output.txt";
        // String outputFile = "output.txt";
        try(FileOutputStream out = new FileOutputStream(outputFile))
        {
            out.write("Invalid piece position".getBytes());
            return " ";
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
            ex.addSuppressed(ex);
        }

        return "H p s";
    }
}

class InvalidGivenKingsException extends Exception {
    public String getMessage() {
        String outputFile = "C://Users//dmitr//Documents//Inno//ass4//output.txt";
        // String outputFile = "output.txt";
        try(FileOutputStream out = new FileOutputStream(outputFile))
        {
            out.write("Invalid given Kings".getBytes());
            return " ";
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
            ex.addSuppressed(ex);
        }

        return "H p s";
    }
}

class InvalidInputException extends Exception {
    public String getMessage() {
        String outputFile = "C://Users//dmitr//Documents//Inno//ass4//output.txt";
        // String outputFile = "output.txt";
        try(FileOutputStream out = new FileOutputStream(outputFile))
        {
            out.write("Invalid input".getBytes());
            return " ";
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
            ex.addSuppressed(ex);
        }

        return "H p s";
    }
}
