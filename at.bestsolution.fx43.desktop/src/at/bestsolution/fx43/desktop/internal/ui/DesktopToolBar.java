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
package at.bestsolution.fx43.desktop.internal.ui;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class DesktopToolBar {
	private HBox pane;
	
	public DesktopToolBar() {
		pane = new HBox(35);
		pane.setPadding(new Insets(0,20,20,20));
		
		pane.getChildren().add(createAppIconNode("/media/tmp/browser.png"));
		pane.getChildren().add(createAppIconNode("/media/tmp/media.png"));
		pane.getChildren().add(createAppIconNode("/media/tmp/twitter.png"));
		pane.getChildren().add(createAppIconNode("/media/tmp/clock.png"));		
	}
	
	private Node createAppIconNode(String imageUrl) {
		ImageView v = new ImageView(new Image(getClass().getClassLoader().getResource(imageUrl).toExternalForm()));
		v.setEffect(new Reflection());
		return v;
	}
	
	public Node getContentNode() {
		return pane;
	}
}