package ec.edu.sistemalicencias.service;

import ec.edu.sistemalicencias.dao.UsuarioDAO;
import ec.edu.sistemalicencias.model.entities.Usuario;
import ec.edu.sistemalicencias.model.exceptions.BaseDatosException;
import ec.edu.sistemalicencias.model.exceptions.DatosInvalidosException;

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

            if (!usuario.isActivo()){
                throw new DatosInvalidosException("El usuario esta inactivo.");
            }

            return usuario;
        } catch (BaseDatosException e){
            throw e;
        }
    }

    public void guardarUsuario(Usuario usuario) throws DatosInvalidosException, BaseDatosException{

        //Validaciones para la creacion o actualizacion de un usuario
        if (usuario == null){
            throw new DatosInvalidosException("El usuario no puede ser nulo.");
        }
        
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()){
            throw new DatosInvalidosException("Debe ingresar el nombre.");
        }

        if (usuario.getCedula() == null || usuario.getCedula().trim().isEmpty()){
            throw new DatosInvalidosException("Debe ingresar el número de cedula.");
        }
        //Validación para el número de digitos para cedula
        if (!usuario.getCedula().matches("\\d{10}")){
            throw new DatosInvalidosException("La cédula debe tener 10 dígitos");
        }

        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()){
            throw new DatosInvalidosException("Debe colocar un nombre de usuario.");
        }

        if (usuario.getRol() == null || usuario.getRol().trim().isEmpty()){
            throw new DatosInvalidosException("Seleccione un rol para el usuario.");
        }

        //Código para que no se repita el nombre de usuario con mayúsculas
        usuario.setUsername(usuario.getUsername().trim().toLowerCase());

        try {
            Usuario usuarioExistente = usuarioDAO.buscarPorUsername(usuario.getUsername());

            //Si el numero de ID es nulo se crea un nuevo usuario
            if (usuario.getId() == null){
                if (usuarioExistente != null){
                    throw new DatosInvalidosException("El usuario ya existe.");
                }

                usuario.setPassword(generarPassword());
                usuario.setActivo(true);

            //Si se actualiza algun dato de los usuarios registrados
            } else {
                if (usuarioExistente != null && !usuarioExistente.getId().equals(usuario.getId())){
                    throw new DatosInvalidosException("El nombre de usuario ya existe.");
                }
            }

            usuarioDAO.insertarUsuario(usuario);

        } catch (BaseDatosException e){
            throw e;
        }
    }

    public List<Usuario> listarUsuarios() throws BaseDatosException{
        try{
            return usuarioDAO.mostrarUsuarios();
        } catch (BaseDatosException e){
            throw e;
        }
    }

    public void eliminarUsuario(Long id) throws BaseDatosException{
        try{
            usuarioDAO.eliminar(id);
        } catch (BaseDatosException e){
            throw e;
        }
    }

    private String generarPassword(){

        return UUID.randomUUID().toString().substring(0, 8);
    }

    public Usuario buscarPorCedula(String cedula)
            throws DatosInvalidosException, BaseDatosException {

        if (cedula == null || cedula.trim().isEmpty()) {
            throw new DatosInvalidosException("Debe ingresar una cédula.");
        }

        if (!cedula.matches("\\d{10}")) {
            throw new DatosInvalidosException("La cédula debe tener 10 dígitos.");
        }

        try {
            return usuarioDAO.buscarPorCedula(cedula);
        } catch (BaseDatosException e) {
            throw e;
        }
    }

}

