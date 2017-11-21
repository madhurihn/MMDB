package mmdb.kvs.app;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import mmdb.kvs.app.resources.pairsResource;
import mmdb.kvs.app.AppConfiguration;

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

/**
 * See pairs!
 *
 */
public class App extends Application<AppConfiguration> {
    
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
    
    	
    	
    	
    	
    	
    	
    	//BufferedReader reader = new BufferedReader("dataset.tbl", "UTF-8");
    	
    	
    	
    	
    	
    	
    	/*
        // nothing to do yet
       	BufferedReader br = new BufferedReader(new FileReader("dataset.tbl"));
        String line =  null;
        	ConcurrentNavigableMap<String,String> map = new ConcurrentSkipListMap<String, String>();
        	
        	
        	while((line=br.readLine())!=null){
                String str[] = line.split("|");
                System.out.println(str);
                
                
                /*for(int i=0;i<str.length;i++){
                    String arr[] = str[i].split("|");
                    map.put(arr[0], arr[1]);
                }*/
  /*          }
            map.keySet().forEach(
            		it->{
            			System.out.println("Key:"+it+", Value:"+map.get(it));
            		}
            	);
        	
            /*map.put("1", "One");
            //map.put("2", "Two");
            map.put("3", "Three");
            map.put("5", "Five");
            map.put("6", "Six");
            System.out.println("Initial ConcurrentHashMap: "+map);
            System.out.println("HeadMap(\"2\") of ConcurrentHashMap: "+map.headMap("2"));
            System.out.println("TailMap(\"2\") of ConcurrentHashMap: "+map.tailMap("2"));
            System.out.println("SubMap(\"2\", \"4\") of ConcurrentHashMap: "+map.subMap("2","4"));*/
        	
        	
      //  }
    }
}
 
