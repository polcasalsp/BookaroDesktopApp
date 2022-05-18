package com.bookaro.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.application.Application;

@SpringBootApplication
public class Bookaro {
	
	public static void main(String[] args) {
		Application.launch(BookaroGUI.class);
	}
}