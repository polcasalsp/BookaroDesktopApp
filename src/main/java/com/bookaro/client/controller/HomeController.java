package com.bookaro.client.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bookaro.client.Utils.Tools;
import com.bookaro.client.model.Client;
import com.bookaro.client.model.Order;
import com.bookaro.client.model.User;
import com.bookaro.client.service.DBCallService;
import com.bookaro.client.service.NetClientsService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class HomeController {
	
	@FXML
	private Label nameLabel, countryLabel, emailLabel, addressLabel;
	
    @FXML
    private GridPane grid;
	
	private DBCallService dbcs;
	
	private List<Order> orders = new ArrayList<Order>();
	
	private ObservableList<Order> updatedOrders = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() throws IOException {
		dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
		User currentUser = dbcs.findByUsername(Tools.getUsernameFromToken()).execute().body();
		populateProfile(currentUser);
		
		if (Tools.getRoleFromToken().equals("ROLE_USER")) {
			Client currentClient = dbcs.findClientByUsername(Tools.getUsernameFromToken()).execute().body();
			showOrders(currentClient);
		}
	}
	
	/**
	 * Muestra los datos del cliente en el perfil personal.
	 * @author Pol Casals
	 * @throws IOException
	 */
	private void populateProfile(User currentUser) throws IOException {		
		nameLabel.setText(currentUser.getName() + " "  + currentUser.getSurname() + ", " + currentUser.getAge());
		countryLabel.setText("Spain");
		emailLabel.setText(currentUser.getEmail());
		addressLabel.setText(currentUser.getAddress());
	}
	
	/**
	 * Crea una vista para cada libro encontrado con los criterios
	 * de búsqueda y la añade al Gridpane que se va a mostrar en 
	 * una disposición vertical de (n columnas (atributo column) x n filas (las necesarias))
	 * @author Pol Casals
	 * @throws IOException
	 */
	public void showOrders(Client currentUser) throws IOException {
		int column = 0;
		int row = 0;
		orders = currentUser.getOrders();
		for (int i = 0; i < orders.size(); i++) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/order.fxml"));
			AnchorPane anchorPane = loader.load();			
			OrderController orderController = loader.getController();
			orderController.setData(orders.get(i));						
			grid.add(anchorPane, column, row++);
		}
		orders.clear();
		updatedOrders.clear();		
	}
}
