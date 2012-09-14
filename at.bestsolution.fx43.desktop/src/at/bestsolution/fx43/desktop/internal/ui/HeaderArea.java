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

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * @author tomschindl
 *
 */
public class HeaderArea {
	private HBox box;
	
	public HeaderArea() {
		box = new HBox();
		box.getStyleClass().add("desktop-header");
		
		Node wireless = new Label("Wireless");
		wireless.getStyleClass().add("wireless");
		box.getChildren().add(wireless);
		
		{
			Node spacer = new Region();
			HBox.setHgrow(spacer, Priority.ALWAYS);
			box.getChildren().add(spacer);
		}
		
		Label clock = new Label("14:04");
		clock.getStyleClass().add("clock");
		box.getChildren().add(clock);

		{
			Node spacer = new Region();
			HBox.setHgrow(spacer, Priority.ALWAYS);
			box.getChildren().add(spacer);
		}
		
		Node battery = new Label("battery");
		battery.getStyleClass().add("battery");
		box.getChildren().add(battery);		
	}
	
	public Node getContentNode() {
		return box;
	}
}
