public class Board {
    private final int[][] data;
    private int turn;

    public Board(int turn) {
        this.data = new int[3][3];
        this.turn = turn;
    }

    public int getTurn() {
        return turn;
    }

    // menampilkan board
    public void disp() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                switch (this.data[i][j]) {
                    case 0 -> System.out.print("  -  ");
                    case -1 -> System.out.print("  X  ");
                    case 1 -> System.out.print("  O  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // isi board kalau masih kosong
    public boolean setBoard(int brs, int kol) {
        if (this.data[brs][kol] == 0) {
            this.data[brs][kol] = turn;
            turn = -turn;
            return true;
        } else {
            return false;
        }
    }

    // mengecek apakah ada pemenang
    public int winner() {
        for (int i = 0; i < 3; i++) {
            int rowSum = data[i][0] + data[i][1] + data[i][2];
            int colSum = data[0][i] + data[1][i] + data[2][i];
            if (rowSum == 3 || colSum == 3) return 1;
            if (rowSum == -3 || colSum == -3) return -1;
        }
        int diag1 = data[0][0] + data[1][1] + data[2][2];
        int diag2 = data[0][2] + data[1][1] + data[2][0];
        if (diag1 == 3 || diag2 == 3) return 1;
        if (diag1 == -3 || diag2 == -3) return -1;
        return 0;
    }

    // cek apakah game sudah selesai
    public boolean gameOver() {
        if (winner() != 0) return true;
        for (int[] row : data)
            for (int cell : row)
                if (cell == 0) return false;
        return true;
    }

    // logika untuk bot easy
    public int[] getRandomMove() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (data[i][j] == 0)
                    return new int[]{i, j};
        return new int[]{-1, -1};
    }

    // logika untuk bot hard (versi pintar)
    public int[] getBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] move = {-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (data[i][j] == 0) {
                    data[i][j] = 1; // Bot main
                    int score = simulate(data, 0, false);
                    data[i][j] = 0;
                    if (score > bestScore) {
                        bestScore = score;
                        move[0] = i;
                        move[1] = j;
                    }
                }
            }
        }
        return move;
    }

    // algoritma 
    private int simulate(int[][] board, int depth, boolean isMaximizing) {
        int result = checkWinner(board);
        if (result != 0) return result * (10 - depth); // menang cepat = skor lebih tinggi
        if (isBoardFull(board)) return 0;

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = 1;
                        best = Math.max(best, simulate(board, depth + 1, false));
                        board[i][j] = 0;
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = -1;
                        best = Math.min(best, simulate(board, depth + 1, true));
                        board[i][j] = 0;
                    }
                }
            }
            return best;
        }
    }

    // mengecek pemenang dari board simulasi
    private int checkWinner(int[][] board) {
        for (int i = 0; i < 3; i++) {
            int row = board[i][0] + board[i][1] + board[i][2];
            int col = board[0][i] + board[1][i] + board[2][i];
            if (row == 3 || col == 3) return 1;
            if (row == -3 || col == -3) return -1;
        }
        int diag1 = board[0][0] + board[1][1] + board[2][2];
        int diag2 = board[0][2] + board[1][1] + board[2][0];
        if (diag1 == 3 || diag2 == 3) return 1;
        if (diag1 == -3 || diag2 == -3) return -1;
        return 0;
    }

    // cek apakah board sudah penuh
    private boolean isBoardFull(int[][] board) {
        for (int[] row : board)
            for (int cell : row)
                if (cell == 0) return false;
        return true;
    }
}
