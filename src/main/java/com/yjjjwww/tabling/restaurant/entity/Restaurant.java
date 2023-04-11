package com.yjjjwww.tabling.restaurant.entity;

import com.yjjjwww.tabling.entity.BaseEntity;
import com.yjjjwww.tabling.manager.entity.Manager;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Restaurant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String address;
    String phone;
    String description;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    @ToString.Exclude
    private Manager manager;
}
