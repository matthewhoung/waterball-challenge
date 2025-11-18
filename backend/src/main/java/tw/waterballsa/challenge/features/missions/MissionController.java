package tw.waterballsa.challenge.features.missions;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/missions")
public class MissionController {

    private final MissionRepository _missionRepository;

    @GetMapping
    public List<MissionDto> listMissions() {
        return _missionRepository.findAll()
                .stream()
                .map(MissionDto::from)
                .toList();
    }
}
