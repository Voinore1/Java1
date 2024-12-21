CREATE TABLE IF NOT EXISTS contacts (
    id SERIAL PRIMARY KEY,              -- Унікальний ідентифікатор контакту (автоматичне інкрементування)
    first_name VARCHAR(100) NOT NULL,   -- Ім'я (обов'язкове поле)
    last_name VARCHAR(100) NOT NULL,    -- Прізвище (обов'язкове поле)
    email VARCHAR(255) UNIQUE,          -- Email (унікальне значення)
    phone_number VARCHAR(15),           -- Номер телефону
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Час створення запису
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Час останнього оновлення
);
