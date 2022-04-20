package com.bookaro.client.controller;

import java.io.IOException;
import java.util.Optional;

import com.bookaro.client.Utils.RetrofitClient;
import com.bookaro.client.Utils.Tools;
import com.bookaro.client.model.User;
import com.bookaro.client.service.DBCallService;
import com.bookaro.client.service.LoginService;

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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import retrofit2.Response;

/**
 * 
 * @author Pol Casals
 *@
 */
public class MainController {
	
	@FXML
	private TableView<User> userTable;
	
	@FXML
	private TableColumn<User, String> usernameColumn, passwordColumm, emailColumn, firstNameColumn, lastNameColumn, roleColumn, idColumn;
	
	@FXML
	private Label currentMenuLabel, currentUsernameLabel;
	
	@FXML
	private Button closeBtn, getUsersBtn, logoutBtn, settingsBtn, booksBtn, homeBtn, profileBtn;
	
	@FXML
	private MenuButton adminTools;
	
	@FXML
	private MenuItem manageUsersBtn, manageBooksBtn;
	
	@FXML
	private Pane homePane, booksPane, settingsPane, profilePane, userManagerPane, bookManagerPane;
	
    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;
    
    private String token = LoginService.getLogin().getToken();
	
	private DBCallService dbcs;
	
	/**
	 * @author Pol Casals
	 */
	@FXML
	private void initialize() {
		homePane.toFront();
		Platform.runLater(new Runnable() {
            @Override
            public void run() {	
            	try {
					dbcs = RetrofitClient.getClient(token).create(DBCallService.class);
					User currentUser = getCurrentUser();
					profileBtn.setText(currentUser.getName() + " " + currentUser.getSurname());
	            	
	            	if (Tools.getRoleFromToken(token).equals("ROLE_ADMIN")) {
	            		adminTools.visibleProperty().set(true);		
	            	}
				} catch (IOException e) {
					e.printStackTrace();
				}              	          	
            }
		});		
	}
	
	/**
	 * @author Pol Casals
	 * @param event
	 */
	@FXML
	private void handleButtonAction(ActionEvent event) {
		if (event.getSource() == profileBtn) {
			currentMenuLabel.setText("Profile");
			profilePane.toFront();
		} else if (event.getSource() == booksBtn) {
			currentMenuLabel.setText("Books");
			booksPane.toFront();
		} else if (event.getSource() == settingsBtn) {
			currentMenuLabel.setText("Settings");
			settingsPane.toFront();
		} else if (event.getSource() == manageUsersBtn){
			currentMenuLabel.setText("Admin Tools - Manage Users");			
			userManagerPane.toFront();
		} else if (event.getSource() == manageBooksBtn){
			currentMenuLabel.setText("Admin Tools - Manage Books");
			bookManagerPane.toFront();
		} else {
			currentMenuLabel.setText("Home");
			homePane.toFront();
		}
	}
	
	/**
	 * @author Pol Casals
	 * @throws IOException
	 */
	public User getCurrentUser() throws IOException {
		Response<User> findUser = dbcs.findByUsername(Tools.getUsernameFromToken(token)).execute();
    	if (findUser != null) {    
        	return findUser.body();
    	}
    	return null;
	}
	
	
	/**
	 * @author Pol Casals
	 * @throws IOException
	 */
	public void logout() throws IOException {
		Alert logoutConfirmation = Tools.confirmationAlert(closeBtn.getScene().getWindow(),
				"Logout", "Are you sure you want to logout from Bookaro?");
		Optional<ButtonType> result = logoutConfirmation.showAndWait();
		if (result.get() == ButtonType.OK) {
			dbcs.logout().execute();
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
	 * @author Pol Casals
	 * @param event
	 * @throws IOException
	 */
	public void close (ActionEvent event) throws IOException {
		Platform.exit();
	}
}
 