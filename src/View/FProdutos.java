package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class FProdutos {
    private JTabbedPane tabbedPaneProdutos;
    private JTextField textFieldProNomeApa;
    private JTextField textFieldPrecoApa;
    private JTextField textFieldQuantApa;
    private JTextField textFieldCatIDApa;
    private JLabel labelImagemApa;
    private JButton deleteButton;
    private JTextField textFieldProIDApa;
    private JButton searchButtonApa;
    private JTextField textFieldProIDCon;
    private JTextField textFieldProNomeCon;
    private JTextField textFieldPrecoCon;
    private JTextField textFieldQuantCon;
    private JTextField textFieldCatIDCon;
    private JButton buttonAnterior;
    private JButton buttonUltimo;
    private JButton buttonProximo;
    private JButton buttonPrimeiro;
    private JLabel labelImagemCon;
    private JTextField textFieldProNomeAlt;
    private JTextField textFieldPrecoAlt;
    private JTextField textFieldQuantAlt;
    private JTextField textFieldCatIDAlt;
    private JButton browseButtonAlt;
    private JLabel labelImagemAlt;
    private JButton updateButton;
    private JTextField textFieldProIDAlt;
    private JButton searchButtonAlt;
    private JTextField textFieldProName;
    private JTextField textFieldPrice;
    private JTextField textFieldQuant;
    private JTextField textFieldCat;
    private JButton browseButton1;
    private JLabel labelImagem;
    private JButton saveButton;
    private JPanel panelProdutos;

    private String path=null;
    private byte[] userImage;
    private Connection con;
    private ResultSet rs;
    private Statement st;

    public void setVisible(boolean b){
        JFrame frame = new JFrame("Gestão de Produtos");
        frame.setContentPane(new FProdutos().panelProdutos);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600,450);
        frame.setLocationRelativeTo(null);  //centrar
        frame.setVisible(true);
    }

    public void Connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lojaesteves?useSSL=false",
                    "root", "1234");
            st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery("select IDProdutos,NOME,PRECO,QUANT,IDCategoria,FOTO from produtos");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public FProdutos() {
        Connection();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = textFieldProName.getText();
                String preco = textFieldPrice.getText();
                String quant = textFieldQuant.getText();
                String cat = textFieldCat.getText();

                try{
                    Connection con = Connect.createConnection();
                    PreparedStatement ps = con.prepareStatement("insert into produtos" +
                            "(NOME,PRECO,QUANT,IDCategoria,FOTO)values(?,?,?,?,?)");
                    ps.setString(1, nome);
                    ps.setString(2, preco);
                    ps.setString(3, quant);
                    ps.setString(4, cat);
                    ps.setBytes(5,userImage);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Added!");

                    textFieldProName.setText("");
                    textFieldPrice.setText("");
                    textFieldQuant.setText("");
                    textFieldCat.setText("");
                    labelImagem.setIcon(null);
                    //textFieldProName.requestFocus(); //ns se é preciso
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Erro: " +
                            ex.getMessage());
                }
                textFieldProName.setText("");
                textFieldPrice.setText("");
                textFieldQuant.setText("");
                textFieldCat.setText("");
                labelImagem.setIcon(null);
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = textFieldProNomeAlt.getText();
                String preco = textFieldPrecoAlt.getText();
                String quant = textFieldQuantAlt.getText();
                String cat = textFieldCatIDAlt.getText();
                String id = textFieldProIDAlt.getText();

                try{
                    Connection con = Connect.createConnection();
                    PreparedStatement ps = con.prepareStatement("update produtos set NOME=?, PRECO=?, " +
                            "QUANT=?, IDCategoria=?, FOTO=? where IDProdutos=?");
                    ps.setString(1,nome);
                    ps.setString(2,preco);
                    ps.setString(3,quant);
                    ps.setString(4, cat);
                    ps.setBytes(5, userImage);
                    ps.setString(6, id);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updated!");

                    textFieldProNomeAlt.setText("");
                    textFieldPrecoAlt.setText("");
                    textFieldQuantAlt.setText("");
                    textFieldCatIDAlt.setText("");
                    labelImagemAlt.setIcon(null);
                    textFieldProIDAlt.setText("");
                    //textFieldProNomeAlt.requestFocus(); //ns se é preciso
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Erro: " +
                            ex.getMessage());
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = textFieldProIDApa.getText();
                try{
                    Connection con = Connect.createConnection();
                    PreparedStatement ps = con.prepareStatement("delete from produtos where IDProdutos=?");
                    ps.setString(1,id);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted!");

                    textFieldProNomeApa.setText("");
                    textFieldPrecoApa.setText("");
                    textFieldQuantApa.setText("");
                    textFieldCatIDApa.setText("");
                    labelImagemApa.setIcon(null);
                    textFieldProIDApa.setText("");
                    //textFieldProNomeApa.requestFocus(); //ns se é preciso
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Erro: " +
                            ex.getMessage());
                }
            }
        });
        searchButtonAlt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = textFieldProIDAlt.getText();
                try{
                    Connection con = Connect.createConnection();
                    PreparedStatement ps = con.prepareStatement("select NOME,PRECO,QUANT,IDCategoria,FOTO" +
                            " from produtos where IDProdutos=?");
                    ps.setString(1, id);
                    ResultSet rs = ps.executeQuery();

                    if(rs.next()==true){
                        String nome = rs.getString("NOME");
                        String preco = rs.getString("PRECO");
                        String quant = rs.getString("QUANT");
                        String cat = rs.getString("IDCategoria");
                        if(rs.getBlob("FOTO")==null){
                            labelImagemAlt.setIcon( new ImageIcon(getClass().getResource("resources/semfoto.png")));
                        }
                        else{
                            Blob blob = rs.getBlob("FOTO");
                            byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                            ImageIcon imageIcon = new ImageIcon(new ImageIcon(imageBytes).getImage().
                                    getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                            labelImagemAlt.setIcon(imageIcon);
                        }
                        textFieldProNomeAlt.setText(nome);
                        textFieldPrecoAlt.setText(preco);
                        textFieldQuantAlt.setText(quant);
                        textFieldCatIDAlt.setText(cat);
                    }
                    else{
                        textFieldProNomeAlt.setText("");
                        textFieldPrecoAlt.setText("");
                        textFieldQuantAlt.setText("");
                        textFieldCatIDAlt.setText("");
                        textFieldProIDAlt.setText("");
                        JOptionPane.showMessageDialog(null, "Invalid Product ID");
                    }
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Erro: " +
                            ex.getMessage());
                }
            }
        });
        searchButtonApa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = textFieldProIDApa.getText();
                try{
                    Connection con = Connect.createConnection();
                    PreparedStatement ps = con.prepareStatement("select NOME,PRECO,QUANT,IDCategoria,FOTO" +
                            " from produtos where IDProdutos=?");
                    ps.setString(1, id);
                    ResultSet rs = ps.executeQuery();

                    if(rs.next()==true){
                        String nome = rs.getString("NOME");
                        String preco = rs.getString("PRECO");
                        String quant = rs.getString("QUANT");
                        String cat = rs.getString("IDCategoria");
                        if(rs.getBlob("FOTO")==null){
                            labelImagemApa.setIcon( new ImageIcon(getClass().getResource("resources/semfoto.png")));
                        }
                        else{
                            Blob blob = rs.getBlob("FOTO");
                            byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                            ImageIcon imageIcon = new ImageIcon(new ImageIcon(imageBytes).getImage().
                                    getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                            labelImagemApa.setIcon(imageIcon);
                        }
                        textFieldProNomeApa.setText(nome);
                        textFieldPrecoApa.setText(preco);
                        textFieldQuantApa.setText(quant);
                        textFieldCatIDApa.setText(cat);
                    }
                    else{
                        textFieldProNomeApa.setText("");
                        textFieldPrecoApa.setText("");
                        textFieldQuantApa.setText("");
                        textFieldCatIDApa.setText("");
                        textFieldProIDApa.setText("");
                        JOptionPane.showMessageDialog(null, "Invalid Product ID");
                    }
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Erro: " +
                            ex.getMessage());
                }
            }
        });
        browseButtonAlt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser imgChoosed=new JFileChooser();
                imgChoosed.showOpenDialog(null);
                File file=imgChoosed.getSelectedFile();
                path=file.getAbsolutePath();
                BufferedImage img;
                try{
                    //prepara a imagem para apresentar na label (icon)
                    img= ImageIO.read(file);
                    ImageIcon imageIcon=new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(250,
                            250, Image.SCALE_DEFAULT));
                    labelImagem.setIcon(imageIcon);

                    //guardar a imagem na variavel byte[] "userImage" para
                    //ser depois guardada na base de dados
                    File image=new File(path);
                    FileInputStream fs=new FileInputStream(image);
                    ByteArrayOutputStream bos=new ByteArrayOutputStream();
                    byte[] buff=new byte[1024];
                    int nByteRead=0;
                    while((nByteRead=fs.read(buff))!=-1)
                        bos.write(buff, 0, nByteRead);
                    userImage=bos.toByteArray();
                }
                catch(IOException e1){
                    e1.printStackTrace();
                }
            }
        });
        browseButtonAlt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser imgChoosed=new JFileChooser();
                imgChoosed.showOpenDialog(null);
                File file=imgChoosed.getSelectedFile();
                path=file.getAbsolutePath();
                BufferedImage img;
                try{
                    //prepara a imagem para apresentar na label (icon)
                    img= ImageIO.read(file);
                    ImageIcon imageIcon=new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(250,
                            250, Image.SCALE_DEFAULT));
                    labelImagemAlt.setIcon(imageIcon);

                    //guardar a imagem na variavel byte[] "userImage" para
                    //ser depois guardada na base de dados
                    File image=new File(path);
                    FileInputStream fs=new FileInputStream(image);
                    ByteArrayOutputStream bos=new ByteArrayOutputStream();
                    byte[] buff=new byte[1024];
                    int nByteRead=0;
                    while((nByteRead=fs.read(buff))!=-1)
                        bos.write(buff, 0, nByteRead);
                    userImage=bos.toByteArray();
                }
                catch(IOException e1){
                    e1.printStackTrace();
                }
            }
        });
        buttonPrimeiro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                textFieldProIDCon.setText("");
                textFieldProNomeCon.setText("");
                textFieldPrecoCon.setText("");
                textFieldQuantCon.setText("");
                textFieldCatIDCon.setText("");
                labelImagemCon.setIcon(null);
                */

                try {
                    rs.first();
                    textFieldProIDCon.setText(String.valueOf(rs.getInt("IDProdutos")));
                    textFieldProNomeCon.setText(rs.getString("NOME"));
                    textFieldPrecoCon.setText(String.valueOf(rs.getDouble("PRECO")));
                    textFieldQuantCon.setText(String.valueOf(rs.getInt("QUANT")));
                    textFieldCatIDCon.setText(String.valueOf(rs.getInt("IDCategoria")));
                    if(rs.getBlob("FOTO")==null){
                        labelImagemCon.setIcon( new ImageIcon(getClass().getResource("resources/semfoto.png")));
                    }
                    else{
                        Blob blob = rs.getBlob("FOTO");
                        byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                        ImageIcon imageIcon = new ImageIcon(new ImageIcon(imageBytes).getImage().
                                getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                        labelImagemCon.setIcon(imageIcon);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonAnterior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              /*
              textFieldProIDCon.setText("");
              textFieldProNomeCon.setText("");
              textFieldPrecoCon.setText("");
              textFieldQuantCon.setText("");
              textFieldCatIDCon.setText("");
              labelImagemCon.setIcon(null);
              */

                try {
                    if (!rs.isFirst()) {
                        rs.previous();
                        textFieldProIDCon.setText(String.valueOf(rs.getInt("IDProdutos")));
                        textFieldProNomeCon.setText(rs.getString("NOME"));
                        textFieldPrecoCon.setText(String.valueOf(rs.getDouble("PRECO")));
                        textFieldQuantCon.setText(String.valueOf(rs.getInt("QUANT")));
                        textFieldCatIDCon.setText(String.valueOf(rs.getInt("IDCategoria")));
                        if(rs.getBlob("FOTO")==null){
                            labelImagemCon.setIcon( new ImageIcon(getClass().getResource("resources/semfoto.png")));
                        }
                        else{
                            Blob blob = rs.getBlob("FOTO");
                            byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                            ImageIcon imageIcon = new ImageIcon(new ImageIcon(imageBytes).getImage().
                                    getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                            labelImagemCon.setIcon(imageIcon);
                        }
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonProximo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               /*
               textFieldProIDCon.setText("");
               textFieldProNomeCon.setText("");
               textFieldPrecoCon.setText("");
               textFieldQuantCon.setText("");
               textFieldCatIDCon.setText("");
               labelImagemCon.setIcon(null);
               */

                try {
                    if (!rs.isLast()) {
                        rs.next();
                        textFieldProIDCon.setText(String.valueOf(rs.getInt("IDProdutos")));
                        textFieldProNomeCon.setText(rs.getString("NOME"));
                        textFieldPrecoCon.setText(String.valueOf(rs.getDouble("PRECO")));
                        textFieldQuantCon.setText(String.valueOf(rs.getInt("QUANT")));
                        textFieldCatIDCon.setText(String.valueOf(rs.getInt("IDCategoria")));
                        if(rs.getBlob("FOTO")==null){
                            labelImagemCon.setIcon( new ImageIcon(getClass().getResource("resources/semfoto.png")));
                        }
                        else{
                            Blob blob = rs.getBlob("FOTO");
                            byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                            ImageIcon imageIcon = new ImageIcon(new ImageIcon(imageBytes).getImage().
                                    getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                            labelImagemCon.setIcon(imageIcon);
                        }
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonUltimo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                textFieldProIDCon.setText("");
                textFieldProNomeCon.setText("");
                textFieldPrecoCon.setText("");
                textFieldQuantCon.setText("");
                textFieldCatIDCon.setText("");
                labelImagemCon.setIcon(null);
                */

                try {
                    rs.last();
                    textFieldProIDCon.setText(String.valueOf(rs.getInt("IDProdutos")));
                    textFieldProNomeCon.setText(rs.getString("NOME"));
                    textFieldPrecoCon.setText(String.valueOf(rs.getDouble("PRECO")));
                    textFieldQuantCon.setText(String.valueOf(rs.getInt("QUANT")));
                    textFieldCatIDCon.setText(String.valueOf(rs.getInt("IDCategoria")));
                    if(rs.getBlob("FOTO")==null){
                        labelImagemCon.setIcon( new ImageIcon(getClass().getResource("resources/semfoto.png")));
                    }
                    else{
                        Blob blob = rs.getBlob("FOTO");
                        byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                        ImageIcon imageIcon = new ImageIcon(new ImageIcon(imageBytes).getImage().
                                getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                        labelImagemCon.setIcon(imageIcon);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        tabbedPaneProdutos.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                //n é preciso

                Connection();
                textFieldProName.setText("");
                textFieldPrice.setText("");
                textFieldQuant.setText("");
                textFieldCat.setText("");
                labelImagem.setIcon(null);
                textFieldProIDAlt.setText("");
                textFieldProNomeAlt.setText("");
                textFieldPrecoAlt.setText("");
                textFieldQuantAlt.setText("");
                textFieldCatIDAlt.setText("");
                labelImagemAlt.setIcon(null);
                //textFieldProIDCon.setText("");
                //textFieldProNomeCon.setText("");
                //textFieldPrecoCon.setText("");
                //textFieldQuantCon.setText("");
                //textFieldCatIDCon.setText("");
                //labelImagemCon.setIcon(null);
                textFieldProIDApa.setText("");
                textFieldProNomeApa.setText("");
                textFieldPrecoApa.setText("");
                textFieldQuantApa.setText("");
                textFieldCatIDApa.setText("");
                labelImagemApa.setIcon(null);
            }
        });
    }
}
