package org.cucumber.server.models.bo;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user", schema = "public")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "password", nullable = false)
    private String password; //must be hashed

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "description")
    private String description;

    @Column(name = "age")
    private Integer age;

    @Column(name = "avatar")
    private String avatar;

    @ManyToMany()
    @JoinTable(
            name = "user_favorite",
            joinColumns = @JoinColumn(name = "from"),
            inverseJoinColumns = @JoinColumn(name = "to")
    )
    @ToString.Exclude
    private Set<User> favorites = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "favorites")
    @ToString.Exclude
    private Set<User> favoritedBy = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "users")
    @ToString.Exclude
    private Set<Room> rooms = new LinkedHashSet<>();

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
