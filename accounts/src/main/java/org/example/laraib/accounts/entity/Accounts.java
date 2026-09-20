package org.example.laraib.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Accounts extends  BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @ToString.Exclude
    private Customer customerId;

    @Id
    @Column(name = "account_number")
    private Long accountNumber;

    @Column
    private String accountType;

    @Column
    private String branchAddress;


}
