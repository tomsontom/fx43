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

import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.PathTransitionBuilder;
import javafx.animation.RotateTransition;
import javafx.animation.RotateTransitionBuilder;
import javafx.animation.ScaleTransition;
import javafx.animation.ScaleTransitionBuilder;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.SVGPathBuilder;
import javafx.scene.transform.ScaleBuilder;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import at.bestsolution.fx43.desktop.internal.DSRegistryComponent;
import at.bestsolution.fx43.desktop.internal.ui.DesktopItem.DisplayAppData;
import at.bestsolution.fx43.framework.ui.FxApplication;

@SuppressWarnings("restriction")
public class DesktopUI {
	private DesktopManager desktopManagerArea;
	private HeaderArea headerArea;
	private final DSRegistryComponent registry;
	private BorderPane pane;
	private DisplayAppData currentShowingApp;
	
	@Inject
	public DesktopUI(Stage stage, IEclipseContext context, IEventBroker broker, DSRegistryComponent registry) {
		headerArea = ContextInjectionFactory.make(HeaderArea.class, context);
		desktopManagerArea = ContextInjectionFactory.make(DesktopManager.class,context);
		this.registry = registry;
		
		pane = new BorderPane();
		Node headerNode = headerArea.getContentNode();
		headerNode.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if( currentShowingApp != null ) {
					hideApplication(currentShowingApp);	
				}
			}
		});
		pane.setTop(headerNode);
		pane.setCenter(desktopManagerArea.getContentNode());
		pane.getStyleClass().add("desktop");
		
		Scene s = new Scene(pane);
		s.getStylesheets().add(getClass().getClassLoader().getResource("/css/desktop-default.css").toExternalForm());
		stage.setScene(s);
		broker.subscribe(EventConstants.APPLICATION_SHOW_TOPIC, new EventHandler() {
			
			@Override
			public void handleEvent(Event event) {
				showApplication((DisplayAppData) event.getProperty(IEventBroker.DATA));
			}
		});
	}
	
	void hideApplication(DisplayAppData displayData) {
		final Node appNode = registry.getAppNode(displayData.application);
		
		Bounds b = pane.getCenter().getLayoutBounds();
		appNode.resizeRelocate(0, pane.getTop().getLayoutBounds().getHeight(),b.getWidth(), b.getHeight());
		
		double to = 1.1;
		
		Duration d = new Duration(600);
		ScaleTransition scale = ScaleTransitionBuilder.create().duration(d).interpolator(Interpolator.EASE_BOTH).fromX(to).toX(0.0).fromY(to).toY(0.0).build();
		FadeTransition fade = FadeTransitionBuilder.create().duration(d).interpolator(Interpolator.EASE_BOTH).fromValue(1.0).toValue(0.0).build();
		
		ParallelTransition scaleOverflow = new ParallelTransition(appNode, scale, fade);
		
		ScaleTransition scaleNormal = ScaleTransitionBuilder.create().duration(new Duration(100)).interpolator(Interpolator.EASE_BOTH).fromX(1.0).toX(to).fromY(1.0).toY(to).build();
		
		SequentialTransition s = new SequentialTransition(appNode, scaleNormal, scaleOverflow);
		s.setDelay(new Duration(200));
		s.setOnFinished(new javafx.event.EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				pane.getChildren().remove(appNode);
				currentShowingApp = null;
			}
		});
		s.play();		
	}
	
	void showApplication(DisplayAppData displayData) {
		this.currentShowingApp = displayData;
		Node appNode = registry.getAppNode(displayData.application);
		appNode.setOpacity(0.0);
		final Node iconNode = displayData.item.getContentNode();
		pane.getChildren().add(appNode);
		
		Bounds b = pane.getCenter().getLayoutBounds();
		appNode.resizeRelocate(0, pane.getTop().getLayoutBounds().getHeight(),b.getWidth(), b.getHeight());
		
		double to = 1.1;
		
		Duration d = new Duration(800);
		ScaleTransition scale = ScaleTransitionBuilder.create().duration(d).interpolator(Interpolator.EASE_BOTH).fromX(0.0).toX(to).fromY(0.0).toY(to).build();
		FadeTransition fade = FadeTransitionBuilder.create().duration(d).interpolator(Interpolator.EASE_BOTH).fromValue(0.0).toValue(1.0).build();
//		TranslateTransition translate = TranslateTransitionBuilder.create().duration(new Duration(200)).build();
		
		Bounds iconBounds = iconNode.getLayoutBounds();  
		
		double iconX = iconBounds.getMinX() + iconBounds.getWidth()/2;
		double iconY = iconBounds.getMinY() + iconBounds.getHeight()/2;
		
		Bounds paneBounds = pane.getLayoutBounds();
		
		double paneX = paneBounds.getMinX() + paneBounds.getWidth()/2;
		double paneY = paneBounds.getMinY() + paneBounds.getHeight()/2;
		
		String svgPath = "M" + iconX + " " + iconY + " ";
		svgPath += "Q" + paneX + " " + iconY + " " + paneX + " " + paneY;
		SVGPath p = SVGPathBuilder.create().content(svgPath).build();
		
		double scaleIconV = 2;
		
		PathTransition path = PathTransitionBuilder.create().node(iconNode).path(p).duration(new Duration(900)).build();
//		RotateTransition rotate = RotateTransitionBuilder.create().node(iconNode).duration(new Duration(900)).byAngle(720).build();
		ScaleTransition scaleIcon = ScaleTransitionBuilder.create().node(iconNode).duration(new Duration(900)).toX(scaleIconV).toY(scaleIconV).build();
		
		ScaleTransition iconOut = ScaleTransitionBuilder.create().node(iconNode).duration(d).toX(8.0).toY(8.0).build();
		FadeTransition iconFade = FadeTransitionBuilder.create().node(iconNode).duration(d).toValue(0.0).build();
		
		ParallelTransition iconTrans = new ParallelTransition(path, scaleIcon);
		ParallelTransition scaleOverflow = new ParallelTransition(appNode, scale, fade, iconFade, iconOut);
		
		ScaleTransition scaleNormal = ScaleTransitionBuilder.create().duration(new Duration(100)).interpolator(Interpolator.EASE_BOTH).fromX(to).toX(1.0).fromY(to).toY(1.0).build();
		
		SequentialTransition s = new SequentialTransition(appNode, iconTrans, scaleOverflow, scaleNormal);
		s.setDelay(new Duration(200));
		s.setOnFinished(new javafx.event.EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				iconNode.setOpacity(1.0);
				iconNode.setTranslateX(0);
				iconNode.setTranslateY(0);
				iconNode.setScaleX(1);
				iconNode.setScaleY(1);
			}
		});
		s.play();
		
		//pane.setCenter(registry.getAppNode(application));
	}
}
