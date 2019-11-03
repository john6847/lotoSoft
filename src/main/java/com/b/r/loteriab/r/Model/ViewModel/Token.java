package com.b.r.loteriab.r.Model.ViewModel;

import lombok.Data;

import java.util.Date;

@Data
public class Token {
    private Long user;
    private String name;
    private Date createdAt;
    private int lifetime;
    private boolean canBeDeleted = false;
    private String token;
    private Long enterpriseId;
}
