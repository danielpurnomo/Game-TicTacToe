import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int turn = -1; // giliran awal pemain (X)
        Board x = new Board(turn);

        // mode game (easy atau hard)       
        System.out.println("Pilih mode permainan:");
        System.out.println("1. Comp Easy");
        System.out.println("2. Comp Hard");
        System.out.print("Pilihan (1/2): ");
        int mode = in.nextInt();

        x.disp(); // tampilkan board

        // loop sampai game selesai
        while (!x.gameOver()) {
            if (x.getTurn() == -1) { // Giliran pemain
                System.out.print("Masukkan baris (0-2): ");
                int brs = in.nextInt();
                System.out.print("Masukkan kolom (0-2): ");
                int kol = in.nextInt();
                if (!x.setBoard(brs, kol)) {
                    System.out.println("Posisi sudah terisi! Coba lagi.");
                }
            } else { // giliran bot
                int[] move;
                if (mode == 1) {
                    move = x.getRandomMove(); // bot Easy
                } else {
                    move = x.getBestMove();   // bot Hard
                }
                x.setBoard(move[0], move[1]);
                System.out.println("Bot memilih: (" + move[0] + ", " + move[1] + ")");
            }

            x.disp(); //tampilkan board setelah suatu move
        }

        // cek hasil akhir permainan
        int win = x.winner();
        if (win == -1) System.out.println("Kamu menang!");
        else if (win == 1) System.out.println("Bot menang!");
        else System.out.println("Seri!");

        in.close();
    }
}
