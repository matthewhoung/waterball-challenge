package tw.waterballsa.challenge.features.missions;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "missions")
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String unlockCondition;
    private String timeLimit;
    private String reward;
    private Integer rewardExp;

    @Enumerated(EnumType.STRING)
    private MissionLevel missionLevel;

    private boolean isPublished;
    private LocalDateTime publishedAt;
}


