package ec.edu.sistemalicencias.config;

import ec.edu.sistemalicencias.model.exceptions.BaseDatosException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de establecer conexión con la base de datos PostgresSQL
 * integrada en supabase.
 */
public class DatabaseConfig {

    // Instancia única (Singleton)
    private static DatabaseConfig instancia;

    //Driver JDBC de PostgresSQL
    private static final String DRIVER = "org.postgresql.Driver";

    //URL de conexión dada por Supabase
    private static final String URL = "jdbc:postgresql://db.hzbqodboqmrkwyxbzqma.supabase.co:5432/postgres?sslmode=require";

    //Usuario de la BD por defecto
    private static final String USER = "postgres";

    //Contraseña establecida para la BD
    private static final String PASSWORD = "SistemaLicen123";

    /**
     * Constructor privado para evitar la creación de múltiples isntancias.
     * Se carga el driver JDBC de PostgresSQL
     */
    private DatabaseConfig() {
        try {
            Class.forName(DRIVER);
            System.out.println("Driver PostgreSQL cargado correctamente.");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se pudo cargar el driver de PostgreSQL.");
            e.printStackTrace();
        }
    }

    /**
     * Retorna la instancia unica de DatabseConfig
     * Si no existe, este lo crea.
     */
    public static synchronized DatabaseConfig getInstance() {
        if (instancia == null) {
            instancia = new DatabaseConfig();
        }
        return instancia;
    }

    /**
     * Obtiene una conexión activa hacia la BD en Supabase.
     * @return Connection conexión a PostgresSQL
     * @throws SQLException
     */
    public Connection obtenerConexion() throws BaseDatosException{
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e){
            throw new BaseDatosException("Error al conectar con la BD en Supabase.", e);
        }
    }

    /**
     * Cierra una conexión de forma segura
     * @param conexion Conexión a cerrar
     */
    public void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    /**
     * Verifica si la conexión a la base de datos es válida
     * @return true si la conexión es exitosa, false en caso contrario
     */
    public boolean verificarConexion() {
        try (Connection conn = obtenerConexion()) {
            return conn != null && !conn.isClosed();
        } catch (Exception e) {
            System.err.println("Error al verificar conexión: " + e.getMessage());
            return false;
        }
    }

}
