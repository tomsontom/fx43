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
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;

import at.bestsolution.fx43.framework.ui.FxApplication;

@SuppressWarnings("restriction")
public class DSRegistryComponent {
	@Inject
	private IEventBroker eventBroker;
	
	private List<FxApplication> applications = new ArrayList<>();
	
	public void addApplication(FxApplication application) {
		applications.add(application);
		if( eventBroker != null ) {
			eventBroker.send("fx43/framework/application/added", application);
		}
	}
	
	public void removeApplication(FxApplication application) {
		applications.remove(application);
		eventBroker.send("fx43/framework/application/removed", application);
	}
	
	public Collection<FxApplication> getApplications() {
		return Collections.unmodifiableCollection(applications);
	}
}
