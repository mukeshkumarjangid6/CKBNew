-- Create Database if not exists
CREATE DATABASE IF NOT EXISTS TaskManagement;

-- Switch to the 'TaskManagementDatabase' database
USE TaskManagement;

-- Drop existing tables if they exist
DROP TABLE IF EXISTS TaskCategory;
DROP TABLE IF EXISTS Attachment;
DROP TABLE IF EXISTS Comment;
DROP TABLE IF EXISTS Notification;
DROP TABLE IF EXISTS UserRoles;
DROP TABLE IF EXISTS UserRole;
DROP TABLE IF EXISTS Task;
DROP TABLE IF EXISTS Project;
DROP TABLE IF EXISTS Category;
DROP TABLE IF EXISTS User;

-- Create User table
CREATE TABLE IF NOT EXISTS User (
    UserID INT PRIMARY KEY,
    Username VARCHAR(255) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Email VARCHAR(255) NOT NULL,
    FullName VARCHAR(255) NOT NULL
);

-- Create Project table
CREATE TABLE IF NOT EXISTS Project (
    ProjectID INT PRIMARY KEY,
    ProjectName VARCHAR(255) NOT NULL,
    Description TEXT,
    StartDate DATE,
    EndDate DATE,
    UserID INT,
    FOREIGN KEY (UserID) REFERENCES User(UserID)
);

-- Create Task table
CREATE TABLE IF NOT EXISTS Task (
    TaskID INT PRIMARY KEY,
    TaskName VARCHAR(255) NOT NULL,
    Description TEXT,
    DueDate DATE,
    Priority VARCHAR(20),
    Status VARCHAR(20),
    ProjectID INT,
    UserID INT,
    FOREIGN KEY (ProjectID) REFERENCES Project(ProjectID),
    FOREIGN KEY (UserID) REFERENCES User(UserID)
);

-- Create Category table
CREATE TABLE IF NOT EXISTS Category (
    CategoryID INT PRIMARY KEY,
    CategoryName VARCHAR(255) NOT NULL
);

-- Create TaskCategory table (for many-to-many relationship)
CREATE TABLE IF NOT EXISTS TaskCategory (
    TaskID INT,
    CategoryID INT,
    FOREIGN KEY (TaskID) REFERENCES Task(TaskID),
    FOREIGN KEY (CategoryID) REFERENCES Category(CategoryID),
    PRIMARY KEY (TaskID, CategoryID)
);



-- Create Comment table
CREATE TABLE IF NOT EXISTS Comment (
    CommentID INT PRIMARY KEY,
    Text TEXT,
    CreatedAt DATETIME,
    TaskID INT,
    UserID INT,
    FOREIGN KEY (TaskID) REFERENCES Task(TaskID),
    FOREIGN KEY (UserID) REFERENCES User(UserID)
);

-- Create Attachment table
CREATE TABLE IF NOT EXISTS Attachment (
    AttachmentID INT PRIMARY KEY,
    FileName VARCHAR(255) NOT NULL,
    FilePath VARCHAR(255) NOT NULL,
    TaskID INT,
    FOREIGN KEY (TaskID) REFERENCES Task(TaskID)
);

-- Create Notification table
CREATE TABLE IF NOT EXISTS Notification (
    NotificationID INT PRIMARY KEY,
    Text TEXT,
    CreatedAt DATETIME,
    UserID INT,
    FOREIGN KEY (UserID) REFERENCES User(UserID)
);

-- Create UserRole table
CREATE TABLE IF NOT EXISTS UserRole (
    UserRoleID INT PRIMARY KEY,
    RoleName VARCHAR(255) NOT NULL
);

-- Create UserRoles table (for many-to-many relationship)
CREATE TABLE IF NOT EXISTS UserRoles (
    UserID INT,
    UserRoleID INT,
    FOREIGN KEY (UserID) REFERENCES User(UserID),
    FOREIGN KEY (UserRoleID) REFERENCES UserRole(UserRoleID),
    PRIMARY KEY(UserID,UserRoleID)
);


