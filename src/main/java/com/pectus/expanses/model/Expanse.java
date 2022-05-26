package com.pectus.expanses.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(Include.NON_NULL)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Expanse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String department;

    @JsonProperty("project_name")
    private String projectName;

    private BigDecimal amount;

    private LocalDate date;

    @JsonProperty("member_name")
    private String memberName;


}
