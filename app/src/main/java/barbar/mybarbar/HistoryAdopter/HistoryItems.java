package barbar.mybarbar.HistoryAdopter;

public class HistoryItems {
    String name,phoneNumber,date,time,status,orderType;

    public HistoryItems(String name, String phoneNumber, String date, String time, String status, String orderType) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.time = time;
        this.status = status;
        this.orderType = orderType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
