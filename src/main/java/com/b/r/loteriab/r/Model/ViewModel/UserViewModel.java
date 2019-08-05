package com.b.r.loteriab.r.Model.ViewModel;

import com.b.r.loteriab.r.Model.Enterprise;
import lombok.Data;

@Data
public class UserViewModel {
    private String username;
    private String password;
    private String serial;
    private String enterpriseName;
    private String role;
}
