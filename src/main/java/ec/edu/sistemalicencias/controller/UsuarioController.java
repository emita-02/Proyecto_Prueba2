package ec.edu.sistemalicencias.controller;

import ec.edu.sistemalicencias.model.entities.Usuario;
import ec.edu.sistemalicencias.model.exceptions.DatosInvalidosException;
import ec.edu.sistemalicencias.service.UsuarioService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.List;

public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController() {
        this.usuarioService = new UsuarioService();
    }

    // ----------CRUD del sistema para el usuario Administrador ------------------
    public void crearUsuario(Usuario usuario) {
        try {
            usuarioService.guardarUsuario(usuario);
            mostrarExito("Usuario creado correctamente.");
        } catch (DatosInvalidosException e) {
            mostrarError(e.getMessage());
        } catch (Exception e) {
            mostrarError("Error al crear usuario: " + e.getMessage());
        }
    }

    // Actualizar usuario
    public void actualizarUsuario(Usuario usuario) {
        try {
            usuarioService.guardarUsuario(usuario);
            mostrarExito("Usuario actualizado correctamente.");
        } catch (DatosInvalidosException e) {
            mostrarError(e.getMessage());
        } catch (Exception e) {
            mostrarError("Error al actualizar usuario: " + e.getMessage());
        }
    }

    // Listar todos los usuarios
    public List<Usuario> obtenerUsuarios() {
        try {
            return usuarioService.listarUsuarios();
        } catch (Exception e) {
            mostrarError("Error al obtener usuarios.");
            return null;
        }
    }

    // Eliminar usuario
    public void eliminarUsuario(Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            mostrarExito("Usuario eliminado correctamente.");
        } catch (Exception e) {
            mostrarError("Error al eliminar usuario.");
        }
    }

    //-------------------- Controlador de la tabla ------------------------
    public TableModel cargarTabla() {
        String[] columnas = {"ID", "Nombre", "Cédula", "Usuario", "Rol", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        try {
            List<Usuario> lista = usuarioService.listarUsuarios();

            for (Usuario u : lista) {
                modelo.addRow(new Object[]{
                        u.getId(),
                        u.getNombre(),
                        u.getCedula(),
                        u.getUsername(),
                        u.getRol(),
                        u.isActivo() ? "ACTIVO" : "DESACTIVADO"
                });
            }
        } catch (Exception e) {
            mostrarError("Error al cargar la tabla");
        }

        return modelo;
    }

    //-------- METODOS PARA BUSCAR USUARIO -----------
    public TableModel buscarPorCedula(String cedula) {
        String[] columnas = {"ID", "Nombre", "Cédula", "Usuario", "Rol", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        try {
            Usuario u = usuarioService.buscarPorCedula(cedula);

            if (u != null) {
                modelo.addRow(new Object[]{
                        u.getId(),
                        u.getNombre(),
                        u.getCedula(),
                        u.getUsername(),
                        u.getRol(),
                        u.isActivo() ? "ACTIVO" : "DESACTIVADO"
                });
            } else {
                mostrarError("Usuario no encontrado");
            }

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }

        return modelo;
    }

    // Buscar usuario por el id
    public Usuario buscarUsuarioId(Long id){
        return usuarioService.buscarUsuarioId(id);
    }


    // ------------Metodos Utiles---------------
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(null,
                mensaje,
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
