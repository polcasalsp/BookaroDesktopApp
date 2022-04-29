package com.bookaro.client.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bookaro.client.model.Book;
import com.bookaro.client.service.DBCallService;
import com.bookaro.client.service.NetClientsService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import retrofit2.Response;



public class HomeController {
	
	@FXML
	private ComboBox<String> bookSearchBy;
	
	@FXML
	private TextField bookSearchBar;
		
    @FXML
    private GridPane grid;
    
    @FXML
    private Button getBooksBtn;
    
    @FXML
    private ScrollPane scroll;
	
	private DBCallService dbcs;
    
    private List<Book> books = new ArrayList<Book>();
	
	private ObservableList<Book> updatedBooks = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() {
		bookSearchBy.getItems().addAll("name", "author", "isbn", "category", "editorial");
		bookSearchBy.setValue("name");
	}
	
	
	/**
	 * Filtra las búsquedas de libros por diferentes criterios
	 * @implNote En un futuro se debera filtrar en el backend
	 * para evitar enviar y recibir mas datos de los necesarios
	 * @author Pol Casals
	 * @throws IOException
	 */
	public void getBooks() throws IOException {			
		dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
	
	
		Response<ArrayList<Book>> bookRes = dbcs.getBooks().execute();		
		for (Book book : bookRes.body()) {
			switch(bookSearchBy.getValue()) {
			
			case("name"):
				
				if (book.getName().toLowerCase()
					.contains(bookSearchBar.getText().toLowerCase())) {	
					updatedBooks.add(book);
				}	
				break;							
			case("author"):		
				
				if (book.getAuthor().toLowerCase()
					.contains(bookSearchBar.getText().toLowerCase())) {	
					updatedBooks.add(book);
				}
				break;							
			case("isbn"):		
				
				if (book.getName().toLowerCase()
					.contains(bookSearchBar.getText().toLowerCase())) {	
					updatedBooks.add(book);
				}
				break;							
			case("category"):	
				
				if (book.getName().toLowerCase()
					.contains(bookSearchBar.getText().toLowerCase())) {	
					updatedBooks.add(book);
				}
				break;							
			case("editorial"):	
				
				if (book.getName().toLowerCase()
					.contains(bookSearchBar.getText().toLowerCase())) {	
					updatedBooks.add(book);
				}
				break;
			}					
		}		
		books.addAll(updatedBooks);
		showBooks();
	}
	
	/**
	 * Crea una vista para cada libro encontrado con los criterios
	 * de búsqueda y la añade al Gridpane que se va a mostrar en 
	 * una disposición vertical de (n columnas (atributo column) x n filas (las necesarias))
	 * @author Pol Casals
	 * @throws IOException
	 */
	public void showBooks() throws IOException {
		grid.getChildren().clear();
		int column = 0;
		int row = 1;
		
		for (int i = 0; i < books.size(); i++) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/book.fxml"));
			AnchorPane anchorPane = loader.load();			
			BookController bookController = loader.getController();
			bookController.setData(books.get(i));			
			if (column == 3) {
				column = 0;
				row++;
			}			
			grid.add(anchorPane, column++, row);
			GridPane.setMargin(anchorPane, new Insets(50, 25, 25, 60));
		}
		books.clear();
		updatedBooks.clear();		
	}	
	
}
