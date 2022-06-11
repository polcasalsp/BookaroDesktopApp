package com.bookaro.client.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.bookaro.client.Utils.Tools;
import com.bookaro.client.model.Book;
import com.bookaro.client.model.Order;
import com.bookaro.client.service.DBCallService;
import com.bookaro.client.service.NetClientsService;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.DefaultStringConverter;
import retrofit2.Response;

public class OrderManagerController {
	
	@FXML
	private MenuButton adminTools;
	
	@FXML
	private TableView<Order> orderTable;
	
	@FXML
	private TableColumn<Order, String> idColumn, bookColumn;
	
	@FXML
	private TableColumn<Order, Boolean> activeColumn;
	
	@FXML
	private MFXButton addBtn, updateBtn, deleteBtn, refreshBtn;
	
	private ObservableList<Order> orders = FXCollections.observableArrayList();
	
	private ObservableList<String> bookNames = FXCollections.observableArrayList();
	
	private Response<ArrayList<Book>> findBooks;
	
	private DBCallService dbcs;
	
	public void initialize() throws IOException {
		addBtn.setDisable(true);
		if (Tools.getRoleFromToken().equals("ROLE_ADMIN")) {
			dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
			getOrders();
			getBooks();
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
		if (event.getSource() == updateBtn) {
			if (!orderTable.getSelectionModel().isEmpty()) {
				dbcs.updateOrder(orderTable.getSelectionModel().getSelectedItem()).execute();
				getOrders();
				orderTable.getItems().setAll(orders);
			}
		} else if (event.getSource() == deleteBtn) {
			if (!orderTable.getSelectionModel().isEmpty()) {
				Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
				dbcs.deleteOrder(selectedOrder.getId()).execute();
				getOrders();
				orderTable.getItems().setAll(orders);
			}			
		} else {
			getBooks();
			getOrders();
			orderTable.getItems().setAll(orders);
		}
	}
	
	/**
	 * Recupera la lista completa de pedidos en el servidor
	 * y la devuelve.
	 * @author Pol Casals
	 * @throws IOException
	 */
	public ObservableList<Order> getOrders() throws IOException {
		Response<ArrayList<Order>> findOrders = dbcs.getOrders().execute();
		orders.clear();	
		for (Order order : findOrders.body()) {
			orders.add(order);
		}		
		return orders;
	}
	
	/**
	 * Recupera la lista completa de libros en el servidor
	 * y la devuelve.
	 * @author Pol Casals
	 * @throws IOException
	 */
	public ObservableList<String> getBooks() throws IOException {
		findBooks = dbcs.getBooks().execute();
		bookNames.clear();
		for (Book book : findBooks.body()) {
			bookNames.add(book.getName());
		}		
		return bookNames;
	}
	
	/**
	 * Proporciona a cada celda el valor que corresponde a un objeto
	 * de la clase Client con el metodo de {@link TableColumn}
	 * {@link TableColumn#cellValueFactoryProperty() cell value factory}
	 * e indica el tipo de dato que se representa en cada celda y de que
	 * tipo de objeto proviene: <Tipo de objeto(Order), Tipo de dato(String)>.
	 * @author Pol Casals
	 */
	public void populateTable() {
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		
		bookColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
		bookColumn.setOnEditCommit(event -> {
			Book newBook = findBooks.body().stream().filter(book -> book.getName().equals(event.getNewValue())).findFirst().get();
			event.getRowValue().setBook(newBook);
		});
		bookColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), bookNames));

		activeColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
		
		orderTable.getItems().setAll(orders);	
	}
}
