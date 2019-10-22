package com.b.r.loteriab.r.Verify;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Verification {
    @Id
    @Column(unique = true, nullable = false)
    private String phone;

    @Column(nullable = false)
    private String requestId;

    @Column(nullable = false)
    private Date expirationDate;
}
