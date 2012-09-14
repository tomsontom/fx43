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

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Callback;

@SuppressWarnings("restriction")
public class DesktopManager {
	private DesktopToolBar toolbar;
	private BorderPane container;
	
	@Inject
	public DesktopManager(IEclipseContext context) {
		this.toolbar = ContextInjectionFactory.make(DesktopToolBar.class,context);
		container = new BorderPane();
		container.setCenter(createApplicationOverview());
		container.setBottom(createToolContent());
	}
	
	private Node createApplicationOverview() {
		Pagination p = new Pagination(3);
		p.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
		p.setPageFactory(new Callback<Integer, Node>() {
			
			@Override
			public Node call(Integer pageIndex) {
				GridPane p = new GridPane();
				p.setPadding(new Insets(10));
				p.setVgap(20);
				
				for( int row = 0; row < pageIndex+1; row++ ) {
					for( int col = 0; col < 8; col++ ) {
						ImageView v = new ImageView(getClass().getClassLoader().getResource("/media/tmp/app_1.png").toExternalForm());
						p.getChildren().add(v);
						GridPane.setColumnIndex(v, col);
						GridPane.setRowIndex(v, row);
						GridPane.setHgrow(v, Priority.ALWAYS);
						GridPane.setHalignment(v, HPos.CENTER);
					}
				}
				
//				p.getChildren().add(new Label("HELLO WORLD: " + pageIndex));
//				p.setStyle("-fx-background-color: red;");
				return p;
			}
		});
		return p;
	}
	
	private Node createToolContent() {
		HBox box = new HBox();
		{
			Node spacer = new Region();
			HBox.setHgrow(spacer, Priority.ALWAYS);
			box.getChildren().add(spacer);
		}
		
		box.getChildren().add(toolbar.getContentNode());

		{
			Node spacer = new Region();
			HBox.setHgrow(spacer, Priority.ALWAYS);
			box.getChildren().add(spacer);
		}
		
		return box;
	}
	
	public Node getContentNode() {
		return container;
	}
}
