package com.RecruitmentTask.User;

import com.RecruitmentTask.Report.Report;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name = "UserTable")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String password;
    private String role;

    @OneToMany(mappedBy = "reportingPerson", cascade = CascadeType.ALL)
    private List<Report> reports;

    public User(Long id) {
        this.id = id;
    }
}