package br.com.agreedpurchase.adapter.controller.request;

import br.com.agreedpurchase.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
  private String email;
  private String password;
  private String name;

  public User toModel() {
    User user = User.builder()
        .email(email)
        .password(password)
        .name(name)
        .build();
    return user;
  }
}
