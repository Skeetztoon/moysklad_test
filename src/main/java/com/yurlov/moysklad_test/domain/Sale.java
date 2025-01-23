package com.yurlov.moysklad_test.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sale_id_generator")
    @SequenceGenerator(name = "sale_id_generator", sequenceName = "sale_id", allocationSize = 1)
    private Integer id;

    @Size(max = 255, message = "Максимальная длина названия - 255 симоволов")
    private String title;

    @NotNull(message = "Товар обязателен")
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Min(value = 1, message = "Количество должно быть больше 0")
    private Integer quantity;

    @DecimalMin(value = "0.0", message = "Стоимость не может быть отрицательной")
    private double price;
}
