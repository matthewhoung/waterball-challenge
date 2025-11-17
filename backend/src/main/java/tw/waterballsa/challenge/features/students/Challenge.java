package tw.waterballsa.challenge.features.students;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.waterballsa.challenge.features.missions.Mission;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "challenges")
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Enumerated(EnumType.STRING)
    private ChallengeStatus status;

    @Enumerated(EnumType.STRING)
    private ChallengeMode mode;

    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
}