-- Insert data into User table
INSERT INTO User (UserID, Username, Password, Email, FullName) VALUES
(1, 'john_doe', 'password123', 'john.doe@email.com', 'John Doe'),
(2, 'jane_smith', 'pass456', 'jane.smith@email.com', 'Jane Smith'),
(3, 'alex_jones', 'secret789', 'alex.jones@email.com', 'Alex Jones'),
(4, 'emily_jackson', 'my_pass', 'emily.jackson@email.com', 'Emily Jackson'),
(5, 'michael_wilson', 'secure_password', 'michael.wilson@email.com', 'Michael Wilson'),
(6, 'sarah_miller', 'sarahpass', 'sarah.miller@email.com', 'Sarah Miller'),
(7, 'robert_clark', 'robert123', 'robert.clark@email.com', 'Robert Clark'),
(8, 'linda_turner', 'lindapass', 'linda.turner@email.com', 'Linda Turner'),
(9, 'peter_anderson', 'peterpass', 'peter.anderson@email.com', 'Peter Anderson'),
(10, 'natalie_brown', 'natalie456', 'natalie.brown@email.com', 'Natalie Brown'),
(11, 'kevin_martin', 'kevinpass', 'kevin.martin@email.com', 'Kevin Martin'),
(12, 'rachel_carter', 'rachel789', 'rachel.carter@email.com', 'Rachel Carter');
-- Add more records as needed...

-- Insert data into Project table
INSERT INTO Project (ProjectID, ProjectName, Description, StartDate, EndDate, UserID) VALUES
(1, 'Project One', 'Description for Project One', '2022-01-01', '2022-02-01', 1),
(2, 'Project Two', 'Description for Project Two', '2022-02-01', '2022-03-01', 2),
(3, 'Project Three', 'Description for Project Three', '2022-03-01', '2022-04-01', 3),
(4, 'New Product Launch', 'Launching our latest product', '2022-02-15', '2022-04-30', 4),
(5, 'Website Redesign', 'Redesigning company website', '2022-03-10', '2022-05-31', 5),
(6, 'Marketing Campaign', 'Launching a new marketing campaign', '2022-04-01', '2022-05-15', 6),
(7, 'Mobile App Development', 'Building a mobile app for iOS and Android', '2022-03-01', '2022-06-30', 7),
(8, 'Customer Support Portal', 'Developing a customer support portal', '2022-05-15', '2022-08-31', 8),
(9, 'Sales Training Program', 'Training program for the sales team', '2022-04-15', '2022-06-15', 9),
(10, 'Internal Documentation Revamp', 'Updating internal documentation and knowledge base', '2022-06-01', '2022-07-15', 10);
-- Add more records as needed...

-- Insert data into Task table
INSERT INTO Task (TaskID, TaskName, Description, DueDate, Priority, Status, ProjectID, UserID) VALUES
(1, 'Task One', 'Description for Task One', '2022-01-10', 'High', 'In Progress', 1, 1),
(2, 'Task Two', 'Description for Task Two', '2022-02-15', 'Medium', 'Pending', 1, 2),
(3, 'Task Three', 'Description for Task Three', '2022-03-20', 'Low', 'Completed', 2, 1),
(4, 'Define Product Features', 'Create a list of features for the new product', '2022-02-18', 'High', 'Pending', 4, 4),
(5, 'Design Landing Page', 'Create a new design for the landing page', '2022-03-15', 'Medium', 'In Progress', 5, 5),
(6, 'Marketing Campaign', 'Launching a new marketing campaign', '2022-04-01', 'High', 'In Progress', 6, 6),
(7, 'Mobile App Development', 'Building a mobile app for iOS and Android', '2022-03-01', 'High', 'In Progress', 7, 7),
(8, 'Customer Support Portal', 'Developing a customer support portal', '2022-05-15', 'Medium', 'In Progress', 8, 8),
(9, 'Sales Training Program', 'Training program for the sales team', '2022-04-15', 'Medium', 'Pending', 9, 9),
(10, 'Internal Documentation Revamp', 'Updating internal documentation and knowledge base', '2022-06-01', 'Low', 'Pending', 10, 10);
-- Add more records as needed...

-- Insert data into Category table
INSERT INTO Category (CategoryID, CategoryName) VALUES
(1, 'Development'),
(2, 'Design'),
(3, 'Marketing'),
(4, 'Testing'),
(5, 'Documentation'),
(6, 'Marketing'),
(7, 'Development'),
(8, 'Customer Support'),
(9, 'Training'),
(10, 'Documentation');
-- Add more records as needed...

