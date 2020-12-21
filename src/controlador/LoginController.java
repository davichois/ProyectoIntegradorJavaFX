package controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.db.ConnectionDB;

/**
 * @author davichois
 */
public class LoginController implements Initializable {

    @FXML
    private Button btnLogin;
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtContraseña;

    private static ConnectionDB db = new ConnectionDB();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void login(ActionEvent event) throws IOException, SQLException {
        String usuario = this.txtUsuario.getText();
        String contraseña = this.txtContraseña.getText();
        String usuarioQ = null;
        String contraseñaQ = null;

        Connection conex = db.openConexion();
        PreparedStatement ps = conex.prepareStatement("select * from usuario where nombre=?");
        ps.setString(1, usuario);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            usuarioQ = rs.getString("nombre");
            contraseñaQ = rs.getString("contraseña");
        }

        if (usuarioQ.equals(usuario)) {
            if (contraseñaQ.equals(contraseña)) {
                Parent root = FXMLLoader.load(getClass().getResource("/vista/MenuGeneralVista.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setResizable(true);
                stage.setTitle("Menu inicial");
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Icorrect Contraseña");
                alert.setContentText("Contraseña Incorrecta");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Icorrect User");
            alert.setContentText("Usuario o contraseña incorrecta");
            alert.showAndWait();
        }
    }

}
