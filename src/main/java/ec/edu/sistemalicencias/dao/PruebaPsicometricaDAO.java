package ec.edu.sistemalicencias.dao;

import ec.edu.sistemalicencias.config.DatabaseConfig;
import ec.edu.sistemalicencias.model.entities.PruebaPsicometrica;
import ec.edu.sistemalicencias.model.exceptions.BaseDatosException;
import ec.edu.sistemalicencias.model.interfaces.Persistible;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PruebaPsicometricaDAO implements Persistible<PruebaPsicometrica> {

    private final DatabaseConfig dbConfig;

    public PruebaPsicometricaDAO() {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    @Override
    public Long guardar(PruebaPsicometrica prueba) throws BaseDatosException {
        if (prueba.getId() == null) {
            return insertar(prueba);
        } else {
            actualizar(prueba);
            return prueba.getId();
        }
    }

    // ================= INSERT =================
    private Long insertar(PruebaPsicometrica prueba) throws BaseDatosException {

        if (prueba.getFechaRealizacion() == null) {
            prueba.setFechaRealizacion(LocalDateTime.now());
        }

        String sql = "INSERT INTO pruebas_psicometricas (" +
                "conductor_id, fecha_realizacion, nota_reaccion, nota_atencion, " +
                "nota_coordinacion, nota_percepcion, nota_psicologica, observaciones) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConfig.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, prueba.getConductorId());
            stmt.setTimestamp(2, Timestamp.valueOf(prueba.getFechaRealizacion()));
            stmt.setDouble(3, prueba.getNotaReaccion());
            stmt.setDouble(4, prueba.getNotaAtencion());
            stmt.setDouble(5, prueba.getNotaCoordinacion());
            stmt.setDouble(6, prueba.getNotaPercepcion());
            stmt.setDouble(7, prueba.getNotaPsicologica());
            stmt.setString(8, prueba.getObservaciones());

            int filas = stmt.executeUpdate();
            if (filas == 0) {
                throw new BaseDatosException("No se pudo insertar la prueba psicométrica");
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }

            throw new BaseDatosException("No se pudo obtener el ID generado");

        } catch (SQLException e) {
            throw new BaseDatosException("Error al insertar prueba psicométrica: " + e.getMessage(), e);
        }
    }

    // ================= UPDATE =================
    private void actualizar(PruebaPsicometrica prueba) throws BaseDatosException {

        String sql = "UPDATE pruebas_psicometricas SET " +
                "nota_reaccion = ?, nota_atencion = ?, nota_coordinacion = ?, " +
                "nota_percepcion = ?, nota_psicologica = ?, observaciones = ? " +
                "WHERE id = ?";

        try (Connection conn = dbConfig.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, prueba.getNotaReaccion());
            stmt.setDouble(2, prueba.getNotaAtencion());
            stmt.setDouble(3, prueba.getNotaCoordinacion());
            stmt.setDouble(4, prueba.getNotaPercepcion());
            stmt.setDouble(5, prueba.getNotaPsicologica());
            stmt.setString(6, prueba.getObservaciones());
            stmt.setLong(7, prueba.getId());

            if (stmt.executeUpdate() == 0) {
                throw new BaseDatosException("No se encontró la prueba con ID: " + prueba.getId());
            }

        } catch (SQLException e) {
            throw new BaseDatosException("Error al actualizar prueba psicométrica: " + e.getMessage(), e);
        }
    }

    // ================= SELECT =================
    @Override
    public PruebaPsicometrica buscarPorId(Long id) throws BaseDatosException {

        String sql = "SELECT * FROM pruebas_psicometricas WHERE id = ?";

        try (Connection conn = dbConfig.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new BaseDatosException("Error al buscar prueba por ID: " + e.getMessage(), e);
        }
    }


    public List<PruebaPsicometrica> buscarPorConductor(Long conductorId) throws BaseDatosException {

        String sql = "SELECT * FROM pruebas_psicometricas " +
                "WHERE conductor_id = ? ORDER BY fecha_realizacion DESC";

        List<PruebaPsicometrica> lista = new ArrayList<>();

        try (Connection conn = dbConfig.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, conductorId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }

            return lista;

        } catch (SQLException e) {
            throw new BaseDatosException("Error al buscar pruebas por conductor: " + e.getMessage(), e);
        }
    }
    public PruebaPsicometrica obtenerUltimaPruebaAprobada(Long conductorId)
            throws BaseDatosException {

        String sql = "SELECT * FROM pruebas_psicometricas " +
                "WHERE conductor_id = ? " +
                "ORDER BY fecha_realizacion DESC LIMIT 1";

        try (Connection conn = dbConfig.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, conductorId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PruebaPsicometrica prueba = mapear(rs);

                    // ✅ VALIDACIÓN CORRECTA
                    if (prueba.estaAprobado()) {
                        return prueba;
                    }
                }
            }

            return null;

        } catch (SQLException e) {
            throw new BaseDatosException(
                    "Error al obtener última prueba aprobada: " + e.getMessage(), e
            );
        }
    }



    // ================= DELETE =================
    @Override
    public boolean eliminar(Long id) throws BaseDatosException {

        String sql = "DELETE FROM pruebas_psicometricas WHERE id = ?";

        try (Connection conn = dbConfig.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new BaseDatosException("Error al eliminar prueba: " + e.getMessage(), e);
        }
    }

    // ================= MAPPER =================
    private PruebaPsicometrica mapear(ResultSet rs) throws SQLException {

        PruebaPsicometrica p = new PruebaPsicometrica();

        p.setId(rs.getLong("id"));
        p.setConductorId(rs.getLong("conductor_id"));
        p.setNotaReaccion(rs.getDouble("nota_reaccion"));
        p.setNotaAtencion(rs.getDouble("nota_atencion"));
        p.setNotaCoordinacion(rs.getDouble("nota_coordinacion"));
        p.setNotaPercepcion(rs.getDouble("nota_percepcion"));
        p.setNotaPsicologica(rs.getDouble("nota_psicologica"));
        p.setObservaciones(rs.getString("observaciones"));

        Timestamp ts = rs.getTimestamp("fecha_realizacion");
        if (ts != null) {
            p.setFechaRealizacion(ts.toLocalDateTime());
        }

        return p;
    }
}
