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

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.TransferMode;
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
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

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

	private final IEventBroker eventBroker;
	private Pagination pageControl;

	private Map<Integer, GridPane> pageMap = new HashMap<Integer, GridPane>();

	@Inject
	public DesktopManager(IEclipseContext context, IEventBroker eventBroker) {
		this.eventBroker = eventBroker;
		this.toolbar = ContextInjectionFactory.make(DesktopToolBar.class, context);
		container = new BorderPane();
		container.setCenter(createApplicationOverview(context));
		container.setBottom(createToolContent());
	}

	private Node createApplicationOverview(IEclipseContext context) {
		ModelPersitence desktopPersistance = ContextInjectionFactory.make(ModelPersitence.class, context);
		final MDesktop desktop = desktopPersistance.loadDesktopConfiguration();
		
		pageControl = new Pagination(desktop.getPages().size());
		pageControl.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
		pageControl.setOnDragDropped(new javafx.event.EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				if(db.hasFiles()){
                    
                    for(File file:db.getFiles()){
                       try {
                    	   String absolutePath = file.getParentFile().getParentFile().toURI().toURL().toExternalForm();
                    	   absolutePath = absolutePath.substring(0, absolutePath.length()-1);
                    	   Bundle b =	 FrameworkUtil.getBundle(DesktopManager.class).getBundleContext().installBundle(absolutePath);
                    	   b.start();
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (BundleException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                       
                    }
 
                    event.setDropCompleted(true);
                }else{
                    event.setDropCompleted(false);
                }
 
                event.consume();
			}
		});
		pageControl.setOnDragOver(new javafx.event.EventHandler <DragEvent>() {
            @Override
            public void handle(DragEvent event) {

                Dragboard db = event.getDragboard();
                if(db.hasFiles()){
                    event.acceptTransferModes(TransferMode.COPY);
                }
                 
                event.consume();
            }
        });
		
		pageControl.setPageFactory(new Callback<Integer, Node>() {
			
			@Override
			public Node call(Integer pageIndex) {
				MDesktopPage page = desktop.getPages().get(pageIndex);
				
				GridPane p = new GridPane();
				p.setPadding(new Insets(10));
				p.setVgap(20);
				p.setHgap(20);
				pageMap.put(pageIndex, p);
				
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
		
		eventBroker.subscribe(EventConstants.APPLICATION_REGISTERED_TOPIC, new EventHandler() {
			
			@Override
			public void handleEvent(final Event event) {
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						addApplication((FxApplication) event.getProperty(IEventBroker.DATA));
					}
				});
				
			}
		});
		
		return pageControl;
	}

	private void addApplication(FxApplication fxApplication) {
		GridPane p = pageMap.get(pageControl.getCurrentPageIndex());

		DesktopItem e = new DesktopItem(fxApplication, registry, eventBroker);
		Node n = e.getContentNode();
		GridPane.setColumnIndex(n, (p.getChildren().size() % 8) + 1);
		GridPane.setRowIndex(n, 0);
		p.getChildren().add(n);
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
