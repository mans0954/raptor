
package uk.ac.cardiff.raptormua.engine;

/**
 * General callback class for implementing when a callable is used.
 * 
 */
public interface CallbackInterface<T> {

    public void returnResult(T result);
}
