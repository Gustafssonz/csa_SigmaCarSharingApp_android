package sigma.scsapp.model;

/**
 * Created by Niklas on 2017-09-12.
 */

public class User {

    // PROFILE
    private String id;
    private String userName;
    private String isApproved;
    private String totalBookingsYear;
    private String totalDistanceYear;

    // Password Field
    // Using EditText with "inputtype=textpassword" makes it *****
    private String password;

    public User() {
    }

    public User(String id, String userName, String isApproved, String totalBookingsYear, String totalDistanceYear, String password)
        {
        this.id = id;
        this.userName = userName;
        this.isApproved = isApproved;
        this.totalBookingsYear = totalBookingsYear;
        this.totalDistanceYear = totalDistanceYear;
        this.password = password;
        }

    public String getId()
        {
        return id;
        }

    public String getUserName()
        {
        return userName;
        }

    public void setUserName(String userName)
        {
        this.userName = userName;
        }

    public String getIsApproved()
        {
        return isApproved;
        }

    public void setIsApproved(String isApproved)
        {
        this.isApproved = isApproved;
        }

    public String getTotalBookingsYear()
        {
        return totalBookingsYear;
        }

    public void setTotalBookingsYear(String totalBookingsYear)
        {
        this.totalBookingsYear = totalBookingsYear;
        }

    public String getTotalDistanceYear()
        {
        return totalDistanceYear;
        }

    public void setTotalDistanceYear(String totalDistanceYear)
        {
        this.totalDistanceYear = totalDistanceYear;
        }

    public String getPassword()
        {
        return password;
        }

    @Override
    public String toString()
        {
        return "User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", isApproved='" + isApproved + '\'' +
                ", totalBookingsYear='" + totalBookingsYear + '\'' +
                ", totalDistanceYear='" + totalDistanceYear + '\'' +
                ", password='" + password + '\'' +
                '}';
        }
}
