-- 1. First, clear existing data (if needed)
TRUNCATE TABLE transaction CASCADE;
TRUNCATE TABLE mt940message CASCADE;
TRUNCATE TABLE bank_account CASCADE;
TRUNCATE TABLE bank CASCADE;
TRUNCATE TABLE transaction_category CASCADE;
TRUNCATE TABLE users_roles CASCADE;
TRUNCATE TABLE users CASCADE;
TRUNCATE TABLE role CASCADE;
TRUNCATE TABLE token CASCADE;

-- 2. Insert static reference data
-- Insert roles first
INSERT INTO role (name)
VALUES
('USER'),
('ADMIN'),
('TREASURY_MANAGER'),
('ACCOUNTANT'),
('AUDITOR');

-- Insert transaction categories
INSERT INTO transaction_category (id, category_type, category_name, category_description, supplementary_keywords, desc_keywords)
VALUES
(1, 'INFLOW', 'localCollections', 'Local collections from customers', 'local,collection,payment,customer', 'customer payment,local payment,collection'),
(2, 'INFLOW', 'exportSales', 'Export sales revenue', 'export,sale,revenue,international', 'export payment,international sale,overseas'),
(3, 'INFLOW', 'taxRefunds', 'Tax refunds received', 'tax,refund,government', 'tax refund,government payment,reimbursement'),
(4, 'OUTFLOW', 'localPurchases', 'Local purchases and expenses', 'local,purchase,expense,supplier', 'supplier payment,local expense,purchase'),
(5, 'OUTFLOW', 'imports', 'Import payments', 'import,payment,international', 'import payment,international purchase,overseas');

-- Insert banks
INSERT INTO bank (name, swift_code, branch, address, contact)
VALUES
('Bank of America', 'BOFAUS3N', 'New York', '123 Wall St, New York, NY', '+1-212-555-1234'),
('JPMorgan Chase', 'CHASUS33', 'New York', '270 Park Ave, New York, NY', '+1-212-555-5678'),
('Citibank', 'CITIUS33', 'New York', '388 Greenwich St, New York, NY', '+1-212-555-9012'),
('HSBC', 'HSBCUS33', 'New York', '452 5th Ave, New York, NY', '+1-212-555-3456'),
('Deutsche Bank', 'DEUTUS33', 'New York', '60 Wall St, New York, NY', '+1-212-555-7890');

-- Insert bank accounts
INSERT INTO bank_account (account_number, account_holder, currency, balance, bank_id)
VALUES
('1234567890', 'Vitira Corp', 'USD', 1000000.00, 1),
('2345678901', 'Vitira Corp', 'EUR', 500000.00, 2),
('3456789012', 'Vitira Corp', 'GBP', 250000.00, 3),
('4567890123', 'Vitira Corp', 'JPY', 10000000.00, 4),
('5678901234', 'Vitira Corp', 'CHF', 750000.00, 5);

