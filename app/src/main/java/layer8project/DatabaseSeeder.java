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
        if (moduleRepository.getModuleById("M001") == null) {
            LearningModule cyberSecurityFoundations = new LearningModule("M001", "Cyber Security Foundations",
            "Introduces the fundamental concepts of cybersecurity",0, 50);
            moduleRepository.addModule(cyberSecurityFoundations);

            ///Sub1 - Introduction to Cybersecurity
            SubModule cybersecurityIntroduction = new SubModule("S001", "Introduction to Cybersecurity", 
                                                    "Cybersecurity is the practice of protecting systems, \"\n" + //
                                "                    + \"networks, applications, and data from unauthorized access, \"\n" + //
                                "                    + \"damage, disruption, or theft. Security professionals work \"\n" + //
                                "                    + \"to protect information and technology while allowing \"\n" + //
                                "                    + \"authorized users to access the resources they need.\",", 0);
            moduleRepository.addSubModule(cyberSecurityFoundations.getModuleID(),cybersecurityIntroduction);

            ///Question1
            ArrayList<String> optionsQ1 = new ArrayList<>();
            optionsQ1.add("Protecting systems, networks, and data");
            optionsQ1.add("Designing computer graphics");
            optionsQ1.add("Increasing internet speed");
            optionsQ1.add("Building computer hardware");
            MultipleChoiceQuestion q1 = new MultipleChoiceQuestion("MQ001", "What is the primary purpose of cybersecurity?", optionsQ1, 0);
            questionRepository.addMultipleChoiceQuestion(q1, cybersecurityIntroduction.getSubModuleID());

            ///Question2
            ArrayList<String> optionsQ2 = new ArrayList<>();
            optionsQ2.add("Hardware");
            optionsQ2.add("Information and systems");
            optionsQ2.add("Only passwords");
            optionsQ2.add("Only internet connections");
            MultipleChoiceQuestion q2 = new MultipleChoiceQuestion("M001Q02", "Cybersecurity primarily protects which of the following?", optionsQ2, 1);
            questionRepository.addMultipleChoiceQuestion(q2, cybersecurityIntroduction.getSubModuleID());

            ///Sub2 - CIA TRIAD
            SubModule ciaTriad =new SubModule("M001S02", "The CIA Triad",
                    "The CIA triad represents three fundamental goals of "
                    + "information security: Confidentiality, Integrity, and "
                    + "Availability. Confidentiality prevents unauthorized access "
                    + "to information. Integrity ensures information remains "
                    + "accurate and unaltered. Availability ensures authorized "
                    + "users can access systems and information when needed.",
                    1
            );
            moduleRepository.addSubModule(cyberSecurityFoundations.getModuleID(),ciaTriad);

            // Question 3
            ArrayList<String> optionsQ3 = new ArrayList<>();
            optionsQ3.add("Confidentiality, Integrity, Availability");
            optionsQ3.add("Control, Inspection, Authentication");
            optionsQ3.add("Confidentiality, Internet, Authorization");
            optionsQ3.add("Control, Integrity, Access");
            MultipleChoiceQuestion q3 = new MultipleChoiceQuestion("M001Q03", "What does the CIA triad stand for?", optionsQ3, 0);
            questionRepository.addMultipleChoiceQuestion(q3, ciaTriad.getSubModuleID());

            // Question 4
            ArrayList<String> optionsQ4 = new ArrayList<>();
            optionsQ4.add("Confidentiality");
            optionsQ4.add("Integrity");
            optionsQ4.add("Availability");
            MultipleChoiceQuestion q4 = new MultipleChoiceQuestion("M001Q04", "Which part of the CIA triad prevents unauthorized users " + "from viewing sensitive information?", optionsQ4, 0);
            questionRepository.addMultipleChoiceQuestion(q4, ciaTriad.getSubModuleID());

            // Question 5
            ArrayList<String> optionsQ5 = new ArrayList<>();
            optionsQ5.add("Confidentiality");
            optionsQ5.add("Integrity");
            optionsQ5.add("Availability");
            MultipleChoiceQuestion q5 = new MultipleChoiceQuestion("M001Q05", "Which part of the CIA triad ensures data has not been " + "improperly modified?", optionsQ5, 1);
            questionRepository.addMultipleChoiceQuestion(q5, ciaTriad.getSubModuleID());
            
            // Question 6
            ArrayList<String> optionsQ6 = new ArrayList<>();
            optionsQ6.add("Confidentiality");
            optionsQ6.add("Integrity");
            optionsQ6.add("Availability");
            MultipleChoiceQuestion q6 = new MultipleChoiceQuestion("M001Q06", "Which part of the CIA triad ensures systems and data " + "are accessible when needed?", optionsQ6, 2);
            questionRepository.addMultipleChoiceQuestion(q6, ciaTriad.getSubModuleID());
            
            // SUBMODULE 3 - Threats and Vulnerabilities
            SubModule threatsAndVulnerabilities = new SubModule( "M001S03", "Threats, Vulnerabilities, and Risk",
                    "A threat is something capable of causing harm to a system "
                    + "or organization. A vulnerability is a weakness that can "
                    + "be exploited by a threat. Risk represents the potential "
                    + "impact or loss that may occur when a threat successfully "
                    + "exploits a vulnerability.",
                    2
            );
            moduleRepository.addSubModule(cyberSecurityFoundations.getModuleID(), threatsAndVulnerabilities);
            
            // Question 7
            ArrayList<String> optionsQ7 = new ArrayList<>();
            optionsQ7.add("Threat");
            optionsQ7.add("Vulnerability");
            optionsQ7.add("Control");
            optionsQ7.add("Asset");
            MultipleChoiceQuestion q7 = new MultipleChoiceQuestion("M001Q07", "What is a weakness that could be exploited by an attacker?", optionsQ7, 1);
            questionRepository.addMultipleChoiceQuestion(q7, threatsAndVulnerabilities.getSubModuleID());

            // Question 8
            ArrayList<String> optionsQ8 = new ArrayList<>();
            optionsQ8.add("A weakness in a system");
            optionsQ8.add("Something capable of causing harm");
            optionsQ8.add("A security safeguard");
            optionsQ8.add("A backup copy");
            MultipleChoiceQuestion q8 = new MultipleChoiceQuestion("M001Q08", "Which description best defines a threat?", optionsQ8, 1);
            questionRepository.addMultipleChoiceQuestion(q8, threatsAndVulnerabilities.getSubModuleID());
    
            // Question 9
            EntryQuestion q9 = new EntryQuestion("M001Q09", "What term describes a weakness that can be exploited?", "vulnerability");
            questionRepository.addEntryQuestion(q9, threatsAndVulnerabilities.getSubModuleID());
            
            
            // SUBMODULE 4 - Security Controls
            SubModule securityControls = new SubModule("M001S04", "Security Controls",
                    "Security controls are safeguards used to reduce risk and "
                    + "protect systems and information. Preventive controls attempt "
                    + "to stop incidents before they occur. Detective controls "
                    + "identify incidents that have occurred, while corrective "
                    + "controls help restore systems after an incident.",
                    3
            );
            moduleRepository.addSubModule(cyberSecurityFoundations.getModuleID(), securityControls);
            
            // Question 10
            ArrayList<String> optionsQ10 = new ArrayList<>();
            optionsQ10.add("Preventive");
            optionsQ10.add("Detective");
            optionsQ10.add("Corrective");
            MultipleChoiceQuestion q10 = new MultipleChoiceQuestion( "M001Q10", "A firewall that blocks unauthorized traffic is primarily " + "what type of security control?", optionsQ10, 0);
            questionRepository.addMultipleChoiceQuestion(q10, securityControls.getSubModuleID());

            // Question 11
            ArrayList<String> optionsQ11 = new ArrayList<>();
            optionsQ11.add("Preventive");
            optionsQ11.add("Detective");
            optionsQ11.add("Corrective");
            MultipleChoiceQuestion q11 = new MultipleChoiceQuestion("M001Q11", "A monitoring system that identifies suspicious activity "+ "is primarily what type of control?", optionsQ11, 1);
            questionRepository.addMultipleChoiceQuestion(q11, securityControls.getSubModuleID());
            
            // Question 12
            ArrayList<String> optionsQ12 = new ArrayList<>();
            optionsQ12.add("Preventive");
            optionsQ12.add("Detective");
            optionsQ12.add("Corrective");
            MultipleChoiceQuestion q12 = new MultipleChoiceQuestion("M001Q12", "Restoring a system from a backup after an incident is an " + "example of what type of control?", optionsQ12, 2);
            questionRepository.addMultipleChoiceQuestion(q12, securityControls.getSubModuleID());
        }
        
        ////////////// MODULE 2 ////////////
        // Check if module exists
        if (moduleRepository.getModuleById("M002") == null) {
            LearningModule exampleModule2 = new LearningModule("EXM2", "Networking Basics",
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
        // MOD3
        if (moduleRepository.getModuleById("M003") == null) {
            LearningModule exampleModule2 = new LearningModule("EXM2", "Linux Foundation",
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
        // MOD4
        if (moduleRepository.getModuleById("M004") == null) {
            LearningModule exampleModule2 = new LearningModule("EXM2", "Windows Administration",
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
        // MOD5
        if (moduleRepository.getModuleById("M005") == null) {
            LearningModule exampleModule2 = new LearningModule("EXM2", "Security Monitoring",
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


        ////////////// MODULE 2 //////////////

        if (moduleRepository.getModuleById("M002") == null) {

            LearningModule networkingBasics = new LearningModule(
                    "M002",
                    "Networking Basics",
                    "Introduces the fundamental concepts of computer networking, "
                    + "including how devices communicate, common types of networks, "
                    + "and the networking devices used to connect systems.",
                    50,
                    100
            );

            moduleRepository.addModule(networkingBasics);
            // SUBMODULE 1 - Network Fundamentals

            SubModule networkFundamentals = new SubModule(
                    "M002S01",
                    "Network Fundamentals",
                    "A computer network is a group of connected devices that "
                    + "communicate and share resources. Networks can vary in size "
                    + "and purpose. A Local Area Network (LAN) typically connects "
                    + "devices within a limited area such as a home, office, or "
                    + "school, while a Wide Area Network (WAN) connects networks "
                    + "across larger geographic areas.",
                    0
            );
            moduleRepository.addSubModule(networkingBasics.getModuleID(),networkFundamentals);
            
            // Question 1
            ArrayList<String> optionsM2Q1 = new ArrayList<>();
            optionsM2Q1.add("Local Area Network");
            optionsM2Q1.add("Long Access Network");
            optionsM2Q1.add("Logical Address Node");
            optionsM2Q1.add("Linked Application Network");
            MultipleChoiceQuestion m2q1 =new MultipleChoiceQuestion("M002Q01","What does LAN stand for?", optionsM2Q1, 0);
            questionRepository.addMultipleChoiceQuestion(m2q1, networkFundamentals.getSubModuleID());
            
            // SUBMODULE 2 - Network Devices
            SubModule networkDevices =new SubModule(
                    "M002S02",
                    "Network Devices",
                    "Network devices allow systems to communicate with each other. "
                    + "A switch connects devices within the same local network and "
                    + "forwards traffic to the appropriate device. A router connects "
                    + "different networks and forwards traffic between them. These "
                    + "devices form an important part of modern computer networks.",
                    1
            );
            moduleRepository.addSubModule(networkingBasics.getModuleID(), networkDevices);
    
            // Question 2
            ArrayList<String> optionsM2Q2 = new ArrayList<>();
            optionsM2Q2.add("Switch");
            optionsM2Q2.add("Router");
            optionsM2Q2.add("Keyboard");
            optionsM2Q2.add("Monitor");
            MultipleChoiceQuestion m2q2 = new MultipleChoiceQuestion("M002Q02", "Which network device is primarily used to connect different networks?", optionsM2Q2, 1);
            questionRepository.addMultipleChoiceQuestion(m2q2, networkDevices.getSubModuleID());
        }

        ////////////// MODULE 3 //////////////
        if (moduleRepository.getModuleById("M003") == null) {
            LearningModule linuxFoundation = new LearningModule("M003", "Linux Foundation", "Introduces Linux operating system fundamentals.", 150,100);
            moduleRepository.addModule(linuxFoundation);
        }
        ////////////// MODULE 4 //////////////
        if (moduleRepository.getModuleById("M004") == null) {
            LearningModule windowsAdministration =
            new LearningModule("M004", "Windows Administration", "Introduces Windows system administration concepts.", 200, 125);
            moduleRepository.addModule(windowsAdministration);
        }
        ////////////// MODULE 5 //////////////
        if (moduleRepository.getModuleById("M005") == null) {
            LearningModule securityMonitoring = new LearningModule("M005", "Security Monitoring", "Introduces security monitoring and incident detection.", 250, 150);
            moduleRepository.addModule(securityMonitoring);
        }
        ////////////// MODULE 6 //////////////
        if (moduleRepository.getModuleById("M006") == null) {
            LearningModule penetrationTesting = new LearningModule("M006", "Penetration Testing", "Introduces ethical penetration testing concepts.", 300, 175);
            moduleRepository.addModule(penetrationTesting);
        }
        ////////////// MODULE 7 //////////////
        if (moduleRepository.getModuleById("M007") == null) {
            LearningModule digitalForensics = new LearningModule("M007", "Digital Forensics and Incident Response","Introduces digital forensics and incident response concepts.", 350, 200);
            moduleRepository.addModule(digitalForensics);
        }
        ////////////// MODULE 8 //////////////
        if (moduleRepository.getModuleById("M008") == null) {
            LearningModule cryptography = new LearningModule("M008", "Cryptography", "Introduces encryption, hashing, and digital signatures.", 400, 225);
            moduleRepository.addModule(cryptography);
        }
        ////////////// MODULE 9 //////////////
        if (moduleRepository.getModuleById("M009") == null) {
            LearningModule cloudSecurity = new LearningModule("M009", "Cloud Security", "Introduces cloud computing security concepts, including " + "identity and access management, cloud infrastructure, " + "shared responsibility, and protecting cloud resources.", 450, 250);
            moduleRepository.addModule(cloudSecurity);
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
