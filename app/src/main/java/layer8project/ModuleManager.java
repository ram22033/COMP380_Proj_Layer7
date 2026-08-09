package layer8project;
import java.util.ArrayList;
import java.util.List;

/**
 * Coordinates learning module operations within the Layer7
 * learning management system.
 *
 * ModuleManager serves as the intermediary between the graphical
 * user interface and the application's repository layer. It is
 * responsible for validating requests, coordinating repository
 * operations, and managing learning modules, submodules,
 * questions, and user progress.
 *
 * Responsibilities:
 * - Manage learning modules
 * - Manage submodules
 * - Manage questions
 * - Manage user learning progress
 * - Coordinate repository communication
 * - Validate module-related operations
 *
 * Main Functions:
 * - addModule()
 * - updateModule()
 * - deleteModule()
 * - addSubModule()
 * - updateSubModule()
 * - deleteSubModule()
 * - addEntryQuestion()
 * - addMultipleChoiceQuestion()
 * - updateEntryQuestion()
 * - updateMultipleChoiceQuestion()
 * - deleteQuestion()
 * - purchaseModule()
 * - unlockSubModule()
 * - markModuleCompleted()
 *
 * @author Christopher Sparks
 * @since August 2026
 */
public class ModuleManager {

    private final UserRepository userRepository;
    private final ProgressRepository progressRepository;
    private ModuleRepository moduleRepository;
    private final QuestionRepository questionRepository;
    public ModuleManager(UserRepository userRepository, ProgressRepository progressRepository, ModuleRepository moduleRepository, QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.progressRepository = progressRepository;
        this.moduleRepository = moduleRepository;
        this.questionRepository = questionRepository;
    }

    /**
     * Unlocks a learning module for a user after verifying
     * sufficient account balance and updates the user's
     * progress and account information.
     * @param user
     * @param userProgress
     * @param module
     * @return
     */
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
        ArrayList<SubModule> subModules = moduleRepository.getSubModules(module.getModuleID());
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

        List<SubModule> subModules = moduleRepository.getSubModules(module.getModuleID());
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


    /**
     * Validates a user's response to an entry question and,
     * if correct, updates question, submodule, and module
     * completion progress.
     * @param user
     * @param userProgress
     * @param module
     * @param subModule
     * @param question
     * @param userAnswer
     * @return
     */
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

    /**
     * Validates a user's selected answer for a multiple-choice
     * question and updates learning progress when answered
     * correctly.
     * @param user
     * @param userProgress
     * @param module
     * @param subModule
     * @param question
     * @param selectedChoiceIndex
     * @return
     */
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

    /**
     * Processes a correctly answered question by recording
     * completion and updating user progress.
     * @param user
     * @param userProgress
     * @param module
     * @param subModule
     * @param question
     */
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
        ArrayList<Question> questions = questionRepository.getQuestionsBySubModuleId(subModule.getSubModuleID());
        // A submodule with no questions should not automatically count as completed.
        if (questions.isEmpty()) {
            return false;
        }
        for (Question question : questions) {
            if (!userProgress.isQuestionCompleted(question.getID())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkModuleCompletion(UserProgress userProgress, LearningModule module) {
        if (userProgress == null || module == null) {
            throw new IllegalArgumentException("User progress and module cannot be null");
        }
        ArrayList<SubModule> subModules = moduleRepository.getSubModules(module.getModuleID());
        if (subModules.isEmpty()) {
            return false;
        }
        for (SubModule subModule : subModules) {
            if (!userProgress.isSubModuleCompleted(subModule.getSubModuleID())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Marks a submodule as completed and unlocks the next
     * available submodule when appropriate.
     * @param user
     * @param userProgress
     * @param module
     * @param subModule
     */
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


   /**
    * Marks a learning module as completed, awards the user
    * the completion reward, and updates persistent data.
    * @param user
    * @param userProgress
    * @param module
    */
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
    
    public ArrayList<LearningModule> getAllModules() {
        return moduleRepository.getAllModules();
    }
    
    public LearningModule getModuleById(String moduleID) {
        return moduleRepository.getModuleById(moduleID);
    }
    
    public ArrayList<SubModule> getSubModules(String moduleID) {
        return moduleRepository.getSubModules(moduleID);
    }

    public SubModule getSubModuleById(String subModuleID) {
        return moduleRepository.getSubModuleById(subModuleID);
    }

    public ArrayList<Question> getQuestionsBySubModuleId(String subModuleID) {
        return questionRepository.getQuestionsBySubModuleId(subModuleID);
    }


   /**
    * Adds a new learning module to the database.
    * @param module
    * @return
    */
    public boolean addModule(LearningModule module) {
        return moduleRepository.addModule(module);
    }


   /**
    * Updates an existing learning module.
    * @param module
    * @return
    */
    public boolean updateModule(LearningModule module) {
        if (module == null) {
            throw new IllegalArgumentException("Module cannot be null");
        }
        return moduleRepository.updateModule(module);
    }


   /**
    * *Removes a learning module from the database.
    * @param moduleID
    * @return
    */
    public boolean deleteModule(String moduleID) {
        if (moduleID == null || moduleID.isBlank()) {
            throw new IllegalArgumentException("Module ID cannot be null or empty");
        }
        return moduleRepository.deleteModule(moduleID);
    }
    /**
     * Adds a submodule to an existing learning module.
     * @param moduleID
     * @param subModule
     * @return
     */
    public boolean addSubModule(String moduleID, SubModule subModule) {
        if (moduleID == null || moduleID.isBlank()) {
            throw new IllegalArgumentException("Module ID cannot be null or empty");
        }
        if (subModule == null) {
            throw new IllegalArgumentException("Submodule cannot be null");
        }
        return moduleRepository.addSubModule(moduleID,subModule);
    }
    public boolean updateSubModule(SubModule subModule) {
        if (subModule == null) {
            throw new IllegalArgumentException("Submodule cannot be null");
        }
        return moduleRepository.updateSubModule(subModule);
    }
    public boolean deleteSubModule(String subModuleID) {
        if (subModuleID == null || subModuleID.isBlank()) {
            throw new IllegalArgumentException("Submodule ID cannot be null or empty");
        }
        return moduleRepository.deleteSubModule(subModuleID);
    }
    public boolean addEntryQuestion(EntryQuestion question, String subModuleID) {
        return questionRepository.addEntryQuestion(question, subModuleID);
    }
    public boolean addMultipleChoiceQuestion(MultipleChoiceQuestion question, String subModuleID) {
        return questionRepository.addMultipleChoiceQuestion(question, subModuleID);
    }
    public Question getQuestionById(String questionID) {
        return questionRepository.getQuestionById(questionID);
    }
    public boolean updateEntryQuestion(EntryQuestion question) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        return questionRepository.updateEntryQuestion(question);
    }
    public boolean updateMultipleChoiceQuestion(MultipleChoiceQuestion question) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        return questionRepository.updateMultipleChoiceQuestion(question);
    }
    
    /**
     * Removes a question from the database.
     * @param questionID
     * @return
     */
    public boolean deleteQuestion(String questionID) {
        if (questionID == null || questionID.isBlank()) {
            throw new IllegalArgumentException("Question ID cannot be null or empty");
        }
        return questionRepository.deleteQuestion(questionID);
    }

    /**
     * Returns the total number of questions in Layer7.
     * @return the total number of questions
     */
    public int getTotalQuestionCount() {
        return questionRepository.getTotalQuestionCount();
    }

}
