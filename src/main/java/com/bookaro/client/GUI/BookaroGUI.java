package com.bookaro.client.GUI;

import org.springframework.boot.SpringApplication;

import com.bookaro.client.Utils.Tools;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Pol Casals
 *
 */
public class BookaroGUI extends Application {
	
	/**
	 * @author Pol Casals
	 */
	@Override
	public void init() throws Exception {
		SpringApplication.run(getClass()).getAutowireCapableBeanFactory().autowireBean(this);
	}

	/**
	 * @author Pol Casals
	 */
	@Override
	public void start(final Stage stage) throws Exception {	

		Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
		Tools.draggableWindow(stage, root);			
		stage.setScene(new Scene(root));
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
	}	

	/**
	 * @author Pol Casals
	 */
	@Override
	public void stop() throws Exception {
		Platform.exit();
	}
}