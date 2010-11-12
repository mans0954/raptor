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
package main.uk.ac.cf.dao.internal;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import uk.ac.cardiff.model.Entry;

/**
 * @author philsmart
 *
 */
public class ICADataConnectionImpl implements ICADataConnection{

    private HibernateTemplate hibernateTemplate;

    /* (non-Javadoc)
     * @see main.uk.ac.cf.dao.internal.ICADataConnection#save(uk.ac.cardiff.model.Entry)
     */
    @Override
    public void save(Entry entry) {
	hibernateTemplate.save(entry);
    }

    /* (non-Javadoc)
     * @see main.uk.ac.cf.dao.internal.ICADataConnection#runQuery(java.lang.String, java.lang.Object[])
     */
    @Override
    public List runQuery(String query, Object[] parameters) {
	return hibernateTemplate.find("from Entry where object_id= ",parameters);
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
	this.hibernateTemplate = hibernateTemplate;
    }

    public HibernateTemplate getHibernateTemplate() {
	return hibernateTemplate;
    }


    public void setSessionFactory(SessionFactory sessionFactory){
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}



}
