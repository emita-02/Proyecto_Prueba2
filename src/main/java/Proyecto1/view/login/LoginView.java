package main.java.Proyecto1.view.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    private JButton jbingresar;
    private JLabel jlbtitulo;
    private JLabel jlbusuario;
    private JLabel jlbcontraseña;
    private JPasswordField jpscontraseña;
    private JTextField txtusuario;
    private JLabel jblindicacion;
    private JPanel rootPanel;

    public LoginView() {

        rootPanel = new JPanel();
        rootPanel.setLayout(null);

        // Título centrado
        jlbtitulo = new JLabel("LOGIN", SwingConstants.CENTER);
        jlbtitulo.setFont(new Font("Arial", Font.BOLD, 16));
        jlbtitulo.setBounds(0, 15, 380, 30);
        rootPanel.add(jlbtitulo);

        // Usuario
        jlbusuario = new JLabel("Usuario:");
        jlbusuario.setBounds(90, 65, 80, 25);
        rootPanel.add(jlbusuario);

        txtusuario = new JTextField();
        txtusuario.setBounds(170, 65, 140, 25);
        rootPanel.add(txtusuario);

        // Contraseña
        jlbcontraseña = new JLabel("Contraseña:");
        jlbcontraseña.setBounds(90, 105, 80, 25);
        rootPanel.add(jlbcontraseña);

        jpscontraseña = new JPasswordField();
        jpscontraseña.setBounds(170, 105, 140, 25);
        rootPanel.add(jpscontraseña);

        // Indicacion debajo del password
        jblindicacion = new JLabel("8 caracteres · 1 mayúscula · 1 número · 1 especial");
        jblindicacion.setFont(new Font("Arial", Font.ITALIC, 10));
        jblindicacion.setForeground(Color.GRAY);
        jblindicacion.setBounds(170, 128, 240, 15);
        rootPanel.add(jblindicacion);

        // Botón centrado
        jbingresar = new JButton("Ingresar");
        jbingresar.setBounds(130, 170, 120, 30);
        rootPanel.add(jbingresar);

        // Acción
        jbingresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        LoginView.this,
                        "Bienvenido " + txtusuario.getText()
                );
            }
        });

        setContentPane(rootPanel);
        setTitle("Login de Usuario");
        setSize(380, 270);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new LoginView().setVisible(true);
    }
}
