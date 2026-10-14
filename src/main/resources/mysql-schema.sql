-- ========================================================
-- JobNest: Online Job Portal - Database Initialization Script
-- Database: MySQL 8.0+
-- ========================================================

-- 1. Create database if it doesn't already exist
CREATE DATABASE IF NOT EXISTS jobnest_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE jobnest_db;

-- 2. Drop existing tables in reverse dependency order (optional for clean re-run)
DROP TABLE IF EXISTS applications;
DROP TABLE IF EXISTS jobs;
DROP TABLE IF EXISTS users;

-- 3. Create Users Table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL, -- 'SEEKER' or 'RECRUITER'
    company VARCHAR(100),
    phone VARCHAR(15),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 4. Create Jobs Table
CREATE TABLE jobs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    company VARCHAR(150) NOT NULL,
    location VARCHAR(100) NOT NULL,
    job_type VARCHAR(30) NOT NULL, -- 'FULL_TIME', 'PART_TIME', 'INTERNSHIP'
    description TEXT,
    requirements TEXT,
    salary DOUBLE,
    salary_range VARCHAR(50),
    experience VARCHAR(100),
    skills VARCHAR(500),
    posted_by BIGINT NOT NULL,
    posted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_jobs_user FOREIGN KEY (posted_by) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 5. Create Applications Table
