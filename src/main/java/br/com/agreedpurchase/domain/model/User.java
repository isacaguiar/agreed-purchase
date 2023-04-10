package br.com.agreedpurchase.domain.model;

import br.com.agreedpurchase.adapter.persistence.entity.UserEntity;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private Set<Buy> buies;
  private Set<Friend> friends;

  private Long id;
  private String name;
  private String email;
  private String password;

  public UserEntity toEntity() {
    UserEntity userEntity = UserEntity.builder()
        .id(id)
        .name(name)
        .email(email)
        .password(password)
        .build();
    return userEntity;
  }
}
