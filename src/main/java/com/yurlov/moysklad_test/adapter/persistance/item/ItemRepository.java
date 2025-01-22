package com.yurlov.moysklad_test.adapter.persistance.item;

import com.yurlov.moysklad_test.domain.item.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query("select i from Item i where " +
            "(:name is null or lower(i.name) like lower(cast(concat('%', :name, '%') as string))) and " +
            "(:minPrice is null or i.price >= :minPrice) and " +
            "(:maxPrice is null or i.price <= :maxPrice) and " +
            "(:inStock IS NULL OR i.inStock = :inStock)"
    )
    List<Item> findByFilters(@Param("name") String name,
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice,
        @Param("inStock") Boolean inStock,
        Pageable pageable);
}
