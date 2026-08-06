package layer8project;

/**
 * App.java
 *
 * Serves as the entry point for the Layer7 learning management system.
 *
 * Responsibilities:
 * - Initializes all repository objects
 * - Creates the manager classes
 * - Seeds the database with default data when necessary
 * - Launches the application's login page
 *
 * This class is responsible for configuring the application's
 * dependencies before presenting the user interface.
 *
 * @author Richard Mondragon
 * @author Christopher Sparks
 * @since August 2026
 */

public class App {
    
    public static void main(String[] args) {

        // Add Repositories and Managers
        UserRepository userRepository = new UserRepository();
        ModuleRepository moduleRepository = new ModuleRepository();
        QuestionRepository questionRepository = new QuestionRepository();
        ProgressRepository progressRepository = new ProgressRepository();
        UserManager userManager = new UserManager(userRepository);
        ModuleManager moduleManager = new ModuleManager(userRepository, progressRepository, moduleRepository, questionRepository);
        
        // Seed default modules
        // DatabaseSeeder.seedModules(moduleRepository, questionRepository);
        // Seed default admin
        DatabaseSeeder.seedAdmin(userRepository);

        // START THE APP

        new LoginPage(userManager, moduleManager, progressRepository);


    }
}
