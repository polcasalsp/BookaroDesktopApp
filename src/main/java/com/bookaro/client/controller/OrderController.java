package com.bookaro.client.controller;

import java.io.IOException;

import com.bookaro.client.model.Order;
import com.bookaro.client.service.DBCallService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class OrderController {
	@FXML
    private Label orderNoLabel, orderDateLabel, bookNameLabel, orderActiveLabel;

    @FXML
    private ImageView coverImg;
    
    @SuppressWarnings("unused")
	private Order order;
    
    /**
     * Pasa a la vista individual los atributos de cada order que
     * se va a mostrar en la lista de búsqueda.
     * @author Pol Casals
     * @param book
     * @throws IOException 
     */
    public void setData(Order order) throws IOException {
    	this.order = order;
    	Image img = DBCallService.getImageByName(order.getBook().getName());
    	orderNoLabel.setText(String.valueOf(order.getId()));
    	orderDateLabel.setText(String.valueOf(order.getStartDate()));
    	bookNameLabel.setText(order.getBook().getName() + ", " + order.getBook().getAuthorName() + "\n" + order.getBook().getEditorialName());
    	orderActiveLabel.setTextFill(Color.RED);
    	if (order.isActive() ) {
    		orderActiveLabel.setText("Active");
    		orderActiveLabel.setTextFill(Color.GREEN);
    	}  	
    	coverImg.setImage(img);
    }
}
