package com.yurlov.moysklad_test.domain.item;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_id_generator")
    @SequenceGenerator(name = "item_id_generator", sequenceName = "item_id", allocationSize = 1)
    private Integer id;

    @NotBlank(message = "Названние обязательно")
    @Size(max = 255, message = "Максимальная длина названия - 255 симоволов")
    private String name;

    @Size(max = 4096, message = "Максимальная длина описания - 4096 симоволов")
    private String description;

    @DecimalMin(value = "0.0", message = "Цена не может быть меньше нуля")
    private double price;

    @Column(name = "instock")
    private boolean inStock = false;

}
