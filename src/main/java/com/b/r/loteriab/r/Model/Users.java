package com.b.r.loteriab.r.Model;

/**
 * Created by Dany on 22/04/2019.
 */

import com.b.r.loteriab.r.Model.Serializers.CombinationTypeSerializer;
import com.b.r.loteriab.r.Model.Serializers.UserSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@JsonSerialize(using = UserSerializer.class)
public class Users implements Serializable{// extends User
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 20, nullable = false)
    private String username;
    @Column(length = 128, nullable = false)
    private String password;
    @Column(length = 28)
    private String token;
    private Date creationDate;
    private Date modificationDate;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Role> roles;
    private boolean enabled;
}
