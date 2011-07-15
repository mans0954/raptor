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

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * @author philsmart
 * 
 */
public class DefaultDataConnection implements RaptorDataConnection {

    /** hibernate template to persist classes */
    private HibernateTemplate hibernateTemplate;

    private SessionFactory sessionFactory;

    /** class logger */
    private static Logger log = LoggerFactory.getLogger(DefaultDataConnection.class);

    public void save(Object object) throws DataAccessException {
        hibernateTemplate.saveOrUpdate(object);
    }

    public void saveAll(@SuppressWarnings("rawtypes") Collection collection) throws DataAccessException {
        hibernateTemplate.saveOrUpdateAll(collection);
    }

    @SuppressWarnings("rawtypes")
    public List runQuery(String query, Object[] parameters) throws DataAccessException {
        return hibernateTemplate.find(query, parameters);
    }

    public void runUpdate(String query, Object[] parameters) throws DataAccessException {
        hibernateTemplate.bulkUpdate(query, parameters);
    }

    @SuppressWarnings("rawtypes")
    public List runQuery(String query, Object[] parameters, int maxResultSize) throws DataAccessException {
        hibernateTemplate.setMaxResults(maxResultSize);
        List result = hibernateTemplate.find(query, parameters);
        hibernateTemplate.setMaxResults(0);
        return result;
    }

    /**
     * Not used method. Specific to this implementation for testing.
     * 
     * @param query
     * @param pageSize
     * @param pageNumber
     * @return
     * @throws DataAccessException
     */
    @SuppressWarnings("rawtypes")
    public List runQueryPaged(final String query, final int pageSize, final int pageNumber) throws DataAccessException {
        HibernateTemplate template = getHibernateTemplate();
        return template.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query queryToRun = session.createQuery(query);
                queryToRun.setMaxResults(pageSize);
                queryToRun.setFirstResult(pageSize * pageNumber);
                return queryToRun.list();
            }
        });
    }
    
    public Object runQueryUnique(String query, Object[] parameters) throws DataAccessException {
        if (parameters != null)
            log.trace("Query to db, {}, with params [{}]", query, Arrays.asList(parameters));
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


    @SuppressWarnings("rawtypes")
    @Override
    public void deleteAllEntries(Collection entries) throws DataAccessException {
        hibernateTemplate.deleteAll(entries);

    }

}
