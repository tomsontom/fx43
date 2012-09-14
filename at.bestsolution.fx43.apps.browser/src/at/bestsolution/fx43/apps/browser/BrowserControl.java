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

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebView;

public class BrowserControl {
	private BorderPane p;
	
	public BrowserControl() {
		p = new BorderPane();
		
		{
			HBox box = new HBox();
			TextField urlField = new TextField();
			box.getChildren().add(urlField);
			HBox.setHgrow(urlField, Priority.ALWAYS);
			
			Button b = new Button("Go");
			box.getChildren().add(b);
			p.setTop(box);			
		}
		
		WebView browser = new WebView();
		browser.getEngine().load("http://www.bestsolution.at");
		p.setCenter(browser);
	}
	
	public Node getNode() {
		return p;
	}
}
