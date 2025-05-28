DROP TABLE IF EXISTS BarrierScenario_Like;

CREATE TABLE IF NOT EXISTS BarrierScenario_Like (
    user_fk INT NOT NULL,
    scenario_fk INT NOT NULL,
    PRIMARY KEY (user_fk, scenario_fk)
);