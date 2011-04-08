/**
 *
 */
package uk.ac.cardiff.raptor.raptorica.dao.external.format;

/**
 * @author philsmart
 *
 */
public interface LineFilter {

    public boolean parsableLine(String line);

}
