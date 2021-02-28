package com.arte.backend.model.database.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "user_data")
public class UserData {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    private String firstName;
    private String lastName;
    private String userName;
    @Column(unique = true)
    private String email;
    private String password;
    private LocalDate birthDate;
    private LocalDate registrationDate;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "favorite_collection_id", referencedColumnName = "id")
    private FavoriteCollection favoriteCollection;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_statistics_id", referencedColumnName = "id")
    private UserStatistics userStatistics;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private List<UserRole> roles = new ArrayList<>();

}
