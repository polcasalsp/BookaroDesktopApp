package com.bookaro.client.controller;

import com.bookaro.client.model.Book;

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
     * @author Pol Casals
     * @param book
     */
    public void setData(Book book) {
    	this.book = book;
    	Image img = new Image("/assets/got.jpg");
    	nameLabel.setText(book.getName());
    	authorLabel.setText(book.getAuthor());
    	coverImg.setImage(img);
    }
}
