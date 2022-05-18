package com.bookaro.client.controller;

import com.bookaro.client.model.Book;
import com.bookaro.client.service.DBCallService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * 
 * @author Pol Casals
 *
 */
public class BookController {

    @FXML
    private Label authorLabel, nameLabel;

    @FXML
    private ImageView coverImg;
    
    @SuppressWarnings("unused")
	private Book book;
    
    /**
     * Pasa a la vista individual los atributos de cada libro que
     * se va a mostrar en la lista de búsqueda.
     * @author Pol Casals
     * @param book
     */
    public void setData(Book book) {
    	this.book = book;
    	Image img = DBCallService.getImageByName("got.png");
    	nameLabel.setText(book.getName());
    	authorLabel.setText(book.getAuthor());
    	coverImg.setImage(img);
    }
}