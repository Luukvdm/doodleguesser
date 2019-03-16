package restauthserver;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;

public class RestServer {

    private static final Logger LOG = LoggerFactory.getLogger( RestServer.class );

    public static void main(String[] args) throws Exception {
        try {
            // Workaround for resources from JAR files
            Resource.setDefaultUseCaches( false );

            buildSwagger();

            final HandlerList handlers = new HandlerList();
            handlers.addHandler( buildSwaggerUI() );
            handlers.addHandler( buildContext() );

            Server jettyServer = new Server(8096);
            jettyServer.setHandler(handlers);

            jettyServer.start();
            jettyServer.join();

        } catch (Exception e) {
            LOG.error( "There was an error starting up the Entity Browser", e );
        }
    }

    private static ContextHandler buildContext() {
        ResourceConfig resourceConfig = new ResourceConfig();
        // io.swagger.jaxrs.listing loads up Swagger resources
        resourceConfig.packages( AuthRestService.class.getPackage().getName(), ApiListingResource.class.getPackage().getName() );
        ServletContainer servletContainer = new ServletContainer( resourceConfig );
        ServletHolder authRestService = new ServletHolder( servletContainer );
        ServletContextHandler context = new ServletContextHandler( ServletContextHandler.SESSIONS );
        context.setContextPath( "/" );
        context.addServlet( authRestService, "/*" );
        return context;
    }

    private static void buildSwagger()
    {
        // This configures Swagger
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion( "1.0.0" );
        beanConfig.setResourcePackage( AuthRestService.class.getPackage().getName() );
        beanConfig.setBasePath( "/" );
        beanConfig.setDescription( "Auth Rest API" );
        beanConfig.setTitle( "Auth Rest Service" );
    }

    private static ContextHandler buildSwaggerUI() throws URISyntaxException {
        final ResourceHandler swaggerUIResourceHandler = new ResourceHandler();
        swaggerUIResourceHandler.setResourceBase( RestServer.class.getClassLoader().getResource( "swaggerui" ).toURI().toString() );
        final ContextHandler swaggerUIContext = new ContextHandler();
        swaggerUIContext.setContextPath( "/docs/" );
        swaggerUIContext.setHandler( swaggerUIResourceHandler );

        return swaggerUIContext;
    }
}
