package com.bookaro.client.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bookaro.client.Utils.RetrofitClient;
import com.bookaro.client.Utils.Tools;
import com.bookaro.client.model.Book;
import com.bookaro.client.model.User;
import com.bookaro.client.service.DBCallService;
import com.bookaro.client.service.LoginService;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
	private ComboBox<String> bookSearchBy;
	
	@FXML
	private TextField bookSearchBar;
	
    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;
    
    private String token = LoginService.getLogin().getToken();
    
    private List<Book> books = new ArrayList<Book>();
	
	private ObservableList<Book> updatedBooks = FXCollections.observableArrayList();
	
	private ObservableList<User> updatedUsers = FXCollections.observableArrayList();
	
	private DBCallService dbcs;
	
	/**
	 * @author Pol Casals
	 */
	@FXML
	private void initialize() {
		homePane.toFront();
		bookSearchBy.getItems().addAll("name", "author", "isbn", "category", "editorial");
		bookSearchBy.setValue("name");
		Platform.runLater(new Runnable() {
            @Override
            public void run() {	
            	try {
					dbcs = RetrofitClient.getClient(token).create(DBCallService.class);
					User currentUser = getCurrentUser();
					profileBtn.setText(currentUser.getFirstName() + " " + currentUser.getLastName()); 
	            	
	            	if (Tools.getRoleFromToken(token).equals("ROLE_ADMIN")) {
	            		adminTools.visibleProperty().set(true);		
	            		getUsers();
	            		populateUsersTable();
	            	}
				} catch (IOException e) {
					e.printStackTrace();
				}              	          	
            }
		});		
	}
	
	/**
	 * Maneja los eventos de click en los diferentes botones de la 
	 * vista principal.
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
	 * Recupera el usuario actualmente autenticado en el servidor
	 * y lo devuelve. Si no se puede encontrar devuelve null.
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
	 * Recupera la lista completa de usuarios en el servidor
	 * y la devuelve.
	 * @author Pol Casals
	 * @throws IOException
	 */
	public ObservableList<User> getUsers() throws IOException {
		Response<ArrayList<User>> findUsers = dbcs.getUsers().execute();		
		for (User user : findUsers.body()) {
			updatedUsers.add(user);
		}		
		return updatedUsers;
	}
	
	/**
	 * Proporciona a cada celda el valor que corresponde a un objeto
	 * de la clase User con el metodo de {@link TableColumn}
	 * {@link TableColumn#cellValueFactoryProperty() cell value factory}
	 * e indica el tipo de dato que se representa en cada celda y de que
	 * tipo de objeto proviene: <Tipo de objeto(User), Tipo de dato(String)>.
	 * @author Pol Casals
	 */
	public void populateUsersTable() {
		idColumn.setCellValueFactory(new PropertyValueFactory<User, String>(String.valueOf("id")));
		usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
		passwordColumm.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
		roleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
		userTable.getItems().setAll(updatedUsers);
		
		for (TableColumn col : userTable.getColumns().subList(1, userTable.getColumns().size())) {
			col.setCellFactory(TextFieldTableCell.forTableColumn());
		}
		
	}
	
	/**
	 * Filtra las búsquedas de libros por diferentes criterios
	 * @implNote En un futuro se debera filtrar en el backend
	 * para evitar enviar y recibir mas datos de los necesarios
	 * @author Pol Casals
	 * @throws IOException
	 */
	public void getBooks() throws IOException {			
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
 