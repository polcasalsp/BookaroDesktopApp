package com.bookaro.client.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.bookaro.client.model.Book;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import retrofit2.Response;

public class BookManagerController {
	
	@FXML
	private MenuButton adminTools;
	
	@FXML
	private TableView<Book> bookTable;
	
	@FXML
	private TableColumn<Book, String> nameColumn, authorColumn, isbnColumn, categoryColumn, editorialColumn, synopsisColumn, idColumn;
	
	@FXML
	private MFXButton addBtn, updateBtn, deleteBtn, refreshBtn;
	
	private ObservableList<Book> books = FXCollections.observableArrayList();
	
	private DBCallService dbcs;
	
	public void initialize() throws IOException {
		dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
		getBooks();
		populateBooksTable();    	
	}
	
	/**
	 * Metodo para gestionar los eventos de cada boton
	 * en la pantalla de manejo de libros
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handleCrudButton(ActionEvent event) throws IOException {
		Book selectedBook = new Book();
		if (event.getSource() == addBtn) {
			Book book = new Book();
			book.setName("new book");
			book.setIsbn("ISBN");
			dbcs.newBook(book).execute();			
			getBooks();
			bookTable.getItems().setAll(books);
		} else if (event.getSource() == updateBtn) {
			selectedBook = bookTable.getSelectionModel().getSelectedItem();
			if (!bookTable.getSelectionModel().isEmpty()) {
				dbcs.updateBook(selectedBook).execute();
				getBooks();
				bookTable.getItems().setAll(books);
			}
		} else if (event.getSource() == deleteBtn) {
			selectedBook = bookTable.getSelectionModel().getSelectedItem();
			if (!bookTable.getSelectionModel().isEmpty()) {				
				dbcs.deleteBook(selectedBook.getId()).execute();
				getBooks();
				bookTable.getItems().setAll(books);
			}			
		} else {
			getBooks();
			bookTable.getItems().setAll(books);
		}
	}
	
	/**
	 * Recupera la lista completa de usuarios en el servidor
	 * y la devuelve.
	 * @author Pol Casals
	 * @throws IOException
	 */
	public ObservableList<Book> getBooks() throws IOException {
		Response<ArrayList<Book>> findBooks = dbcs.getBooks().execute();
		books.clear();
		for (Book book : findBooks.body()) {
			books.add(book);
		}		
		return books;
	}
	
	/**
	 * Proporciona a cada celda el valor que corresponde a un objeto
	 * de la clase Book con el metodo de {@link TableColumn}
	 * {@link TableColumn#cellValueFactoryProperty() cell value factory}
	 * e indica el tipo de dato que se representa en cada celda y de que
	 * tipo de objeto proviene: <Tipo de objeto(Book), Tipo de dato(String)>.
	 * @author Pol Casals
	 */
	@SuppressWarnings("rawtypes")
	public void populateBooksTable() {
		idColumn.setCellValueFactory(new PropertyValueFactory<>(String.valueOf("id")));
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn.setOnEditCommit(e -> e.getRowValue().setName(e.getNewValue()));
		
		authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
		authorColumn.setOnEditCommit(e -> e.getRowValue().setAuthor(e.getNewValue()));
		
		isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
		isbnColumn.setOnEditCommit(e -> e.getRowValue().setIsbn(e.getNewValue()));
		
		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
		categoryColumn.setOnEditCommit(e -> e.getRowValue().setCategory(e.getNewValue()));
		
		editorialColumn.setCellValueFactory(new PropertyValueFactory<>("editorial"));
		editorialColumn.setOnEditCommit(e -> e.getRowValue().setEditorial(e.getNewValue()));
		
		synopsisColumn.setCellValueFactory(new PropertyValueFactory<>("synopsis"));
		synopsisColumn.setOnEditCommit(e -> e.getRowValue().setSynopsis(e.getNewValue()));
		
		bookTable.getItems().setAll(books);
		
		for (TableColumn col : bookTable.getColumns().subList(1, bookTable.getColumns().size())) {
			col.setCellFactory(TextFieldTableCell.forTableColumn());
		}
		
	}
}
