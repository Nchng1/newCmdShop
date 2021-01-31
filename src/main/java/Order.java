import java.sql.Date;
import java.util.HashMap;

public class Order {
    /*
    该有哪些属性？依据是什么？
    依据：数据库有哪些字段，那么类就有哪些属性
    */
    private User user;
    private Product product[];
    private HashMap<Product,Integer> amount;
    private float totalPay;
    private float actualPay;
    private Date orderDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product[] getProduct() {
        return product;
    }

    public void setProduct(Product[] product) {
        this.product = product;
    }

    public HashMap<Product, Integer> getAmount() {
        return amount;
    }

    public void setAmount(HashMap<Product, Integer> amount) {
        this.amount = amount;
    }

    public float getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(float totalPay) {
        this.totalPay = totalPay;
    }

    public float getActualPay() {
        return actualPay;
    }

    public void setActualPay(float actualPay) {
        this.actualPay = actualPay;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
