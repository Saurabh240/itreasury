package com.vitira.itreasury.entity;

import com.vitira.itreasury.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
//@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payment {

	private Double amount;
	private String currency;
	private String description;

	// TODO: Note that below properties are there in BaseEntity class so just extend it
	//  but I am facing some casting issue if I extend so resolve it.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdDate;

	@LastModifiedDate
	@Column(insertable = false)
	private LocalDateTime lastModifiedDate;

	@CreatedBy
	@Column(nullable = false, updatable = false)
	private String createdBy;

	@LastModifiedBy
	@Column(insertable = false)
	private String lastModifiedBy;

}