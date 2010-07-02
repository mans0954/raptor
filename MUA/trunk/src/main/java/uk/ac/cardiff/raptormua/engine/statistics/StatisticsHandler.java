/**
 *
 */
package uk.ac.cardiff.raptormua.engine.statistics;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;

import uk.ac.cardiff.model.Entry;

/**
 * @author philsmart
 * Allows the storage of statistical units
 */
public class StatisticsHandler {
	static Logger log = Logger.getLogger(StatisticsHandler.class);

	private List<Statistic> statisticalUnits;
	private List<Entry> entries;

	public void setStatisticalUnits(List<Statistic> statisticalUnits) {
		this.statisticalUnits = statisticalUnits;
	}

	public List<Statistic> getStatisticalUnits() {
		return statisticalUnits;
	}

	/**
	 * @param statisticName
	 */
	public void peformStatistic(String statisticName) {
		for (Statistic statistic : statisticalUnits){
			if (statistic.getUnitName().equals(statisticName)){
				statistic.setEntries(entries);
				invoke(statistic);
			}
		}

	}

	/**
	 * @param statistic
	 */
	private void invoke(Statistic statistic) {
		try{
			List<String> params = statistic.getMethodParams();
			Object[] paramsO = new Object[params.size()];
			for (int i=0; i < paramsO.length; i++){
				paramsO[i] = params.get(i);
			}
			invoke(statistic.getMethodName(),paramsO,statistic);
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	private void invoke(String fieldname, Object[] params, Object object) {
		try {
		    Class id = object.getClass();
		    Class[] paramC = new Class[params.length];
		    for (int i=0; i < params.length;i++){
		    	paramC[i] = params[i].getClass();
		    }
		    Method setter = id.getMethod(fieldname, paramC);
		    log.debug("Trying to Set :"+setter);
		    setter.invoke(object, params);
		} catch (Throwable e) {
		    log.error("Field name '" + fieldname + "' does not match internal model attribute");
		    e.printStackTrace();
		    // System.exit(1);

		}
	    }

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
		
	}




}
