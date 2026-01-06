package ec.edu.sistemalicencias.model.entities;

public class Usuario {
    private Long id;
    private String nombre;
    private String cedula;
    private String username;
    private String password;
    private String rol; //administrados o analista
    private boolean activo;

    public Usuario() {
    }

    public Usuario(Long id, String username, String password, String rol, boolean estado) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.activo = estado;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
}
