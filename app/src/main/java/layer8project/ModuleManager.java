package layer8project;
import java.util.ArrayList;
import java.util.List;

// This class manages the unlocking and completion of learning modules and submodules for users. It provides methods to purchase modules, unlock submodules, check if modules or submodules are unlocked or completed, and submit answers to questions. The class interacts with User, UserProgress, LearningModule, SubModule, and Question classes to facilitate user progress tracking and reward management.
// purchaseModule, unlockSubModule, unlockNextSubModule, isModuleUnlocked, isSubModuleUnlocked, checkIfModuleCompleted, checkIfSubModuleCompleted, submitAnswer, canUserAccessSubModule, processCorrectAnswer, processSubModuleCompletion, processModuleCompletion

public class ModuleManager {

    private final UserRepository userRepository;
    private final ProgressRepository progressRepository;
    public ModuleManager(UserRepository userRepository, ProgressRepository progressRepository) {
        this.userRepository = userRepository;
        this.progressRepository = progressRepository;
    }

    public boolean purchaseModule(User user, UserProgress userProgress, LearningModule module) {
        if (user == null || userProgress == null || module == null) {
            throw new IllegalArgumentException("User, progress, and module cannot be null");
            }

            String moduleId = module.getModuleID(); 
            if (userProgress.isModuleUnlocked(moduleId)) { // Check if the module is already unlocked for the user
                System.out.println("Module already unlocked.");
                return false;
            }

        if (user.getBalance() < module.getUnlockPrice()) { // Check if the user has enough balance to purchase the module
            System.out.println("Insufficient balance to purchase the module.");
            return false;
        }

        double newBalance = user.getBalance() - module.getUnlockPrice();
        user.setBalance(newBalance); // Deduct the unlock price from the user's balance
        
        // Unlock the module for the user
        userProgress.unlockModule(moduleId);

        // save the updated user progress to the database
        userRepository.updateBalance(user.getUsername(), newBalance);
        progressRepository.saveUnlockedModule(userProgress.getUserID(), moduleId);

        // unlocks the first submodule of the purchased module for the user
        ArrayList<SubModule> subModules = module.getSubModules();
        if (!subModules.isEmpty()) {
            SubModule firstSubModule = subModules.get(0);
            userProgress.unlockSubModule(firstSubModule.getSubModuleID());
            
            // save submodule unlock to the database
            progressRepository.saveUnlockedSubModule(userProgress.getUserID(), subModules.get(0).getSubModuleID());
        }
        return true;
    }

    private boolean unlockSubModule(User user,UserProgress userProgress, SubModule subModule) {
        if (user == null || userProgress == null || subModule == null) {
            throw new IllegalArgumentException("User, user progress, and submodule cannot be null");
        }

        String subModuleId = subModule.getSubModuleID();
        if (userProgress.isSubModuleUnlocked(subModuleId)) {
            return false;
        }

        boolean unlocked = userProgress.unlockSubModule(subModuleId);
        if (unlocked) {
            progressRepository.saveUnlockedSubModule(userProgress.getUserID(), subModuleId);
        }
        return unlocked;
    }

    private boolean unlockNextSubModule(User user, UserProgress userProgress, LearningModule module, SubModule currentSubModule) {
        if (user == null || userProgress == null || module == null || currentSubModule == null) {
            throw new IllegalArgumentException("User, user progress, and module cannot be null");
        }

        List<SubModule> subModules = module.getSubModules();
        for (int i = 0; i < subModules.size(); i++) {
            SubModule subModule = subModules.get(i);
            if (subModule.getSubModuleID().equals(currentSubModule.getSubModuleID())) {
                int nextIndex = i + 1;
                if (nextIndex >= subModules.size()) {
                    System.out.println("No more submodules to unlock.");
                    return false; // No more submodules to unlock
                }
                SubModule nextSubModule = subModules.get(nextIndex);
                return unlockSubModule(user, userProgress, nextSubModule);

            }
        }
        throw new IllegalArgumentException("Current submodule not found in the provided module");
    }
        

    public boolean isModuleUnlocked(UserProgress userProgress, String moduleId) {
        if (userProgress == null || moduleId == null || moduleId.isEmpty()) {
            throw new IllegalArgumentException("User progress and module ID cannot be null or empty");
        }
        return userProgress.isModuleUnlocked(moduleId);
    }

    public boolean isSubModuleUnlocked(UserProgress userProgress, String subModuleId) {
        if (userProgress == null || subModuleId == null || subModuleId.isEmpty()) {
            throw new IllegalArgumentException("User progress and submodule ID cannot be null or empty");
        }
        return userProgress.isSubModuleUnlocked(subModuleId);
    }


    public boolean checkIfModuleCompleted(UserProgress userProgress, LearningModule module) {
        if (userProgress == null || module == null) {
            throw new IllegalArgumentException("User progress and module cannot be null");
        }
        return userProgress.isModuleCompleted(module.getModuleID());
    }

