package br.com.agreedpurchase.adapter.persistence.repository;

import br.com.agreedpurchase.adapter.persistence.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

}
