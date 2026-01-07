package ec.edu.sistemalicencias.service;

import ec.edu.sistemalicencias.dao.UsuarioDAO;
import ec.edu.sistemalicencias.model.entities.Usuario;
import ec.edu.sistemalicencias.model.exceptions.BaseDatosException;
import ec.edu.sistemalicencias.model.exceptions.DatosInvalidosException;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class UsuarioService{

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario autenticar(String username, String password) throws DatosInvalidosException, BaseDatosException {
        if (username == null || username.trim().isEmpty()){
            throw new DatosInvalidosException("Debe ingresar el nombre de usuario.");
        }

        if (password == null || password.isEmpty()){
            throw new DatosInvalidosException("Debe ingresar la contraseña.");
        }

        try {
            Usuario usuario = usuarioDAO.buscarPorUsername(username);

            if (usuario == null){
                throw new DatosInvalidosException("El usuario no existe.");
            }
            if (!usuario.getPassword().equals(password)){
                throw new DatosInvalidosException("La contraseña es incorrecta.");
            }

            return usuario;
        } catch (BaseDatosException e){
            throw e;
        }
    }

    public void crearAnalista(String username) throws DatosInvalidosException, BaseDatosException{
        if (username == null || username.trim().isEmpty()){
            throw new DatosInvalidosException("Debe ingresar un nombre de usuario.");
        }

        try {
            Usuario usuarioExistente = usuarioDAO.buscarPorUsername(username);

            if (usuarioExistente != null){
                throw new DatosInvalidosException("El usuario ya existe.");
            }

            Usuario usuario = new Usuario();
            usuario.setUsername(username);
            usuario.setPassword(generarPassword());
            usuario.setRol("ANALISTA");
            usuario.setActivo(true);

            usuarioDAO.insertarUsuario(usuario);
        } catch (SQLException e){
            throw new BaseDatosException("Error al crear el usuario.");
        }
    }

    public List<Usuario> listarUsuarios() throws BaseDatosException{
        try{
            return usuarioDAO.mostrarUsuarios();
        } catch (SQLException e){
            throw new BaseDatosException("Error al listar los usuarios analista.");
        }
    }

    public void eliminarUsuario(Long id) throws BaseDatosException{
        try{
            usuarioDAO.eliminar(id);
        } catch (SQLException e){
            throw new BaseDatosException("Error al eliminar el usuario analista.");
        }
    }

    private String generarPassword(){
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
