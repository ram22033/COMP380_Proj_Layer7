package layer8project;
import java.util.List;
import java.util.ArrayList;

// continue this and add the following methods to the ModuleManager class:
// purchaseModule, unlockSubModule, isModuleUnlocked, isSubModuleUnlocked, isModule, submitAnswer(for both question types), getUserProgress, getModuleById, getSubModuleById, checkIfModuleCompleted, 
// checkIfSubModuleCompleted, processAnswer

public class ModuleManager {
    public boolean purchaseModule(User user, UserProgress userProgress, LearningModule module) {

        if (user == null || userProgress == null || module == null) {
            throw new IllegalArgumentException("User, progress, and module cannot be null");
            }

            String moduleId = module.getModuleID();
            if (userProgress.isModuleUnlocked(moduleId)) {
                System.out.println("Module already unlocked.");
                return false;
            }

        if (user.getBalance() >= module.getUnlockPrice()) { // Check if the user has enough balance to purchase the module
            user.setBalance(user.getBalance() - module.getUnlockPrice());
            userProgress.unlockModule(module.getModuleID());
        } else { // If the user doesn't have enough balance, print a message and return false
            System.out.println("Insufficient balance to purchase the module.");
            return false;
        }
            // unlocks the first submodule of the purchased module for the user
        ArrayList<SubModule> subModules = module.getSubModules();
        if (!subModules.isEmpty()) {
            SubModule firstSubModule = subModules.get(0);
            userProgress.unlockSubModule(firstSubModule.getSubModuleID());
        }
        return true;
    }

}
