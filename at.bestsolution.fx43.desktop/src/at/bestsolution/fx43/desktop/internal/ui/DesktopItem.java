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

import java.util.concurrent.atomic.AtomicReference;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;

import at.bestsolution.fx43.desktop.internal.DSRegistryComponent;
import at.bestsolution.fx43.framework.ui.FxApplication;
import at.bestsolution.fx43.framework.ui.FxApplication.LaunchState;
import at.bestsolution.fx43.framework.ui.util.ProgressReporter;

@SuppressWarnings("restriction")
public class DesktopItem {
	private final FxApplication application;
	private final IEclipseContext applicationContext;
	private final DSRegistryComponent registry;
	private GridPane box;
	private final IEventBroker eventBroker;
	
	@Inject
	public DesktopItem(FxApplication application, DSRegistryComponent registry, IEventBroker eventBroker) {
		this.application = application;
		this.applicationContext = createApplicationContext(application);
		this.registry = registry;
		this.eventBroker = eventBroker;
		init();
	}
	
	private void init() {
		box = new GridPane();
		box.getStyleClass().add("desktop-item");
		
		ImageView img = new ImageView(application.getIcon());
		box.getChildren().add(img);
		GridPane.setConstraints(img, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER);
		
		Text label = new Text(application.getName());
		DropShadow shadow = new DropShadow(0, new Color(0, 0, 0, 0.5));
		shadow.setOffsetX(0);
		shadow.setOffsetY(2);
		label.setEffect(shadow);
		box.getChildren().add(label);
		GridPane.setConstraints(label, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);
		
		box.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Node node = registry.getAppNode(application);
				if( node != null ) {
					displayApplication(application);
				} else {
					final AtomicReference<LaunchState> stateRef = new AtomicReference<LaunchState>(LaunchState.LAUNCHING);
					registry.launchApp(application, new ProgressReporter<LaunchState>() {
						
						@Override
						public void state(LaunchState state, String progressInfo, double progress) {
							if( state == LaunchState.DONE ) {
								stateRef.set(state);
							} else {
								System.err.println("Launch progress " + progress);
							}
						}
					}, applicationContext);
					
					if( stateRef.get() == LaunchState.DONE ) {
						displayApplication(application);
					} else {
						new Thread() {
							public void run() {
								while( true ) {
									if( stateRef.get() == LaunchState.DONE ) {
										Platform.runLater(new Runnable() {
											
											@Override
											public void run() {
												displayApplication(application);
											}
										});
										break;
									}
									
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {
									}
								}
							};
						}.start();	
					}
				}
			}
		});
	}
	
	private void displayApplication(FxApplication application) {
		eventBroker.send(EventConstants.APPLICATION_SHOW_TOPIC, application);
	}
	
	private IEclipseContext createApplicationContext(FxApplication application) {
		IEclipseContext appContext = EclipseContextFactory.create();
//		appContext.set(FxApplication.class, application);
		return appContext;
	}
	
	public Node getContentNode() {
		return box;
	}
}
