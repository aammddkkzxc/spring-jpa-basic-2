package hellojpa.jpqlpractice;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private int orderAmount;

    @Embedded
    public Address Address;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}