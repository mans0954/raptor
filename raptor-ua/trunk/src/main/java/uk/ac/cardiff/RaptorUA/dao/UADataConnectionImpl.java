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
package uk.ac.cardiff.RaptorUA.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import uk.ac.cardiff.model.Entry;

/**
 * @author philsmart
 *
 */
public class UADataConnectionImpl implements UADataConnection{

    /* hibernate template to persist classes */
    private HibernateTemplate hibernateTemplate;

    private SessionFactory sessionFactory;

    /* class logger */
    static Logger log = Logger.getLogger(UADataConnectionImpl.class);

    /* (non-Javadoc)
     * @see main.uk.ac.cf.dao.internal.ICADataConnection#save(uk.ac.cardiff.model.Entry)
     */
    @Override
    public void save(Object object) {
	//log.debug("Saving..."+entry);
	hibernateTemplate.saveOrUpdate(object);
	//hibernateTemplate.flush();

    }

    /* (non-Javadoc)
     * @see main.uk.ac.cf.dao.internal.ICADataConnection#saveAll(uk.ac.cardiff.model.Entry)
     */
    @Override
    public void saveAll(Collection collection) {
	//log.debug("Saving...");
	hibernateTemplate.saveOrUpdateAll(collection);
	//hibernateTemplate.flush();

    }

    /* (non-Javadoc)
     * @see main.uk.ac.cf.dao.internal.ICADataConnection#runQuery(java.lang.String, java.lang.Object[])
     */
    @Override
    public List runQuery(String query, Object[] parameters) {
	return hibernateTemplate.find(query,parameters);
    }

    /* (non-Javadoc)
     * @see main.uk.ac.cf.dao.internal.ICADataConnection#runQueryUnique(java.lang.String, java.lang.Object[])
     */
    @Override
    public Object runQueryUnique(String query, Object[] parameters) {
	Object object = DataAccessUtils.uniqueResult(getHibernateTemplate( ).find(query,parameters));
	return object;
    }


    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
	this.hibernateTemplate = hibernateTemplate;
    }

    public HibernateTemplate getHibernateTemplate() {
	return hibernateTemplate;
    }


    public void setSessionFactory(SessionFactory sessionFactory){
	this.sessionFactory = sessionFactory;
	this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    /* <p> import, here we load all entries to bind them in hibernate (make them persistent)
     * then we delete them. Not sure if all these entries are loaded
     * then delete if lazy loading is false.</p>
     * (non-Javadoc)
     * @see main.uk.ac.cf.dao.internal.ICADataConnection#deleteAllEntries()
     */
    @Override
    public void deleteAllEntries(Collection entries) {
	hibernateTemplate.deleteAll(entries);
    }







}
