package main.java.Proyecto1.view.login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    private JButton jbingresar;
    private JLabel jlbtitulo;
    private JLabel jlbusuario;
    private JLabel jlbcontraseña;
    private JPasswordField jpscontraseña;
    private JTextField txtusuario;
    private JPanel rootPanel;

    public LoginView() {

        // Panel Principal
        rootPanel = new JPanel();
        rootPanel.setLayout(null);

        // Título
        jlbtitulo = new JLabel("LOGIN");
        jlbtitulo.setBounds(140, 10, 100, 30);
        rootPanel.add(jlbtitulo);

        // Usuario
        jlbusuario = new JLabel("Usuario:");
        jlbusuario.setBounds(50, 60, 100, 25);
        rootPanel.add(jlbusuario);

        txtusuario = new JTextField();
        txtusuario.setBounds(150, 60, 150, 25);
        rootPanel.add(txtusuario);

        // Contraseña
        jlbcontraseña = new JLabel("Contraseña:");
        jlbcontraseña.setBounds(50, 100, 100, 25);
        rootPanel.add(jlbcontraseña);

        jpscontraseña = new JPasswordField();
        jpscontraseña.setBounds(150, 100, 150, 25);
        rootPanel.add(jpscontraseña);

        // Botón
        jbingresar = new JButton("Ingresar");
        jbingresar.setBounds(120, 150, 120, 30);
        rootPanel.add(jbingresar);

        // Acción del botón
        jbingresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtusuario.getText();
                String clave = String.valueOf(jpscontraseña.getPassword()); // se mantiene, pero no se muestra

                JOptionPane.showMessageDialog(
                        LoginView.this,
                        "Bienvenido " + usuario
                );
            }
        });

        setContentPane(rootPanel);
        setTitle("Login de Usuario"); // pequeño cambio
        setSize(380, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new LoginView().setVisible(true);
    }
}


