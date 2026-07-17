package layer8project;
import java.util.ArrayList;

// This class represents a learning module that contains multiple sub-modules. Each learning module has a unique identifier, title, description, unlock price, and completion reward. It also maintains a list of sub-modules that belong to the learning module.
// getModuleID, getTitle, getDescription, getUnlockPrice, getCompletionReward, getSubModules, addSubModule, removeSubModule

public class LearningModule {

    private final String moduleID; // Unique identifier for the learning module
    private String title; // Title of the learning module
    private String description; // Description of the learning module
    private double unlockPrice; // Price to unlock the learning module
    private double completionReward; // Reward for completing the learning module
    private final ArrayList<SubModule> subModules; // List of sub-modules within the learning module

    public LearningModule(String moduleID, String title, String description, double unlockPrice, double completionReward) {
        // Check for null or empty values for moduleID, title, and description
        if (moduleID == null || moduleID.isEmpty()) {
            throw new IllegalArgumentException("Module ID cannot be null or empty");
        }
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        // Check for negative values for unlockPrice and completionReward
        if (unlockPrice < 0) {
            throw new IllegalArgumentException("Unlock price cannot be negative");
        }
        if (completionReward < 0) {
            throw new IllegalArgumentException("Completion reward cannot be negative");
        }

        this.moduleID = moduleID;
        this.title = title;
        this.description = description;
        this.unlockPrice = unlockPrice;
        this.completionReward = completionReward;
        this.subModules = new ArrayList<>();
    }

    public String getModuleID() {
        return moduleID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        // Check for null or empty value for title
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        // Check for null or empty value for description
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        this.description = description;
    }

    public double getUnlockPrice() {
        return unlockPrice;
    }

    public void setUnlockPrice(double unlockPrice) {
        // Check for negative value for unlockPrice
        if (unlockPrice < 0) {
            throw new IllegalArgumentException("Unlock price cannot be negative");
        }
        this.unlockPrice = unlockPrice;
    }

    public double getCompletionReward() {
        return completionReward;
    }

    public void setCompletionReward(double completionReward) {
        // Check for negative value for completionReward
        if (completionReward < 0) {
            throw new IllegalArgumentException("Completion reward cannot be negative");
        }
        this.completionReward = completionReward;
    }

    public ArrayList<SubModule> getSubModules() {
        return new ArrayList<>(subModules); // Return a copy to prevent external modification
    }

    public void addSubModule(SubModule subModule) {
        // Check for null value for subModule
        if (subModule == null) {
            throw new IllegalArgumentException("SubModule cannot be null");
        }
        subModules.add(subModule);
    }

    public void removeSubModule(SubModule subModule) {
        // Check for null value for subModule
        if (subModule == null) {
            throw new IllegalArgumentException("SubModule cannot be null");
        }
        subModules.remove(subModule);
    }


}
