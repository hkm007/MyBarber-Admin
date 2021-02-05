package barbar.mybarbar.RequestAdopter;

public class RequestItems {
    String id, date, time, description, customerName, customerPhone;
    boolean accepted, declined;

    public RequestItems(String id, String date, String time, String description, String customerName, String customerPhone, boolean accepted, boolean declined) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.description = description;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.accepted = accepted;
        this.declined = declined;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isDeclined() {
        return declined;
    }

    public void setDeclined(boolean declined) {
        this.declined = declined;
    }
}
