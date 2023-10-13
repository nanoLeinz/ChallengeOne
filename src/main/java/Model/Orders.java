package Model;

public class Orders {
    private Integer orderId;
    private Integer qty;
    private Integer totalPrice;

    public Orders(Integer orderId, Integer qty, Integer price) {
        this.orderId = orderId;
        this.qty = qty;
        this.totalPrice = qty * price;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Integer getQty() {
        return qty;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }
}
