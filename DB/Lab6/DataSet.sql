use lab6;

CREATE TABLE football_player (
last_name VARCHAR(30) NOT NULL,
first_name VARCHAR(30) NOT NULL, 
years_of_experience TINYINT DEFAULT 0,
club_name VARCHAR(50) DEFAULT 'Unknown',
FOREIGN KEY (club_name) REFERENCES football_club (club_name),
PRIMARY KEY(last_name, first_name));

CREATE TABLE football_club (
club_name VARCHAR(50) NOT NULL,
club_owner_name VARCHAR(50) DEFAULT 'Unknown',
coach_name VARCHAR(50) DEFAULT 'Unknown',
country VARCHAR(50) DEFAULT 'Unknown',
PRIMARY KEY(club_name));

CREATE TABLE ad_company (
company_name VARCHAR(50) NOT NULL,
budget DOUBLE DEFAULT 0.0,
industry VARCHAR(50) DEFAULT 'Unknown',
counry VARCHAR(50) DEFAULT 'Unknown',
PRIMARY KEY(company_name));

CREATE TABLE playerscompanies (
last_name VARCHAR(30) NOT NULL,
first_name VARCHAR(30) NOT NULL, 
company_name VARCHAR(50) NOT NULL,
FOREIGN KEY (last_name, first_name) REFERENCES football_player (last_name, first_name),
FOREIGN KEY (company_name) REFERENCES ad_company (company_name),
PRIMARY KEY(last_name, first_name, company_name));
