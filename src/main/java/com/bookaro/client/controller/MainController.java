package com.bookaro.client.controller;

import java.io.IOException;
import java.util.Optional;

import com.bookaro.client.Utils.Tools;
import com.bookaro.client.model.User;
import com.bookaro.client.service.DBCallService;
import com.bookaro.client.service.LoginService;
import com.bookaro.client.service.NetClientsService;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import retrofit2.Response;

/**
 * 
 * @author Pol Casals
 *
 */
public class MainController {	
	
	@FXML
	private Label currentMenuLabel, currentUsernameLabel;
	
	@FXML
	private Button closeBtn, logoutBtn, subsBtn, booksBtn, homeBtn, manageProfileBtn;
	
	@FXML
	private MenuButton adminTools;
	
	@FXML
	private MenuItem manageClientsBtn, manageEmployeesBtn, manageBooksBtn, manageAuthorsBtn, manageEditorialsBtn, manageOrdersBtn;
	
	@FXML
	private Pane searchBooksPane, homePane, manageSubscriptionsPane, manageProfilePane, clientManagerPane, employeeManagerPane, bookManagerPane, authorManagerPane, editorialManagerPane, orderManagerPane;	
	
	private DBCallService dbcs;
	
	/**
	 * @author Pol Casals
	 */
	@FXML
	private void initialize() {
		homePane.toFront();
		
    	try {
			dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
			User currentUser = getCurrentUser();
			manageProfileBtn.setText(currentUser.getName() + "\n" + currentUser.getSurname());
			if (Tools.getRoleFromToken().equals("ROLE_ADMIN")) {
	    		adminTools.visibleProperty().set(true);		
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Maneja los eventos de click en los diferentes botones de la 
	 * vista principal.
	 * @author Pol Casals
	 * @param event
	 */
	@FXML
	private void handleButtonAction(ActionEvent event) {
		if (event.getSource() == manageProfileBtn) {
			currentMenuLabel.setText("Manage Profile");
			manageProfilePane.toFront();
		} else if (event.getSource() == booksBtn) {
			currentMenuLabel.setText("Search Books");
			searchBooksPane.toFront();
		} else if (event.getSource() == subsBtn) {
			currentMenuLabel.setText("Subscriptions");
			manageSubscriptionsPane.toFront();
		} else if (event.getSource() == manageClientsBtn){
			currentMenuLabel.setText("Admin Tools - Manage Clients");			
			clientManagerPane.toFront();
		} else if (event.getSource() == manageEmployeesBtn){
			currentMenuLabel.setText("Admin Tools - Manage Employees");	
			employeeManagerPane.toFront();
		} else if (event.getSource() == manageBooksBtn){
			currentMenuLabel.setText("Admin Tools - Manage Books");
			bookManagerPane.toFront();
		} else if (event.getSource() == manageAuthorsBtn){
			currentMenuLabel.setText("Admin Tools - Manage Authors");
			authorManagerPane.toFront();
		} else if (event.getSource() == manageEditorialsBtn){
			currentMenuLabel.setText("Admin Tools - Manage Editorials");
			editorialManagerPane.toFront();
		} else if (event.getSource() == manageOrdersBtn){
			currentMenuLabel.setText("Admin Tools - Manage Orders");
			orderManagerPane.toFront();
		} else {
			currentMenuLabel.setText("Home");
			homePane.toFront();
		}
	}
	
	/**
	 * Recupera el usuario actualmente autenticado en el servidor
	 * y lo devuelve. Si no se puede encontrar devuelve null.
	 * @author Pol Casals
	 * @throws IOException
	 */
	public User getCurrentUser() throws IOException {
		Response<User> findUser = dbcs.findByUsername(Tools.getUsernameFromToken()).execute();
    	if (findUser != null) {    
        	return findUser.body();
    	}
    	return null;
	}
	
	
	/**
	 * Verifica con una notificación que precisa que el usuario acepte 
	 * previamente, y si se da el caso se procede a cerrar la sesión
	 * @implNote Falta la llamada de un metodo que avise al servidor
	 * para que desactive/liste el token para que no se pueda volver a 
	 * utilizar.
	 * @author Pol Casals
	 * @throws IOException
	 */
	public void logout() throws IOException {
		Alert logoutConfirmation = Tools.confirmationAlert(closeBtn.getScene().getWindow(),
				"Logout", "Are you sure you want to logout from Bookaro?");
		Optional<ButtonType> result = logoutConfirmation.showAndWait();
		if (result.get() == ButtonType.OK) {
			dbcs.logout().execute();
			LoginService.getLogin().logout();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
	        Parent loginScreen = loader.load();
	        Stage stage = (Stage)logoutBtn.getScene().getWindow();
	        Tools.draggableWindow(stage, loginScreen);
	        Scene scene = new Scene(loginScreen, 1000, 600);
	        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
	        stage.setScene(scene);
		}		
	}
	
	/**
	 * Escucha al evento del boton de cerrar ventana.
	 * @author Pol Casals
	 * @param event
	 * @throws IOException
	 */
	public void close (ActionEvent event) throws IOException {
		Platform.exit();
	}
}
 
