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
package at.bestsolution.fx43.desktop.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Node;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;

import at.bestsolution.fx43.desktop.internal.ui.EventConstants;
import at.bestsolution.fx43.framework.ui.FxApplication;
import at.bestsolution.fx43.framework.ui.FxApplication.LaunchState;
import at.bestsolution.fx43.framework.ui.util.ProgressReporter;

@SuppressWarnings("restriction")
public class DSRegistryComponent {
	@Inject
	private IEventBroker eventBroker;
	
	private List<FxApplication> applications = new ArrayList<FxApplication>();
	
	private Map<FxApplication, Node> runningApps = new HashMap<FxApplication,Node>();
	
	public void addApplication(FxApplication application) {
		applications.add(application);
		if( eventBroker != null ) {
			eventBroker.send(EventConstants.APPLICATION_REGISTERED_TOPIC, application);
		}
	}
	
	public void removeApplication(FxApplication application) {
		applications.remove(application);
		eventBroker.send(EventConstants.APPLICATION_UNREGISTERED_TOPIC, application);
	}
	
	public Collection<FxApplication> getApplications() {
		return Collections.unmodifiableCollection(applications);
	}
	
	public void launchApp(FxApplication application, ProgressReporter<LaunchState> reporter, IEclipseContext applicationContext) {
		Node n = runningApps.get(application);
		if( n == null ) {
			n = application.launch(reporter, applicationContext); 
			runningApps.put(application, n);
		} else {
			throw new IllegalStateException("Application already running");
		}
	}
	
	public Node getAppNode(FxApplication application) {
		return runningApps.get(application);
	}
	
	public FxApplication findApplicationById(String id) {
		for( FxApplication app : applications ) {
			if( id.equals(app.getId()) ) {
				return app;
			}
		}
		return null;
	}
}
