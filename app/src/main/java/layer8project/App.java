package layer8project;


public class App {
    
    public static void main(String[] args) {

        // Add Repositories and Managers
        UserRepository userRepository = new UserRepository();
        ModuleRepository moduleRepository = new ModuleRepository();
        QuestionRepository questionRepository = new QuestionRepository();
        ProgressRepository progressRepository = new ProgressRepository();
        UserManager userManager = new UserManager(userRepository);
        ModuleManager moduleManager = new ModuleManager(userRepository, progressRepository, moduleRepository);
        
        // Seed default modules
        DatabaseSeeder.seedModules(moduleRepository, questionRepository);
        // Seed default admin
        DatabaseSeeder.seedAdmin(userRepository);

        // START THE APP

        new LoginPage(userManager, moduleManager, progressRepository);


    }
}
