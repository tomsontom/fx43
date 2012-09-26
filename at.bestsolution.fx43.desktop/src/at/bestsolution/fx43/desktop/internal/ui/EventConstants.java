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

public class EventConstants {
	private EventConstants() {
	}
	
	public static final String ROOT_TOPIC = "fx43/framework/";
	
	public static final String APPLICATION_TOPIC = ROOT_TOPIC + "application/";
	
	public static final String APPLICATION_CREATE_TOPIC = APPLICATION_TOPIC + "launched";
	public static final String APPLICATION_SHOW_TOPIC = APPLICATION_TOPIC + "show";
	public static final String APPLICATION_SWITCH_TOPIC = APPLICATION_TOPIC + "switch";
	public static final String APPLICATION_HIDE_TOPIC = APPLICATION_TOPIC + "hide";
	public static final String APPLICATION_DESTROY_TOPIC = APPLICATION_TOPIC + "destroy";
	
	public static final String APPLICATION_REGISTERED_TOPIC = APPLICATION_TOPIC + "registered";
	public static final String APPLICATION_UNREGISTERED_TOPIC = APPLICATION_TOPIC + "unregistered";
}
