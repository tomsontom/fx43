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
import javafx.scene.layout.GridPane;

public class DesktopManagerPage {
	private GridPane p;
	
	public DesktopManagerPage(GridPane p) {
		p = new GridPane();
		p.setPadding(new Insets(10));
		p.setVgap(20);
		
		
//			for( int col = 0; col < 8; col++ ) {
//				ImageView v = new ImageView(getClass().getClassLoader().getResource("/media/tmp/app_1.png").toExternalForm());
//				p.getChildren().add(v);
//				GridPane.setColumnIndex(v, col);
//				GridPane.setRowIndex(v, row);
//				GridPane.setHgrow(v, Priority.ALWAYS);
//				GridPane.setHalignment(v, HPos.CENTER);
//			}
	}
	
	public Node getContentNode() {
		return p;
	}
}
