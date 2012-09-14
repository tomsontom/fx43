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

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class DesktopUI {
	private DesktopManager desktopManagerArea;
	private HeaderArea headerArea;
	
	@Inject
	public DesktopUI(Stage stage, IEclipseContext context) {
		headerArea = ContextInjectionFactory.make(HeaderArea.class, context);
		desktopManagerArea = ContextInjectionFactory.make(DesktopManager.class,context);
		
		BorderPane pane = new BorderPane();
		pane.setTop(headerArea.getContentNode());
		pane.setCenter(desktopManagerArea.getContentNode());
		pane.getStyleClass().add("desktop");
		
		Scene s = new Scene(pane);
		s.getStylesheets().add(getClass().getClassLoader().getResource("/css/desktop-default.css").toExternalForm());
		stage.setScene(s);
	}
	
}
