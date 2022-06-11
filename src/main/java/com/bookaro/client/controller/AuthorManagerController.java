package com.bookaro.client.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.bookaro.client.Utils.Tools;
import com.bookaro.client.model.Author;
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

public class AuthorManagerController {
	
	@FXML
	private MenuButton adminTools;
	
	@FXML
	private TableView<Author> authorTable;
	
	@FXML
	private TableColumn<Author, String> idColumn, nameColumn, nacionalityColumn;
	
	@FXML
	private MFXButton addBtn, updateBtn, deleteBtn, refreshBtn;
	
	private ObservableList<Author> authors = FXCollections.observableArrayList();
	
	private DBCallService dbcs;
	
	public void initialize() throws IOException {
		if (Tools.getRoleFromToken().equals("ROLE_ADMIN")) {
			dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
			getAuthors();
			populateTable();   
		} 			
	}
	
	/**
	 * Metodo para gestionar los eventos de cada boton
	 * en la pantalla de manejo de autores
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handleCrudButton(ActionEvent event) throws IOException {
		if (event.getSource() == addBtn) {
			Author author = new Author();
			author.setName("");
			author.setNacionality("");
			dbcs.newAuthor(author).execute();			
			getAuthors();
			authorTable.getItems().setAll(authors);
		} else if (event.getSource() == updateBtn) {
			if (!authorTable.getSelectionModel().isEmpty()) {
				dbcs.updateAuthor(authorTable.getSelectionModel().getSelectedItem()).execute();
				getAuthors();
				authorTable.getItems().setAll(authors);
			}
		} else if (event.getSource() == deleteBtn) {
			if (!authorTable.getSelectionModel().isEmpty()) {
				Author selectedAuthor = authorTable.getSelectionModel().getSelectedItem();
				dbcs.deleteAuthor(selectedAuthor.getId()).execute();
				getAuthors();
				authorTable.getItems().setAll(authors);
			}			
		} else {
			getAuthors();
			authorTable.getItems().setAll(authors);
		}
	}
	
	/**
	 * Recupera la lista completa de autores en el servidor
	 * y la devuelve.
	 * @author Pol Casals
	 * @throws IOException
	 */
	public ObservableList<Author> getAuthors() throws IOException {
		Response<ArrayList<Author>> findAuthors = dbcs.getAuthors().execute();
		authors.clear();	
		for (Author author : findAuthors.body()) {
			authors.add(author);
		}		
		return authors;
	}
	
	/**
	 * Proporciona a cada celda el valor que corresponde a un objeto
	 * de la clase Author con el metodo de {@link TableColumn}
	 * {@link TableColumn#cellValueFactoryProperty() cell value factory}
	 * e indica el tipo de dato que se representa en cada celda y de que
	 * tipo de objeto proviene: <Tipo de objeto(Author), Tipo de dato(String)>.
	 * @author Pol Casals
	 */
	@SuppressWarnings("rawtypes")
	public void populateTable() {
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn.setOnEditCommit(e -> e.getRowValue().setName(e.getNewValue()));	
		
		nacionalityColumn.setCellValueFactory(new PropertyValueFactory<>("nacionality"));
		nacionalityColumn.setOnEditCommit(e -> e.getRowValue().setNacionality(e.getNewValue()));	
		
		authorTable.getItems().setAll(authors);
		
		for (TableColumn col : authorTable.getColumns().subList(1, authorTable.getColumns().size())) {
			col.setCellFactory(TextFieldTableCell.forTableColumn());
		}		
	}
}
