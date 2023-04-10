package br.com.agreedpurchase.domain.service.impl;

import br.com.agreedpurchase.adapter.persistence.entity.UserEntity;
import br.com.agreedpurchase.domain.exception.BusinessException;
import br.com.agreedpurchase.domain.model.User;
import br.com.agreedpurchase.domain.port.PersistencePort;
import br.com.agreedpurchase.domain.service.UserService;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final SecretKey CHAVE = Keys.hmacShaKeyFor("APP-AGREED-PURCHASE-qwertyasdfg%asdwertret!GosadSapopppdsfhjfdhs".getBytes(StandardCharsets.UTF_8));

  @Autowired
  PersistencePort persistencePort;
  @Override
  public String login(User user) throws BusinessException {

    Optional<UserEntity> userEntityOptional =
        persistencePort.login(user.toEntity());
    if(userEntityOptional.isPresent()) {
      if (!user.getPassword().equals(userEntityOptional.get().getPassword())) {
        throw new BusinessException("Invalid password");
      }



    } else {
      throw new BusinessException("User not found");
    }
    return "";
  }

  @Override
  public User register(User user) {
    return persistencePort.registerUser(user.toEntity()).toModel();
  }
}
