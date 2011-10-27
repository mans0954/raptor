
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
