package org.metabuild.grobot.server.config;

import java.io.File;
import java.io.IOException;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;

import org.metabuild.grobot.domain.TargetHostCache;
import org.metabuild.grobot.domain.TargetHostCacheImpl;
import org.metabuild.grobot.tasks.BindingProvider;
import org.metabuild.grobot.tasks.groovy.GroovyBindingProvider;
import org.metabuild.grobot.tasks.groovy.GroovyTaskCache;
import org.metabuild.grobot.tasks.groovy.GroovyTaskFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Main spring application configuration
 * 
 * @author jburbridge
 * @since 9/30/2012
 */
@Configuration
@ComponentScan(basePackages = {"org.metabuild.grobot.webapp.domain"})
@PropertySource("classpath:grobot.properties")
public class AppConfig {

	@Autowired
	Environment environment;

	@Bean(name="targetCache")
	public TargetHostCache getTargetCache() {
//		return new FakeTargetHostCacheImpl(15);
		return new TargetHostCacheImpl();
	}

	@Bean(name="tasksDir")
	public String getTasksDir() {
		return environment.getProperty("user.dir") + "/tasks";
	}
	
	@Autowired(required=true)
	@Bean(name="GroovyScriptEngine")
	public GroovyScriptEngine getGroovyScriptEngine(@Qualifier(value="tasksDir") String tasksDir) 
			throws IOException {
		return new GroovyScriptEngine(tasksDir);
	}
	
	@Bean(name="bindingProvider")
	public BindingProvider getBindingProvider() {
		return new GroovyBindingProvider(System.getProperties(), new Binding());
	}
	
	@Bean(name="groovyTaskCache")
	public GroovyTaskCache getGroovyTaskCache(BindingProvider bindingProvider, String tasksDir, GroovyScriptEngine groovyScriptEngine) {
		final GroovyTaskFactory groovyTaskFactory = new GroovyTaskFactory(bindingProvider, new File(tasksDir), groovyScriptEngine);
		return new GroovyTaskCache(groovyTaskFactory);
	}
	
	@Bean(name="serverHostname")
	public String getServerHostname() {
		return environment.getProperty("grobot.server.hostname");
	}
	
	@Bean(name="helloMessage")
	public String getHelloMessage() {
		return environment.getProperty("grobot.server.hello.message");
	}
	
//	@Profile("default")
//	@Configuration
//	class ProdAppConfig {
//		
//	}
//	
//	@Profile("testing")
//	@Configuration
//	class TestAppConfig {
//		
//		@Bean(name="targetCache")
//		public TargetCache getTargetCache() {
//			return new FakeTargetCacheImpl(15);
//		}
//	}
}
