package sigma.scsapp.utility;

/**
 * Created by Niklas on 2017-10-19.
 */

public class URL {

    public final static String URL_TO_HIT = "http://10.0.2.2:8000/";
    public final static String userId = "2"; //user.getId();
    public final static String bookingId = null; //
    public final static String activeBookingsForUser = "users/" + userId + "/bookings/";
    public final static String specifikBookingForUser = "users/" + userId + "/bookings/" + bookingId;
    public final static String getAllVehicle = "Vehicles.json";
    public final static String getUser = "ActiveUser.json";
    public final static String getAllBookings = "PastBookings.json";
    public final static String getActiveBookings = "ActiveBookings.json";
    public final static String getActiveVehicles = "ActiveVehicles.json";
}
