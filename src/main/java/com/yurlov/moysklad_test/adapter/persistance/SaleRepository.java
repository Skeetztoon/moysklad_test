package com.yurlov.moysklad_test.adapter.persistance;

import com.yurlov.moysklad_test.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Integer> {

}