CREATE TABLE applications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    job_id BIGINT NOT NULL,
    applicant_name VARCHAR(100),
    applicant_email VARCHAR(150),
    cover_letter TEXT,
    resume_link VARCHAR(500),
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING', -- 'PENDING', 'REVIEWED', 'SHORTLISTED', 'REJECTED', 'ACCEPTED'
    job_title VARCHAR(200),
    job_company VARCHAR(150),
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_user_job UNIQUE (user_id, job_id),
    CONSTRAINT fk_app_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_app_job FOREIGN KEY (job_id) REFERENCES jobs(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ========================================================
-- Seed Initial Accounts and Sample Listings
-- Passwords below are BCrypt hashes of 'password123'
-- ========================================================

INSERT INTO users (full_name, email, password, role, company, phone) VALUES
('Priya Sharma', 'priya@techcorp.com', '$2a$10$3Z49zH9.m8f9B1d4b68eEuZ78o.gO0k.fT7aV8xG0wF9vP4o7v7q2', 'RECRUITER', 'TechCorp Solutions', '9876543210'),
('Rahul Verma', 'rahul@innovatetech.com', '$2a$10$3Z49zH9.m8f9B1d4b68eEuZ78o.gO0k.fT7aV8xG0wF9vP4o7v7q2', 'RECRUITER', 'InnovateTech Pvt Ltd', '9876543211'),
('Anita Desai', 'anita@globalsoft.com', '$2a$10$3Z49zH9.m8f9B1d4b68eEuZ78o.gO0k.fT7aV8xG0wF9vP4o7v7q2', 'RECRUITER', 'GlobalSoft India', '9876543212'),
('Vikram Kumar', 'vikram@email.com', '$2a$10$3Z49zH9.m8f9B1d4b68eEuZ78o.gO0k.fT7aV8xG0wF9vP4o7v7q2', 'SEEKER', NULL, '9876543213'),
('Sneha Reddy', 'sneha@email.com', '$2a$10$3Z49zH9.m8f9B1d4b68eEuZ78o.gO0k.fT7aV8xG0wF9vP4o7v7q2', 'SEEKER', NULL, '9876543214');

-- Sample Jobs Across Key Indian Cities & Remote
INSERT INTO jobs (title, company, location, job_type, description, requirements, salary, salary_range, experience, skills, posted_by) VALUES
('Senior Java Developer', 'TechCorp Solutions', 'Hyderabad', 'FULL_TIME',
 'Develop high-performance enterprise applications using Spring Boot microservices. Architect scalable RESTful services and ensure code hygiene.',
 '5+ years experience in Java, Spring Boot, Microservices, and MySQL. Solid understanding of Docker and AWS.',
 1200000, '₹10,00,000 - ₹14,00,000 per annum', '5-8 years', 'Java, Spring Boot, Microservices, MySQL, REST API', 1),

('Data Entry Operator', 'GlobalSoft India', 'Hyderabad', 'PART_TIME',
 'Manage digital documents, perform data verification, maintain database hygiene and generate weekly summary reports.',
 'High school diploma or undergraduate. Minimum 35 WPM typing accuracy. Familiarity with spreadsheets.',
 300000, '₹2,50,000 - ₹3,50,000 per annum', '0-2 years', 'MS Office, Data Entry, Excel, Typing', 3),

('Frontend Developer (React)', 'InnovateTech Pvt Ltd', 'Bangalore', 'FULL_TIME',
 'Craft responsive and accessible UI components with React.js. Collaborate closely with product designers and backend API engineers.',
 '3+ years working in modern React, JavaScript ES6+, HTML5, and CSS3. Experience with state management.',
 1000000, '₹8,00,000 - ₹12,00,000 per annum', '3-5 years', 'React.js, JavaScript, HTML, CSS, Redux, REST API', 2),

('Java Developer Intern', 'TechCorp Solutions', 'Bangalore', 'INTERNSHIP',
 'Kickstart your engineering career with mentorship from senior architects. Work on real REST endpoints and database modeling.',
 'Pursuing or graduated in Computer Science. Strong grasp of OOP, Core Java, and basic SQL queries.',
 200000, '₹15,00,000 - ₹20,000 per month', 'Fresher', 'Java, SQL, OOP, Git', 1),

('Customer Support Executive', 'GlobalSoft India', 'Chennai', 'FULL_TIME',
 'Deliver world-class assistance to global clients over email, tickets, and voice. Resolve customer issues with empathy and speed.',
 'Fluency in English. Excellent written and oral communication skills. Prior helpdesk experience is an advantage.',
 450000, '₹3,50,000 - ₹5,50,000 per annum', '1-3 years', 'Communication, Customer Service, CRM, Problem Solving', 3),

('Full Stack Java Developer', 'InnovateTech Pvt Ltd', 'Chennai', 'FULL_TIME',
 'Build complete web features end-to-end using Spring Boot for backend APIs and React for dynamic frontend rendering.',
 '4+ years full stack experience. Proficient in Java, Spring Boot, React, and MySQL database performance tuning.',
 1400000, '₹12,00,000 - ₹16,00,000 per annum', '4-7 years', 'Java, Spring Boot, React.js, MySQL, REST API', 2),

('Online Tutor - Computer Science', 'TechCorp Solutions', 'Pune', 'PART_TIME',
 'Conduct interactive online tutoring classes covering Java programming, algorithms, and web fundamentals for university students.',
 'B.Tech/M.Tech in CSE or related discipline. Passion for pedagogy and clear conceptual explanations.',
 400000, '₹500 - ₹800 per hour', '1-3 years', 'Programming, Data Structures, Algorithms, Teaching', 1),

('Java Developer', 'GlobalSoft India', 'Pune', 'FULL_TIME',
 'Design and maintain high-throughput backend services for financial transactions. Write unit tests and maintain documentation.',
 '3+ years experience with Core Java, Spring MVC / Boot, and Hibernate ORM. Strong SQL profiling skills.',
 900000, '₹7,00,000 - ₹11,00,000 per annum', '3-5 years', 'Java, Spring, Hibernate, MySQL, Design Patterns', 3),

('Frontend Developer', 'InnovateTech Pvt Ltd', 'Mumbai', 'FULL_TIME',
 'Create modern interactive dashboards with pixel-perfect design fidelity. Optimize client-side load time and accessibility.',
 '2+ years frontend development experience. Strong knowledge of CSS Grid/Flexbox, React, and REST consumption.',
 800000, '₹6,00,000 - ₹10,00,000 per annum', '2-4 years', 'JavaScript, React, HTML, CSS, Figma', 2),

('Data Entry Intern', 'TechCorp Solutions', 'Mumbai', 'INTERNSHIP',
 'Assist data operations team with catalog maintenance, data cleaning, and digital archiving.',
 'Enrolled in college degree. Basic spreadsheet skills and high attention to detail.',
 120000, '₹8,000 - ₹12,000 per month', 'Fresher', 'MS Excel, Google Sheets, Data Entry, Typing', 1),

('Java Developer', 'TechCorp Solutions', 'Visakhapatnam', 'FULL_TIME',
 'Develop cloud-native microservices for our growing technology hub in Vizag. Collaborate in an Agile Scrum sprint cycle.',
 '2+ years Java and Spring framework experience. Experience with relational databases and REST standards.',
 700000, '₹5,00,000 - ₹9,00,000 per annum', '2-4 years', 'Java, Spring Boot, REST API, MySQL', 1),

('Customer Support Executive', 'InnovateTech Pvt Ltd', 'Visakhapatnam', 'PART_TIME',
 'Assist domestic enterprise customers with setup, billing inquiries, and basic technical questions.',
 'Degree in any discipline. Courteous phone manner and basic typing skills.',
 250000, '₹2,00,000 - ₹3,00,000 per annum', '0-1 years', 'Communication, Customer Service, Computer Basics', 2),

('Online Tutor - Programming', 'GlobalSoft India', 'Remote', 'PART_TIME',
 'Teach Python, Java, and Web Development to remote students. Flexible hours and work-from-home freedom.',
 'Solid proficiency in at least two popular programming languages. Strong communication skills and reliable internet.',
 500000, '₹600 - ₹1,000 per hour', '1-5 years', 'Java, Python, JavaScript, Teaching', 3),

('Frontend Developer (Remote)', 'TechCorp Solutions', 'Remote', 'FULL_TIME',
 'Work 100% remotely building modern SaaS web applications. Implement responsive design systems and clean React components.',
 '3+ years React experience. Self-driven, collaborative, and proficient in asynchronous remote workflows.',
 1100000, '₹9,00,000 - ₹13,00,000 per annum', '3-6 years', 'React.js, TypeScript, CSS, Git', 1),

('Data Entry Operator (Remote)', 'InnovateTech Pvt Ltd', 'Remote', 'PART_TIME',
 'Flexible work-from-home data entry role. Enter records, update customer registries, and verify information.',
 'Reliable PC and internet connection. Accurate typing skill and proficiency in online document tools.',
 280000, '₹2,00,000 - ₹3,50,000 per annum', '0-2 years', 'Data Entry, MS Office, Google Workspace, Typing', 2);
