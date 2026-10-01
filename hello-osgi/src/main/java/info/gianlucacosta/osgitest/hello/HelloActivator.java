package info.gianlucacosta.osgitest.hello;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class HelloActivator implements BundleActivator {
    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Hello, world! ^__^ I'm a private bundle activator!");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Goodbye! ^__^");
    }
}
