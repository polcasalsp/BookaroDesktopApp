package com.bookaro.client.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.bookaro.client.Utils.Tools;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import retrofit2.Response;

public class EditorialManagerController {
	
	@FXML
	private MenuButton adminTools;
	
	@FXML
	private TableView<Editorial> editorialTable;
	
	@FXML
	private TableColumn<Editorial, String> idColumn, nameColumn;
	
	@FXML
	private MFXButton addBtn, updateBtn, deleteBtn, refreshBtn;
	
	private ObservableList<Editorial> editorials = FXCollections.observableArrayList();
	
	private DBCallService dbcs;
	
	public void initialize() throws IOException {
		if (Tools.getRoleFromToken().equals("ROLE_ADMIN")) {
			dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
			getEditorials();
			populateTable();   
		} 			
	}
	
	/**
	 * Metodo para gestionar los eventos de cada boton
	 * en la pantalla de manejo de editoriales
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handleCrudButton(ActionEvent event) throws IOException {
		if (event.getSource() == addBtn) {
			Editorial editorial = new Editorial();
			editorial.setName("");
			dbcs.newEditorial(editorial).execute();			
			getEditorials();
			editorialTable.getItems().setAll(editorials);
		} else if (event.getSource() == updateBtn) {
			if (!editorialTable.getSelectionModel().isEmpty()) {
				dbcs.updateEditorial(editorialTable.getSelectionModel().getSelectedItem()).execute();
				getEditorials();
				editorialTable.getItems().setAll(editorials);
			}
		} else if (event.getSource() == deleteBtn) {
			if (!editorialTable.getSelectionModel().isEmpty()) {
				Editorial selectedEditorial = editorialTable.getSelectionModel().getSelectedItem();
				dbcs.deleteEditorial(selectedEditorial.getId()).execute();
				getEditorials();
				editorialTable.getItems().setAll(editorials);
			}			
		} else {
			getEditorials();
			editorialTable.getItems().setAll(editorials);
		}
	}
	
	/**
	 * Recupera la lista completa de editoriales en el servidor
	 * y la devuelve.
	 * @author Pol Casals
	 * @throws IOException
	 */
	public ObservableList<Editorial> getEditorials() throws IOException {
		Response<ArrayList<Editorial>> findEditorials = dbcs.getEditorials().execute();
		editorials.clear();	
		for (Editorial editorial : findEditorials.body()) {
			editorials.add(editorial);
		}		
		return editorials;
	}
	
	/**
	 * Proporciona a cada celda el valor que corresponde a un objeto
	 * de la clase Editorial con el metodo de {@link TableColumn}
	 * {@link TableColumn#cellValueFactoryProperty() cell value factory}
	 * e indica el tipo de dato que se representa en cada celda y de que
	 * tipo de objeto proviene: <Tipo de objeto(Editorial), Tipo de dato(String)>.
	 * @author Pol Casals
	 */
	@SuppressWarnings("rawtypes")
	public void populateTable() {
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn.setOnEditCommit(e -> e.getRowValue().setName(e.getNewValue()));		
		
		editorialTable.getItems().setAll(editorials);
		
		for (TableColumn col : editorialTable.getColumns().subList(1, editorialTable.getColumns().size())) {
			col.setCellFactory(TextFieldTableCell.forTableColumn());
		}		
	}
}
