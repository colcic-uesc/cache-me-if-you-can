package com.cachemeifyoucan.econometro.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cachemeifyoucan.econometro.domain.model.Alert;
import com.cachemeifyoucan.econometro.domain.model.AlertId;
import com.cachemeifyoucan.econometro.domain.model.User;

@Repository
public interface AlertRepository extends JpaRepository<Alert, AlertId> {

    List<Alert> findByUser(User user); 

}
