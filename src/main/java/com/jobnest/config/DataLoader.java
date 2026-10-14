package com.jobnest.config;

import com.jobnest.model.Job;
import com.jobnest.model.User;
import com.jobnest.repository.JobRepository;
import com.jobnest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Loads sample data into the database on application startup.
 * Only inserts data if the database is empty.
 */
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) {
        // Only load sample data if database is empty
        if (userRepository.count() > 0 || jobRepository.count() > 0) {
            return;
        }

        System.out.println("Loading sample data...");

        // ===== Create sample users =====

        User recruiter1 = new User("Priya Sharma", "priya@techcorp.com",
                passwordEncoder.encode("password123"), "RECRUITER");
        recruiter1.setCompany("TechCorp Solutions");
        recruiter1.setPhone("9876543210");
        recruiter1 = userRepository.save(recruiter1);

        User recruiter2 = new User("Rahul Verma", "rahul@innovatetech.com",
                passwordEncoder.encode("password123"), "RECRUITER");
        recruiter2.setCompany("InnovateTech Pvt Ltd");
        recruiter2.setPhone("9876543211");
        recruiter2 = userRepository.save(recruiter2);

        User recruiter3 = new User("Anita Desai", "anita@globalsoft.com",
                passwordEncoder.encode("password123"), "RECRUITER");
        recruiter3.setCompany("GlobalSoft India");
        recruiter3.setPhone("9876543212");
        recruiter3 = userRepository.save(recruiter3);

        User seeker1 = new User("Vikram Kumar", "vikram@email.com",
                passwordEncoder.encode("password123"), "SEEKER");
        seeker1.setPhone("9876543213");
        userRepository.save(seeker1);

        User seeker2 = new User("Sneha Reddy", "sneha@email.com",
                passwordEncoder.encode("password123"), "SEEKER");
        seeker2.setPhone("9876543214");
        userRepository.save(seeker2);

        // ===== Create sample jobs =====

        // Hyderabad jobs
        jobRepository.save(new Job(
            "Senior Java Developer",
            "TechCorp Solutions",
            "Hyderabad",
            "FULL_TIME",
            "We are looking for an experienced Java Developer to join our team. You will be responsible for developing high-quality applications, designing and implementing robust back-end services, and collaborating with cross-functional teams to deliver scalable software solutions.",
            "5+ years of experience in Java development. Proficiency in Spring Boot, Microservices, and REST APIs. Strong knowledge of SQL databases and ORM frameworks. Experience with Git, Maven, and CI/CD pipelines.",
            1200000.0,
            "₹10,00,000 - ₹14,00,000 per annum",
            "5-8 years",
            "Java, Spring Boot, Microservices, MySQL, REST API, Docker",
            recruiter1.getId()
        ));

        jobRepository.save(new Job(
            "Data Entry Operator",
            "GlobalSoft India",
            "Hyderabad",
            "PART_TIME",
            "Seeking a detail-oriented Data Entry Operator for part-time work. Responsibilities include entering data into company systems, verifying accuracy of data, maintaining databases, and generating reports as requested.",
            "Good typing speed (minimum 35 WPM). Familiarity with MS Office and data management tools. High school diploma or equivalent. Attention to detail and accuracy.",
            300000.0,
            "₹2,50,000 - ₹3,50,000 per annum",
            "0-2 years",
            "MS Office, Data Entry, Excel, Typing",
            recruiter3.getId()
        ));

        // Bangalore jobs
        jobRepository.save(new Job(
            "Frontend Developer (React)",
            "InnovateTech Pvt Ltd",
            "Bangalore",
            "FULL_TIME",
            "Join our dynamic frontend team to build modern, responsive web applications using React.js. You will work closely with UI/UX designers and backend engineers to create seamless user experiences for our SaaS products.",
            "3+ years of experience with React.js and modern JavaScript (ES6+). Proficiency in HTML5, CSS3, and responsive design. Experience with state management libraries (Redux/Context API). Knowledge of RESTful APIs and Git version control.",
            1000000.0,
            "₹8,00,000 - ₹12,00,000 per annum",
            "3-5 years",
            "React.js, JavaScript, HTML, CSS, Redux, REST API, Git",
            recruiter2.getId()
        ));

        jobRepository.save(new Job(
            "Java Developer Intern",
            "TechCorp Solutions",
            "Bangalore",
            "INTERNSHIP",
            "Exciting internship opportunity for aspiring Java developers! Gain hands-on experience working on real-world enterprise projects. Learn industry best practices in software development, code reviews, and agile methodologies.",
            "Currently pursuing B.Tech/BCA/MCA in Computer Science or related field. Basic knowledge of Java, OOP concepts, and SQL. Eagerness to learn and grow in a professional environment. Good communication skills.",
            200000.0,
            "₹15,000 - ₹20,000 per month",
            "Fresher",
            "Java, SQL, OOP, Git",
            recruiter1.getId()
        ));

        // Chennai jobs
        jobRepository.save(new Job(
            "Customer Support Executive",
            "GlobalSoft India",
            "Chennai",
            "FULL_TIME",
            "We are hiring a Customer Support Executive to handle client queries, resolve issues, and ensure customer satisfaction. You will communicate with customers via phone, email, and chat to provide technical and non-technical support.",
            "Excellent communication skills in English and Hindi. Previous experience in customer support or call center is a plus. Ability to multitask and work under pressure. Problem-solving skills and patience.",
            450000.0,
            "₹3,50,000 - ₹5,50,000 per annum",
            "1-3 years",
            "Communication, Customer Service, CRM, Problem Solving",
            recruiter3.getId()
        ));

        jobRepository.save(new Job(
            "Full Stack Java Developer",
            "InnovateTech Pvt Ltd",
            "Chennai",
            "FULL_TIME",
            "Looking for a Full Stack Developer proficient in Java backend and React frontend. You will architect, develop, and maintain full-stack web applications, optimize performance, and mentor junior developers.",
            "4+ years in full-stack development with Java and React. Experience with Spring Boot, REST APIs, and database design. Frontend skills in React.js, CSS, and responsive design. Knowledge of deployment and cloud services.",
            1400000.0,
            "₹12,00,000 - ₹16,00,000 per annum",
            "4-7 years",
            "Java, Spring Boot, React.js, MySQL, REST API, AWS",
            recruiter2.getId()
        ));

        // Pune jobs
        jobRepository.save(new Job(
            "Online Tutor - Computer Science",
            "TechCorp Solutions",
            "Pune",
            "PART_TIME",
            "Teach Computer Science subjects online to undergraduate students. Topics include programming fundamentals, data structures, algorithms, and web development. Flexible timing and remote-friendly role.",
            "B.Tech/M.Tech in Computer Science or equivalent. Strong command over programming concepts and fundamentals. Teaching or tutoring experience is preferred. Reliable internet connection and good communication skills.",
            400000.0,
            "₹500 - ₹800 per hour",
            "1-3 years",
            "Programming, Data Structures, Algorithms, Teaching, Communication",
            recruiter1.getId()
        ));

        jobRepository.save(new Job(
            "Java Developer",
            "GlobalSoft India",
            "Pune",
            "FULL_TIME",
            "Seeking a passionate Java Developer to build robust enterprise applications. You will be part of a collaborative team working on cutting-edge financial technology solutions for banking clients.",
            "3+ years of Java development experience. Proficiency in Spring Framework, Hibernate, and MySQL. Understanding of design patterns and clean code principles. Strong debugging and troubleshooting skills.",
            900000.0,
            "₹7,00,000 - ₹11,00,000 per annum",
            "3-5 years",
            "Java, Spring, Hibernate, MySQL, Design Patterns",
            recruiter3.getId()
        ));

        // Mumbai jobs
        jobRepository.save(new Job(
            "Frontend Developer",
            "InnovateTech Pvt Ltd",
            "Mumbai",
            "FULL_TIME",
            "We are looking for a creative Frontend Developer to join our product team in Mumbai. Build beautiful, responsive, and interactive user interfaces using modern web technologies for our growing portfolio of web applications.",
            "2+ years of frontend development experience. Strong proficiency in JavaScript, React, HTML5, and CSS3. Experience with responsive and mobile-first design. Portfolio of completed web projects is a plus.",
            800000.0,
            "₹6,00,000 - ₹10,00,000 per annum",
            "2-4 years",
            "JavaScript, React, HTML, CSS, Bootstrap, Figma",
            recruiter2.getId()
        ));

        jobRepository.save(new Job(
            "Data Entry Intern",
            "TechCorp Solutions",
            "Mumbai",
            "INTERNSHIP",
            "Part-time data entry internship suitable for students and freshers. You will assist with data collection, validation, spreadsheet management, and documentation tasks. Great opportunity to gain office experience.",
            "Currently enrolled in any undergraduate program. Good typing speed and attention to detail. Familiarity with MS Excel and Google Sheets. Available for at least 4 hours per day.",
            120000.0,
            "₹8,000 - ₹12,000 per month",
            "Fresher",
            "MS Excel, Google Sheets, Data Entry, Typing",
            recruiter1.getId()
        ));

        // Visakhapatnam jobs
        jobRepository.save(new Job(
            "Java Developer",
            "TechCorp Solutions",
            "Visakhapatnam",
            "FULL_TIME",
            "Join our expanding development center in Visakhapatnam. We are building a talented engineering team to work on scalable Java applications for clients across healthcare and logistics industries.",
            "2+ years of experience in Java. Knowledge of Spring Boot and RESTful APIs. Familiarity with MySQL or PostgreSQL. Good analytical and problem-solving abilities.",
            700000.0,
            "₹5,00,000 - ₹9,00,000 per annum",
            "2-4 years",
            "Java, Spring Boot, REST API, MySQL",
            recruiter1.getId()
        ));

        jobRepository.save(new Job(
            "Customer Support Executive",
            "InnovateTech Pvt Ltd",
            "Visakhapatnam",
            "PART_TIME",
            "Part-time customer support role for our Vizag operations. Handle inbound calls, resolve product queries, and escalate complex issues. Ideal for graduates looking for flexible work options.",
            "Good verbal communication in English and Telugu. Any graduate can apply. Basic computer literacy. Willingness to work in shifts.",
            250000.0,
            "₹2,00,000 - ₹3,00,000 per annum",
            "0-1 years",
            "Communication, Customer Service, Computer Basics",
            recruiter2.getId()
        ));

        // Remote jobs
        jobRepository.save(new Job(
            "Online Tutor - Programming",
            "GlobalSoft India",
            "Remote",
            "PART_TIME",
            "Teach programming languages (Java, Python, JavaScript) to beginners and intermediate learners through live online sessions. Create learning materials, assignments, and assess student progress remotely.",
            "Proficiency in at least two programming languages. Previous teaching or mentoring experience. Strong internet connection and quiet workspace. Ability to explain complex concepts simply.",
            500000.0,
            "₹600 - ₹1,000 per hour",
            "1-5 years",
            "Java, Python, JavaScript, Teaching, Communication",
            recruiter3.getId()
        ));

        jobRepository.save(new Job(
            "Frontend Developer (Remote)",
            "TechCorp Solutions",
            "Remote",
            "FULL_TIME",
            "Work from anywhere! We are hiring a remote Frontend Developer to build and maintain modern web interfaces. Collaborate with a distributed team using agile methodologies and cutting-edge frontend technologies.",
            "3+ years of frontend experience. Expert in React.js, TypeScript, and CSS. Experience with remote collaboration tools (Jira, Slack, Git). Self-motivated with strong time management skills.",
            1100000.0,
            "₹9,00,000 - ₹13,00,000 per annum",
            "3-6 years",
            "React.js, TypeScript, CSS, Git, Agile",
            recruiter1.getId()
        ));

        jobRepository.save(new Job(
            "Data Entry Operator (Remote)",
            "InnovateTech Pvt Ltd",
            "Remote",
            "PART_TIME",
            "Remote data entry position with flexible hours. Enter product information, customer data, and inventory records into our cloud-based systems. Perfect for those seeking work-from-home opportunities.",
            "Accurate typing at 30+ WPM. Access to a personal computer with reliable internet. Basic knowledge of spreadsheets and cloud tools. Self-disciplined with ability to meet deadlines independently.",
            280000.0,
            "₹2,00,000 - ₹3,50,000 per annum",
            "0-2 years",
            "Data Entry, MS Office, Google Workspace, Typing",
            recruiter2.getId()
        ));

        System.out.println("Sample data loaded successfully!");
        System.out.println("  - 5 users created (3 recruiters, 2 seekers)");
        System.out.println("  - 15 job listings created");
        System.out.println("  Test accounts:");
        System.out.println("    Recruiter: priya@techcorp.com / password123");
        System.out.println("    Recruiter: rahul@innovatetech.com / password123");
        System.out.println("    Seeker:    vikram@email.com / password123");
        System.out.println("    Seeker:    sneha@email.com / password123");
    }
}
