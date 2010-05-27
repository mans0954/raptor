package main.uk.ac.cf.wsinterface;

import java.util.List;

import javax.jws.WebService;

@WebService
public interface Collector {

	String getVersion();
	List parse();
}
