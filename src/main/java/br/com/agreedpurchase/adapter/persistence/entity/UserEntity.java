package br.com.agreedpurchase.adapter.persistence.entity;

import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.domain.model.Friend;
import br.com.agreedpurchase.domain.model.User;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ap_user")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(nullable = false, unique = true)
  private Long id;

  @OneToMany(mappedBy = "user", cascade= CascadeType.ALL, orphanRemoval = true)
  private Set<BuyEntity> buyEntities;

  @OneToMany(mappedBy = "user", cascade= CascadeType.ALL, orphanRemoval = true)
  private Set<FriendEntity> friendEntities;

  private String name;
  private String email;
  private String password;

  public User toModel() {
    User user = User.builder()
        .id(id)
        .name(name)
        .email(email)
        .password(password)
        .buies(toBuies())
        .friends(toFriends())
        .build();
    return user;
  }

  private Set<Buy> toBuies() {
    Set<Buy> buies = null;
    if(buyEntities != null) {
      buies = new HashSet<>();
      for(BuyEntity source : buyEntities) {
        Buy target = Buy.builder().build();
        BeanUtils.copyProperties(source , target);
        buies.add(target);
      }
    }
    return buies;
  }

  private Set<Friend> toFriends() {
    Set<Friend> friends = null;
    if(friendEntities != null) {
      friends = new HashSet<>();
      for(FriendEntity source : friendEntities) {
        Friend target = Friend.builder().build();
        BeanUtils.copyProperties(source , target);
        friends.add(target);
      }
    }
    return friends;
  }
}
