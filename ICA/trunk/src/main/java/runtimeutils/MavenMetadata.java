/**
 * Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
