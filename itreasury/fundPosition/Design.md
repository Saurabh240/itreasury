# Fund Position Design
Simple Low-Level Design (LLD) for a system that shows the Fund Position for a company. 
We will fetch the account balances from all the bank accounts associated with the company. 
We will make use of Spring Boot and design the necessary database entities.

## Database Entities
1. Company:
- id (Primary Key)
- name
- address
- contact

2. Bank:
- id (Primary Key)
- name
- branch
- address
- contact

3. BankAccount:
- id (Primary Key)
- account_number
- balance
- company_id (Foreign Key referencing Company)
- bank_id (Foreign Key referencing Bank)

## Entity Relationships
- A Company can have multiple BankAccounts.

- A Bank can have multiple BankAccounts.

## Spring Boot Application
1. Dependencies:
 - Spring Data JPA 
 - Spring Web 
 - Postgre Database
 - Lombok
2. Entity Classes:
- Company 
- Bank
- BankAccount
3. Repository Interfaces:
- CompanyRepository
- BankRepository
- BankAccountRepository
4. Service Classes:
- FundPositionService
5. Controller Classes:
- FundPositionController