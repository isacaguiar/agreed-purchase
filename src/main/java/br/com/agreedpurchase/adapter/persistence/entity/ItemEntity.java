package br.com.agreedpurchase.adapter.persistence.entity;

import com.sun.istack.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ap_item")
public class ItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(nullable = false, unique = true)
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="buy_id", referencedColumnName="id", nullable=false)
  private BuyEntity buy;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="friend_id", referencedColumnName="id", nullable=false)
  private FriendEntity friend;

  @NotNull
  private String descripton;

  @NotNull
  private BigDecimal amount;

}
