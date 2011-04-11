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
package uk.ac.cardiff.raptor.store.dao;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * @author philsmart
 * 
 */
public class MUADataConnectionImpl implements MUADataConnection {

	/* hibernate template to persist classes */
	private HibernateTemplate hibernateTemplate;

	private SessionFactory sessionFactory;

	/** class logger */
	private static Logger log = Logger.getLogger(MUADataConnectionImpl.class);

	public void save(Object object) {
		// log.debug("Saving..."+entry);
		hibernateTemplate.saveOrUpdate(object);
		// hibernateTemplate.flush();

	}

	public void saveAll(Collection collection) {
		// log.debug("Saving...");
		hibernateTemplate.saveOrUpdateAll(collection);
		// hibernateTemplate.flush();

	}

	public List runQuery(String query, Object[] parameters) {
		return hibernateTemplate.find(query, parameters);
	}

	public Object runQueryUnique(String query, Object[] parameters) {
		Object object = DataAccessUtils.uniqueResult(getHibernateTemplate().find(query, parameters));
		return object;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	/**
	 * <p> import, here we load all entries to bind them in hibernate then we
	 * delete them. Not sure if all these entries are loaded then delete if lazy
	 * loading is false.</p> (non-Javadoc)
	 * 
	 * @see main.uk.ac.cf.dao.internal.ICADataConnection#deleteAllEntries()
	 */
	@Override
	public void deleteAllEntries(Collection entries) {
		hibernateTemplate.deleteAll(entries);

	}

}
