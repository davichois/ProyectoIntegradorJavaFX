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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    private void busquedaProducto(KeyEvent event) {
    }

    @FXML
    private void agregar(MouseEvent event) {
    }

    @FXML
    private void salir(MouseEvent event) {
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
