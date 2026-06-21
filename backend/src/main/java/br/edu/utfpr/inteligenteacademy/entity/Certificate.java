package br.edu.utfpr.inteligenteacademy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
                columnNames = "enrollment_id"
        )
    }
)
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Código público de validação.
     */
    @Column(nullable = false, unique = true)
    private String validationCode;

    /**
     * Data de emissão.
     */
    @Column(nullable = false)
    private Instant issuedAt;

    /**
     * Caminho do PDF no storage.
     */
    private String pdfUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "enrollment_id",
            nullable = false,
            unique = true
    )
    private Enrollment enrollment;
}