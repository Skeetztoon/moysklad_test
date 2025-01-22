package com.yurlov.moysklad_test.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_id_generator")
    @SequenceGenerator(name = "delivery_id_generator", sequenceName = "delivery_id", allocationSize = 1)
    private Integer id;

    @Size(max = 255, message = "Максимальная длина названия - 255 симоволов")
    private String title;

    @NotNull(message = "Товар обязателен")
    @ManyToOne(fetch = FetchType.EAGER)
    @Lazy
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Min(value = 1, message = "Количество должно быть больше 0")
    private Integer quantity;
}
