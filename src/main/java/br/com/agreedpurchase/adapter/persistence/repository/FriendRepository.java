package br.com.agreedpurchase.adapter.persistence.repository;

import br.com.agreedpurchase.adapter.persistence.entity.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<FriendEntity, Long>  {

}
