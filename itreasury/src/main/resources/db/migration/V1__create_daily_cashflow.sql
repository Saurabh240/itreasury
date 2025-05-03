CREATE TABLE daily_cashflow_entry (
  id BIGSERIAL PRIMARY KEY,
  date DATE NOT NULL,
  category VARCHAR(100) NOT NULL,
  type VARCHAR(10) NOT NULL,       -- 'INCOME' or 'EXPENSE'
  amount NUMERIC(18,2) NOT NULL,
  description TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX IDX_daily_cashflow_date ON daily_cashflow_entry(date);


INSERT INTO public.daily_cashflow_entry
  (id, "date", category, "type", amount, description, created_at, updated_at)
VALUES
  (nextval('daily_cashflow_entry_id_seq'::regclass), '2025-04-18', 'Sales',           'INCOME', 1500.00, 'Online product sale',             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (nextval('daily_cashflow_entry_id_seq'::regclass), '2025-04-18', 'Rent',            'EXPENSE',  500.00, 'Office rent payment',             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (nextval('daily_cashflow_entry_id_seq'::regclass), '2025-04-19', 'Utilities',       'EXPENSE',  120.75, 'Electricity bill',                 CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (nextval('daily_cashflow_entry_id_seq'::regclass), '2025-04-19', 'Service Income',  'INCOME',  800.00, 'Consulting fee',                   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (nextval('daily_cashflow_entry_id_seq'::regclass), '2025-04-20', 'Salary',          'EXPENSE', 2000.00, 'Staff salaries',                   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (nextval('daily_cashflow_entry_id_seq'::regclass), '2025-04-21', 'Office Supplies', 'EXPENSE',   75.20, 'Printer cartridges',               CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (nextval('daily_cashflow_entry_id_seq'::regclass), '2025-04-22', 'Maintenance',     'EXPENSE',  250.00, 'Air‐con servicing',                CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (nextval('daily_cashflow_entry_id_seq'::regclass), '2025-04-23', 'Travel',          'EXPENSE',  180.50, 'Client meeting travel',            CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (nextval('daily_cashflow_entry_id_seq'::regclass), '2025-04-24', 'Insurance',       'EXPENSE',  320.00, 'Business liability insurance',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (nextval('daily_cashflow_entry_id_seq'::regclass), '2025-04-25', 'Miscellaneous',   'EXPENSE',   60.00, 'Office stationery purchase',       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP;

CREATE TABLE tenant_1.daily_cashflow_entry (
  id BIGSERIAL PRIMARY KEY,
  date DATE NOT NULL,
  category VARCHAR(100) NOT NULL,
  type VARCHAR(10) NOT NULL,       -- 'INCOME' or 'EXPENSE'
  amount NUMERIC(18,2) NOT NULL,
  description TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX IDX_tenant1_daily_cashflow_date ON tenant_1.daily_cashflow_entry(date);