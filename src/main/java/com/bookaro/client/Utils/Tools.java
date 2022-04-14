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
	 * @author Pol Casals
	 * @param alertType
	 * @param owner
	 * @param title
	 * @param message
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
     * @author Pol Casals
     * @param owner
     * @param title
     * @param message
     * @return
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
     * @author Pol Casals
     * @param token
     * @return
     */
    public static String getRoleFromToken(String token) {
    	String[] chunks = token.split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String payload = new String(decoder.decode(chunks[1]));
		JSONObject jsonPayload = new JSONObject(payload);
		JSONArray authorities = jsonPayload.getJSONArray("authorities");
		return authorities.get(0).toString();
    }
    
    /**
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
