package main.java.Proyecto1.view.login;

import main.java.Proyecto1.controller.LoginController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    private JButton btningresar;
    private JLabel jlbtitulo;
    private JLabel jlbusuario;
    private JLabel jlbcontraseña;
    private JPasswordField jpscontraseña;
    private JTextField txtusuario;
    private JPanel rootPanel;

    //Declarar atributo de LoginController
    private LoginController controller;


    public LoginView() {
        //conexión con LoginController
        controller = new LoginController(this);

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
        btningresar = new JButton("Ingresar");
        btningresar.setBounds(120, 150, 120, 30);
        rootPanel.add(btningresar);

        // Acción del botón
        btningresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtusuario.getText();
                String clave = new String(jpscontraseña.getPassword());

                JOptionPane.showMessageDialog(null,
                        "Usuario: " + usuario + "\nContraseña: " + clave);

                controller.autenticar(usuario, clave);
            }

        });

        setContentPane(rootPanel);
        setTitle("Login");
        setSize(380, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}

