package com.b.r.loteriab.r.Model.ViewModel;

import com.b.r.loteriab.r.Model.Users;
import lombok.Data;

@Data
public class PasswordResetViewModel {
    private Users users;
    private String password;
    private String confirmPassword;
    private String type;
}
