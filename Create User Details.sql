use eazybank;

INSERT INTO `accounts` (`customer_id`, `account_number`, `account_type`, `branch_address`, `create_dt`)
 VALUES (2, 1234672478, 'Savings', '345 Main Street, New York', CURDATE());
 
 INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
`closing_balance`, `create_dt`)  VALUES (UUID(), 1234672478, 2, DATE_SUB(CURDATE(), INTERVAL 7 DAY), 'Coffee Shop', 'Withdrawal', 30, 34500, DATE_SUB(CURDATE(), INTERVAL 7 DAY));

INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
`closing_balance`, `create_dt`)  VALUES (UUID(), 1234672478, 2, DATE_SUB(CURDATE(), INTERVAL 6 DAY), 'Uber', 'Withdrawal', 100, 34400, DATE_SUB(CURDATE(), INTERVAL 6 DAY));

INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
`closing_balance`, `create_dt`)  VALUES (UUID(), 1234672478, 2, DATE_SUB(CURDATE(), INTERVAL 5 DAY), 'Self Deposit', 'Deposit', 500, 34900, DATE_SUB(CURDATE(), INTERVAL 5 DAY));

INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
`closing_balance`, `create_dt`)  VALUES (UUID(), 1234672478, 2, DATE_SUB(CURDATE(), INTERVAL 4 DAY), 'Ebay', 'Withdrawal', 600, 34300, DATE_SUB(CURDATE(), INTERVAL 4 DAY));

INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
`closing_balance`, `create_dt`)  VALUES (UUID(), 1234672478, 2, DATE_SUB(CURDATE(), INTERVAL 2 DAY), 'OnlineTransfer', 'Deposit', 700, 35000, DATE_SUB(CURDATE(), INTERVAL 2 DAY));

INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
`closing_balance`, `create_dt`)  VALUES (UUID(), 1234672478, 2, DATE_SUB(CURDATE(), INTERVAL 1 DAY), 'Amazon.com', 'Withdrawal', 100, 34900, DATE_SUB(CURDATE(), INTERVAL 1 DAY));

INSERT INTO `loans` ( `customer_id`, `start_dt`, `loan_type`, `total_loan`, `amount_paid`, `outstanding_amount`, `create_dt`)
 VALUES ( 2, '2020-10-13', 'Home', 200000, 50000, 150000, '2020-10-13');

INSERT INTO `loans` ( `customer_id`, `start_dt`, `loan_type`, `total_loan`, `amount_paid`, `outstanding_amount`, `create_dt`)
 VALUES ( 2, '2020-06-06', 'Vehicle', 40000, 10000, 30000, '2020-06-06');

INSERT INTO `loans` ( `customer_id`, `start_dt`, `loan_type`, `total_loan`, `amount_paid`, `outstanding_amount`, `create_dt`)
 VALUES ( 2, '2018-02-14', 'Home', 50000, 10000, 40000, '2018-02-14');

INSERT INTO `loans` ( `customer_id`, `start_dt`, `loan_type`, `total_loan`, `amount_paid`, `outstanding_amount`, `create_dt`)
 VALUES ( 2, '2018-02-14', 'Personal', 10000, 3500, 6500, '2018-02-14');
 
INSERT INTO `cards` (`card_number`, `customer_id`, `card_type`, `total_limit`, `amount_used`, `available_amount`, `create_dt`)
VALUES ('5678XXXX5678', 2, 'Credit', 10000, 500, 9500, CURDATE());

INSERT INTO `cards` (`card_number`, `customer_id`, `card_type`, `total_limit`, `amount_used`, `available_amount`, `create_dt`)
VALUES ('4566XXXX1255', 2, 'Credit', 7500, 600, 6900, CURDATE());

INSERT INTO `cards` (`card_number`, `customer_id`, `card_type`, `total_limit`, `amount_used`, `available_amount`, `create_dt`)
VALUES ('1351XXXX4621', 2, 'Credit', 20000, 4000, 16000, CURDATE());

INSERT INTO `cards` (`card_number`, `customer_id`, `card_type`, `total_limit`, `amount_used`, `available_amount`, `create_dt`)
VALUES ('9673XXXX6134', 2, 'Credit', 100000, 40000, 60000, CURDATE());

INSERT INTO `authorities` (`customer_id`, `name`)
VALUES (2, 'VIEWACCOUNT');

INSERT INTO `authorities` (`customer_id`, `name`)
VALUES (2, 'VIEWCARDS');

INSERT INTO `authorities` (`customer_id`, `name`)
VALUES (2, 'VIEWLOANS');

INSERT INTO `authorities` (`customer_id`, `name`)
VALUES (2, 'VIEWBALANCE');