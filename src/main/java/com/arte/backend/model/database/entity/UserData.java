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
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @Singular
    @EqualsAndHashCode.Exclude
    private Set<FavoriteFolder> favoriteFolders;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_statistics_id", referencedColumnName = "id")
    private UserStatistics userStatistics;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private List<UserRole> roles = new ArrayList<>();

}
