INSERT INTO classifiers (title, description)
VALUES('RISK_TYPE', 'Risk type classifier');

SET @classifiers_id_risk_type = (SELECT id FROM classifiers WHERE title = 'RISK_TYPE');

INSERT INTO classifier_values(classifier_id,ic,description)
VALUES(
       (@classifiers_id_risk_type,'TRAVEL_MEDICAL', 'Medical expenses coverage.' ),
       (@classifiers_id_risk_type,'TRAVEL_CANCELLATION', 'Trip cancellation coverage.'),
       (@classifiers_id_risk_type,'TRAVEL_LOSS_BAGGAGE', 'Lost baggage coverage.' ),
       (@classifiers_id_risk_type,'TRAVEL_THIRD_PARTY_LIABILITY', 'Third-party liability coverage.' ),
       (@classifiers_id_risk_type,'TRAVEL_EVACUATION', 'Emergency evacuation coverage.' ),
       (@classifiers_id_risk_type,'TRAVEL_SPORT_ACTIVITIES', 'Sports activities coverage.' )
    );

