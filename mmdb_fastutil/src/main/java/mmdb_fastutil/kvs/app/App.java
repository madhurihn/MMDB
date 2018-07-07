package mmdb_fastutil.kvs.app;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import mmdb_fastutil.kvs.app.AppConfiguration;
import mmdb_fastutil.kvs.app.resources.pairsResource;

import java.io.BufferedReader;
import java.io.File;

import io.dropwizard.assets.AssetsBundle;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.server.DefaultServerFactory;
import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsScanner;
import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.reader.ClassReaders;


public class App extends Application<AppConfiguration> {

	/**
	 * See pairs!
	 */
	    
		private Injector injector = null;
		
		public static void main(String[] args) throws Exception {
	        new App().run(args);
	        
	        
	    }

	    @Override
	    public String getName() {
	        return "mmdb";
	    }

	    @Override
	    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
	    	bootstrap.addBundle(new AssetsBundle("/swagger/", "/docs", "index.html"));
	    }

	    @Override
	    public void  run (AppConfiguration configuration, Environment environment) 
	    	{
	    	
			AppGuiceModule guiceModule = new AppGuiceModule(configuration, environment);
			injector = Guice.createInjector(guiceModule);
			
	    	// Swagger Resource
	    	environment.jersey().register(new ApiListingResourceJSON());

	    	// Swagger providers  
	    	environment.jersey().register(new ApiDeclarationProvider());
	    	environment.jersey().register(new ResourceListingProvider());
	    	
	    			
	    	// Swagger Scanner, which finds all the resources for @Api Annotations
	    	ScannerFactory.setScanner(new DefaultJaxrsScanner());

	    	// get the base path
	    	DefaultServerFactory defaultServerFactory = (DefaultServerFactory) configuration.getServerFactory();
	    	String basePath = defaultServerFactory.getApplicationContextPath();

	    	// Add the reader...
	    	ClassReaders.setReader(new DefaultJaxrsApiReader());

	    	// Set the swagger config options
	    	SwaggerConfig config = ConfigFactory.config();
	    	config.setApiVersion("1.0.1");        
	    	config.setBasePath(basePath);

	    	// register REST resources
	    	final pairsResource resource = new pairsResource();
	    	environment.jersey().register(resource);
	
	    }
	}
	 


