import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame obj = new JFrame();
        obj.setBounds(10, 10, 700, 600);
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setTitle("Breaking out");
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Gameplay gameplay = new Gameplay();
        obj.add(gameplay);

    }
}
