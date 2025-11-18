package tw.waterballsa.challenge.features.missions;

public record MissionDto (
        String id,
        String title,
        String condition,
        String timeLimit,
        String reward,
        String status,
        String level
) {
    public static MissionDto from(Mission mission) {
        return new MissionDto(
                mission.getId(),
                mission.getTitle(),
                mission.getUnlockCondition(),
                mission.getTimeLimit(),
                mission.getReward(),
                //TODO: 之後會根據使用者Challenge的狀態動態計算，目前先全部設為AVAILABLE
                "AVAILABLE",
                mission.getMissionLevel().name()
        );
    }
}
