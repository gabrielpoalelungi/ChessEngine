import java.awt.*;
import java.util.Vector;

public class Board {

    private static Board single_instance = null;
    Piece[][] gameboard;

    public Vector <Piece> whitePieces;
    public Vector <Piece> blackPieces;

    public Vector<Rook> blackRooks;
    public Vector<Rook> whiteRooks;
    public King blackKing, whiteKing;
    //currentColor retine cu ce culoare joaca bot-ul;
    public Color currentColor;

    //Constructor ce creeaza tabla de joc interna a engine-ului;
    //Se populeaza tabla cu obiecte de tip Piece;
    //Fiecare piesa este un obiect anonim cu un anumit rol (Rook, Bishop etc) si este plasat pe tabla
    //conform regulilor;
    public Board(){
        Piece[][] newboard = {
                {new Rook(Color.black, new CellPosition(0, 0)), new Knight(Color.black, new CellPosition(0, 1)), 
                 new Bishop(Color.black, new CellPosition(0, 2)), new Queen(Color.black, new CellPosition(0, 3)),
                 new King(Color.black, new CellPosition(0, 4)), new Bishop(Color.black, new CellPosition(0, 5)), 
                 new Knight(Color.black, new CellPosition(0, 6)), new Rook(Color.black, new CellPosition(0, 7))},

                {new Pawn(Color.black, new CellPosition(1,0)), new Pawn(Color.black, new CellPosition(1,1)),
                 new Pawn(Color.black, new CellPosition(1,2)), new Pawn(Color.black, new CellPosition(1,3)),
                 new Pawn(Color.black, new CellPosition(1,4)), new Pawn(Color.black, new CellPosition(1,5)),
                 new Pawn(Color.black, new CellPosition(1,6)), new Pawn(Color.black, new CellPosition(1,7))},

                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},

                {new Pawn(Color.white, new CellPosition(6,0)), new Pawn(Color.white, new CellPosition(6,1)),
                 new Pawn(Color.white, new CellPosition(6,2)), new Pawn(Color.white, new CellPosition(6,3)),
                 new Pawn(Color.white, new CellPosition(6,4)), new Pawn(Color.white, new CellPosition(6,5)),
                 new Pawn(Color.white, new CellPosition(6,6)), new Pawn(Color.white, new CellPosition(6,7))},

                {new Rook(Color.white, new CellPosition(7, 0)), new Knight(Color.white, new CellPosition(7, 1)), 
                 new Bishop(Color.white, new CellPosition(7, 2)), new Queen(Color.white, new CellPosition(7, 3)),
                 new King(Color.white, new CellPosition(7, 4)), new Bishop(Color.white, new CellPosition(7, 5)), 
                 new Knight(Color.white, new CellPosition(7, 6)), new Rook(Color.white, new CellPosition(7, 7))},
        };
        gameboard = new Piece[8][8];
        gameboard = newboard;

        whitePieces = new Vector<>();
        blackPieces = new Vector<>();
        populatePieces();

        blackRooks = new Vector<>();
        whiteRooks = new Vector<>();
        populateRooks();

        blackKing = (King) gameboard[0][4];
        whiteKing = (King) gameboard[7][4];

        currentColor = Color.black;
    }

    public Piece[][] cloneBoard() {
        Piece[][] clonedGameboard = new Piece[8][8];
        for(int i = 0; i <=7; i++) {
            for (int j = 0; j<=7; j++) {
                if (gameboard[i][j] == null) {
                    clonedGameboard[i][j] = null;
                } else {
                    if (gameboard[i][j] instanceof Pawn) {
                        clonedGameboard[i][j] = new Pawn(gameboard[i][j].color, new CellPosition(i, j));
                        ((Pawn) clonedGameboard[i][j]).firstMove = ((Pawn) gameboard[i][j]).firstMove;
                        ((Pawn) clonedGameboard[i][j]).canBeTakenEnPassant = ((Pawn) gameboard[i][j]).canBeTakenEnPassant;
                    } else {
                        if (gameboard[i][j] instanceof Knight) {
                            clonedGameboard[i][j] = new Knight(gameboard[i][j].color, new CellPosition(i, j));
                        } else {
                            if (gameboard[i][j] instanceof Bishop) {
                                clonedGameboard[i][j] = new Bishop(gameboard[i][j].color, new CellPosition(i, j));
                            } else {
                                if (gameboard[i][j] instanceof Queen) {
                                    clonedGameboard[i][j] = new Queen(gameboard[i][j].color, new CellPosition(i, j));
                                } else {
                                    if (gameboard[i][j] instanceof Rook) {
                                        clonedGameboard[i][j] = new Rook(gameboard[i][j].color, new CellPosition(i, j));
                                        ((Rook) clonedGameboard[i][j]).hasBeenMoved = ((Rook) gameboard[i][j]).hasBeenMoved;
                                    } else {
                                        if (gameboard[i][j] instanceof King) {
                                            clonedGameboard[i][j] = new King(gameboard[i][j].color, new CellPosition(i, j));
                                            ((King) clonedGameboard[i][j]).hasBeenMoved = ((King) gameboard[i][j]).hasBeenMoved;
                                            ((King) clonedGameboard[i][j]).hasBeenInChess = ((King) gameboard[i][j]).hasBeenInChess;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return clonedGameboard;
    }

    public void populatePieces(){
        for(int i = 0; i < 8; i++){
            blackPieces.add(gameboard[1][i]);
            blackPieces.add(gameboard[0][i]);
            whitePieces.add(gameboard[6][i]);
            whitePieces.add(gameboard[7][i]);
        }
    }

    public void populateRooks() {
        blackRooks.add((Rook) gameboard[0][0]);
        blackRooks.add((Rook) gameboard[0][7]);
        whiteRooks.add((Rook) gameboard[7][0]);
        whiteRooks.add((Rook) gameboard[7][7]);
    }

    //Singleton pattern here;
    public static Board getInstance() {
        if (single_instance == null)
            single_instance = new Board();
        return single_instance;
    }

    //Metoda ce reinitializeaza tabla de joc (folosita atunci cand engine-ul primeste comanda "new");
    public static void Reinitialize() {
        single_instance = null;
        single_instance = new Board();
    }

    public String toString() {
        String result = "";

        result += "     a b c d e f g h\n\n";
        for(int i = 0; i <= 7; i++) {
            result += "[" + (8-i) + "]" + "  ";
            for (int j = 0; j <= 7; j++)
                if (gameboard[i][j] == null)
                    result += "- ";
                else
                    result += gameboard[i][j].toString() + " ";
            result += "\n";
        }
        return result;
    }

    //Returneaza un obiect de tip CellPosition ce contine coordonatele x si y de pe matricea
    //tablei de joc ale unei piese;
    public CellPosition getPiecePosition(Piece piece) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (Board.getInstance().gameboard[i][j] == piece)
                    return new CellPosition(i, j);
            }
        }
        return null;
    }

    //Returneaza piesa ce se afla la coordonatele x si y de pe matricea tablei de joc
    public Piece getPiece(int x, int y) {
        return Board.getInstance().gameboard[x][y];
    }
}
