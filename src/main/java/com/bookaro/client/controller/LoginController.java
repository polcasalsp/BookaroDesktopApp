package com.bookaro.client.controller;

import java.io.IOException;
import java.net.ConnectException;

import com.bookaro.client.service.DBCallService;
import com.bookaro.client.service.LoginService;
import com.bookaro.client.service.NetClientsService;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;

import com.bookaro.client.Utils.Tools;
import com.bookaro.client.model.Client;
import com.bookaro.client.model.Subscription;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * @author Pol Casals
 *
 */
public class LoginController {	
	
	@FXML
	private MFXTextField usernameField, newUsernameField, newPasswordField, newNameField,
	newSurnameField, newDniField, newAdressField, newEmailField, newAgeField;
	
	@FXML
	private MFXPasswordField passwordField;
	
	@FXML
	private MFXButton loginBtn, registerBtn;
	
	@FXML
	private Button closeBtn, backBtn, createUserBtn;
	
	@FXML 
	private BorderPane bp;
	
	@FXML
	private Pane registerPane, loginPane;
	
	private DBCallService dbcs;
	
	/**
	 * @author Pol Casals
	 * @throws IOException 
	 */
	@FXML
	private void initialize() throws IOException {
		loginPane.toFront();
		dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
	}
	
	/**
	 * Verifica y envia los datos que el usuario introduce 
	 * en el formulario al servicio de login y recupera el token
	 * recibido del servidor.
	 * @author Pol Casals
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void login (ActionEvent event) throws IOException {
		String token = "";
		Window owner = loginBtn.getScene().getWindow();
		
		
		if (usernameField.getText().isEmpty()) {
            Tools.showAlert(Alert.AlertType.ERROR, owner, "Username Error", "Please enter username");
            return;
        }
		
        if (passwordField.getText().isEmpty()) {
        	Tools.showAlert(Alert.AlertType.ERROR, owner, "Password Error", "Please enter password");
            return;
        }       
        
        try {			
		    LoginService loginService = LoginService.getLogin();
		    loginService.sendUserCredentials(usernameField.getText(), passwordField.getText());		
		    token = loginService.getToken();
		} catch (ArrayIndexOutOfBoundsException e) {
			Tools.showAlert(Alert.AlertType.ERROR, owner, "Credentials Error", "Incorrect username/password combination");
		} catch (ConnectException e) {
			Tools.showAlert(Alert.AlertType.ERROR, owner, "Connection Error", "Refused Connection");
		}
        checkLoginChangeScene(token);    
	}
	
	/**
	 * Si las credenciales son correctas y por tanto
	 * se ha recibido un token del servidor, procede
	 * a cambiar la escena a la vista principal.
	 * @author Pol Casals
	 * @throws IOException
	 */
	private void checkLoginChangeScene(String token) throws IOException {
		if (token != "") {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));  
        	Parent mainScreen = loader.load();
            Stage stage = (Stage)loginBtn.getScene().getWindow();
            Scene scene = new Scene(mainScreen, 1280, 800);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            Tools.draggableWindow(stage, mainScreen);
            stage.setScene(scene);
        }     
	}
	
	/**
	 * Escucha al evento del boton atras, para volver
	 * a la pantalla de login desde el registro.
	 * @author Pol Casals
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void back (ActionEvent event) throws IOException {
		loginPane.toFront();
	}
	
	/**
	 * Escucha al evento del boton sign up, que muestra
	 * la pantalla de registro de un nuevo usuario.
	 * @author Pol Casals
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void register (ActionEvent event) {
		registerPane.toFront();
	}
	
	/**
	 * Escucha al evento del boton create, que recoge los datos
	 * introducidos por el usuario en el formulario de registro
	 * y los envia al servidor para crear un nuevo cliente (user)
	 * @author Pol Casals
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void createUser(ActionEvent event) throws IOException {
		Client cli = new Client();
		cli.setUsername(newUsernameField.getText());		
		cli.setPassword(newPasswordField.getText());
		cli.setDni(newDniField.getText());
		cli.setAddress(newAdressField.getText());
		cli.setAge(Integer.parseInt(newAgeField.getText()));
		cli.setEmail(newEmailField.getText());
		cli.setName(newNameField.getText());
		cli.setSurname(newSurnameField.getText());
		Subscription sub = dbcs.findSubById(3).execute().body();
		cli.setSubscription(sub);
		dbcs.newClient(cli).execute();
	}
	
	/**
	 * Escucha al evento del boton de cerrar ventana.
	 * @author Pol Casals
	 * @param event
	 * @throws IOException
	 */
	public void close (ActionEvent event) throws IOException {
		Platform.exit();
	}
}