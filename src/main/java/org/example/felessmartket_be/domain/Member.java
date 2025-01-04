package org.example.felessmartket_be.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memeber_id")
    Long id;
    String username;
    String name;
    String password;
    String phone;

    @Column(unique = true)
    String email;
    String address;
    String userStatus; // enum
    LocalDateTime created_at;
    LocalDateTime updated_at;
    LocalDateTime deleted_at;

    @OneToOne(mappedBy = "member", optional = false)
    Cart cart;

    @OneToMany(mappedBy = "member")
    List<Orders> orderList;

    public Member(String username, String password, String phoneNumber, String email) {
        this.username = username;
        this.password = password;
        this.phone = phoneNumber;
        this.email = email;
    }
}
