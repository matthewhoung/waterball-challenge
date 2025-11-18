package tw.waterballsa.challenge.contexts.identity.domain.enums;

public enum StudentRank {
    NONE("無段位", -1),
    WHITE_1("白段一", 0),
    WHITE_2("白段二", 100),
    WHITE_3("白段三", 300),
    BLACK_1("黑段一", 600),
    BLACK_2("黑段二", 1000),
    BLACK_3("黑段三", 1500);

    private final String displayName;
    private final int requiredExp;

    StudentRank(String diplayName, int reuquiredExp) {
        this.displayName = diplayName;
        this.requiredExp = reuquiredExp;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getRequiredExp() {
        return requiredExp;
    }

    public static StudentRank fromExp(int totalExp) {
        StudentRank[] ranks = values();
        for (int i = ranks.length - 1; i >= 0; i--) {
            if (totalExp >= ranks[i].requiredExp) {
                return ranks[i];
            }
        }
        return WHITE_1;
    }
}
