package com.arte.backend.model.database.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserData {
    @Id
    @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private LocalDate birthDate;
    private LocalDate registrationDate;
    @OneToMany(mappedBy = "userId", cascade = CascadeType.PERSIST)
    @Singular
    @EqualsAndHashCode.Exclude
    private Set<FavoriteFolder> favoriteFolders;

}
