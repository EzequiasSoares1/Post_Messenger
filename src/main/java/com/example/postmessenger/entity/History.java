package com.example.postmessenger.entity;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class History {
    public History(Status status) {
        this.status = status;
        this.date = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private Status status;
}

