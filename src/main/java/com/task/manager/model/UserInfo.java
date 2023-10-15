package com.task.manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User_info")
public class UserInfo {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;


    private String firstName;
    private String lastName;

    @JsonIgnore
    @OneToOne(mappedBy = "userInfo", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private User user;

}
