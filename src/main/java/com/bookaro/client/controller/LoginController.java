package com.bookaro.client.controller;

import java.io.IOException;
import java.net.ConnectException;

import com.bookaro.client.service.LoginService;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;

import com.bookaro.client.Utils.Tools;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * @author Pol Casals
 *
 */
public class LoginController {	
	
	@FXML
	private MFXTextField usernameField;
	
	@FXML
	private MFXPasswordField passwordField;
	
	@FXML
	private MFXButton loginBtn;
	
	@FXML
	private MFXButton registerBtn;
	
	@FXML
	private Button closeBtn;
	
	@FXML 
	private BorderPane bp;
	
	private String token = "";
	
	/**
	 * @author Pol Casals
	 * @param event
	 * @throws IOException
	 */
	
	@FXML
	private void login (ActionEvent event) throws IOException {
		
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
		    loginService.sendCredentials(usernameField.getText(), passwordField.getText());		
		    token = loginService.getToken();
		} catch (ArrayIndexOutOfBoundsException e) {
			Tools.showAlert(Alert.AlertType.ERROR, owner, "Credentials Error", "Incorrect username/password combination");
		} catch (ConnectException e) {
			Tools.showAlert(Alert.AlertType.ERROR, owner, "Connection Error", "Refused Connection");
		}
        checkLoginChangeScene();  
        
	}
	
	private void checkLoginChangeScene() throws IOException {
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
	 * @author Pol Casals
	 * @param event
	 * @throws IOException
	 */
	public void close (ActionEvent event) throws IOException {
		Platform.exit();
	}
}