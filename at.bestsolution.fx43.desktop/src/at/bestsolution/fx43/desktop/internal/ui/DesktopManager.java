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
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Callback;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;

import at.bestsolution.fx43.desktop.internal.DSRegistryComponent;
import at.bestsolution.fx43.desktop.internal.model.MDesktop;
import at.bestsolution.fx43.desktop.internal.model.MDesktopPage;
import at.bestsolution.fx43.desktop.internal.model.ModelPersitence;
import at.bestsolution.fx43.framework.ui.FxApplication;

@SuppressWarnings("restriction")
public class DesktopManager {
	private DesktopToolBar toolbar;
	private BorderPane container;
	
	@Inject
	private DSRegistryComponent registry;
	
	@Inject
	private IEventBroker eventBroker;
	
	@Inject
	public DesktopManager(IEclipseContext context) {
		this.toolbar = ContextInjectionFactory.make(DesktopToolBar.class,context);
		container = new BorderPane();
		container.setCenter(createApplicationOverview(context));
		container.setBottom(createToolContent());
	}
	
	private Node createApplicationOverview(IEclipseContext context) {
		ModelPersitence desktopPersistance = ContextInjectionFactory.make(ModelPersitence.class, context);
		final MDesktop desktop = desktopPersistance.loadDesktopConfiguration();
		
		Pagination p = new Pagination(desktop.getPages().size());
		p.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
		
		p.setPageFactory(new Callback<Integer, Node>() {
			
			@Override
			public Node call(Integer pageIndex) {
				MDesktopPage page = desktop.getPages().get(pageIndex);
				
				GridPane p = new GridPane();
				p.setPadding(new Insets(10));
				p.setVgap(20);
				
				int columnIndex = 0;
				int rowIndex = 0;
				
				for( String app : page.getApplications() ) {
					FxApplication fxApplication = registry.findApplicationById(app);
					DesktopItem e = new DesktopItem(fxApplication, registry, eventBroker);
					Node n = e.getContentNode();
					GridPane.setColumnIndex(n, columnIndex++);
					GridPane.setRowIndex(n, rowIndex);
					p.getChildren().add(n);
					
					if( columnIndex == 7 ) {
						columnIndex = 0;
						rowIndex += 1;
					}
					
				}
				
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
