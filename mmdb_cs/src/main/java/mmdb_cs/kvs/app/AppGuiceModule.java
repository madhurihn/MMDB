package mmdb_cs.kvs.app;

import com.google.inject.AbstractModule;

import io.dropwizard.setup.Environment;

public class AppGuiceModule extends AbstractModule {

	private final AppConfiguration config;
	private final Environment environment;
	
	public AppGuiceModule(AppConfiguration config, Environment environment) {
		this.config = config;
		this.environment = environment;
	}
	
	@Override
	protected void configure() {
		bind(Environment.class).toInstance(environment);
		bind(AppConfiguration.class).toInstance(config);				
	}

}