package br.com.agreedpurchase.adapter.persistence.repository;

import br.com.agreedpurchase.adapter.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long>  {

}
