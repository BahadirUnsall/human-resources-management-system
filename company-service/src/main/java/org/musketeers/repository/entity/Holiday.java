package org.musketeers.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "tbl_holidays")
public class Holiday{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String companyId;
    private String name;
    private Integer duration;
}
