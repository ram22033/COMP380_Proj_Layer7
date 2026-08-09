package layer8project;
import java.util.HashSet;
import java.util.Set;

/**
 * Tracks a user's progress through the Layer7 learning
 * management system.
 *
 * UserProgress records which modules and submodules have been
 * unlocked or completed, as well as which questions have been
 * answered correctly. HashSet collections are used to prevent
 * duplicate progress records and provide efficient membership checks.
 *
 * Responsibilities:
 * - Track unlocked modules
 * - Track completed modules
 * - Track unlocked submodules
 * - Track completed submodules
 * - Track completed questions
 * - Calculate question completion totals and percentages
 * - Determine whether submodules and modules are fully completed
 *
 * Main Functions:
 * - markQuestionCorrect()
 * - markSubModuleCompleted()
 * - markModuleCompleted()
 * - unlockModule()
 * - unlockSubModule()
 * - isModuleUnlocked()
 * - isSubModuleUnlocked()
 * - isModuleCompleted()
 * - isSubModuleCompleted()
 * - isQuestionCompleted()
 * - getCompletedQuestionCount()
 * - getRemainingQuestionCount()
 * - getRemainingQuestionPercentage()
 * - getCompletedQuestionPercentage()
 * - isSubModuleFullyCompleted()
 * - isModuleFullyCompleted()
 *
 * @author Christopher Sparks
 * @since August 2026
 */
public class UserProgress {
    private final String userID;
    // private final Map<String, QuestionProgress> questionProgress; // Maps Question ID to its progress; Use this is if you want to track progress at the question level and create a QuestionProgress class to hold details like attempts, correct answers, etc.
    private final Set<String> unlockedModuleIds; // Set of unlocked LearningModule IDs
    private final Set<String> completedModuleIds; // Keeps track of modules that have already been rewarded
    private final Set<String> unlockedSubModuleIds; // Set of unlocked SubModule IDs
    private final Set<String> completedSubModuleIds; // Keeps track of submodules that have already been rewarded
    private final Set<String> completedQuestionIds; // Keeps track of questions that have already been rewarded

    /**
     * Creates a new progress tracker for a user.
     * 
     * @param userID the unique identifier of the user
     * @throws IllegalArgumentException if the user ID is null or empty
     */
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

    /**
     * Records that a question has been answered correctly.
     * @param questionId the unique identifier of the completed question
     * @return {@code true} if the question was newly recorded;
     * {@code false} if it was already completed
     */
    public boolean markQuestionCorrect(String questionId) {
        return completedQuestionIds.add(questionId);
    }

    /**
     * Records that a submodule has been completed.
     * @param subModuleId the unique identifier of the completed submodule
     * @return {@code true} if the submodule was newly recorded;
     * {@code false} if it was already completed
     */
    public boolean markSubModuleCompleted(String subModuleId) {
        return completedSubModuleIds.add(subModuleId);
    }

    /**
     * Records that a learning module has been completed.
     * 
     * @param moduleId the unique identifier of the completed module
     * @return {@code true} if the module was newly recorded;
     * {@code false} if it was already completed
     */
    public boolean markModuleCompleted(String moduleId) {
        return completedModuleIds.add(moduleId);
    }

    /**
     * Records that a learning module has been unlocked.
     * 
     * @param moduleId the unique identifier of the unlocked module
     * @return {@code true} if the module was newly unlocked;
     * {@code false} if it was already unlocked
     */
    public boolean unlockModule(String moduleId) {
        return unlockedModuleIds.add(moduleId);
    }

    /**
     * Records that a submodule has been unlocked.
     * 
     * @param subModuleId the unique identifier of the unlocked submodule
     * @return {@code true} if the submodule was newly unlocked;
     * {@code false} if it was already unlocked
     */
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

    /**
     * Counts the completed questions within a specific submodule.
     * 
     * @param subModule the submodule whose completed questions are counted
     * @return the number of completed questions in the submodule
     */
    public int getCompletedQuestionCount(SubModule subModule) {
        int count = 0;
        for (Question question : subModule.getQuestions()) {
            if (completedQuestionIds.contains(question.getID())) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the number of incomplete questions remaining within a submodule.
     * 
     * @param subModule the submodule whose remaining questions are counted
     * @return the number of incomplete questions
     */
    public int getRemainingQuestionCount(SubModule subModule) {
        int totalQuestions = subModule.getQuestionCount();
        int completedQuestions = getCompletedQuestionCount(subModule);

        return totalQuestions - completedQuestions;

    }

    /**
     * Calculates the percentage of questions that remain incomplete within a submodule.
     * 
     * @param subModule the submodule used for the calculation
     * @return the percentage of remaining questions, or {@code 0} when the submodule contains no questions
     */
    public double getRemainingQuestionPercentage(SubModule subModule) {
        int totalQuestions = subModule.getQuestionCount();
        int completedQuestions = getCompletedQuestionCount(subModule);

        if (totalQuestions == 0) {
            return 0; // Avoid division by zero
        }

        return ((double) (totalQuestions - completedQuestions) / totalQuestions) * 100;
    }

    /**
     * the percentage of remaining questions, or {@code 0} when the submodule contains no questions
     * 
     * @param subModule the submodule used for the calculation
     * @return the completed-question percentage, or {@code 0} when the submodule contains no questions
     */
    public double getCompletedQuestionPercentage(SubModule subModule) {
        int totalQuestions = subModule.getQuestionCount();
        int completedQuestions = getCompletedQuestionCount(subModule);

        if (totalQuestions == 0) {
            return 0; // Avoid division by zero
        }

        return ((double) completedQuestions / totalQuestions) * 100;
    }

    /**
     * Determines whether every question in a submodule has been completed.
     * 
     * @param subModule the submodule to evaluate
     * @return {@code true} if no incomplete questions remain;
     * otherwise {@code false}
     */
    public boolean isSubModuleFullyCompleted(SubModule subModule) {
        return getRemainingQuestionCount(subModule) == 0;
    }

    /**
     * Determines whether every submodule in a learning module has been fully completed.
     * 
     * @param module the learning module to evaluate
     * @return {@code true} if every submodule is complete;
     * otherwise {@code false}
     */
    public boolean isModuleFullyCompleted(LearningModule module) {
        for (SubModule subModule : module.getSubModules()) {
            if (!isSubModuleFullyCompleted(subModule)) {
                return false;
            }
        }
        return true;
    }

    public int getCompletedQuestionCount() {
        return completedQuestionIds.size();
    }

    /**
     * Calculates the user's overall question completion percentage.
     * @param totalQuestions the total number of questions in Layer7
     * @return the user's overall completion percentage
     */
    public double getOverallProgressPercentage(int totalQuestions) {
        if (totalQuestions <= 0) {
            return 0;
        }
        return ((double) completedQuestionIds.size() / totalQuestions) * 100;
    }

    



}
