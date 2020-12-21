package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * @author davichois
 */
public class VentaControllerGeneral implements Initializable {

    @FXML
    private TableView<?> tblVenta;
    @FXML
    private TableColumn<?, ?> colID;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnVender;
    @FXML
    private TextField txtDni;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtZona;
    @FXML
    private TextField txtVendedor;
    @FXML
    private ComboBox<?> cbModoPago;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void agregar(ActionEvent event) {
    }

    @FXML
    private void eliminar(ActionEvent event) {
    }

    @FXML
    private void vender(ActionEvent event) {
    }

    @FXML
    private void buscarUsuario(KeyEvent event) {
        System.out.println("Hola mundo");
    }

}
