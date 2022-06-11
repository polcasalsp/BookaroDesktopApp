package com.bookaro.client.controller;

import java.io.IOException;

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
     * @throws IOException 
     */
    public void setData(Book book) throws IOException {
    	this.book = book;
    	Image img = DBCallService.getImageByName(book.getName());
    	nameLabel.setText(book.getName());
    	authorLabel.setText(book.getAuthor().getName());
    	coverImg.setImage(img);
    }
    
	
}
