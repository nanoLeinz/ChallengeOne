package model;

public class Orders {
    private Integer orderId;
    private Integer qty;
    private Integer totalPrice;

    public Orders(Integer orderId, Integer qty) {
        this.orderId = orderId;
        this.qty = qty;
        Menus menuList = new Menus();
        this.totalPrice = qty * (Integer) menuList.getNamaMenu()[orderId - 1][2];
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
