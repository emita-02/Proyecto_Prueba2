package ec.edu.sistemalicencias.dao;

import ec.edu.sistemalicencias.config.DatabaseConfig;
import ec.edu.sistemalicencias.model.entities.Usuario;
import ec.edu.sistemalicencias.model.exceptions.BaseDatosException;
import ec.edu.sistemalicencias.model.interfaces.Persistible;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements Persistible<Usuario> {

    private final DatabaseConfig dbConfig;

    public UsuarioDAO(){
        this.dbConfig = DatabaseConfig.getInstance();
    }

    @Override
    public Long guardar(Usuario usuario) throws BaseDatosException {
        if (usuario.getId() == null){
            return insertarUsuario(usuario);
        } else {
            actualizarUsuario(usuario);
            return usuario.getId();
        }
    }

    public Long insertarUsuario(Usuario usuario) throws BaseDatosException{
        String sql = "INSERT INTO usuarios(nombre, cedula, username, password, rol, activo) VALUES(?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dbConfig.obtenerConexion();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCedula());
            stmt.setString(3, usuario.getUsername());
            stmt.setString(4, usuario.getPassword());
            stmt.setString(5, usuario.getRol());
            stmt.setBoolean(6, usuario.isActivo());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0){
                throw new BaseDatosException("No se puedo insertar el usuario.");
            }

            //Obtener el ID  generado
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                throw new BaseDatosException("No se pudo obtener el ID generado");
            }
        } catch (SQLException e){
            throw new BaseDatosException("Error al insertar usuario: "+e.getMessage(), e);

        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
    }

    public void actualizarUsuario(Usuario usuario) throws BaseDatosException {
        String sql = "UPDATE usuarios SET nombre = ?, cedula = ?, username = ?, password = ?, rol = ?, activo = ? " +
                "WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbConfig.obtenerConexion();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCedula());
            stmt.setString(3, usuario.getUsername());
            stmt.setString(4, usuario.getPassword());
            stmt.setString(5, usuario.getRol());
            stmt.setBoolean(6, usuario.isActivo());
            stmt.setLong(7, usuario.getId());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0) {
                throw new BaseDatosException("No se encontró el usuario con ID: " + usuario.getId());
            }
        } catch (SQLException e){
            throw new BaseDatosException("Error al actualizar usuario: "+e.getMessage(), e);
        } finally {
            cerrarRecursos(conn, stmt, null);
        }
    }

    @Override
    public Usuario buscarPorId(Long id) throws BaseDatosException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dbConfig.obtenerConexion();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);

            rs = stmt.executeQuery();

            if (rs.next()){
                return mapearUsuario(rs);
            }

            return null;
        } catch (SQLException e){
            throw new BaseDatosException("Error al buscar usuario por ID: "+e.getMessage(), e);
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
    }

    public Usuario buscarPorCedula(String cedula) throws BaseDatosException {
        String sql = "SELECT * FROM usuarios WHERE cedula = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dbConfig.obtenerConexion();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cedula);

            rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearUsuario(rs);
            }

            return null;

        } catch (SQLException e) {
            throw new BaseDatosException("Error al buscar usuario por cédula: " + e.getMessage(), e);
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
    }

    public List<Usuario> buscarPorUsername(String username) throws BaseDatosException {
        String sql = "SELECT * FROM usuarios WHERE nombre = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> usuarios = new ArrayList<>();

        try {
            conn = dbConfig.obtenerConexion();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            rs = stmt.executeQuery();

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }

            return usuarios;

        } catch (SQLException e) {
            throw new BaseDatosException("Error al buscar usuarios: " + e.getMessage(), e);
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
    }

    public List<Usuario> mostrarUsuarios() throws BaseDatosException{
        String sql = "SELECT * FROM usuarios";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> listaUsuarios = new ArrayList<>();


        try {
            conn = dbConfig.obtenerConexion();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()){
                listaUsuarios.add(mapearUsuario(rs));
            }

            return  listaUsuarios;
        } catch (SQLException e){
            throw new BaseDatosException("Error al obtener usuarios: "+e.getMessage(), e);
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
    }

    @Override
    public boolean eliminar(Long id) throws BaseDatosException {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try{
            conn = dbConfig.obtenerConexion();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);

            int filarAfectadas = stmt.executeUpdate();
            return filarAfectadas > 0;
        } catch (SQLException e){
            throw new BaseDatosException("Error al eliminar usuario: " + e.getMessage(), e);
        } finally {
            cerrarRecursos(conn, stmt, null);
        }
    }

    public Usuario mapearUsuario(ResultSet rs) throws SQLException{
        Usuario u = new Usuario();
        u.setId(rs.getLong("id"));
        u.setNombre(rs.getString("nombre"));
        u.setCedula(rs.getString("cedula"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setRol(rs.getString("rol"));
        u.setActivo(rs.getBoolean("activo"));

        return u;
    }

    public void cerrarRecursos(Connection conn, Statement stmt, ResultSet rs){
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e){
            System.out.println("Error al cerrar recursos: " + e.getMessage());
        }
    }

}

