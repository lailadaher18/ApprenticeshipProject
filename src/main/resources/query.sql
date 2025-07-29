DROP DATABASE IF EXISTS apprenticeship_project;
CREATE DATABASE apprenticeship_project;
USE apprenticeship_project;


CREATE TABLE `User` (
                       userId BIGINT PRIMARY KEY,
                       username VARCHAR(50),
                       firstName VARCHAR(50),
                       password VARCHAR(100),
                       userToken VARCHAR(100),
                       createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       latestStreak INT DEFAULT 0);

insert into apprenticeship_project.User values(1,'user1','name1','password123','',DEFAULT, DEFAULT);
insert into apprenticeship_project.User values(2,'user2','name2','password123','',DEFAULT, DEFAULT);
insert into apprenticeship_project.User values(3,'user3','name3','password123','',DEFAULT, DEFAULT);
insert into apprenticeship_project.User values(4,'user4','name4','password123','',DEFAULT, DEFAULT);
insert into apprenticeship_project.User values(5,'user5','name5','password123','',DEFAULT, DEFAULT);


CREATE TABLE Badge (    badgeId BIGINT AUTO_INCREMENT PRIMARY KEY,
                        badgeTitle VARCHAR(50),
                        badgeImage VARCHAR(255));

insert into apprenticeship_project.Badge values(DEFAULT,'First Login','/first_login.png');
insert into apprenticeship_project.Badge values(DEFAULT,'5 Reflections insert into a row)','/first_reflection.png');
insert into apprenticeship_project.Badge values(DEFAULT,'5 Positive Confidence Ratings in a row','/confidence_streak.png');
insert into apprenticeship_project.Badge values(DEFAULT,'First Emoji Added','/emoji.png');
insert into apprenticeship_project.Badge values(DEFAULT,'First Confidence Rating Added','/confidence_rating.png');

CREATE TABLE UserBadge (
                           rowId BIGINT AUTO_INCREMENT PRIMARY KEY,
                           userId BIGINT, FOREIGN KEY (userId) REFERENCES `User`(userId),
                           badgeId BIGINT, FOREIGN KEY (badgeId) REFERENCES `Badge`(badgeId) );

insert into apprenticeship_project.UserBadge values(DEFAULT,1,1);
insert into apprenticeship_project.UserBadge values(DEFAULT,2,2);
insert into apprenticeship_project.UserBadge values(DEFAULT,3,3);
insert into apprenticeship_project.UserBadge values(DEFAULT,1,4);
insert into apprenticeship_project.UserBadge values(DEFAULT,5,5);
insert into apprenticeship_project.UserBadge values(DEFAULT,2,1);
insert into apprenticeship_project.UserBadge values(DEFAULT,3,2);
insert into apprenticeship_project.UserBadge values(DEFAULT,4,3);
insert into apprenticeship_project.UserBadge values(DEFAULT,5,4);


CREATE TABLE Reflection (
                              reflectionId BIGINT AUTO_INCREMENT PRIMARY KEY,
                              userId BIGINT, FOREIGN KEY (userId) REFERENCES `User`(userId),
                              reflectionText VARCHAR(255),
                              confidenceRating INT,
                              emoji TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
                              createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP);

insert into apprenticeship_project.Reflection values(DEFAULT,1,'This is my first reflection',5,'😊',DEFAULT);
insert into apprenticeship_project.Reflection values(DEFAULT,2,'This is my second reflection',4,'😐',DEFAULT);
insert into apprenticeship_project.Reflection values(DEFAULT,3,'This is my third reflection',3,'😢',DEFAULT);
insert into apprenticeship_project.Reflection values(DEFAULT,4,'This is my fourth reflection',2,'😠',DEFAULT);
insert into apprenticeship_project.Reflection values(DEFAULT,5,'This is my fifth reflection',1,'😞',DEFAULT);


CREATE TABLE MotivationalMessage (
                                messageId BIGINT AUTO_INCREMENT PRIMARY KEY,
                                userId BIGINT, FOREIGN KEY (userId) REFERENCES `User`(userId),
                                messageText VARCHAR(255),
                                createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP);

insert into apprenticeship_project.MotivationalMessage values(DEFAULT,1,'Keep pushing forward!',DEFAULT);
insert into apprenticeship_project.MotivationalMessage values(DEFAULT,2,'Believe in yourself!',DEFAULT);
insert into apprenticeship_project.MotivationalMessage values(DEFAULT,3,'You are doing great!',DEFAULT);
insert into apprenticeship_project.MotivationalMessage values(DEFAULT,4,'Stay positive!',DEFAULT);
insert into apprenticeship_project.MotivationalMessage values(DEFAULT,5,'Every day is a new opportunity!',DEFAULT);

