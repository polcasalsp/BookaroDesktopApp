package com.bookaro.client.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.bookaro.client.model.Book;
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

public class BookManagerController {
	
	@FXML
	private MenuButton adminTools;
	
	@FXML
	private TableView<Book> userTable;
	
	@FXML
	private TableColumn<Book, String> nameColumn, authorColumn, isbnColumn, categoryColumn, editorialColumn, synopsisColumn, idColumn;
	
	private ObservableList<Book> updatedBooks = FXCollections.observableArrayList();
	
	private DBCallService dbcs;
	
	public void initialize() throws IOException {
		dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
		getBooks();
		populateBooksTable();    	
	}
	
	/**
	 * Recupera la lista completa de usuarios en el servidor
	 * y la devuelve.
	 * @author Pol Casals
	 * @throws IOException
	 */
	public ObservableList<Book> getBooks() throws IOException {
		Response<ArrayList<Book>> findBooks = dbcs.getBooks().execute();		
		for (Book book : findBooks.body()) {
			updatedBooks.add(book);
		}		
		return updatedBooks;
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
	public void populateBooksTable() {
		idColumn.setCellValueFactory(new PropertyValueFactory<Book, String>(String.valueOf("id")));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));
		authorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
		isbnColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));
		categoryColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("category"));
		editorialColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("editorial"));
		synopsisColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("synopsis"));
		userTable.getItems().setAll(updatedBooks);
		
		for (TableColumn col : userTable.getColumns().subList(1, userTable.getColumns().size())) {
			col.setCellFactory(TextFieldTableCell.forTableColumn());
		}
		
	}
}
