package org.cucumber.server.models.bo;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "room", schema = "public")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "closed")
    private Boolean closed;


    @Column(name = "started_at")
    private Date started_at;

    @OneToMany(mappedBy = "room")
    @ToString.Exclude
    private Set<Message> messages = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(
            name = "room_user",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @ToString.Exclude
    private Set<User> users = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Room room = (Room) o;
        return id != null && Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
