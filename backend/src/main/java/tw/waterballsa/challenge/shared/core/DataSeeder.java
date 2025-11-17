package tw.waterballsa.challenge.shared.core;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tw.waterballsa.challenge.features.missions.Mission;
import tw.waterballsa.challenge.features.missions.MissionRepository;
import tw.waterballsa.challenge.features.missions.MissionLevel;
import tw.waterballsa.challenge.features.students.Student;
import tw.waterballsa.challenge.features.students.StudentRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final MissionRepository missionRepository;
    private final StudentRepository studentRepository;

    @Override
    public void run(String... args) throws Exception {
        // 避免重複執行
        if (missionRepository.count() > 0) return;

        System.out.println("[DataSeeder] 開始初始化資料...");

        // 1. 建立任務 (對應前端截圖)
        var mNewbie1 = createMission("新手任務一", "觀看介紹影片", "無限制", "經驗值 300", 300, MissionLevel.NEWBIE);
        var mNewbie2 = createMission("新手任務二", "完成新手任務一", "無限制", "延長期限 30 天", 0, MissionLevel.NEWBIE);
        var mWhite2 = createMission("白段任務二", "通過道館 3", "30 天", "延長期限 30 天", 300, MissionLevel.WHITE);
        var mWhite3 = createMission("白段任務三", "通過道館 4", "30 天", "延長期限 90 天", 500, MissionLevel.WHITE);
        var mBlack1 = createMission("黑段任務一", "完成白段任務三", "30 天", "延長期限 30 天", 1000, MissionLevel.BLACK);

        missionRepository.saveAll(List.of(mNewbie1, mNewbie2, mWhite2, mWhite3, mBlack1));

        // 2. 建立預設學生
        Student student = new Student();
        student.setName("Matthew Hong");
        student.setEmail("matthew@waterballsa.tw");
        student.setLevel(1);
        student.setTotalExp(0L);
        studentRepository.save(student);

        System.out.println("✅ [DataSeeder] 資料初始化完成！");
    }

    private Mission createMission(String title, String condition, String timeLimit, String reward, int xp, MissionLevel type) {
        Mission m = new Mission();
        m.setTitle(title);
        m.setDescription("這是 " + title + " 的詳細說明...");
        m.setUnlockCondition(condition);
        m.setTimeLimit(timeLimit);
        m.setReward(reward);
        m.setRewardExp(xp);
        m.setMissionLevel(type);
        m.setPublished(true);
        return m;
    }
}