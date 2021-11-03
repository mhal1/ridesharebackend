package com.reactlearning.RideSharingBackend.repository;

import com.reactlearning.RideSharingBackend.entity.CurrentUserLogged;
import org.springframework.data.repository.CrudRepository;

public interface CurrentUserRepository extends CrudRepository<CurrentUserLogged,Integer> {

}
