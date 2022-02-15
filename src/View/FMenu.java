package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FMenu {
    private JPanel panelMenu;
    private JButton categoriasButton;
    private JButton produtosButton;

    public void setVisible(boolean b){
        JFrame frame = new JFrame("Gestão de Menu");
        frame.setContentPane(new FMenu().panelMenu);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600,450);
        //frame.pack();
        frame.setLocationRelativeTo(null);  //centrar
        frame.setVisible(true);
    }

    public FMenu() {
        produtosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FProdutos().setVisible(true);
            }
        });
        categoriasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FCategoria().setVisible(true);
            }
        });
    }
}
