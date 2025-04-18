package com.vitira.itreasury.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BankSwiftCode {

    @Id
    private Long id;
    @JsonProperty("bank_name")
    private String bankName;
    @JsonProperty("swift_code")
    private String swiftCode;

    private String branch;
    private String city;

}
