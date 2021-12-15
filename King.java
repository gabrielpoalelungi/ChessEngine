import java.awt.*;
import java.util.Vector;

public class King extends Piece{

	boolean hasBeenMoved = false;
	boolean hasBeenInChess = false;
	Vector<CellPosition> moves;

	public King (Color color, CellPosition pos){
		this.color = color;
		this.pos = pos;
	}

	public String toString() {
		if(color == Color.black)
			return "K";
		return "k";
	}

	public void getMoves() {
		moves = new Vector<CellPosition>();
		CellPosition position = Board.getInstance().getPiecePosition(this);

		for(int i = position.x - 1; i <= position.x + 1; i++) {
			for (int j = position.y - 1; j <= position.y + 1; j++) {
				if (i >= 0 && i <= 7 && j >= 0 && j <= 7) {
					if (Board.getInstance().gameboard[i][j] != null && Board.getInstance().gameboard[i][j].color == this.color) {
						continue;
					}
					Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
					cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, i, j);
					King futureKing = new King(this.color, new CellPosition(i, j));

					if (!((King)cloned_gameboard[i][j]).detectChess(cloned_gameboard)) {
						moves.add(new CellPosition(i, j));
					}
				}
			}
		}
	}

	public boolean move() {
		getMoves();
		System.out.println("King From: " + this.pos);
		if (moves.size() > 0) {
			hasBeenMoved = true;
			int poz = (int) (Math.random() * moves.size());
			CellPosition nextMove = moves.get(poz);
			moveFromToEngine(this.pos.x, this.pos.y, nextMove.x, nextMove.y);
			return true;
		}
		return false;
	}

	public boolean doLongCastle() {
		if (this.hasBeenMoved == false) {
			Rook leftRook;
			if (this.color == Color.BLACK) {
				leftRook = Board.getInstance().blackRooks.get(0);
			} else {
				leftRook = Board.getInstance().whiteRooks.get(0);
			}
			if (leftRook.hasBeenMoved || this.hasBeenInChess || leftRook.hasBeenTaken) {
				return false;
			}

			for (int i = leftRook.pos.y + 1; i < this.pos.y; i++) {
				if (Board.getInstance().gameboard[leftRook.pos.x][i] == null) {
					Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
					cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, this.pos.x, i);
					if (((King) cloned_gameboard[this.pos.x][i]).detectChess(cloned_gameboard)) {
						return false;
					}
				} else {
					return false;
				}
			}

			moveFromToEngine(this.pos.x, this.pos.y, this.pos.x, this.pos.y - 2);
			movePiece(leftRook.pos.x , leftRook.pos.y, leftRook.pos.x, leftRook.pos.y + 3);
			this.hasBeenMoved = true;
			return true;
		}
		return false;
	}

	public boolean doShortCastle() {
		if (this.hasBeenMoved == false) {
			Rook leftRook;
			if (this.color == Color.BLACK) {
				leftRook = Board.getInstance().blackRooks.get(1);
			} else {
				leftRook = Board.getInstance().whiteRooks.get(1);
			}
			if (leftRook.hasBeenMoved || this.hasBeenInChess || leftRook.hasBeenTaken) {
				return false;
			}

			for (int i = leftRook.pos.y - 1; i > this.pos.y; i--) {
				if (Board.getInstance().gameboard[leftRook.pos.x][i] == null) {
					Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
					cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, this.pos.x, i);
					if (((King) cloned_gameboard[this.pos.x][i]).detectChess(cloned_gameboard)) {
						return false;
					}
				} else {
					return false;
				}
			}

			moveFromToEngine(this.pos.x, this.pos.y, this.pos.x, this.pos.y + 2);
			movePiece(leftRook.pos.x, leftRook.pos.y, leftRook.pos.x, leftRook.pos.y - 2);
			this.hasBeenMoved = true;
			return true;
		}
		return false;
	}

	public void doLongCastleFromPlayer() {
		moveFromToPlayer(this.pos.x, this.pos.y - 4, this.pos.x, this.pos.y - 1);
	}

	public void doShortCastleFromPlayer() {
		moveFromToPlayer(this.pos.x, this.pos.y + 3, this.pos.x, this.pos.y + 1);
	}

	public boolean escapeFromChess() {
		if (this.detectChess(Board.getInstance().gameboard)) {
			this.hasBeenInChess = true;

			if (this.color == Color.BLACK) {
				for (Piece piece : Board.getInstance().blackPieces) {
					if (piece.move()) {
						return true;
					}
				}
			} else {
				for (Piece piece : Board.getInstance().whitePieces) {
					if (piece.move()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean detectChess(Piece[][] gameboard) {

		for(int i = this.pos.x - 1; i <= this.pos.x + 1; i++) {
			for (int j = this.pos.y - 1; j <= this.pos.y + 1; j++) {
				if (i >= 0 && i <= 7 && j >= 0 && j <= 7) {
					if (i != this.pos.x || j != this.pos.y) {
						Piece attakingKing = gameboard[i][j];
						if (attakingKing instanceof King && attakingKing.color != this.color) {
							return true;
						}
					}
				}
			}
		}

		if (this.pos.x + 1 >= 0 && this.pos.x + 1 <= 7 && this.pos.y - 2 >= 0 && this.pos.y - 2<= 7) {
			Piece attackingKnight = gameboard[this.pos.x + 1][this.pos.y - 2];
			if (attackingKnight instanceof Knight && attackingKnight.color != this.color) {
				return true;
			}
		}
		if (this.pos.x + 2 >= 0 && this.pos.x + 2 <= 7 && this.pos.y - 1 >= 0 && this.pos.y - 1<= 7) {
			Piece attackingKnight = gameboard[this.pos.x + 2][this.pos.y - 1];
			if (attackingKnight instanceof Knight && attackingKnight.color != this.color) {
				return true;
			}
		}
		if (this.pos.x + 2 >= 0 && this.pos.x + 2 <= 7 && this.pos.y + 1 >= 0 && this.pos.y + 1<= 7) {
			Piece attackingKnight = gameboard[this.pos.x + 2][this.pos.y + 1];
			if (attackingKnight instanceof Knight && attackingKnight.color != this.color) {
				return true;
			}
		}
		if (this.pos.x + 1 >= 0 && this.pos.x + 1 <= 7 && this.pos.y + 2 >= 0 && this.pos.y + 2<= 7) {
			Piece attackingKnight = gameboard[this.pos.x + 1][this.pos.y + 2];
			if (attackingKnight instanceof Knight && attackingKnight.color != this.color) {
				return true;
			}
		}

		if (this.pos.x -1 >= 0 && this.pos.x -1 <= 7 && this.pos.y - 2 >= 0 && this.pos.y - 2<= 7) {
			Piece attackingKnight = gameboard[this.pos.x - 1][this.pos.y - 2];
			if (attackingKnight instanceof Knight && attackingKnight.color != this.color) {
				return true;
			}
		}
		if (this.pos.x - 2 >= 0 && this.pos.x - 2 <= 7 && this.pos.y - 1 >= 0 && this.pos.y - 1 <= 7) {
			Piece attackingKnight = gameboard[this.pos.x - 2][this.pos.y - 1];
			if (attackingKnight instanceof Knight && attackingKnight.color != this.color) {
				return true;
			}
		}
		if (this.pos.x - 2 >= 0 && this.pos.x - 2 <= 7 && this.pos.y + 1 >= 0 && this.pos.y + 1 <= 7) {
			Piece attackingKnight = gameboard[this.pos.x - 2][this.pos.y + 1];
			if (attackingKnight instanceof Knight && attackingKnight.color != this.color) {
				return true;
			}
		}
		if (this.pos.x - 1 >= 0 && this.pos.x - 1 <= 7 && this.pos.y + 2 >= 0 && this.pos.y + 2<= 7) {
			Piece attackingKnight = gameboard[this.pos.x - 1][this.pos.y + 2];
			if (attackingKnight instanceof Knight && attackingKnight.color != this.color) {
				return true;
			}
		}
		// Sah de la nebun sau regina diagonala stanga-jos
		int i = this.pos.x - 1, j = this.pos.y - 1;
		while (i >= 0 && j >= 0) {
			if ((gameboard[i][j] instanceof Bishop || gameboard[i][j] instanceof Queen) &&
			gameboard[i][j].color != this.color) {
				return true;
			} else {
				if (gameboard[i][j] != null) {
					break;
				}
			}
			i--;
			j--;
		}

		// Sah de la nebun sau regina diagonala dreapta jos
		i = this.pos.x - 1;
		j = this.pos.y + 1;
		while (i >= 0 && j <= 7) {
			if ((gameboard[i][j] instanceof Bishop || gameboard[i][j] instanceof Queen) &&
					gameboard[i][j].color != this.color) {
				return true;
			} else {
				if (gameboard[i][j] != null) {
					break;
				}
			}
			i--;
			j++;
		}

		// Sah de la nebun sau regina diagonala stanga sus
		i = this.pos.x + 1;
		j = this.pos.y - 1;
		while (i <= 7 && j >= 0) {
			if ((gameboard[i][j] instanceof Bishop || gameboard[i][j] instanceof Queen) &&
					gameboard[i][j].color != this.color) {
				return true;
			} else {
				if (gameboard[i][j] != null) {
					break;
				}
			}
			i++;
			j--;
		}

		// Sah de la nebun sau regina diagonala dreapta sus
		i = this.pos.x + 1;
		j = this.pos.y + 1;
		while (i <= 7 && j <= 7) {
			if ((gameboard[i][j] instanceof Bishop || gameboard[i][j] instanceof Queen) &&
					gameboard[i][j].color != this.color) {
				return true;
			} else {
				if (gameboard[i][j] != null) {
					break;
				}
			}
			i++;
			j++;
		}
		// Sah de la regina sau turn din Nord;
		i = this.pos.x + 1;
		j = this.pos.y;
		while (i <= 7) {
			if ((gameboard[i][j] instanceof Rook || gameboard[i][j] instanceof Queen) &&
					gameboard[i][j].color != this.color) {
				return true;
			} else {
				if (gameboard[i][j] != null) {
					break;
				}
			}
			i++;
		}
		// Sah de la regina sau turn din Sud;
		i = this.pos.x - 1;
		j = this.pos.y;
		while (i >= 0) {
			if ((gameboard[i][j] instanceof Rook || gameboard[i][j] instanceof Queen) &&
					gameboard[i][j].color != this.color) {
				return true;
			} else {
				if (gameboard[i][j] != null) {
					break;
				}
			}
			i--;
		}

		// Sah de la regina sau turn din Vest;
		i = this.pos.x;
		j = this.pos.y - 1;
		while (j >= 0) {
			if ((gameboard[i][j] instanceof Rook || gameboard[i][j] instanceof Queen) &&
					gameboard[i][j].color != this.color) {
				return true;
			} else {
				if (gameboard[i][j] != null) {
					break;
				}
			}
			j--;
		}

		// Sah de la regina sau turn din Est;
		i = this.pos.x;
		j = this.pos.y + 1;
		while (j <= 7) {
			if ((gameboard[i][j] instanceof Rook || gameboard[i][j] instanceof Queen) &&
					gameboard[i][j].color != this.color) {
				return true;
			} else {
				if (gameboard[i][j] != null) {
					break;
				}
			}
			j++;
		}

		if (this.color == Color.BLACK && (this.pos.x + 1 < 8) && (this.pos.y - 1) >=0 && gameboard[this.pos.x + 1][this.pos.y - 1] instanceof Pawn &&
				gameboard[this.pos.x + 1][this.pos.y - 1].color != this.color) {
			return true;
		}
		if (this.color == Color.BLACK && (this.pos.x + 1 < 8) && (this.pos.y + 1) < 8 && gameboard[this.pos.x + 1][this.pos.y + 1] instanceof Pawn &&
				gameboard[this.pos.x + 1][this.pos.y + 1].color != this.color) {
			return true;
		}

		if (this.color == Color.white && (this.pos.x - 1 >= 0) && (this.pos.y - 1) >=0 && gameboard[this.pos.x - 1][this.pos.y - 1] instanceof Pawn &&
				gameboard[this.pos.x - 1][this.pos.y - 1].color != this.color) {
			return true;
		}
		if (this.color == Color.white && (this.pos.x - 1 >= 0) && (this.pos.y + 1) < 8 && gameboard[this.pos.x - 1][this.pos.y + 1] instanceof Pawn &&
				gameboard[this.pos.x - 1][this.pos.y + 1].color != this.color) {
			return true;
		}
		return false;
	}
}
