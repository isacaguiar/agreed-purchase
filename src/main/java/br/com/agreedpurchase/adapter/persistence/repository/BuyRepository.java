package br.com.agreedpurchase.adapter.persistence.repository;

import br.com.agreedpurchase.adapter.persistence.entity.BuyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyRepository extends JpaRepository<BuyEntity, Long>  {

}
