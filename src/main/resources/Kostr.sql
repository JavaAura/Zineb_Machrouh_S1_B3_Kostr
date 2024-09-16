CREATE DATABASE Kostr;

CREATE TYPE projectStatus AS ENUM ('In Progress', 'Done', 'Canceled');
CREATE TYPE componentType AS ENUM ('Materials', 'Workforce');

CREATE TABLE Clients (
    id UUID primary key not null,
    name varchar(50),
    address varchar(250),
    phoneNumber varchar(20),
    isProfessional boolean default false
);

CREATE TABLE Projects (
    id UUID primary key not null,
    name varchar(250),
    profitMargin NUMERIC,
    totalCost NUMERIC,
    status projectStatus,
    clientId UUID references Clients(id)
);

CREATE TABLE Quotes (
    id UUID primary key not null,
    projectId UUID references Projects(id),
    estimatedAmount NUMERIC,
    issueDate DATE,
    validityDate DATE,
    isAccepted boolean not null default false
);

CREATE TABLE Components (
    id UUID primary key not null,
    name varchar(250),
    type componentType,
    vatRate NUMERIC,
    projectId UUID references Projects(id)
);

CREATE TABLE Materials(
    unitCost NUMERIC,
    quantity NUMERIC,
    transportCost NUMERIC,
    qualityCoefficient  NUMERIC
) inherits (Components);

CREATE TABLE Workforce(
    hourlyRate NUMERIC,
    hoursWorked NUMERIC,
    workerProductivity NUMERIC
) inherits (Components);