-- Insert data into DepositLimitEntity
INSERT INTO DEPOSIT_LIMIT (id, amount)
VALUES (1, 1000.00);
-- Insert data into DiscoveredEntity
INSERT INTO DISCOVERED (id, AUTHORIZED_DISCOVERY_AMOUNT)
VALUES (1, 1000.00);
-- Insert data into CurrentAccountEntity
INSERT INTO BANK_ACCOUNT (account_number, balance, OVERDRAFT_LIMIT_ID, account_type)
VALUES (1, 0, 1, 'current');
-- Insert data into SavingsAccountEntity
INSERT INTO BANK_ACCOUNT (account_number, balance, DEPOSIT_LIMIT_ID, account_type)
VALUES (2, 0, 1, 'savings');


