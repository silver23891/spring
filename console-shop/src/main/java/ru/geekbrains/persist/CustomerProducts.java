package ru.geekbrains.persist;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_products")
@NamedQueries({
        @NamedQuery(name = "CustomerProducts.getCustomersByProduct", query = "" +
                "select c.name " +
                "from CustomerProducts cp " +
                "join Customer c on cp.customer=c.id " +
                "join Product p on cp.product=p.id " +
                "where p.productName=:name")
})
public class CustomerProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "buy_date")
    @CreationTimestamp
    private LocalDateTime buyDate;

    @Column
    private float cost;

    public CustomerProducts() {}

    public CustomerProducts(Customer customer, Product product) {
        this.product = product;
        this.customer = customer;
        this.cost = product.getCost();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(LocalDateTime buyDate) {
        this.buyDate = buyDate;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "CustomerProducts{" +
                "id=" + id +
                ", product=" + product.getId() +
                ", customer=" + customer.getId() +
                ", buyDate=" + buyDate +
                ", cost=" + cost +
                '}';
    }
}
