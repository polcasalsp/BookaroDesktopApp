package com.bookaro.client.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.bookaro.client.Utils.Tools;
import com.bookaro.client.model.Employee;
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
import javafx.util.converter.DoubleStringConverter;
import retrofit2.Response;

public class EmployeeManagerController {
	
	@FXML
	private MenuButton adminTools;
	
	@FXML
	private TableView<Employee> employeeTable;
	
	@FXML
	private TableColumn<Employee, String> usernameColumn, passwordColumm, emailColumn, firstNameColumn, lastNameColumn, idColumn, positionColumn;
	
	@FXML
	private TableColumn<Employee, Double> salaryColumn;
	
	@FXML
	private MFXButton addBtn, updateBtn, deleteBtn, refreshBtn;
	
	private ObservableList<Employee> Employees = FXCollections.observableArrayList();
	
	private DBCallService dbcs;
	
	public void initialize() throws IOException {
		if (Tools.getRoleFromToken().equals("ROLE_ADMIN")) {
			dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
			getEmployees();
			populateTable();   	
		} 			
	}
	
	/**
	 * Metodo para gestionar los eventos de cada boton
	 * en la pantalla de manejo de empleados
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handleCrudButton(ActionEvent event) throws IOException {
		if (event.getSource() == addBtn) {
			Employee emp = new Employee();
			emp.setUsername("");
			emp.setPassword("");
			emp.setEmail("");
			dbcs.newEmployee(emp).execute();			
			getEmployees();
			employeeTable.getItems().setAll(Employees);
		} else if (event.getSource() == updateBtn) {
			if (!employeeTable.getSelectionModel().isEmpty()) {
				dbcs.updateEmployeeAdmin(employeeTable.getSelectionModel().getSelectedItem()).execute();
				getEmployees();
				employeeTable.getItems().setAll(Employees);
			}
		} else if (event.getSource() == deleteBtn) {
			if (!employeeTable.getSelectionModel().isEmpty()) {
				dbcs.deleteEmployee(employeeTable.getSelectionModel().getSelectedItem().getId()).execute();
				getEmployees();
				employeeTable.getItems().setAll(Employees);
			}			
		} else {
			getEmployees();
			employeeTable.getItems().setAll(Employees);
		}
	}
	
	/**
	 * Recupera la lista completa de empleados en el servidor
	 * y la devuelve.
	 * @author Pol Casals
	 * @throws IOException
	 */
	public ObservableList<Employee> getEmployees() throws IOException {
		Response<ArrayList<Employee>> findEmployees = dbcs.getEmployees().execute();
		Employees.clear();
		for (Employee employee : findEmployees.body()) {
			Employees.add(employee);
		}		
		return Employees;
	}
	
	/**
	 * Proporciona a cada celda el valor que corresponde a un objeto
	 * de la clase Employee con el metodo de {@link TableColumn}
	 * {@link TableColumn#cellValueFactoryProperty() cell value factory}
	 * e indica el tipo de dato que se representa en cada celda y de que
	 * tipo de objeto proviene: <Tipo de objeto(Employee), Tipo de dato(String)>.
	 * @author Pol Casals
	 */

	public void populateTable() {
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		
		usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		usernameColumn.setOnEditCommit(e -> e.getRowValue().setUsername(e.getNewValue()));		
		
		passwordColumm.setCellValueFactory(new PropertyValueFactory<>("password"));
		passwordColumm.setOnEditCommit(e -> e.getRowValue().setPassword(e.getNewValue()));
		
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		emailColumn.setOnEditCommit(e -> e.getRowValue().setEmail(e.getNewValue()));
		
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		firstNameColumn.setOnEditCommit(e -> e.getRowValue().setName(e.getNewValue()));
		
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
		lastNameColumn.setOnEditCommit(e -> e.getRowValue().setSurname(e.getNewValue()));
		
		positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
		positionColumn.setOnEditCommit(e -> e.getRowValue().setPosition(e.getNewValue()));
		
		salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
		salaryColumn.setOnEditCommit(e -> e.getRowValue().setSalary(e.getNewValue()));
		
		employeeTable.getItems().setAll(Employees);		
		
		for (TableColumn col : employeeTable.getColumns().subList(1, employeeTable.getColumns().size())) {
			col.setCellFactory(TextFieldTableCell.forTableColumn());
		}		
		
		salaryColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
	}
}
