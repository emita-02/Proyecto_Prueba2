package ec.edu.sistemalicencias.controller;

import ec.edu.sistemalicencias.model.entities.Usuario;
import ec.edu.sistemalicencias.model.exceptions.BaseDatosException;
import ec.edu.sistemalicencias.model.exceptions.DatosInvalidosException;
import ec.edu.sistemalicencias.service.UsuarioService;
import ec.edu.sistemalicencias.view.LoginView;
import ec.edu.sistemalicencias.view.MainAdmin;
import ec.edu.sistemalicencias.view.MainView;

import javax.swing.*;

public class LoginController {

    private UsuarioService usuarioService = new UsuarioService();
    private LoginView view;
    private int intentos = 0;

    public LoginController(LoginView view) {
        this.view = view;
    }

    public void autenticar(String usuario, String password) {
        try {
            //Ingresar al sistema con usuarios de la BD
            Usuario u = usuarioService.autenticar(usuario, password);

            // Validar que el rol coincida con lo seleccionado
            if (view.esAdmin() && !"ADMINISTRADOR".equalsIgnoreCase(u.getRol())) {
                JOptionPane.showMessageDialog(view, "Este usuario no es administrador");
                return;
            }
            if (view.esAnalista() && !"ANALISTA".equalsIgnoreCase(u.getRol())) {
                JOptionPane.showMessageDialog(view, "Este usuario no es analista");
                return;
            }

            JOptionPane.showMessageDialog(view, "Bienvenido " + u.getNombre());

            if ("ADMINISTRADOR".equalsIgnoreCase(u.getRol())) {
                new MainAdmin().setVisible(true);
            } else {
                new MainView().setVisible(true);
            }

            view.dispose();
            return;

        } catch (DatosInvalidosException e) {

        } catch (BaseDatosException e) {
            JOptionPane.showMessageDialog(view, "Error de base de datos: " + e.getMessage());
        }

        //Datos quemados para el ingreso
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


