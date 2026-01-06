package ec.edu.sistemalicencias.controller;
import ec.edu.sistemalicencias.view.LoginView;
import ec.edu.sistemalicencias.view.MainAdmin;
import ec.edu.sistemalicencias.view.MainView;

import javax.swing.*;


public class LoginController {
    private LoginView view;
    private int intentos = 0;

    public LoginController(LoginView view) {
        this.view = view;

        view.getBtnIngresar().addActionListener(e -> login());
        view.getBtnSalir().addActionListener(e -> System.exit(0));
    }
    private void login() {
        String usuario = view.getTxtUsuario().getText();
        String clave = new String(view.getPasswordField1().getPassword());

        if (usuario.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Todos los campos deben estar completos");
            return;
        }

        if (view.getRdbAdmin().isSelected()) {
            validar("Admin", "admin123", new MainAdmin());
        }
        else if (view.getRdbAnalista().isSelected()) {
            validar("Analista", "analista123", new MainView());
        }
        else {
            JOptionPane.showMessageDialog(view, "Seleccione un rol");
        }


    }

    private void validar(String user, String pass, JFrame vistaDestino) {
        if (view.getTxtUsuario().getText().equals(user)
                && new String(view.getPasswordField1().getPassword()).equals(pass)) {

            JOptionPane.showMessageDialog(view, "Bienvenido " + user);
            vistaDestino.setVisible(true);
            view.dispose();

        } else {
            manejarFallo();
        }
    }

    private void manejarFallo() {
        intentos++;
        view.getTxtUsuario().setText("");
        view.getPasswordField1().setText("");

        if (intentos >= 3) {
            JOptionPane.showMessageDialog(view, "Acceso bloqueado por 3 intentos fallidos");
            view.getTxtUsuario().setEnabled(false);
            view.getPasswordField1().setEnabled(false);
            view.getBtnIngresar().setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(
                    view,
                    "Usuario o contraseña incorrecta.\nIntentos restantes: " + (3 - intentos)
            );
        }
    }
}

