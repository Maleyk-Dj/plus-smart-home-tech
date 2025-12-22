package ru.yandex.practicum.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "address")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class  Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private long id;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    private String street;
    private String house;
    private String flat;

    @OneToMany(mappedBy = "fromAddress")
    @ToString.Exclude
    private List<Delivery> deliveriesFrom;

    @OneToMany(mappedBy = "toAddress")
    @ToString.Exclude
    private List<Delivery> deliveriesTo;
}