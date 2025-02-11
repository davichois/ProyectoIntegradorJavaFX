package controlador;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Cliente;
import utils.db.ConnectionDB;

/**
 * @author davichois
 */
public class VentaDialogUsuarioController implements Initializable {

    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableColumn colID;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellido;
    @FXML
    private TableColumn colDni;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private TableColumn colIDZona;
    @FXML
    private TableColumn colSexo;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnSalir;
    @FXML
    private TableView<Cliente> tblUsuario;

    private ObservableList<Cliente> usuario;

    private ObservableList<Cliente> usuarioFiltrado;

    private Cliente c;

    private static ConnectionDB db = new ConnectionDB();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuario = FXCollections.observableArrayList();
        usuarioFiltrado = FXCollections.observableArrayList();

        this.tblUsuario.setItems(usuario);

        this.colID.setCellValueFactory(new PropertyValueFactory("idCliente"));
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colApellido.setCellValueFactory(new PropertyValueFactory("apellido"));
        this.colDni.setCellValueFactory(new PropertyValueFactory("dni"));
        this.colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        this.colIDZona.setCellValueFactory(new PropertyValueFactory("idZona"));
        this.colSexo.setCellValueFactory(new PropertyValueFactory("sexo"));

        try {
            traerDatos();
        } catch (SQLException ex) {
            Logger.getLogger(VentaDialogUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void agregar(MouseEvent event) {
        Cliente c = this.tblUsuario.getSelectionModel().getSelectedItem();
        if (c == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println(alert);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar a un producto.");
            alert.showAndWait();
        } else {
            Cliente cliente = new Cliente(c.getIdCliente(), c.getNombre(), c.getApellido(), c.getDni(), c.getTelefono(), c.getIdZona(), c.getSexo());
            this.c = cliente;
        }

        Stage stage = (Stage) this.btnAgregar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void salir(MouseEvent event) {
        this.c = null;
        Stage stage = (Stage) this.btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void busquedaProducto(KeyEvent event) {
        String busquedaC = this.txtBusqueda.getText();
        if (busquedaC.isEmpty()) {
            this.tblUsuario.setItems(usuario);
        } else {
            this.usuarioFiltrado.clear();
            for (Cliente p : this.usuario) {
                if (p.getNombre().toLowerCase().contains(busquedaC.toLowerCase())) {
                    this.usuarioFiltrado.add(p);
                }
            }
            this.tblUsuario.setItems(usuarioFiltrado);
            this.tblUsuario.refresh();
        }
    }

    private void traerDatos() throws SQLException {
        Connection conex = db.openConexion();
        PreparedStatement ps = conex.prepareStatement("select * from cliente");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Cliente newCliente = new Cliente(rs.getInt("idCliente"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("dni"), rs.getInt("telefono"), String.valueOf(rs.getInt("idZona")), rs.getString("sexo"));
            this.usuario.add(newCliente);
            this.tblUsuario.refresh();
        }
    }

    public Cliente getC() {
        return c;
    }

}
