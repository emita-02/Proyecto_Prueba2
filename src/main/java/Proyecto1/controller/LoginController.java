package main.java.Proyecto1.controller;

import main.java.Proyecto1.view.admin.AdminView;
import main.java.Proyecto1.view.analista.AnalistaView;
import main.java.Proyecto1.view.login.LoginView;

import javax.swing.JOptionPane;

public class LoginController {
    private LoginView loginView;

    public LoginController(LoginView loginView){
        this.loginView = loginView;
    }

    public void autenticar(String usuario, String contraseña){
        if  (usuario.equals("admin") && contraseña.equals("admin123")){
            AdminView adminView = new AdminView();
            adminView.setVisible(true);
            loginView.dispose();
        } else if (usuario.equals("analis") && contraseña.equals("analist123")){
            AnalistaView analistaView = new AnalistaView();
            analistaView.setVisible(true);
            loginView.dispose();
        } else {
            JOptionPane.showMessageDialog(loginView,
                    "Usuario o contraseña incorrectos",
                    "Error de login",
                    JOptionPane.ERROR_MESSAGE);
        }


    }
}
