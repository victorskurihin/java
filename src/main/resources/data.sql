INSERT INTO City (name ,state, country) SELECT
'San Francisco', 'CA', 'US'
WHERE NOT EXISTS (SELECT 1 FROM City WHERE name='San Francisco');

INSERT INTO City (name ,state, country) SELECT
'California', 'CA', 'US'
WHERE NOT EXISTS (SELECT 1 FROM City WHERE name='California');

INSERT INTO City (name ,state, country) SELECT
'Москва', 'МО', 'РФ'
WHERE NOT EXISTS (SELECT 1 FROM City WHERE name='Москва');

INSERT INTO City (name ,state, country) SELECT
'Воронеж', 'ВО', 'РФ'
WHERE NOT EXISTS (SELECT 1 FROM City WHERE name='Воронеж');
