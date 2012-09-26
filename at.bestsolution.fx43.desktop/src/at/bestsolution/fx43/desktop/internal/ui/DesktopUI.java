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

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import at.bestsolution.fx43.desktop.internal.DSRegistryComponent;
import at.bestsolution.fx43.framework.ui.FxApplication;

@SuppressWarnings("restriction")
public class DesktopUI {
	private DesktopManager desktopManagerArea;
	private HeaderArea headerArea;
	private final DSRegistryComponent registry;
	private BorderPane pane;
	
	@Inject
	public DesktopUI(Stage stage, IEclipseContext context, IEventBroker broker, DSRegistryComponent registry) {
		headerArea = ContextInjectionFactory.make(HeaderArea.class, context);
		desktopManagerArea = ContextInjectionFactory.make(DesktopManager.class,context);
		this.registry = registry;
		
		pane = new BorderPane();
		pane.setTop(headerArea.getContentNode());
		pane.setCenter(desktopManagerArea.getContentNode());
		pane.getStyleClass().add("desktop");
		
		Scene s = new Scene(pane);
		s.getStylesheets().add(getClass().getClassLoader().getResource("/css/desktop-default.css").toExternalForm());
		stage.setScene(s);
		broker.subscribe(EventConstants.APPLICATION_SHOW_TOPIC, new EventHandler() {
			
			@Override
			public void handleEvent(Event event) {
				showApplication((FxApplication) event.getProperty(IEventBroker.DATA));
			}
		});
	}
	
	void showApplication(FxApplication application) {
		pane.setCenter(registry.getAppNode(application));
	}
}
