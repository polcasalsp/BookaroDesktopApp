package com.bookaro.client.controller;

import java.io.IOException;

import com.bookaro.client.Utils.Tools;
import com.bookaro.client.model.Client;
import com.bookaro.client.service.DBCallService;
import com.bookaro.client.service.NetClientsService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ManageProfileController {
	
	@FXML
	private Label pwMatchLabel;
	
	@FXML
	private Button saveBtn;
	
	@FXML
	private TextField usernameField, passwordField, rePasswordField, addressField, emailField;	
	
	DBCallService dbcs;
	
	private Client currentClient;
	
	@FXML
	private void initialize() throws IOException {		
		if (Tools.getRoleFromToken().equals("ROLE_USER")) {
			dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
			currentClient = dbcs.findClientByUsername(Tools.getUsernameFromToken()).execute().body();
			usernameField.setText(currentClient.getUsername());
			addressField.setText(currentClient.getAddress());
			emailField.setText(currentClient.getEmail());		
			usernameField.setDisable(true);
		} else {
			usernameField.setDisable(true);
			passwordField.setDisable(true);
			rePasswordField.setDisable(true);
			addressField.setDisable(true);
			emailField.setDisable(true);
		}	
	}
	
	/**
	 * Guarda los campos que han sido modificados
	 * por el cliente a la bd.
	 * @author Pol Casals
	 * @throws IOException
	 */
	@FXML
	private void save() throws IOException {
		currentClient.setAddress(addressField.getText());
		currentClient.setEmail(emailField.getText());
		if (!passwordField.getText().isEmpty() && passwordField.getText().equals(rePasswordField.getText())) {
			currentClient.setPassword(passwordField.getText());
		}
		dbcs.updateClient(currentClient).execute();
	}
}
