package ec.edu.sistemalicencias.controller;

import ec.edu.sistemalicencias.model.entities.Usuario;
import ec.edu.sistemalicencias.model.exceptions.DatosInvalidosException;

import javax.swing.*;
import java.util.List;

public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController() {
        this.usuarioService = new UsuarioService();
    }

    //Crear usuario analista
    public void crearAnalista(String username){
        try{
            usuarioService.crearAnalista(username);
            mostrarExito("Usuario analista creado correctamente");
        } catch (DatosInvalidosException e){
            mostrarError(e.getMessage());
        } catch (Exception e){
            mostrarError("Error al crear usuario: " + e.getMessage());
        }
    }

    public List<Usuario> obtenerUsuarios(){
        try {
            return usuarioService.listarUsuarios();
        } catch (Exception e){
            mostrarError("Error al obtener usuarios.");
            return null;
        }
    }

    public void eliminarUsuario(Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            mostrarExito("Usuario eliminado correctamente");

        } catch (Exception e) {
            mostrarError("Error al eliminar usuario");
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}
