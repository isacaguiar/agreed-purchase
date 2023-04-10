package br.com.agreedpurchase.domain.service;

import br.com.agreedpurchase.adapter.persistence.entity.UserEntity;
import br.com.agreedpurchase.domain.model.User;

public interface UserService {

  String login(User user);

  User register(User user);
}
