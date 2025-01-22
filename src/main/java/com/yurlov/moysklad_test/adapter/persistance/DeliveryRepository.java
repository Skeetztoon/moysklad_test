package com.yurlov.moysklad_test.adapter.persistance;

import com.yurlov.moysklad_test.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {


}
