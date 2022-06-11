package com.bookaro.client.controller;

import java.io.IOException;

import com.bookaro.client.Utils.Tools;
import com.bookaro.client.model.Client;
import com.bookaro.client.model.Subscription;
import com.bookaro.client.service.DBCallService;
import com.bookaro.client.service.NetClientsService;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ManageSubscriptionsController {

	@FXML
	MFXButton selectBasicBtn, selectFamiliarBtn;
	
	DBCallService dbcs;
	
	@FXML
	private void initialize() throws IOException {
		dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
		if (Tools.getRoleFromToken().equals("ROLE_USER")) {			
			checkActiveSubscription();
		}		
	}
	/**
	 * Comprova quina subscripcio te l'usuari, desactiva el boto corresponent
	 * a la mateixa i activa l'altre.
	 * @author Pol Casals
	 * @throws IOException
	 */
	private void checkActiveSubscription() throws IOException {
		if (Tools.getCurrentClient().getSubscription().getType().equals("Familiar")) {
			selectFamiliarBtn.setDisable(true);
			selectBasicBtn.setDisable(false);
		} else if (Tools.getCurrentClient().getSubscription().getType().equals("Básica")) {
			selectBasicBtn.setDisable(true);
			selectFamiliarBtn.setDisable(false);
		}
	}
	
	/**
	 * Gestiona els events al clickar els botons de canvi de subscripcio.
	 * @author Pol Casals
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void selectHandle (ActionEvent event) throws IOException {
		Client cli = dbcs.findClientByUsername(Tools.getUsernameFromToken()).execute().body();
		Subscription sub = cli.getSubscription();
		if (event.getSource() == selectBasicBtn) {
			sub = dbcs.findSubById(2L).execute().body();
		} else {
			sub = dbcs.findSubById(1L).execute().body();
		}
		cli.setSubscription(sub);
		dbcs.updateClient(cli).execute();
		checkActiveSubscription();
	}
}
