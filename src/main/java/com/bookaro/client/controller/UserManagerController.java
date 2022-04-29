package com.bookaro.client.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.bookaro.client.model.Client;
import com.bookaro.client.model.User;
import com.bookaro.client.service.DBCallService;
import com.bookaro.client.service.NetClientsService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import retrofit2.Response;

public class UserManagerController {
	
	@FXML
	private MenuButton adminTools;
	
	@FXML
	private TableView<Client> userTable;
	
	@FXML
	private TableColumn<Client, String> usernameColumn, passwordColumm, emailColumn, firstNameColumn, lastNameColumn, roleColumn, idColumn;
	
	private ObservableList<Client> updatedClients = FXCollections.observableArrayList();
	
	private DBCallService dbcs;
	
	public void initialize() throws IOException {
		dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
		getUsers();
		populateUsersTable();    	
	}
	
	/**
	 * Recupera la lista completa de usuarios en el servidor
	 * y la devuelve.
	 * @author Pol Casals
	 * @throws IOException
	 */
	public ObservableList<Client> getUsers() throws IOException {
		Response<ArrayList<Client>> findClients = dbcs.getClients().execute();
		
		for (Client client : findClients.body()) {
			updatedClients.add(client);
		}		
		return updatedClients;
	}
	
	/**
	 * Proporciona a cada celda el valor que corresponde a un objeto
	 * de la clase User con el metodo de {@link TableColumn}
	 * {@link TableColumn#cellValueFactoryProperty() cell value factory}
	 * e indica el tipo de dato que se representa en cada celda y de que
	 * tipo de objeto proviene: <Tipo de objeto(User), Tipo de dato(String)>.
	 * @author Pol Casals
	 */
	@SuppressWarnings("rawtypes")
	public void populateUsersTable() {
		idColumn.setCellValueFactory(new PropertyValueFactory<Client, String>(String.valueOf("id")));
		usernameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("username"));
		passwordColumm.setCellValueFactory(new PropertyValueFactory<Client, String>("password"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("surname"));
		roleColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("role"));
		userTable.getItems().setAll(updatedClients);
		
		for (TableColumn col : userTable.getColumns().subList(1, userTable.getColumns().size())) {
			col.setCellFactory(TextFieldTableCell.forTableColumn());
		}
		
	}
}
