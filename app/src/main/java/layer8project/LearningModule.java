package layer8project;
import java.util.ArrayList;
/**
 * Represents a learning module within the Layer7 learning
 * management system.
 *
 * A learning module contains instructional content organized into
 * one or more submodules. Each module has a unique identifier,
 * title, description, unlock price, completion reward, and a
 * collection of associated submodules.
 *
 * Learning modules serve as the primary organizational unit for
 * educational content presented to users.
 *
 * Responsibilities:
 * - Store module information
 * - Maintain a collection of submodules
 * - Manage unlock pricing
 * - Manage completion rewards
 *
 * Main Functions:
 * - addSubModule()
 * - removeSubModule()
 * - getSubModules()
 * - setTitle()
 * - setDescription()
 * - setUnlockPrice()
 * - setCompletionReward()
 * 
 * @author Christopher Sparks
 * @since August 2026
 */
public class LearningModule {

    private final String moduleID; // Unique identifier for the learning module
    private String title; // Title of the learning module
    private String description; // Description of the learning module
    private double unlockPrice; // Price to unlock the learning module
    private double completionReward; // Reward for completing the learning module
    private final ArrayList<SubModule> subModules; // List of sub-modules within the learning module


    /**
     * Creates a new learning module.
     * @param moduleID
     * @param title
     * @param description
     * @param unlockPrice
     * @param completionReward
     */
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

    /**
     * Returns the unique identifier for this learning module.
     * @return
     */
    public String getModuleID() {
        return moduleID;
    }

    /**
     * Returns the title of this learning module.v
     * @return
     */
    public String getTitle() {
        return title;
    }


    /**
     * Updates the title of this learning module.
     * @param title
     * @throws IllegalArgumentException if the title is null or empty
     */
    public void setTitle(String title) {
        // Check for null or empty value for title
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.title = title;
    }
    /**
     * Returns the description of this learning module.
     * @return
     */
    public String getDescription() {
        return description;
    }
    /**
     * Updates the description of this learning module.
     * @param description
     * @throws IllegalArgumentException if the description is null or empty
     */
    public void setDescription(String description) {
        // Check for null or empty value for description
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        this.description = description;
    }
    /**
     * Returns the unlock price for this learning module.
     * @return
     */
    public double getUnlockPrice() {
        return unlockPrice;
    }

    /**
     * Updates the unlock price of this learning module.
     * @param unlockPrice the new unlock price
     * @throws IllegalArgumentException if the price is negative
     */
    public void setUnlockPrice(double unlockPrice) {
        // Check for negative value for unlockPrice
        if (unlockPrice < 0) {
            throw new IllegalArgumentException("Unlock price cannot be negative");
        }
        this.unlockPrice = unlockPrice;
    }


    /**
     * Returns the completion reward for this learning module.
     * @return
     */
    public double getCompletionReward() {
        return completionReward;
    }

    /**
     * Updates the completion reward of this learning module.
     * @param completionReward the new completion reward
     * @throws IllegalArgumentException if the reward is negative
     */
    public void setCompletionReward(double completionReward) {
        // Check for negative value for completionReward
        if (completionReward < 0) {
            throw new IllegalArgumentException("Completion reward cannot be negative");
        }
        this.completionReward = completionReward;
    }

    /**
     * Returns a copy of the submodules contained within this
     * learning module.
     * A copy is returned to prevent external modification of the
     * internal submodule collection.
     * @return
     */
    public ArrayList<SubModule> getSubModules() {
        return new ArrayList<>(subModules); // Return a copy to prevent external modification
    }

    /**
     * Adds a submodule to this learning module.
     * @param subModule the submodule to add
     * @throws IllegalArgumentException if the submodule is null
     */
    public void addSubModule(SubModule subModule) {
        // Check for null value for subModule
        if (subModule == null) {
            throw new IllegalArgumentException("SubModule cannot be null");
        }
        subModules.add(subModule);
    }

    /**
     * Removes a submodule from this learning module.
     * @param subModule the submodule to remove
     * @throws IllegalArgumentException if the submodule is null
     */
    public void removeSubModule(SubModule subModule) {
        // Check for null value for subModule
        if (subModule == null) {
            throw new IllegalArgumentException("SubModule cannot be null");
        }
        subModules.remove(subModule);
    }


}
