package br.com.agreedpurchase.adapter.persistence.entity;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ap_friend")
public class FriendEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(nullable = false, unique = true)
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="user_id", referencedColumnName="id", nullable=false)
  private UserEntity user;

  @OneToMany(mappedBy = "friend", cascade= CascadeType.ALL, orphanRemoval = true)
  private Set<ItemEntity> itemEntities;

  private String name;
  private String pix;
}
