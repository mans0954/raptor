/**
 *
 */
package uk.ac.cardiff.RaptorUA.service;

/**
 * @author philsmart
 *
 */
public interface UAProcess {

    /*
     * Polls the attached ICAs
     */
    public void poll();


    /*
     * Sends all entries stored to the current logger
     */
    public void toStdOut();

}
