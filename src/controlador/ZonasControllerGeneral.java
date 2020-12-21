package controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Zona;
import utils.db.ConnectionDB;

/**
 * @author davichois
 */
public class ZonasControllerGeneral implements Initializable {

    @FXML
    private TableColumn colID;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private TableView<Zona> tblZona;
    @FXML
    private TableColumn colDepartamento;
    @FXML
    private TableColumn colProvincia;
    @FXML
    private TableColumn colDistrito;
    @FXML
    private Button btnActualizar;
    @FXML
    private TextField txtBusqueda;

    private ObservableList<modelo.Zona> zona;

    private ObservableList<modelo.Zona> zonaFiltrada;

    private static ConnectionDB db = new ConnectionDB();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Creamos lista o array
        zona = FXCollections.observableArrayList();
        zonaFiltrada = FXCollections.observableArrayList();
        //Pasamos item del array a la tabla
        this.tblZona.setItems(zona);
        //Pasamos parametros
        this.colID.setCellValueFactory(new PropertyValueFactory("idZona"));
        this.colDepartamento.setCellValueFactory(new PropertyValueFactory("departamento"));
        this.colProvincia.setCellValueFactory(new PropertyValueFactory("provincia"));
        this.colDistrito.setCellValueFactory(new PropertyValueFactory("distrito"));
        //Metodo de traer datos de la db para poner en la tabla 
        try {
            traerDatos();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Traer datos DB");
            alert.setContentText("Error al traer datos de la db");
            alert.showAndWait();
        }
    }

    @FXML
    private void agregar(ActionEvent event) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/ZonaDialogVista.fxml"));

            //Cargamos al padre
            Parent root = loader.load();

            ZonaDialogController controlador = loader.getController();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Agregar Usuario");
            stage.setScene(scene);
            stage.showAndWait();

            Zona z = controlador.getZona();
            if (z != null) {
                Connection conex = db.openConexion();
                PreparedStatement ps = conex.prepareStatement("insert into zona(departamento, provincia, distrito) values(?,?,?)");
                ps.setString(1, z.getDepartamento());
                ps.setString(2, z.getProvincia());
                ps.setString(3, z.getDistrito());

                int resultado = ps.executeUpdate();

                if (resultado > 2) {
                    System.out.println("Registro insertado correctamente...");
                } else {
                    System.out.println("Error al registrar");
                }
                this.zona.clear();
                traerDatos();
                db.cerrarConnection();
            }

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void eliminar(ActionEvent event) throws SQLException {
        Zona z = this.tblZona.getSelectionModel().getSelectedItem();

        if (z == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println(alert);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar a una persona.");
            alert.showAndWait();
        } else {
            Connection conex = db.openConexion();
            PreparedStatement ps = conex.prepareStatement("delete from zona where idZona=?");
            ps.setInt(1, z.getIdZona());

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                System.out.println("Registro eliminado correctamente");
            } else {
                System.out.println("Error al eliminar");
            }
            this.zona.clear();
            traerDatos();
            db.cerrarConnection();
        }
    }

    @FXML
    private void actualizar(ActionEvent event) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/ZonaDialogVista.fxml"));

        //Cargamos al padre
        Parent root = loader.load();

        ZonaDialogController controlador = loader.getController();

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Agregar Usuario");
        stage.setScene(scene);
        stage.showAndWait();

        Zona z = controlador.getZona();

        Zona zs = this.tblZona.getSelectionModel().getSelectedItem();

        if (z == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar a una persona.");
            alert.showAndWait();
        } else {
            Connection conex = db.openConexion();
            PreparedStatement ps = conex.prepareStatement("update zona set departamento=?, provincia=?, distrito=? where idZona=?");
            ps.setString(1, z.getDepartamento());
            ps.setString(2, z.getProvincia());
            ps.setString(3, z.getDistrito());
            ps.setInt(4, zs.getIdZona());

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Info");
                alert.setContentText("Actualizado Correctamente");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("No se pudo actualizar");
                alert.showAndWait();
            }

            this.zona.clear();
            traerDatos();
            db.cerrarConnection();
        }
    }

    private void traerDatos() throws SQLException {
        Connection conex = db.openConexion();
        PreparedStatement ps = conex.prepareStatement("select * from zona");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Zona newZona = new Zona(rs.getInt("idZona"), rs.getString("departamento"), rs.getString("provincia"), rs.getString("distrito"));
            this.zona.add(newZona);
            this.tblZona.refresh();
        }
    }

    @FXML
    private void busquedaFiltrada(KeyEvent event) {
        String busquedaC = this.txtBusqueda.getText();
        if (busquedaC.isEmpty()) {
            this.tblZona.setItems(zona);
        } else {
            this.zonaFiltrada.clear();
            for (Zona z : zona) {
                if (z.getDepartamento().toLowerCase().contains(busquedaC.toLowerCase())) {
                    this.zonaFiltrada.add(z);
                } else if (z.getProvincia().toLowerCase().contains(busquedaC.toLowerCase())) {
                    this.zonaFiltrada.add(z);
                } else if (z.getDistrito().toLowerCase().contains(busquedaC.toLowerCase())) {
                    this.zonaFiltrada.add(z);
                }
            }
            this.tblZona.setItems(zonaFiltrada);
            this.tblZona.refresh();
        }
    }

    @FXML
    private void refrescar(MouseEvent event) throws SQLException {
        zona.clear();
        traerDatos();
    }

}