-- Insert users
INSERT INTO users (email, password, customer_code, enabled, account_locked, created_date, last_modified_date, created_by, last_modified_by)
VALUES
('user1@vitira.com', '$2a$10$X7Q8Y9Z0A1B2C3D4E5F6G7H8I9J0K1L2M3N4O5P6Q7R8S9T0U1V2W3X4Y5Z', 'CUST001', true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
('user2@vitira.com', '$2a$10$X7Q8Y9Z0A1B2C3D4E5F6G7H8I9J0K1L2M3N4O5P6Q7R8S9T0U1V2W3X4Y5Z', 'CUST002', true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
('user3@vitira.com', '$2a$10$X7Q8Y9Z0A1B2C3D4E5F6G7H8I9J0K1L2M3N4O5P6Q7R8S9T0U1V2W3X4Y5Z', 'CUST003', true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
('user4@vitira.com', '$2a$10$X7Q8Y9Z0A1B2C3D4E5F6G7H8I9J0K1L2M3N4O5P6Q7R8S9T0U1V2W3X4Y5Z', 'CUST004', true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system'),
('user5@vitira.com', '$2a$10$X7Q8Y9Z0A1B2C3D4E5F6G7H8I9J0K1L2M3N4O5P6Q7R8S9T0U1V2W3X4Y5Z', 'CUST005', true, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system');

-- Insert user roles
INSERT INTO users_roles (user_id, role_id)
VALUES
(1, 1), (1, 2),
(2, 1), (2, 3),
(3, 1), (3, 4),
(4, 1), (4, 5),
(5, 1);

-- 3. Create the functions for generating dynamic data
CREATE OR REPLACE FUNCTION generate_mt940_messages(count INTEGER) RETURNS void AS $$
DECLARE
    i INTEGER;
    bank_account_id INTEGER;
    message_data TEXT;
BEGIN
    FOR bank_account_id IN SELECT id FROM bank_account LOOP
        FOR i IN 1..(count/5) LOOP
            message_data := 'MT940 message for account ' || bank_account_id ||
                          ' with reference ' || i || ' generated at ' || CURRENT_TIMESTAMP;

            INSERT INTO mt940message (
                transaction_reference_number,
                bank_account_id,
                message_type,
                message_data,
                received_at,
                created_at
            ) VALUES (
                'REF' || bank_account_id || '_' || i,
                bank_account_id,
                'MT940',
                message_data,
                CURRENT_TIMESTAMP - (random() * 30 || ' days')::interval,
                CURRENT_TIMESTAMP - (random() * 30 || ' days')::interval
            );
        END LOOP;
    END LOOP;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_categorized_transactions(count INTEGER) RETURNS void AS $$
DECLARE
    i INTEGER;
    category RECORD;
    keywords TEXT[];
    keyword TEXT;
    description TEXT;
    supplementary_info TEXT;
    amount NUMERIC;
    currency TEXT;
    debit_credit TEXT;
    mt940_message_id INTEGER;
BEGIN
    FOR category IN SELECT * FROM transaction_category LOOP
        FOR i IN 1..(count/5) LOOP
            -- Get a random MT940 message
            SELECT id INTO mt940_message_id
            FROM mt940message
            ORDER BY random()
            LIMIT 1;

            keywords := string_to_array(category.supplementary_keywords, ',');
            keyword := keywords[floor(random() * array_length(keywords, 1) + 1)];

            description := CASE
                WHEN category.category_type = 'INFLOW' THEN
                    CASE
                        WHEN category.category_name = 'localCollections' THEN
                            'Payment from customer ' || i || ' for local services'
                        WHEN category.category_name = 'exportSales' THEN
                            'Export payment from international client ' || i
                        WHEN category.category_name = 'taxRefunds' THEN
                            'Tax refund for period ' || i
                        ELSE 'Inflow transaction ' || i
                    END
                ELSE
                    CASE
                        WHEN category.category_name = 'localPurchases' THEN
                            'Payment to local supplier ' || i
                        WHEN category.category_name = 'imports' THEN
                            'Import payment to international supplier ' || i
                        ELSE 'Outflow transaction ' || i
                    END
            END;

            supplementary_info := keyword || ' transaction ' || i || ' ' ||
                                CASE WHEN random() > 0.5 THEN 'urgent' ELSE 'regular' END;

            amount := CASE
                WHEN category.category_name = 'localCollections' THEN (random() * 50000 + 1000)::numeric(10,2)
                WHEN category.category_name = 'exportSales' THEN (random() * 100000 + 5000)::numeric(10,2)
                WHEN category.category_name = 'taxRefunds' THEN (random() * 20000 + 1000)::numeric(10,2)
                WHEN category.category_name = 'localPurchases' THEN (random() * 30000 + 1000)::numeric(10,2)
                WHEN category.category_name = 'imports' THEN (random() * 80000 + 5000)::numeric(10,2)
                ELSE (random() * 10000 + 100)::numeric(10,2)
            END;

            currency := CASE
                WHEN category.category_name IN ('localCollections', 'localPurchases') THEN 'USD'
                WHEN category.category_name = 'exportSales' THEN
                    (ARRAY['EUR', 'GBP', 'JPY'])[floor(random() * 3 + 1)]
                WHEN category.category_name = 'imports' THEN
                    (ARRAY['EUR', 'GBP', 'JPY', 'CNY'])[floor(random() * 4 + 1)]
                ELSE 'USD'
            END;

            debit_credit := CASE
                WHEN category.category_type = 'INFLOW' THEN 'C'
                ELSE 'D'
            END;

            INSERT INTO transaction (
                mt940_message_id,
                value_date,
                entry_date,
                debit_credit_mark,
                funds_code,
                amount,
                currency,
                transaction_type,
                identification_code,
                reference_for_acc_owner,
                ref_of_acc_serving_institution,
                supplementary_info,
                description,
                category_id
            ) VALUES (
                mt940_message_id,
                CURRENT_TIMESTAMP - (random() * 30 || ' days')::interval,
                CURRENT_TIMESTAMP - (random() * 30 || ' days')::interval,
                debit_credit,
                'N',
                amount,
                currency,
                'NTRF',
                'CHG',
                'REF' || i || '_' || category.id,
                'BANK' || i || '_' || category.id,
                supplementary_info,
                description,
                category.id
            );
        END LOOP;
    END LOOP;
END;
$$ LANGUAGE plpgsql;

-- 4. Generate the data
-- First generate MT940 messages
SELECT generate_mt940_messages(100);

-- Then generate transactions
SELECT generate_categorized_transactions(1000);

-- 5. Insert tokens
INSERT INTO token (token, created_at, expires_at, validated_at, user_id)
SELECT
    'token_' || id,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP + INTERVAL '1 day',
    NULL,
    id
FROM users;