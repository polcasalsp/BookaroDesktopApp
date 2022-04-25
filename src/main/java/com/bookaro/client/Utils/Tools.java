package com.bookaro.client.Utils;

import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * @author Pol Casals
 *
 */
public class Tools {
	
	/**
	 * Alerta tipo error.
	 * @author Pol Casals
	 * @param alertType
	 * Tipo de la alerta.
	 * @param owner
	 * Objeto del que procede la alerta
	 * @param title
	 * Titulo de la ventana para la alerta
	 * @param message
	 * Mensaje que muestra la alerta al usuario
	 */
    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    
    /**
     * Alerta confirmación necesaria.
     * @author Pol Casals
     * @param owner
     * Objeto del que procede la alerta
     * @param title
     * Titulo de la ventana para la alerta
     * @param message
     * Mensaje que muestra la alerta al usuario
     * @return
     * Devuelve la alerta para ser mostrada y tratar la respuesta del usuario.
     */
    public static Alert confirmationAlert(Window owner, String title, String message) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
		return alert;    	
    }
    
    /**
     * Recibe el token en formato String, lo decodifica y
     * obtiene los roles/derechos del usuario autenticado en el servidor
     * @author Pol Casals
     * @param token
     * @return
     */
    public static String getRoleFromToken(String token) {
    	String[] chunks = token.split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String payload = new String(decoder.decode(chunks[1]));
		JSONObject jsonPayload = new JSONObject(payload);
		JSONArray authorities = jsonPayload.getJSONArray("role");
		return authorities.get(0).toString();
    }
    
    /**
     * Recibe el token en formato String, lo decodifica y
     * obtiene el nombre del usuario autenticado en el servidor
     * @author Pol Casals
     * @param token
     * @return
     */
    public static String getUsernameFromToken(String token) {
    	String[] chunks = token.split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String payload = new String(decoder.decode(chunks[1]));
		JSONObject jsonPayload = new JSONObject(payload);
		return jsonPayload.get("sub").toString();
    }
    
    /**
     * Recibe un objeto Stage y le da la propiedad de poder
     * ser movido al clickar encima y arrastrarlo con el puntero
     * del ratón.
     * @author Pol Casals
     * @param stage
     * @param parent
     */
    public static void draggableWindow(Stage stage, Parent parent) {
    	parent.setOnMousePressed(pressEvent -> {
    		parent.setOnMouseDragged(dragEvent -> {
		    	stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
		    	stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
		    });
		});	
    }
}
