package com.ms3.spring.batch.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@Entity
@Table(name = "record")
public class Record {

    @Id
    @Column(name = "record_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic
    private Long id;

    @NotEmpty(message = "First name must not be null/empty.")
    @Column(name = "a")
    private String A;

    @NotEmpty(message = "Last name must not be null/empty.")
    @Column(name = "b")
    private String B;

    @Email(message = "Email must be valid.")
    @Column(name = "c")
    private String C;

    @NotEmpty(message = "Gender must not be null/empty.")
    @Column(name = "d")
    private String D;

    @NotEmpty(message = "Data image must not be null/empty.")
    @Column(name = "e")
    private String E;

    @NotEmpty(message = "Card name must not be null/empty.")
    @Column(name = "f")
    private String F;

    @NotEmpty(message = "Total must not be null/empty.")
    @Column(name = "g")
    private String G;

    @NotEmpty(message = "Boolean must not be null/empty.")
    @Column(name = "h")
    private String H;

    @NotEmpty(message = "Boolean must not be null/empty.")
    @Column(name = "i")
    private String I;

    @NotEmpty(message = "Transaction place must be not null/empty.")
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    @Column(name = "j")
    private String J;

}
