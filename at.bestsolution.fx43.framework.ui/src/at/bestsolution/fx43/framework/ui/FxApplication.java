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
package at.bestsolution.fx43.framework.ui;

import javafx.scene.Node;
import javafx.scene.image.Image;

import org.eclipse.e4.core.contexts.IEclipseContext;

import at.bestsolution.fx43.framework.ui.util.ProgressReporter;

@SuppressWarnings("restriction")
public interface FxApplication {
	public enum LaunchState {
		LAUNCHING,
		DONE
	}
	
	
	public Node launch(ProgressReporter<LaunchState> reporter, IEclipseContext applicationContext);
	
	public void show();
	
	public void hide();
	
	public void destroy();
	
	// -------------------
	
	public Image getIcon();
	
	public String getId();
	
	public String getVersion();
	
	public String getName();
}
