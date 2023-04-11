package ru.clevertec.ecl.repository.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "ecl", name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @Override
    public Long getId() {
        return this.id;
    }
}
