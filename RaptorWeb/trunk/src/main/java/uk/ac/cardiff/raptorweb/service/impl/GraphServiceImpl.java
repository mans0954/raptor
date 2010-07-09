/**
 *
 */
package uk.ac.cardiff.raptorweb.service.impl;

import uk.ac.cardiff.raptorweb.engine.RaptorWebEngine;
import uk.ac.cardiff.raptorweb.service.GraphService;

/**
 * @author philsmart
 *
 */
public class GraphServiceImpl implements GraphService{

	private RaptorWebEngine webEngine;

	public void setWebEngine(RaptorWebEngine webEngine) {
		this.webEngine = webEngine;
	}

	public RaptorWebEngine getWebEngine() {
		return webEngine;
	}

}
