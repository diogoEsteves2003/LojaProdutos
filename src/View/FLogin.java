package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FLogin {
    private JTextField textFieldUser;
    private JButton cancelButton;
    private JButton OKButton;
    private JPanel panelLogin;
    private JPasswordField passwordFieldPass;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gestão de Login");
        frame.setContentPane(new FLogin().panelLogin);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,450);
        //frame.pack();
        frame.setLocationRelativeTo(null);  //centrar
        frame.setVisible(true);
    }

    public FLogin() {
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String txtUser = textFieldUser.getText();
                String txtPass = passwordFieldPass.getText();

                try{
                    Connection con = Connect.createConnection();
                    String sql = "SELECT NOME,TELEFONE,NOMEutilizador,PALAVRApasse FROM Empregados";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();
                    boolean fg = false;

                    while(rs.next()){
                        String nome=rs.getString("NOME");
                        String tele=rs.getString("TELEFONE");
                        String user=rs.getString("NOMEutilizador");
                        String pass=rs.getString("PALAVRApasse");
                        if(txtUser.equals(user) && (txtPass.equals(pass))){
                            JOptionPane.showMessageDialog(null, nome + " - " +
                                    tele + " - " + user + " - " + pass);
                            new FMenu().setVisible(true);
                            fg = true;
                        }
                    }
                    if(!fg){
                        JOptionPane.showMessageDialog(null,
                                "Login errado! username/password incorretos!");
                    }
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Erro na ligação: " +
                            ex.getMessage());
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldUser.setText("");
                passwordFieldPass.setText("");
            }
        });
    }
}
