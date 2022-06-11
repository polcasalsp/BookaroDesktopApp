package com.bookaro.client.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.bookaro.client.model.Author;
import com.bookaro.client.model.Book;
import com.bookaro.client.model.Editorial;
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
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.converter.DefaultStringConverter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class BookManagerController {
	
	@FXML
	private MenuButton adminTools;
	
	@FXML
	private TableView<Book> bookTable;
	
	@FXML
	private TableColumn<Book, String> nameColumn, authorColumn, isbnColumn, categoryColumn, editorialColumn, synopsisColumn, idColumn;
	
	@FXML
	private MFXButton addBtn, updateBtn, deleteBtn, refreshBtn, addCoverBtn;
	
	private ObservableList<Book> books = FXCollections.observableArrayList();
	
	private ObservableList<String> authorNames = FXCollections.observableArrayList();
	
	private ObservableList<String> editorialNames = FXCollections.observableArrayList();
	
	private Response<ArrayList<Author>> findAuthors;
	
	private Response<ArrayList<Editorial>> findEditorials;
	
	private DBCallService dbcs;
	
	public void initialize() throws IOException {
		dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
		getBooks();
		getAuthors();
		getEditorials();
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
		Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
		if (event.getSource() == addBtn) {
			Book book = new Book();
			book.setName("");
			book.setIsbn("");
			book.setAuthor(findAuthors.body().get(0));
			book.setEditorial(findEditorials.body().get(0));
			dbcs.newBook(book).execute();				
		}
		if (!bookTable.getSelectionModel().isEmpty()) {
			if (event.getSource() == updateBtn) {	
				dbcs.updateBook(selectedBook).execute();						
			} else if (event.getSource() == deleteBtn) {								
				dbcs.deleteBook(selectedBook.getId()).execute();								
			} else if (event.getSource() == addCoverBtn) {									
				FileChooser fc = new FileChooser();
				File file = fc.showOpenDialog(null);
				MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", selectedBook.getName()+".jpg", RequestBody.create(MediaType.parse("image/jpg"), file));
				dbcs.newImage(filePart, selectedBook.getId()).execute();				
			}	
		}
		getAuthors();
		getEditorials();
		getBooks();
		bookTable.getItems().setAll(books);		
	}
	
	/**
	 * Recupera la lista completa de libros en el servidor
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
	 * Recupera la lista completa de autores en el servidor
	 * y la devuelve.
	 * @author Pol Casals
	 * @throws IOException
	 */
	public ObservableList<String> getAuthors() throws IOException {
		findAuthors = dbcs.getAuthors().execute();
		authorNames.clear();
		for (Author author : findAuthors.body()) {
			authorNames.add(author.getName());
		}		
		return authorNames;
	}
	
	/**
	 * Recupera la lista completa de editoriales en el servidor
	 * y la devuelve.
	 * @author Pol Casals
	 * @throws IOException
	 */
	public ObservableList<String> getEditorials() throws IOException {
		findEditorials = dbcs.getEditorials().execute();
		editorialNames.clear();
		for (Editorial editorial : findEditorials.body()) {
			editorialNames.add(editorial.getName());
		}		
		return editorialNames;
	}
	
	/**
	 * Proporciona a cada celda el valor que corresponde a un objeto
	 * de la clase Book con el metodo de {@link TableColumn}
	 * {@link TableColumn#cellValueFactoryProperty() cell value factory}
	 * e indica el tipo de dato que se representa en cada celda y de que
	 * tipo de objeto proviene: <Tipo de objeto(Book), Tipo de dato(String)>.
	 * @author Pol Casals
	 */
	public void populateBooksTable() {
		idColumn.setCellValueFactory(new PropertyValueFactory<>(String.valueOf("id")));
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn.setOnEditCommit(e -> e.getRowValue().setName(e.getNewValue()));
		nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		authorColumn.setCellValueFactory(new PropertyValueFactory<>("authorName"));
		authorColumn.setOnEditCommit(event -> {
			Author newAuthor = findAuthors.body().stream().filter(author -> author.getName().equals(event.getNewValue())).findFirst().get();
			event.getRowValue().setAuthor(newAuthor);
		});
		authorColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), authorNames));
		
		isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
		isbnColumn.setOnEditCommit(e -> e.getRowValue().setIsbn(e.getNewValue()));
		isbnColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
		categoryColumn.setOnEditCommit(e -> e.getRowValue().setCategory(e.getNewValue()));
		categoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		editorialColumn.setCellValueFactory(new PropertyValueFactory<>("editorialName"));
		editorialColumn.setOnEditCommit(event -> {
			Editorial newEditorial = findEditorials.body().stream().filter(editorial -> editorial.getName().equals(event.getNewValue())).findFirst().get();
			event.getRowValue().setEditorial(newEditorial);
		});
		editorialColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), editorialNames));
		
		synopsisColumn.setCellValueFactory(new PropertyValueFactory<>("synopsis"));
		synopsisColumn.setOnEditCommit(e -> e.getRowValue().setSynopsis(e.getNewValue()));
		synopsisColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		bookTable.getItems().setAll(books);		
	}
}
