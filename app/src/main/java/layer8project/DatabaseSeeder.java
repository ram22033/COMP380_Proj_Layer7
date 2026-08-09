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
            SubModule cybersecurityIntroduction = new SubModule("M001S01",
                "Introduction to Cybersecurity",
                "Cybersecurity is the practice of protecting computers, networks, applications, "
                + "and data from unauthorized access, damage, disruption, or theft.\n\n"
                + "Cybersecurity is important because organizations and individuals rely on "
                + "technology to store sensitive information such as passwords, financial records, "
                + "personal information, and business data. A successful cyberattack can result in "
                + "data loss, financial damage, service outages, or unauthorized access to systems.\n\n"
                + "Cybersecurity professionals work to reduce these risks by identifying threats "
                + "and vulnerabilities, monitoring systems for suspicious activity, implementing "
                + "security controls, and responding to security incidents.\n\n"
                + "A threat is something that has the potential to cause harm to a system or its data. "
                + "A vulnerability is a weakness that a threat may exploit. Security controls, such "
                + "as firewalls, access controls, encryption, and security policies, are used to help "
                + "protect systems and reduce risk.\n\n"
                + "The overall goal of cybersecurity is not simply to block access. It is to protect "
                + "information and systems while still allowing authorized users to access the "
                + "resources they need.",
                0
            );
            
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
                    "The CIA Triad is one of the foundational models of cybersecurity. "
                    + "CIA stands for Confidentiality, Integrity, and Availability. These three "
                    + "principles help security professionals determine what needs to be protected "
                    + "and what security controls should be implemented.\n\n"

                    + "Confidentiality means protecting information from unauthorized access or "
                    + "disclosure. Only people or systems with proper authorization should be able "
                    + "to view sensitive information. Passwords, access controls, and encryption "
                    + "are common methods used to maintain confidentiality.\n\n"

                    + "Integrity means protecting information from unauthorized modification or "
                    + "destruction. Data should remain accurate and trustworthy throughout its "
                    + "lifecycle. Hashing, digital signatures, permissions, and file integrity "
                    + "monitoring can help detect or prevent unauthorized changes.\n\n"

                    + "Availability means ensuring that systems, services, and information are "
                    + "accessible to authorized users when they are needed. Hardware failures, "
                    + "power outages, ransomware, and denial-of-service attacks can all affect "
                    + "availability. Backups, redundancy, fault tolerance, and disaster recovery "
                    + "planning can help maintain availability.\n\n"

                    + "Security professionals often have to balance all three parts of the CIA "
                    + "Triad. A system that protects confidentiality but is never available would "
                    + "not be useful. Likewise, a highly available system that exposes confidential "
                    + "information would not be secure.",
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
                    "Understanding threats, vulnerabilities, and risk is an important part of "
                    + "cybersecurity. Although these terms are related, they describe different "
                    + "parts of the security problems organizations face.\n\n"

                    + "A threat is anything that has the potential to cause harm to a system, "
                    + "network, or organization. Threats can include hackers, malware, malicious "
                    + "insiders, natural disasters, hardware failures, and human error. A threat "
                    + "does not necessarily mean that damage has already occurred; it represents "
                    + "something that could cause harm.\n\n"

                    + "A vulnerability is a weakness that could be exploited by a threat. Examples "
                    + "include outdated software, weak passwords, incorrect permissions, "
                    + "misconfigured systems, and unpatched security flaws. Organizations regularly "
                    + "perform vulnerability scans and security assessments to identify these "
                    + "weaknesses before attackers can exploit them.\n\n"

                    + "Risk represents the potential for loss or damage when a threat is able to "
                    + "exploit a vulnerability. Security teams evaluate both the likelihood of an "
                    + "event occurring and the impact it could have on the organization. Higher-risk "
                    + "problems are generally addressed before lower-risk problems.\n\n"

                    + "For example, imagine that a company operates an internet-facing server with "
                    + "an unpatched software vulnerability. The vulnerability is the weakness, an "
                    + "attacker capable of exploiting it represents a threat, and the possibility "
                    + "of the attacker compromising the server represents risk. Identifying and "
                    + "reducing these risks is a major responsibility of cybersecurity professionals.",
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
                    "Security controls are safeguards used to reduce security risks and protect "
                    + "an organization's systems, networks, people, and information. Organizations "
                    + "typically use multiple types of controls together rather than relying on a "
                    + "single security solution.\n\n"

                    + "Technical controls use technology to protect systems and information. "
                    + "Examples include firewalls, antivirus software, encryption, intrusion "
                    + "detection systems, multifactor authentication, and access control systems. "
                    + "These controls can help prevent attacks or detect suspicious activity.\n\n"

                    + "Administrative controls are policies, procedures, and practices that guide "
                    + "how people interact with technology and information. Examples include "
                    + "security policies, employee security awareness training, password policies, "
                    + "incident response procedures, and acceptable-use policies.\n\n"

                    + "Physical controls protect buildings, equipment, and other physical assets. "
                    + "Examples include locks, fences, security cameras, badge readers, security "
                    + "guards, and restricted server rooms. Physical security is important because "
                    + "an attacker who gains physical access to a device may be able to bypass "
                    + "technical protections.\n\n"

                    + "Security controls can also be classified by what they are designed to do. "
                    + "Preventive controls attempt to stop an incident before it occurs, detective "
                    + "controls help identify incidents that are occurring or have occurred, and "
                    + "corrective controls help restore systems or reduce damage after an incident.\n\n"

                    + "Effective cybersecurity uses layers of different security controls. This "
                    + "approach is often called defense in depth. If one control fails, additional "
                    + "controls can continue protecting the organization.",
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
                    "A computer network is a group of devices that communicate with one another "
                    + "and share resources. Networks allow computers, servers, phones, printers, "
                    + "and other devices to exchange information using established communication "
                    + "rules called protocols.\n\n"

                    + "Networks can be categorized by their size and purpose. A Local Area Network "
                    + "(LAN) connects devices within a limited area such as a home, office, or school. "
                    + "A Wide Area Network (WAN) connects networks across much larger geographic "
                    + "areas. The Internet is the largest example of a WAN because it connects "
                    + "networks throughout the world.\n\n"

                    + "Devices on a network use IP addresses to identify and communicate with one "
                    + "another. IPv4 addresses are 32 bits long and are commonly written as four "
                    + "decimal numbers separated by periods, such as 192.168.1.10. IPv6 uses "
                    + "128-bit addresses and was developed in part to provide a much larger number "
                    + "of available addresses.\n\n"

                    + "Network communication also relies on protocols. TCP provides reliable, "
                    + "connection-oriented communication and ensures that data arrives correctly "
                    + "and in the proper order. UDP is connectionless and does not provide the same "
                    + "delivery guarantees, but its lower overhead makes it useful when speed is "
                    + "more important than guaranteed delivery.\n\n"

                    + "Many network services are associated with port numbers. For example, HTTP "
                    + "commonly uses TCP port 80, HTTPS uses TCP port 443, and DNS commonly uses "
                    + "port 53. IP addresses identify network devices, while port numbers help "
                    + "identify the applications and services communicating on those devices.\n\n"

                    + "Understanding how devices, addresses, ports, and protocols work together is "
                    + "essential in cybersecurity because attacks and security controls frequently "
                    + "operate across networks. Security professionals often analyze network traffic "
                    + "to identify suspicious connections and determine how systems are communicating.",
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
                    "Computer networks rely on several types of devices to connect systems and "
                    + "control how network traffic moves. Understanding the purpose of these devices "
                    + "is important for both network administration and cybersecurity.\n\n"

                    + "A switch connects devices within a local network. It primarily operates at "
                    + "Layer 2 of the OSI model and forwards Ethernet frames using MAC addresses. "
                    + "Switches learn which devices are connected to their ports and use a MAC "
                    + "address table to determine where traffic should be forwarded.\n\n"

                    + "A router connects different networks and primarily operates at Layer 3 of "
                    + "the OSI model. Routers examine IP addresses and use routing information to "
                    + "determine where packets should be sent. A home router, for example, allows "
                    + "devices on a local network to communicate with networks on the Internet.\n\n"

                    + "A wireless access point allows wireless devices to connect to a network using "
                    + "Wi-Fi. Access points commonly connect back to the wired network through an "
                    + "Ethernet connection. Organizations may deploy multiple access points to "
                    + "provide wireless coverage throughout a building or campus.\n\n"

                    + "A firewall controls network traffic according to configured security rules. "
                    + "A firewall can permit or deny traffic based on information such as source "
                    + "and destination IP addresses, protocols, and port numbers. More advanced "
                    + "firewalls may also inspect application traffic and detect malicious activity.\n\n"

                    + "Other network and security devices include intrusion detection systems (IDS), "
                    + "which monitor traffic for suspicious activity, and intrusion prevention "
                    + "systems (IPS), which can detect and actively block malicious traffic. "
                    + "Understanding where these devices are located in a network helps security "
                    + "professionals understand how traffic flows and where security controls can "
                    + "be applied.\n\n"

                    + "In simple terms, switches connect devices within a network, routers connect "
                    + "different networks, access points provide wireless connectivity, and "
                    + "firewalls control which network traffic is allowed to pass.",
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
