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


import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * @author philsmart
 *
 */
public class DefaultDataConnection implements RaptorDataConnection {

	/* hibernate template to persist classes */
	private HibernateTemplate hibernateTemplate;

	private SessionFactory sessionFactory;

	/** class logger */
	private static Logger log = LoggerFactory.getLogger(DefaultDataConnection.class);

	public void save(Object object) throws DataAccessException{
		hibernateTemplate.saveOrUpdate(object);
	}

	public void saveAll(Collection collection) throws DataAccessException{
		hibernateTemplate.saveOrUpdateAll(collection);
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
	public void deleteAllEntries(Collection entries) throws DataAccessException{
		hibernateTemplate.deleteAll(entries);

	}

}
