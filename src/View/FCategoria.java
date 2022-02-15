package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FCategoria {
    private JTextField textFieldNome;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextArea textAreaProdutos;
    private JTextField textFieldId;
    private JButton searchButton;
    private JPanel panelCat;

    public void setVisible(boolean b){
        JFrame frame = new JFrame("Gestão de Menu");
        frame.setContentPane(new FCategoria().panelCat);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600,450);
        //frame.pack();
        frame.setLocationRelativeTo(null);  //centrar
        frame.setVisible(true);
    }

    public FCategoria() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = textFieldNome.getText();
                //String id = textFieldId.getText();

                try {
                    Connection con = Connect.createConnection();
                    PreparedStatement ps = con.prepareStatement("insert into Categoria" +
                            "(NOME) values(?)");
                    ps.setString(1, nome);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Added!");

                    textFieldNome.setText("");
                    textFieldNome.requestFocus();
                    textFieldId.setText("");
                    textAreaProdutos.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro: " +
                            ex.getMessage());
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = textFieldId.getText();

                try {
                    Connection con = Connect.createConnection();
                    PreparedStatement ps = con.prepareStatement("delete from Categoria where IDCategoria=?");
                    ps.setString(1, id);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted!");

                    textFieldNome.setText("");
                    textFieldNome.requestFocus();
                    textFieldId.setText("");
                    textAreaProdutos.setText("");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro: " +
                            ex.getMessage());
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = textFieldNome.getText();
                String id = textFieldId.getText();

                try {
                    Connection con = Connect.createConnection();
                    PreparedStatement ps = con.prepareStatement("update categoria set NOME=? " +
                            "where IDCategoria=?");
                    ps.setString(1, nome);
                    ps.setString(2, id);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updated!");

                    textFieldNome.setText("");
                    textFieldNome.requestFocus();
                    textFieldId.setText("");
                    textAreaProdutos.setText("");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro: " +
                            ex.getMessage());
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = textFieldId.getText();

                try {
                    Connection con = Connect.createConnection();
                    PreparedStatement ps = con.prepareStatement("select NOME,PRECO,QUANT " +
                            "from produtos where IDCategoria=?");
                    ps.setString(1, id);
                    ResultSet rs = ps.executeQuery();
                    textAreaProdutos.setText("");

                    while (rs.next() == true) {
                        String nome = rs.getString("NOME");
                        String preco = rs.getString("PRECO");
                        String quant = rs.getString("QUANT");
                        textAreaProdutos.append(nome + ", €" + preco + ", " + quant + "\n");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro: " +
                            ex.getMessage());
                }
            }
        });
    }
}
