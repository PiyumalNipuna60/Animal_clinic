package lk.ijse.dto.tm;

public class OrderTm {
    private String id;
    private String quantity_an_hand;
    private String date;
    private int total;
    private String customerId;


    public OrderTm() {
    }

    public OrderTm(String id, String quantity_an_hand, String date, int total) {
        this.id = id;
        this.quantity_an_hand = quantity_an_hand;
        this.date = date;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity_an_hand() {
        return quantity_an_hand;
    }

    public void setQuantity_an_hand(String quantity_an_hand) {
        this.quantity_an_hand = quantity_an_hand;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OderDTO{" +
                "id='" + id + '\'' +
                ", quantity_an_hand='" + quantity_an_hand + '\'' +
                ", date='" + date + '\'' +
                ", total=" + total +
                '}';
    }
}
