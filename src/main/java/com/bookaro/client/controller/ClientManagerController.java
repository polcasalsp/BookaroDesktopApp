package com.bookaro.client.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.bookaro.client.Utils.Tools;
import com.bookaro.client.model.Client;
import com.bookaro.client.model.Subscription;
import com.bookaro.client.service.DBCallService;
import com.bookaro.client.service.NetClientsService;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import retrofit2.Response;

public class ClientManagerController {
	
	@FXML
	private MenuButton adminTools;
	
	@FXML
	private TableView<Client> clientTable;
	
	@FXML
	private TableColumn<Client, String> usernameColumn, passwordColumm, emailColumn, nameColumn, surnameColumn, dniColumn, idColumn, subColumn;
	
	@FXML
	private TableColumn<Client, Integer> ageColumn;
	
	@FXML
	private MFXButton addBtn, updateBtn, deleteBtn, refreshBtn;
	
	private ObservableList<Client> clients = FXCollections.observableArrayList();
	
	private DBCallService dbcs;
	
	public void initialize() throws IOException {
		if (Tools.getRoleFromToken().equals("ROLE_ADMIN")) {
			dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
			getClients();
			populateTable();   
		} 			
	}
	
	/**
	 * Metodo para gestionar los eventos de cada boton
	 * en la pantalla de manejo de clientes
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handleCrudButton(ActionEvent event) throws IOException {
		if (event.getSource() == addBtn) {
			Client cli = new Client();
			Subscription sub = dbcs.findSubById(3).execute().body();
			cli.setUsername("");
			cli.setPassword("");
			cli.setEmail("");
			cli.setSubscription(sub);
			dbcs.newClient(cli).execute();			
			getClients();
			clientTable.getItems().setAll(clients);
		} else if (event.getSource() == updateBtn) {
			if (!clientTable.getSelectionModel().isEmpty()) {
				dbcs.updateClientAdmin(clientTable.getSelectionModel().getSelectedItem()).execute();
				getClients();
				clientTable.getItems().setAll(clients);
			}
		} else if (event.getSource() == deleteBtn) {
			if (!clientTable.getSelectionModel().isEmpty()) {
				Client selectedCli = clientTable.getSelectionModel().getSelectedItem();
				selectedCli.getOrders().forEach(i -> dbcs.deleteOrder(i.getId()));
				dbcs.deleteClient(selectedCli.getId()).execute();
				getClients();
				clientTable.getItems().setAll(clients);
			}			
		} else {
			getClients();
			clientTable.getItems().setAll(clients);
		}
	}
	
	/**
	 * Recupera la lista completa de clientes en el servidor
	 * y la devuelve.
	 * @author Pol Casals
	 * @throws IOException
	 */
	public ObservableList<Client> getClients() throws IOException {
		Response<ArrayList<Client>> findClients = dbcs.getClients().execute();
		clients.clear();	
		for (Client client : findClients.body()) {
			clients.add(client);
		}		
		return clients;
	}
	
	/**
	 * Proporciona a cada celda el valor que corresponde a un objeto
	 * de la clase Client con el metodo de {@link TableColumn}
	 * {@link TableColumn#cellValueFactoryProperty() cell value factory}
	 * e indica el tipo de dato que se representa en cada celda y de que
	 * tipo de objeto proviene: <Tipo de objeto(Client), Tipo de dato(String)>.
	 * @author Pol Casals
	 */
	@SuppressWarnings("rawtypes")
	public void populateTable() {
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		
		usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		usernameColumn.setOnEditCommit(e -> e.getRowValue().setUsername(e.getNewValue()));
		
		passwordColumm.setCellValueFactory(new PropertyValueFactory<>("password"));
		passwordColumm.setOnEditCommit(e -> e.getRowValue().setPassword(e.getNewValue()));
		
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		emailColumn.setOnEditCommit(e -> e.getRowValue().setEmail(e.getNewValue()));
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn.setOnEditCommit(e -> e.getRowValue().setName(e.getNewValue()));
		
		surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
		surnameColumn.setOnEditCommit(e -> e.getRowValue().setSurname(e.getNewValue()));
		
		dniColumn.setCellValueFactory(new PropertyValueFactory<>("dni"));
		dniColumn.setOnEditCommit(e -> e.getRowValue().setDni(e.getNewValue()));
		
		ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
		ageColumn.setOnEditCommit(e -> e.getRowValue().setAge(e.getNewValue()));

		subColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSubscription().getType()));		
		
		clientTable.getItems().setAll(clients);
		
		for (TableColumn col : clientTable.getColumns().subList(1, clientTable.getColumns().size()-2)) {
			col.setCellFactory(TextFieldTableCell.forTableColumn());
		}		
	}
}