    public boolean checkIfSubModuleCompleted(UserProgress userProgress, SubModule subModule) {
        if (userProgress == null || subModule == null) {
            throw new IllegalArgumentException("User progress and submodule cannot be null");
        }
        return userProgress.isSubModuleCompleted(subModule.getSubModuleID());
    }
    public boolean submitAnswer(User user,UserProgress userProgress,LearningModule module,SubModule subModule,EntryQuestion question,String userAnswer) {

        if (user == null || userProgress == null || module == null || subModule == null || question == null || userAnswer == null) {
            throw new IllegalArgumentException("Submission information cannot be null");
        }

    // User cannot answer questions in locked content.
        if (!userProgress.isModuleUnlocked(module.getModuleID()) || !userProgress.isSubModuleUnlocked(subModule.getSubModuleID())) {
            return false;
        }
    

        if (!question.checkAnswer(userAnswer)) {
            System.out.println("Incorrect answer. Try again.");
            return false;
        }

        processCorrectAnswer(user, userProgress, module, subModule, question);
        System.out.println("Correct answer!");
        return true;
    }

    public boolean submitAnswer(User user, UserProgress userProgress, LearningModule module, SubModule subModule, MultipleChoiceQuestion question, int selectedChoiceIndex) {

        if (user == null || userProgress == null || module == null || subModule == null || question == null) {

            throw new IllegalArgumentException("Submission information cannot be null");
        }

        if (!userProgress.isModuleUnlocked(module.getModuleID()) || !userProgress.isSubModuleUnlocked(subModule.getSubModuleID())) {
            return false;
        }

        if (!question.validateAnswer(selectedChoiceIndex)) {
            System.out.println("Incorrect answer. Try again.");
            return false;
        }

        processCorrectAnswer(user, userProgress, module, subModule, question);
        System.out.println("Correct answer!");
        return true;
    }

    private void processCorrectAnswer(User user, UserProgress userProgress, LearningModule module, SubModule subModule, Question question) {
        boolean newlyCompleted = userProgress.markQuestionCorrect(question.getID());

        // If the question was already completed,
        // don't count it again.
        if (!newlyCompleted) {
            return;
        }
        progressRepository.saveCompletedQuestion(userProgress.getUserID(), question.getID());
        processSubModuleCompletion(user, userProgress, module, subModule);
        processModuleCompletion(user, userProgress, module);
    }


    public boolean canUserAccessSubModule(UserProgress userProgress, SubModule subModule) {
        if (userProgress == null || subModule == null) {
            throw new IllegalArgumentException("User progress and submodule cannot be null");
        }
        return userProgress.isSubModuleUnlocked(subModule.getSubModuleID());
    }

    private boolean checkSubModuleCompletion(UserProgress userProgress, SubModule subModule) {
        if (userProgress == null || subModule == null) {
            throw new IllegalArgumentException("User progress and submodule cannot be null");
        }
        return userProgress.isSubModuleFullyCompleted(subModule);
    }

    private boolean checkModuleCompletion(UserProgress userProgress, LearningModule module) {
        if (userProgress == null || module == null) {
            throw new IllegalArgumentException("User progress and module cannot be null");
        }
        return userProgress.isModuleFullyCompleted(module);


    }

    private void processSubModuleCompletion(User user, UserProgress userProgress, LearningModule module, SubModule subModule) {
        if (!checkSubModuleCompletion(userProgress, subModule)) {
            return;
        }
        boolean newlyCompleted = userProgress.markSubModuleCompleted(subModule.getSubModuleID());
        if (!newlyCompleted) {
                return; // Submodule was already marked as completed
            }
        System.out.println("Submodule completed!");

        // save the updated user progress to the database
        progressRepository.saveCompletedSubModule(userProgress.getUserID(), subModule.getSubModuleID());
        // unlock the next submodule in the module for the user
        unlockNextSubModule(user, userProgress, module, subModule);
    }

    private void processModuleCompletion(User user, UserProgress userProgress, LearningModule module) {
        if (!checkModuleCompletion(userProgress, module)) {
            return;
        }
        boolean newlyCompleted = userProgress.markModuleCompleted(module.getModuleID());
        if (!newlyCompleted) {
            return; // Module was already marked as completed              
        }

        // Save module completion to the database
        progressRepository.saveCompletedModule(userProgress.getUserID(), module.getModuleID());
        // Give reward
        double newBalance = user.getBalance() + module.getCompletionReward();
        user.setBalance(newBalance);

        // Save new balance
        userRepository.updateBalance(user.getUsername(), newBalance);
        
        System.out.println("Module completed!");
        System.out.println("User rewarded with " + module.getCompletionReward() + " points");

    }

}
