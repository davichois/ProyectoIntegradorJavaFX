package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Vendedor;

/**
 * @author davichois
 */
public class VendedoresDialogController implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtEdad;
    @FXML
    private TextField txtDni;
    @FXML
    private TextField txtTelefono;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnSalir;

    private Vendedor vendedor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void guardar(MouseEvent event) {
        String nombre = this.txtNombre.getText();
        int edad = Integer.parseInt(this.txtEdad.getText());
        int dni = Integer.parseInt(this.txtDni.getText());
        int telefono = Integer.parseInt(this.txtTelefono.getText());

        Vendedor v = new Vendedor(nombre, edad, dni, telefono);

        this.vendedor = v;

        Stage stage = (Stage) this.btnGuardar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void salir(MouseEvent event) {
        this.vendedor = null;
        Stage stage = (Stage) this.btnSalir.getScene().getWindow();
        stage.close();
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

}
