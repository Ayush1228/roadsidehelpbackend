-- Insert Admin User
INSERT INTO user_account (id, full_name, email, password_hash, phone_number, user_type, is_active, is_verified, created_at, updated_at)
SELECT UUID(), 'Admin User ','admin@roadsidehelp.com',
       '$2a$10$sZh.I5/tObYOycaF47o1aO94SMDvMObh2zMyY9amTfcyQ07mpmMKS',
       '9999999999', 'ADMIN', TRUE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
    WHERE NOT EXISTS (SELECT 1 FROM user_account WHERE email = 'admin@roadsidehelp.com');

-- Insert Admin Role
INSERT INTO user_roles(user_id, role)
SELECT ua.id, 'ADMIN'
FROM user_account ua
         LEFT JOIN user_roles ur ON ur.user_id = ua.id AND ur.role = 'ADMIN'
WHERE ua.email = 'admin@roadsidehelp.com' AND ur.user_id IS NULL;
