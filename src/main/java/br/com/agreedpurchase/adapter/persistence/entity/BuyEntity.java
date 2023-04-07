package br.com.agreedpurchase.adapter.persistence.entity;

import br.com.agreedpurchase.domain.model.Buy;
import br.com.agreedpurchase.domain.model.Item;
import com.sun.istack.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "buy")
public class BuyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(nullable = false, unique = true)
  private Long id;

  @Temporal(TemporalType.DATE)
  private Date date;

  @NotNull
  private BigDecimal rate;

  @NotNull
  private BigDecimal discount;

  @OneToMany(mappedBy = "buy", cascade=CascadeType.PERSIST)
  private Set<ItemEntity> itemEntities;

  public Buy toModel() {
    Buy buy = Buy.builder()
        .discount(discount)
        .rate(rate)
        .build();

    if(itemEntities != null) {
      for(ItemEntity source : itemEntities) {
        Item target = Item.builder().build();
        BeanUtils.copyProperties(source , target);
        if (buy.getItems() == null) {
          buy.setItems(new HashSet<>());
        }
        buy.getItems().add(target);
      }
    }

    return buy;
  }

}
