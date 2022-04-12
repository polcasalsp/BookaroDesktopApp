package com.bookaro.client.controller;

import com.bookaro.client.model.Book;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BookController {

    @FXML
    private Label authorLabel;

    @FXML
    private Label nameLabel;
    
    @SuppressWarnings("unused")
	private Book book;
    
    public void setData(Book book) {
    	this.book = book;
    	nameLabel.setText(book.getName());
    	authorLabel.setText(book.getAuthor());
    }
}
