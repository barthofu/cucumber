package org.cucumber.server.models.bo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_favorite", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserFavorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "from")
    private User from;

    @ManyToOne
    @JoinColumn(name = "to")
    private User to;
}
