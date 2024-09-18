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
    profitMargin NUMERIC default null,
    totalCost NUMERIC default null,
    status projectStatus default 'In Progress',
    clientId UUID references Clients(id)
);

CREATE TABLE Quotes (
    id UUID primary key not null,
    projectId UUID references Projects(id),
    estimatedCost NUMERIC default 0.0,
    issueDate DATE default current_date,
    validityDate DATE default null,
    isAccepted boolean not null default false
);

CREATE TABLE Components (
    id UUID primary key not null,
    name varchar(250),
    type componentType,
    vatRate NUMERIC default null,
    totalPrice NUMERIC default null,
    projectId UUID references Projects(id)
);

CREATE TABLE Materials(
    unitCost NUMERIC default null,
    quantity NUMERIC default null,
    transportCost NUMERIC default null,
    qualityCoefficient  NUMERIC default 1.0
) inherits (Components);

CREATE TABLE Workforce(
    hourlyRate NUMERIC default null,
    hoursWorked NUMERIC default null,
    workerProductivity NUMERIC default 1.0
) inherits (Components);