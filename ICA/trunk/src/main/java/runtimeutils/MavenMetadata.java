package runtimeutils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.maven.shared.runtime.MavenProjectProperties;
import org.apache.maven.shared.runtime.MavenRuntime;
import org.apache.maven.shared.runtime.MavenRuntimeException;

public class MavenMetadata {

	/**
	 * @component
	 */
	private MavenRuntime runtime;

	public void printProjects() throws MavenRuntimeException {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		System.out.println("runtime: " + runtime + "  classloader: "
				+ classLoader);
		// for ( MavenProjectProperties properties :
		// runtime.getProjectsProperties( classLoader ) )
		// {
		// System.out.println( properties );
		// }
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("../META-INF/maven/ICA/ICA/pom.properties");
		Properties properties = new Properties();

		System.out.println("InputStream is: " + inputStream);

		// load the inputStream using the Properties
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// get the value of the property
		String propValue = properties.getProperty("version");
	}
}
