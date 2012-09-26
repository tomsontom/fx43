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
package at.bestsolution.fx43.apps.browser;

import javafx.scene.Node;
import javafx.scene.image.Image;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;

import at.bestsolution.fx43.framework.ui.FxApplication;
import at.bestsolution.fx43.framework.ui.util.ProgressReporter;

@SuppressWarnings("restriction")
public class BrowserApplication implements FxApplication {

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public Image getIcon() {
		return new Image(getClass().getClassLoader().getResource("/icons/app_icon_64.png").toExternalForm());
	}

	@Override
	public String getId() {
		return "at.bestsolution.fx43.apps.browser";
	}

	@Override
	public String getVersion() {
		return "0.0.1";
	}

	@Override
	public String getName() {
		return "FX Browse";
	}

	@Override
	public Node launch(ProgressReporter<LaunchState> reporter, IEclipseContext applicationContext) {
		reporter.state(LaunchState.DONE, "", 1.0);
		return ContextInjectionFactory.make(BrowserControl.class, applicationContext).getNode();
	}

	@Override
	public void destroy() {
		
	}
}