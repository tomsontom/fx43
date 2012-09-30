/*******************************************************************************
 * Copyright (c) 2012 BestSolution.at and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tom Schindl<tom.schindl@bestsolution.at> - initial API and implementation
 *******************************************************************************/
package at.bestsolution.fx43.apps.browser;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebView;

public class BrowserControl {
	private BorderPane p;
	private Button goButton;
	private ComboBox<String> urlField;
	
	public BrowserControl() {
		p = new BorderPane();
		p.setStyle("-fx-background-color: white");
		
		{
			ToolBar box = new ToolBar();
			
			urlField = new ComboBox<>();
			urlField.setEditable(true);
			urlField.setMaxWidth(3000);
			urlField.setItems(FXCollections.observableArrayList("file:///Users/tomschindl/git/e-fx-clipse/homepage/index.html"));
			box.getItems().add(urlField);
			HBox.setHgrow(urlField, Priority.ALWAYS);
			
			TextField searchField = new TextField();
			searchField.setPromptText("Search");
			box.getItems().add(searchField);
			
			goButton = new Button("Go");
			box.getItems().add(goButton);
			p.setTop(box);
		}
		
		final WebView browser = new WebView();
		goButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				browser.getEngine().load(urlField.getEditor().getText()); 
			}
		});
		p.setCenter(browser);
	}
	
	public Node getNode() {
		return p;
	}
}
