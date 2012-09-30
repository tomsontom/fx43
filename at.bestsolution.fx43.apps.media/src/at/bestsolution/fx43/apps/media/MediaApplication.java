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
package at.bestsolution.fx43.apps.media;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;

import javafx.scene.Node;
import javafx.scene.image.Image;
import at.bestsolution.fx43.framework.ui.FxApplication;
import at.bestsolution.fx43.framework.ui.util.ProgressReporter;

@SuppressWarnings("restriction")
public class MediaApplication implements FxApplication {

	@Override
	public Node launch(ProgressReporter<LaunchState> reporter, IEclipseContext applicationContext) {
		return ContextInjectionFactory.make(MediaControl.class, applicationContext).getNode();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Image getIcon() {
		return new Image(getClass().getClassLoader().getResource("/icons/app_icon_64.png").toExternalForm());
	}

	@Override
	public String getId() {
		return "at.bestsolution.fx43.apps.media";
	}

	@Override
	public String getVersion() {
		return "0.0.1";
	}

	@Override
	public String getName() {
		return "Mediathek";
	}

}
