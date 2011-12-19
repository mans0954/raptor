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

package uk.ac.cardiff.raptor.store;

import java.util.List;

import uk.ac.cardiff.raptor.store.dao.StorageException;

public interface QueryableEventHandler extends EventHandler {

    /**
     * Performs the update query <code>query</code> with the parameters <code>parameters</code>. Concrete
     * implementations of the interface may require the query in a specific, specified, language e.g. HQL or JPQL.
     * 
     * @param query the query to run as an update.
     * @param parameters the parameters to substitute into the query string <code>query</code>
     * @return a <code>List</code> of objects that are the result of running the query <code>query</code> with the
     *         parameters <code>parameters</code>
     */
    public void update(String query, Object[] parameters) throws StorageException;

    /**
     * Runs the query <code>query</code> with the parameters <code>parameters</code> and returns a <code>List</code> of
     * results. Concrete implementations of the interface may require the query in a specific, specified, language e.g.
     * HQL or JPQL.
     * 
     * @param query the query to run.
     * @param parameters the parameters to substitute into the query string <code>query</code>
     * @return a <code>List</code> of objects that are the result of running the query <code>query</code> with the
     *         parameters <code>parameters</code>
     */
    public List query(String query, Object[] parameters);

    /**
     * Runs the query <code>query</code> with the parameters <code>parameters</code> and returns a <code>List</code> of
     * results up to the specified <code>maxNoResults</code>. Concrete implementations of the interface may require the
     * query in a specific, specified, language e.g. HQL or JPQL.
     * 
     * @param query the query to run.
     * @param parameters the parameters to substitute into the query string <code>query</code>
     * @return a <code>List</code> of objects that are the result of running the query <code>query</code> with the
     *         parameters <code>parameters</code>
     */
    public List query(String query, Object[] parameters, int maxNoResults);

    /**
     * Runs the query <code>query</code> with the parameters <code>parameters</code> and returns a single unique result.
     * Concrete implementations of the interface may require the query in a specific, specified, language e.g. HQL or
     * JPQL.
     * 
     * @param query the query to run.
     * @param parameters the parameters to substitute into the query string <code>query</code>
     * @return a single object that is the result of running the query <code>query</code> with the parameters
     *         <code>parameters</code>
     */
    public Object queryUnique(String query, Object[] parameters);

}
