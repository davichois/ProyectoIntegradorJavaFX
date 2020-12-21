package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Zona;

/**
 * @author davichois
 */
public class ZonaDialogController implements Initializable {

    @FXML
    private TextField txtDepartamento;
    @FXML
    private TextField txtProvincia;
    @FXML
    private TextField txtDistrito;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnSalir;

    private Zona zona;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void guardar(MouseEvent event) {
        String departamento = this.txtDepartamento.getText();
        String provicia = this.txtProvincia.getText();
        String distrito = this.txtDistrito.getText();

        Zona z = new Zona(departamento, provicia, distrito);

        this.zona = z;

        Stage stage = (Stage) this.btnGuardar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void salir(MouseEvent event) {
        this.zona = null;
        Stage stage = (Stage) this.btnSalir.getScene().getWindow();
        stage.close();

    }

    public Zona getZona() {
        return zona;
    }
}
