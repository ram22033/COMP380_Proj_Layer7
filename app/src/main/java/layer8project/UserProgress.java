package layer8project;
import java.util.HashSet;
import java.util.Set;

// This class tracks the progress of a user through various learning modules, submodules, and questions. It maintains sets of unlocked and completed modules, submodules, and questions, allowing for efficient tracking of user progress. The class provides methods to mark questions, submodules, and modules as completed or unlocked, as well as methods to retrieve counts and percentages of completed and remaining items.
// markQuestionCorrect, markSubModuleCompleted, markModuleCompleted, unlockModule, unlockSubModule, isModuleUnlocked, isSubModuleUnlocked, isModuleCompleted, isSubModuleCompleted, isQuestionCompleted, getUserID, getUnlockedModuleCount, getCompletedModuleCount, getUnlockedSubModuleCount, getCompletedSubModuleCount, getCompletedQuestionCount, getRemainingQuestionCount, getRemainingQuestionPercentage, getCompletedQuestionPercentage, isSubModuleFullyCompleted, isModuleFullyCompleted

public class UserProgress {


    private final String userID;
    // private final Map<String, QuestionProgress> questionProgress; // Maps Question ID to its progress; Use this is if you want to track progress at the question level and create a QuestionProgress class to hold details like attempts, correct answers, etc.
    private final Set<String> unlockedModuleIds; // Set of unlocked LearningModule IDs
    private final Set<String> completedModuleIds; // Keeps track of modules that have already been rewarded
    private final Set<String> unlockedSubModuleIds; // Set of unlocked SubModule IDs
    private final Set<String> completedSubModuleIds; // Keeps track of submodules that have already been rewarded
    private final Set<String> completedQuestionIds; // Keeps track of questions that have already been rewarded

    public UserProgress(String userID) {
        if (userID == null || userID.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        this.userID = userID;
        this.unlockedModuleIds = new HashSet<>();
        this.completedModuleIds = new HashSet<>();
        this.unlockedSubModuleIds = new HashSet<>();
        this.completedSubModuleIds = new HashSet<>();
        this.completedQuestionIds = new HashSet<>();
    }
    public boolean markQuestionCorrect(String questionId) {
        return completedQuestionIds.add(questionId);
    }

    public boolean markSubModuleCompleted(String subModuleId) {
        return completedSubModuleIds.add(subModuleId);
    }

    public boolean markModuleCompleted(String moduleId) {
        return completedModuleIds.add(moduleId);
    }

    public boolean unlockModule(String moduleId) {
        return unlockedModuleIds.add(moduleId);
    }

    public boolean unlockSubModule(String subModuleId) {
        return unlockedSubModuleIds.add(subModuleId);
    }

    public boolean isModuleUnlocked(String moduleId) {
        return unlockedModuleIds.contains(moduleId);
    }

    public boolean isSubModuleUnlocked(String subModuleId) {
        return unlockedSubModuleIds.contains(subModuleId);
    }

    public boolean isModuleCompleted(String moduleId) {
        return completedModuleIds.contains(moduleId);
    }

    public boolean isSubModuleCompleted(String subModuleId) {
        return completedSubModuleIds.contains(subModuleId);
    }

    public boolean isQuestionCompleted(String questionId) {
        return completedQuestionIds.contains(questionId);
    }

    public String getUserID() {
        return userID;
    }

    public int getUnlockedModuleCount() {
        return unlockedModuleIds.size();
    }

    public int getCompletedModuleCount() {
        return completedModuleIds.size();
    }

    public int getUnlockedSubModuleCount() {
        return unlockedSubModuleIds.size();
    }

    public int getCompletedSubModuleCount() {
        return completedSubModuleIds.size();
    }

    public int getCompletedQuestionCount(SubModule subModule) {
        int count = 0;
        for (Question question : subModule.getQuestions()) {
            if (completedQuestionIds.contains(question.getID())) {
                count++;
            }
        }
        return count;
    }

    public int getRemainingQuestionCount(SubModule subModule) {
        int totalQuestions = subModule.getQuestionCount();
        int completedQuestions = getCompletedQuestionCount(subModule);

        return totalQuestions - completedQuestions;

    }

    public double getRemainingQuestionPercentage(SubModule subModule) {
        int totalQuestions = subModule.getQuestionCount();
        int completedQuestions = getCompletedQuestionCount(subModule);

        if (totalQuestions == 0) {
            return 0; // Avoid division by zero
        }

        return ((double) (totalQuestions - completedQuestions) / totalQuestions) * 100;
    }

    public double getCompletedQuestionPercentage(SubModule subModule) {
        int totalQuestions = subModule.getQuestionCount();
        int completedQuestions = getCompletedQuestionCount(subModule);

        if (totalQuestions == 0) {
            return 0; // Avoid division by zero
        }

        return ((double) completedQuestions / totalQuestions) * 100;
    }

    public boolean isSubModuleFullyCompleted(SubModule subModule) {
        return getRemainingQuestionCount(subModule) == 0;
    }

    public boolean isModuleFullyCompleted(LearningModule module) {
        for (SubModule subModule : module.getSubModules()) {
            if (!isSubModuleFullyCompleted(subModule)) {
                return false;
            }
        }
        return true;
    }



}
