package at.bestsolution.fx43.desktop;

import javafx.stage.Stage;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;

import at.bestsolution.fx43.desktop.internal.ui.DesktopUI;

/**
 * This class controls all aspects of the JavaFX OSGi application's execution
 */
@SuppressWarnings("restriction")
public class Application {
	@PostConstruct
	public void launch(Stage stage, IEclipseContext context) {
		Activator.doInjection(context);
		ContextInjectionFactory.make(DesktopUI.class, context);
		
		stage.setFullScreen(true);
		stage.setWidth(1024);
		stage.setHeight(800);
		stage.show();
	}
}