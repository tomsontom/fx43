package at.bestsolution.fx43.desktop;

import java.util.Collection;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.osgi.service.datalocation.Location;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import at.bestsolution.fx43.desktop.internal.DSRegistryComponent;

@SuppressWarnings("restriction")
public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

	public static void doInjection(IEclipseContext context) {
		ContextInjectionFactory.inject(Activator.getApplicationRegistry(), context);
	}
	
	private static DSRegistryComponent getApplicationRegistry() {
		ServiceReference<DSRegistryComponent> ref = getContext().getServiceReference(DSRegistryComponent.class);
		DSRegistryComponent comp = getContext().getService(ref);
		return comp;
	}
	
	public static Location getInstanceLocation() {
		try {
			Collection<ServiceReference<Location>> references = context.getServiceReferences(Location.class, Location.INSTANCE_FILTER);
			if( ! references.isEmpty() ) {
				return context.getService(references.iterator().next());
			}
		} catch (InvalidSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
