package com.appcsv.upload.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "USER_TAB")
@NoArgsConstructor
@Data
@ToString
@AllArgsConstructor
@JsonPropertyOrder({"name", "address"})

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private int id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String address;
}
