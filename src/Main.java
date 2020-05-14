import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        GameLoop game = new GameLoop();
        game.setVisible(true);
        game.runGameLoop();
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