-- Insert data into TaskCategory table
INSERT INTO TaskCategory (TaskID, CategoryID) VALUES
(1, 2),
(2, 2),
(3, 2),
(5, 4),
(5, 5),
(5, 6),
(7, 2),
(8, 8),
(4, 3),
(10, 10);
-- Add more records as needed...

-- Insert data into Comment table
INSERT INTO Comment (CommentID, Text, CreatedAt, TaskID, UserID) VALUES
(1, 'Comment for Task One', '2022-01-05 12:00:00', 1, 1),
(2, 'Comment for Task Two', '2022-02-10 15:30:00', 2, 2),
(3, 'Comment for Task Three', '2022-03-25 09:45:00', 3, 3),
(4, 'Progress update for Task Four', '2022-02-20 10:15:00', 4, 4),
(5, 'Discuss design changes for Task Five', '2022-03-18 14:00:00', 5, 5),
(6, 'Discussing campaign strategy details', '2022-04-02 14:30:00', 6, 6),
(7, 'Reviewing initial app design concepts', '2022-03-12 11:45:00', 7, 7),
(8, 'Addressing customer support system implementation issues', '2022-06-05 09:30:00', 8, 8),
(9, 'Feedback on sales pitch training session', '2022-05-05 15:00:00', 9, 9),
(10, 'Collaborating on knowledge base updates', '2022-07-02 10:30:00', 10, 10);
-- Add more records as needed..

-- Insert data into Attachment table
INSERT INTO Attachment (AttachmentID, FileName, FilePath, TaskID) VALUES
(1, 'File1.txt', '/path/to/file1', 1),
(2, 'File2.txt', '/path/to/file2', 2),
(3, 'File3.txt', '/path/to/file3', 3),
(4, 'FeatureList.doc', '/path/to/FeatureList.doc', 4),
(5, 'DesignMockup.png', '/path/to/DesignMockup.png', 5),
(6, 'CampaignDetails.pdf', '/path/to/CampaignDetails.pdf', 6),
(7, 'AppDesignConcepts.png', '/path/to/AppDesignConcepts.png', 7),
(8, 'SupportSystemDocs.doc', '/path/to/SupportSystemDocs.doc', 8),
(9, 'SalesTrainingFeedback.docx', '/path/to/SalesTrainingFeedback.docx', 9),
(10, 'KnowledgeBaseUpdates.xlsx', '/path/to/KnowledgeBaseUpdates.xlsx', 10);
-- Add more records as needed...

-- Insert data into Notification table
INSERT INTO Notification (NotificationID, Text, CreatedAt, UserID) VALUES
(1, 'Notification for User One', '2022-01-15 10:00:00', 1),
(2, 'Notification for User Two', '2022-02-20 14:45:00', 2),
(3, 'Notification for User Three', '2022-03-30 08:30:00', 3),
(4, 'Product Launch Meeting Tomorrow', '2022-02-14 16:30:00', 4),
(5, 'Reminder: Design Review Meeting', '2022-03-12 09:00:00', 5),
(6, 'Reminder: Marketing Strategy Meeting Tomorrow', '2022-04-04 16:00:00', 6),
(7, 'Deadline Approaching: App UI/UX Design', '2022-03-20 12:00:00', 7),
(8, 'Notification for Support System Implementation', '2022-06-10 08:00:00', 8),
(9, 'Upcoming Sales Training Session', '2022-04-28 10:30:00', 9),
(10, 'Reminder: Knowledge Base Review Meeting', '2022-07-05 14:00:00', 10);
-- Add more records as needed...

-- Insert data into UserRole table
INSERT INTO UserRole (UserRoleID, RoleName) VALUES
(1, 'Admin'),
(2, 'User'),
(3, 'Manager'),
(4, 'Product Manager'),
(5, 'Designer'),
(6, 'Marketing Specialist'),
(7, 'Developer'),
(8, 'Customer Support Representative'),
(9, 'Sales Trainer'),
(10, 'Documentation Specialist');
-- Add more records as needed...

-- Insert data into UserRoles table
INSERT INTO UserRoles (UserID, UserRoleID) VALUES
(1, 2),
(2, 2),
(3, 2),
(5, 4),
(5, 5),
(5, 6),
(7, 2),
(8, 8),
(4, 3),
(10, 10);
-- Add more records as needed...
