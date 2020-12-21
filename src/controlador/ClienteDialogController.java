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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Cliente;
import modelo.Zona;
import utils.db.ConnectionDB;

/**
 * @author davichois
 */
public class ClienteDialogController implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtDni;
    @FXML
    private TextField txtTelefono;
    @FXML
    private ComboBox cbZona;
    @FXML
    private ComboBox cbSexo;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnSalir;

    private Cliente cliente;

    private ObservableList zona;

    private ObservableList sexo;

    private static ConnectionDB db = new ConnectionDB();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        zona = FXCollections.observableArrayList();
        sexo = FXCollections.observableArrayList("Femenino", "Masculino");
        this.cbSexo.setItems(sexo);
        try {
            initZona();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDialogController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void guardar(MouseEvent event) {
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        String dni = txtDni.getText();
        int telefono = Integer.parseInt(txtTelefono.getText());
        String zona = String.valueOf(this.cbZona.getValue());
        String sexo = String.valueOf(this.cbSexo.getValue());
        
        //System.out.println(zona.split(":")[0]);

        Cliente c = new Cliente(nombre, apellidos, dni, telefono, zona.split(":")[0], sexo);

        this.cliente = c;

        Stage stage = (Stage) this.btnGuardar.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void salir(MouseEvent event) {
        this.cliente = null;
        Stage stage = (Stage) this.btnSalir.getScene().getWindow();
        stage.close();
    }

    private void initZona() throws SQLException {
        Connection conex = db.openConexion();
        PreparedStatement ps = conex.prepareStatement("select * from zona");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Zona newZona = new Zona(rs.getInt("idZona"), rs.getString("departamento"), rs.getString("provincia"), rs.getString("distrito"));
            this.zona.add(newZona.getIdZona() + ": " + newZona.getDepartamento() + " - " + newZona.getProvincia() + " - " + newZona.getDistrito());
            this.cbZona.setItems(zona);
        }
        /*String nombre = "1Davidprada";
        System.out.println(nombre.charAt(0));*/
    }

    public Cliente getCliente() {
        return cliente;
    }

}
