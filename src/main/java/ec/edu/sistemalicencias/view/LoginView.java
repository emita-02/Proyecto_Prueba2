package ec.edu.sistemalicencias.view;

import ec.edu.sistemalicencias.controller.LoginController;

import javax.swing.*;

public class LoginView extends JFrame {

    private JRadioButton rdbAdmin;
    private JRadioButton rdbAnalista;
    private JTextField txtUsuario;
    private JPasswordField passwordField1;
    private JButton btnIngresar;
    private JButton btnSalir;
    private JPanel jpLogin;

    private ButtonGroup grupoRol;
    private LoginController controller;

    public LoginView() {

        controller = new LoginController(this);

        setTitle("Sistema de Licencias");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(jpLogin);

        // Agrupar roles
        grupoRol = new ButtonGroup();
        grupoRol.add(rdbAdmin);
        grupoRol.add(rdbAnalista);

        // Deshabilitar al inicio
        txtUsuario.setEnabled(false);
        passwordField1.setEnabled(false);
        btnIngresar.setEnabled(false);

        // Eventos
        rdbAdmin.addActionListener(e -> habilitarLogin());
        rdbAnalista.addActionListener(e -> habilitarLogin());

        btnIngresar.addActionListener(e -> ingresar());
        btnSalir.addActionListener(e -> System.exit(0));

        getRootPane().setDefaultButton(btnIngresar);
    }

    private void habilitarLogin() {
        txtUsuario.setEnabled(true);
        passwordField1.setEnabled(true);
        btnIngresar.setEnabled(true);
    }

    private void ingresar() {
        String usuario = txtUsuario.getText();
        String clave = new String(passwordField1.getPassword());

        controller.autenticar(usuario, clave);
    }

    // Métodos Controller
    public boolean esAdmin() {
        return rdbAdmin.isSelected();
    }

    public boolean esAnalista() {
        return rdbAnalista.isSelected();
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JPasswordField getPasswordField1() {
        return passwordField1;
    }

    public JButton getBtnIngresar() {
        return btnIngresar;
    }

     
}

