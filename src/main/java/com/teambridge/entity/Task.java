package com.teambridge.entity;

import com.teambridge.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tasks")
@Where(clause = "is_deleted=false")
public class Task extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @Column(unique = true)
    private String taskCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private User assignedEmployee;

    private String taskSubject;

    private String taskDetail;

    @Column(columnDefinition = "DATE")
    private LocalDate assignedDate;

    @Enumerated(EnumType.STRING)
    private Status taskStatus;


}
