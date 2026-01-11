package ec.edu.sistemalicencias.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import ec.edu.sistemalicencias.controller.UsuarioController;
import ec.edu.sistemalicencias.model.entities.Usuario;
import ec.edu.sistemalicencias.model.exceptions.BaseDatosException;
import ec.edu.sistemalicencias.model.exceptions.DatosInvalidosException;
import ec.edu.sistemalicencias.util.PasswordUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class GestionUsuarios extends JFrame {

    private JPanel panelAdmin;
    private JPanel panelBotones;
    private JPanel paneBusqueda;
    private JPanel panelFormulario;
    private JTextField txtCedula;
    private JTextField txtContraseña;
    private JTable tablaUsuarios;
    private JButton btnBuscar;
    private JTextField txtCEDULA;
    private JCheckBox cbAdmin;
    private JCheckBox cbAnalista;
    private JCheckBox cbActivo;
    private JCheckBox cbInactivo;
    private JButton btnCrear;
    private JButton btnActualizar;
    private JButton btnLimpiar;
    private JLabel lblNombre;
    private JLabel lblUserName;
    private JLabel lblCedula;
    private JLabel lblContraseña;
    private JTextField txtNombre;
    private JTextField txtUserName;
    private JLabel lblCEDULA;
    private JLabel lblRol;
    private JLabel lblEstado;
    private JButton btnRegresar;
    private JButton btnContraseña;


    private UsuarioController controller;
    private Usuario usuarioSeleccionado;
    private ButtonGroup grupoRol;
    private ButtonGroup grupoEstado;

    //-------- Constructor de la Vista ---------
    public GestionUsuarios(UsuarioController controller) {
        this.controller = controller;

        $$$setupUI$$$();
        setTitle("Gestión de Usuarios");
        setContentPane(panelAdmin);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();

        configurarGrupos();
        eventos();
        cargarTabla();
    }

    // ------- Configuracion de los Eventos de los componentes---
    private void eventos() {
        btnCrear.addActionListener(e -> crearUsuario());
        btnActualizar.addActionListener(e -> actualizarUsuario());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnBuscar.addActionListener(e -> buscarOCargar());
        btnContraseña.addActionListener(e -> generarPassword());
        btnRegresar.addActionListener(e -> dispose());

        //Seleccionar el usuario en la tabla para modificarlo
        tablaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = tablaUsuarios.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    cargarUsuario(filaSeleccionada);
                }
            }
        });
    }

    // ---------- LOGICA -----------
    private void crearUsuario() {
        try {
            controller.crearUsuario(obtenerDatos());
            JOptionPane.showMessageDialog(this, "Usuario creado correctamente");
            cargarTabla();
            limpiarFormulario();
        } catch (DatosInvalidosException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Datos inválidos", JOptionPane.ERROR_MESSAGE);
        } catch (BaseDatosException e) {
            JOptionPane.showMessageDialog(this, "Error en base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarUsuario() {
        try {
            controller.actualizarUsuario(obtenerDatos());
            JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente");
            cargarTabla();
        } catch (DatosInvalidosException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Datos inválidos", JOptionPane.ERROR_MESSAGE);
        } catch (BaseDatosException e) {
            JOptionPane.showMessageDialog(this, "Error en base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTabla() {
        try {
            tablaUsuarios.setModel(controller.cargarTabla());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void buscarOCargar() {
        String cedula = txtCEDULA.getText().trim();
        try {
            if (cedula.isEmpty()) {
                cargarTabla();
            } else {
                tablaUsuarios.setModel(controller.cargarTabla());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado");
        }
    }

    private void generarPassword() {
        String password = PasswordUtil.generar(8);
        txtContraseña.setText(password);
    }

    // Auxiliares
    private Usuario obtenerDatos() {
        Usuario u;

        if (usuarioSeleccionado != null) {
            u = usuarioSeleccionado; //Actualizar usuario
        } else {
            u = new Usuario(); // Crear Usuario
        }

        u.setNombre(txtNombre.getText());
        u.setCedula(txtCedula.getText());
        u.setUsername(txtUserName.getText());
        if (!txtContraseña.getText().trim().isEmpty()) {
            u.setPassword(txtContraseña.getText());
        }

        if (cbAdmin.isSelected()) {
            u.setRol("ADMINISTRADOR");
        } else {
            u.setRol("ANALISTA");
        }

        u.setActivo(cbActivo.isSelected());

        return u;
    }

    private void limpiarFormulario() {
        usuarioSeleccionado = null;

        txtNombre.setText("");
        txtCedula.setText("");
        txtUserName.setText("");
        txtContraseña.setText("");
        grupoRol.clearSelection();
        grupoEstado.clearSelection();

        tablaUsuarios.clearSelection();
    }

    private void configurarGrupos() {
        grupoRol = new ButtonGroup();
        grupoRol.add(cbAdmin);
        grupoRol.add(cbAnalista);

        grupoEstado = new ButtonGroup();
        grupoEstado.add(cbActivo);
        grupoEstado.add(cbInactivo);
    }

    private void cargarUsuario(int fila) {
        try {
            TableModel modelo = tablaUsuarios.getModel();
            Long id = (Long) modelo.getValueAt(fila, 0);
            usuarioSeleccionado = controller.buscarUsuarioId(id);

            if (usuarioSeleccionado != null) {
                txtCedula.setText(usuarioSeleccionado.getCedula());
                txtNombre.setText(usuarioSeleccionado.getNombre());
                txtUserName.setText(usuarioSeleccionado.getUsername());
                //Seleccionar rol
                String rol = usuarioSeleccionado.getRol();
                cbAdmin.setSelected("ADMINISTRADOR".equalsIgnoreCase(rol));
                cbAnalista.setSelected("ANALISTA".equalsIgnoreCase(rol));
                //Seleccionar estado
                boolean activo = usuarioSeleccionado.isActivo();
                cbActivo.setSelected(activo);
                cbInactivo.setSelected(!activo);
            }
        } catch (DatosInvalidosException e) {
            controller.mostrarError("Error al cargar datos del usuario: " + e.getMessage());
        }
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelAdmin = new JPanel();
        panelAdmin.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayoutManager(4, 5, new Insets(10, 10, 10, 10), -1, -1));
        panelAdmin.add(panelFormulario, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panelFormulario.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Datos de Usuario", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        lblNombre = new JLabel();
        lblNombre.setText("Nombre:");
        panelFormulario.add(lblNombre, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtCedula = new JTextField();
        panelFormulario.add(txtCedula, new GridConstraints(0, 3, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtUserName = new JTextField();
        panelFormulario.add(txtUserName, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblContraseña = new JLabel();
        lblContraseña.setText("Contraseña:");
        panelFormulario.add(lblContraseña, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtContraseña = new JTextField();
        panelFormulario.add(txtContraseña, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        btnContraseña = new JButton();
        btnContraseña.setText("Generar Contraseña");
        panelFormulario.add(btnContraseña, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblRol = new JLabel();
        lblRol.setText("Rol:");
        panelFormulario.add(lblRol, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbAdmin = new JCheckBox();
        cbAdmin.setText("ADMINISTRADOR");
        panelFormulario.add(cbAdmin, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbAnalista = new JCheckBox();
        cbAnalista.setText("ANALISTA");
        panelFormulario.add(cbAnalista, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblEstado = new JLabel();
        lblEstado.setText("Estado:");
        panelFormulario.add(lblEstado, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbActivo = new JCheckBox();
        cbActivo.setText("ACTIVO");
        panelFormulario.add(cbActivo, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbInactivo = new JCheckBox();
        cbInactivo.setText("INACTIVO");
        panelFormulario.add(cbInactivo, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblCedula = new JLabel();
        lblCedula.setText("Cedula:");
        panelFormulario.add(lblCedula, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblUserName = new JLabel();
        lblUserName.setText("UserName:");
        panelFormulario.add(lblUserName, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panelAdmin.add(scrollPane1, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setBorder(BorderFactory.createTitledBorder(null, "Usuarios Registrados", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        tablaUsuarios = new JTable();
        scrollPane1.setViewportView(tablaUsuarios);
        panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panelAdmin.add(panelBotones, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnCrear = new JButton();
        btnCrear.setText("Crear");
        panelBotones.add(btnCrear, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnActualizar = new JButton();
        btnActualizar.setText("Actualizar");
        panelBotones.add(btnActualizar, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnLimpiar = new JButton();
        btnLimpiar.setText("Limpiar");
        panelBotones.add(btnLimpiar, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnRegresar = new JButton();
        btnRegresar.setText("Regresar");
        panelBotones.add(btnRegresar, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        paneBusqueda = new JPanel();
        paneBusqueda.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        panelAdmin.add(paneBusqueda, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnBuscar = new JButton();
        btnBuscar.setText("Buscar");
        paneBusqueda.add(btnBuscar, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        paneBusqueda.add(spacer1, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        txtCEDULA = new JTextField();
        paneBusqueda.add(txtCEDULA, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblCEDULA = new JLabel();
        lblCEDULA.setText("\uD83D\uDD0E  CEDULA:");
        paneBusqueda.add(lblCEDULA, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        paneBusqueda.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelAdmin;
    }

}
