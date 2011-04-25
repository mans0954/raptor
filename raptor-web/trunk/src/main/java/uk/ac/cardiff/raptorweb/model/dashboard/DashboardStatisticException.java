package uk.ac.cardiff.raptorweb.model.dashboard;

public class DashboardStatisticException extends Exception {

    /** Generated Serial UID*/
    private static final long serialVersionUID = -2465908221233758151L;

    public DashboardStatisticException(String message) {
        super(message);
    }

    public DashboardStatisticException(String message, Exception wrappedException) {
        super(message, wrappedException);
    }

}
