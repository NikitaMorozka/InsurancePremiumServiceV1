INSERT INTO classifiers (title, description)
VALUES('RISK_TYPE', 'Risk type classifier');

-- Получаем id классификатора 'RISK_TYPE'
SET @classifiers_id_risk_type = (SELECT id FROM classifiers WHERE title = 'RISK_TYPE');

-- Вставляем значения для классификатора 'RISK_TYPE'
INSERT INTO classifier_values(classifier_id, ic, description)
VALUES
    (@classifiers_id_risk_type, 'TRAVEL_MEDICAL', 'Medical expenses coverage.'),
    (@classifiers_id_risk_type, 'TRAVEL_CANCELLATION', 'Trip cancellation coverage.'),
    (@classifiers_id_risk_type, 'TRAVEL_LOSS_BAGGAGE', 'Lost baggage coverage.'),
    (@classifiers_id_risk_type, 'TRAVEL_THIRD_PARTY_LIABILITY', 'Third-party liability coverage.'),
    (@classifiers_id_risk_type, 'TRAVEL_EVACUATION', 'Emergency evacuation coverage.'),
    (@classifiers_id_risk_type, 'TRAVEL_SPORT_ACTIVITIES', 'Sports activities coverage.');

-- Вставляем классификатор 'COUNTRY'
INSERT INTO classifiers (title, description)
VALUES('COUNTRY', 'Country classifier');

-- Получаем id классификатора 'COUNTRY'
SET @classifiers_id_country = (SELECT id FROM classifiers WHERE title = 'COUNTRY');

-- Вставляем значения для классификатора 'COUNTRY'
INSERT INTO classifier_values(classifier_id, ic, description)
VALUES
    (@classifiers_id_country, 'LATVIA', 'Country Latvia.'),
    (@classifiers_id_country, 'SPAIN', 'Country Spain.'),
    (@classifiers_id_country, 'JAPAN', 'Country Japan.');

-- Вставляем ставки для стран
INSERT INTO country_default_day_rate (country_ic, default_day_rate)
VALUES
    ('LATVIA', 1.00),
    ('SPAIN', 2.50),
    ('JAPAN', 3.50);