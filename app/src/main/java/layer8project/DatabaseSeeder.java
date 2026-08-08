package layer8project;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Populates the Layer7 database with default application data during
 * initialization.
 *
 * Main Responsibilities:
 * - Create the default administrator account
 * - Insert default learning modules
 * - Insert default submodules
 * - Insert default questions and answer choices
 * - Prevent duplicate seed data from being inserted
 *
 * This class is intended to be executed during application startup
 * to ensure the database contains the minimum required data for
 * administrators and users to interact with the system.
 *
 * @author Christopher Sparks
 * @since August 2026
 */
public class DatabaseSeeder {
    /**
 * Inserts the default learning modules, submodules, and questions
 * into the database if they have not already been created.
 *
 * @param moduleRepository the repository used to manage learning modules
 * @param questionRepository the repository used to manage questions
 */
    public static void seedModules(ModuleRepository moduleRepository,  QuestionRepository questionRepository){
        
        ////////////// MODULE 1 ////////////
        // Check if module exists
        if (moduleRepository.getModuleById("MOD01") == null) {
            LearningModule exampleModule1 = new LearningModule("EXM1", "Example Title Mod1",
            "Example Description 1",0, 50);
            moduleRepository.addModule(exampleModule1);

            ///Sub1
            SubModule exampleSubModule1 = new SubModule("EXSUB1", "Example SubModule1", 
            "Example SubModule Description", 0);
            moduleRepository.addSubModule(exampleModule1.getModuleID(),exampleSubModule1);

            ///Question1
            EntryQuestion ex1 = new EntryQuestion("Q001","What barks?","dog");
            questionRepository.addEntryQuestion(ex1, exampleSubModule1.getSubModuleID());
            ///Question2
            EntryQuestion ex2 = new EntryQuestion("Q002","What meows?","cat");
            questionRepository.addEntryQuestion(ex2, exampleSubModule1.getSubModuleID());

            ///Sub2
            SubModule exampleSubModule2 = new SubModule("EXSUB2", "Example SubModule2", 
            "Example SubModule Description", 1);
            moduleRepository.addSubModule(exampleModule1.getModuleID(),exampleSubModule1);

            ///Question3
            ArrayList<String> optionsQ2 = new ArrayList<>();
            optionsQ2.add("Dog");
            optionsQ2.add("Cat");
            optionsQ2.add("Duck");
            optionsQ2.add("Fish");
            MultipleChoiceQuestion q3 = new MultipleChoiceQuestion("Q003", "What barks?", optionsQ2, 1);
            questionRepository.addMultipleChoiceQuestion(q3,exampleSubModule2.getSubModuleID());

            ///Question4
            EntryQuestion ex4 = new EntryQuestion("Q004","Spell FISH","FISH");
            questionRepository.addEntryQuestion(ex4, exampleSubModule2.getSubModuleID());
        }
        ////////////// MODULE 2 ////////////
        // Check if module exists
        if (moduleRepository.getModuleById("MOD02") == null) {
            LearningModule exampleModule2 = new LearningModule("EXM2", "Example Title Mod2",
            "Example Description 1",50, 50);
            moduleRepository.addModule(exampleModule2);

            ///Sub3
            SubModule exampleSubModule3 = new SubModule("EXSUB3", "Example SubModule3", 
            "Example SubModule Description", 0);
            moduleRepository.addSubModule(exampleModule2.getModuleID(),exampleSubModule3);

            ///Question5
            EntryQuestion ex5 = new EntryQuestion("Q005","What barks?","dog");
            questionRepository.addEntryQuestion(ex5, exampleSubModule3.getSubModuleID());
            ///Question6
            EntryQuestion ex6 = new EntryQuestion("Q006","What meows?","cat");
            questionRepository.addEntryQuestion(ex6, exampleSubModule3.getSubModuleID());

            ///Sub4
            SubModule exampleSubModule4 = new SubModule("EXSUB4", "Example SubModule4", 
            "Example SubModule Description", 1);
            moduleRepository.addSubModule(exampleModule2.getModuleID(),exampleSubModule4);

            ///Question7
            ArrayList<String> optionsQ7 = new ArrayList<>();
            optionsQ7.add("Dog");
            optionsQ7.add("Cat");
            optionsQ7.add("Duck");
            optionsQ7.add("Fish");
            MultipleChoiceQuestion q7 = new MultipleChoiceQuestion("Q007", "What barks?", optionsQ7, 1);
            questionRepository.addMultipleChoiceQuestion(q7,exampleSubModule4.getSubModuleID());

            ///Question8
            EntryQuestion ex8 = new EntryQuestion("Q008","Spell FISH","FISH");
            questionRepository.addEntryQuestion(ex8, exampleSubModule4.getSubModuleID());
        }

    }
    
    /**
 * Inserts the default administrator account into the database
 * if it does not already exist.
 *
 * @param userRepository the repository used to access user records
 */
    public static void seedAdmin(UserRepository userRepository) {
        // Only create the admin if it does not already exist
        if (userRepository.findUser("admin") == null) {
            String hashedPassword = BCrypt.hashpw("admin", BCrypt.gensalt());
            User admin = new User("admin",hashedPassword,Role.ADMIN);
            userRepository.addUser(admin);
        }
    }

}
