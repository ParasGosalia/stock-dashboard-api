package com.sample.payconiq.stocks.repository;

import com.sample.payconiq.stocks.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByUserName(String name);
}
