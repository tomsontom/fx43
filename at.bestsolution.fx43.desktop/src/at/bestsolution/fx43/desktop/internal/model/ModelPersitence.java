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
package at.bestsolution.fx43.desktop.internal.model;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.inject.Inject;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.osgi.service.datalocation.Location;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import at.bestsolution.fx43.desktop.Activator;
import at.bestsolution.fx43.desktop.internal.DSRegistryComponent;
import at.bestsolution.fx43.framework.ui.FxApplication;

public class ModelPersitence {
	@Inject
	private DSRegistryComponent registry;
	
	public MDesktop loadDesktopConfiguration() {
		try {
			Location location = Activator.getInstanceLocation();
			URL uri = location.getDataArea("fx43/desktop.cfg");
			File f = new File(uri.toExternalForm());
			if( ! f.exists() ) {
				MDesktop desktop = new MDesktop();
				int i = 0;
				MDesktopPage page = null;
				for( FxApplication app : registry.getApplications() ) {
					System.err.println("Adding application: " + app);
					if( i % 30 == 0 ) {
						page = new MDesktopPage();
						desktop.getPages().add(page);
					}
					
					page.getApplications().add(app.getId());
					
					i++;
				}
				
				if( desktop.getPages().size() == 0 ) {
					desktop.getPages().add(new MDesktopPage());
				}
				
				return desktop;
			} else {
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser parser = factory.newSAXParser();
				HandlerImpl h = new HandlerImpl();
				parser.parse(f, h);
				return h.rootElement;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void saveDesktopConfiguration() {
		
	}
	
	public static class HandlerImpl extends DefaultHandler {
		public final MDesktop rootElement = new MDesktop();
		
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if( "mdesktoppage".equals(qName) ) {
				MDesktopPage page = new MDesktopPage();
				String apps = attributes.getValue("applications");
				if( apps != null && apps.trim().length() > 0 ) {
					for( String aId : apps.split(",") ) {
						page.getApplications().add(aId.trim());
					}
				}
				rootElement.getPages().add(page);
			}
		}
	}
}
