package br.com.agreedpurchase.domain.model;

import br.com.agreedpurchase.adapter.persistence.entity.BuyEntity;
import br.com.agreedpurchase.adapter.persistence.entity.FriendEntity;
import br.com.agreedpurchase.adapter.persistence.entity.ItemEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
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
public class Friend {
  private Long id;
  private String name;
  private User user;

  public FriendEntity toEntity() {
    FriendEntity entity = FriendEntity.builder()
        .id(id)
        .name(name)
        .user(user.toEntity())
        .build();
    return entity;
  }
}
