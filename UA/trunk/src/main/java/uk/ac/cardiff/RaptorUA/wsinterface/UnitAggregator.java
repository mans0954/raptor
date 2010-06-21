package uk.ac.cardiff.RaptorUA.wsinterface;

import java.util.List;

import javax.jws.WebService;

/**
 * @author philsmart
 *
 */

@WebService
public interface UnitAggregator {

		String getVersion();
		List getAllAuthentications();



}
