package br.com.agreedpurchase.adapter.persistence;

import br.com.agreedpurchase.adapter.persistence.entity.BuyEntity;
import br.com.agreedpurchase.adapter.persistence.entity.UserEntity;
import br.com.agreedpurchase.adapter.persistence.repository.BuyRepository;
import br.com.agreedpurchase.adapter.persistence.repository.UserRepository;
import br.com.agreedpurchase.domain.port.PersistencePort;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PersistenceAdapter implements PersistencePort {

  @Autowired
  BuyRepository buyRepository;

  @Autowired
  UserRepository userRepository;

  public BuyEntity buy(BuyEntity buyEntity) {
    return buyRepository.save(buyEntity);
  }

  public Optional<BuyEntity> getBuyEntityById(Long id) {
    return buyRepository.findById(id);
  }

  public List<BuyEntity> getBuyEntities() {
    return buyRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
  }

  public void deleteBuyEntityById(Long id) {
    buyRepository.deleteById(id);
  }

  public Optional<UserEntity> login(UserEntity userEntity) {
    return null;
  }

  public UserEntity registerUser(UserEntity userEntity) {
    return userRepository.save(userEntity);
  }
}
