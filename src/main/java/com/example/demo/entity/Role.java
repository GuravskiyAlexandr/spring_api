package com.example.demo.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Data
@EqualsAndHashCode(of = {"id", "role"})
@AllArgsConstructor
@RequiredArgsConstructor
@ToString(of = {"id", "role"})
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority , Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Id.class)
    private Long id;
    @JsonView(Views.Name.class)
    private String role;
    @ManyToMany(mappedBy = "roles")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    public Set<User> user;

    public Role(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Role(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
