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
    }

    public void autenticar(String usuario, String clave) {

        if (usuario.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Complete todos los campos");
            return;
        }

        if (view.esAdmin()) {
            validar("Admin", "admin123", new MainAdmin());
        }
        else if (view.esAnalista()) {
            validar("Analista", "analista123", new MainView());
        }
        else {
            JOptionPane.showMessageDialog(view, "Seleccione un rol");
        }
    }

    private void validar(String user, String pass, JFrame vistaDestino) {

        if (user.equals(view.getTxtUsuario().getText())
                && pass.equals(new String(view.getPasswordField1().getPassword()))) {

            JOptionPane.showMessageDialog(view, "Bienvenido " + user);
            vistaDestino.setVisible(true);
            view.dispose();

        } else {
            manejarFallo();
        }
    }

    private void manejarFallo() {
        intentos++;

        if (intentos >= 3) {
            JOptionPane.showMessageDialog(
                    view,
                    "Acceso bloqueado por 3 intentos fallidos"
            );
            view.getBtnIngresar().setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(
                    view,
                    "Credenciales incorrectas.\nIntentos restantes: " + (3 - intentos)
            );
        }
    }
}


