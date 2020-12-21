package controlador;

import java.io.IOException;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Cliente;
import utils.db.ConnectionDB;

/**
 * @author davichois
 */
public class ClientesControllerGeneral implements Initializable {

    @FXML
    private TableView<?> tblClientes;
    @FXML
    private TableColumn<?, ?> colID;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colApellido;
    @FXML
    private TableColumn<?, ?> colDNI;
    @FXML
    private TableColumn<?, ?> colTelefono;
    @FXML
    private TableColumn<?, ?> colZona;
    @FXML
    private TableColumn<?, ?> colSexo;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private TextField txtBusqueda;

    private ObservableList cliente;

    private static ConnectionDB db = new ConnectionDB();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cliente = FXCollections.observableArrayList();
        this.tblClientes.setItems(cliente);

        this.colID.setCellValueFactory(new PropertyValueFactory("idCliente"));
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colApellido.setCellValueFactory(new PropertyValueFactory("apellido"));
        this.colDNI.setCellValueFactory(new PropertyValueFactory("dni"));
        this.colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        this.colZona.setCellValueFactory(new PropertyValueFactory("idZona"));
        this.colSexo.setCellValueFactory(new PropertyValueFactory("sexo"));

        try {
            traerDatos();
        } catch (SQLException ex) {
            Logger.getLogger(ClientesControllerGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void agregar(ActionEvent event) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/ClienteDialogVista.fxml"));

            //Cargamos al padre
            Parent root = loader.load();

            ClienteDialogController controlador = loader.getController();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Agregar Cliente");
            stage.setScene(scene);
            stage.showAndWait();

            Cliente c = controlador.getCliente();


            if (c != null) {
                Connection conex = db.openConexion();
                PreparedStatement ps = conex.prepareStatement("insert into cliente(nombre, apellido, dni, telefono, idZona, sexo) values(?,?,?,?,?,?)");
                ps.setString(1, c.getNombre());
                ps.setString(2, c.getApellido());
                ps.setString(3, c.getDni());
                ps.setInt(4, c.getTelefono());
                ps.setString(5, c.getIdZona());
                ps.setString(6, c.getSexo());

                int resultado = ps.executeUpdate();

                if (resultado > 0) {
                    System.out.println("Registro insertado correctamente...");
                } else {
                    System.out.println("Error al registrar");
                }
                this.cliente.clear();
                traerDatos();
                db.cerrarConnection();
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void actualizar(ActionEvent event) {
    }

    @FXML
    private void eliminar(ActionEvent event) {
    }

    private void traerDatos() throws SQLException {
        Connection conex = db.openConexion();
        PreparedStatement ps = conex.prepareStatement("select * from cliente");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Cliente newCliente = new Cliente(rs.getInt("idCliente"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("dni"), rs.getInt("telefono"), String.valueOf(rs.getInt("idZona")), rs.getString("sexo"));
            this.cliente.add(newCliente);
            this.tblClientes.refresh();
        }
    }

}
