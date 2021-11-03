package com.reactlearning.RideSharingBackend.repository;

import com.reactlearning.RideSharingBackend.entity.ValidUsers;
import org.springframework.data.repository.CrudRepository;

public interface RideSharingRepository extends CrudRepository<ValidUsers,Integer> {

    ValidUsers findByUserName(String username);

}
