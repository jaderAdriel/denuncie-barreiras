CREATE TABLE BarrierScenario_Law (
    barrierScenario_fk INT NOT NULL,
    law_fk VARCHAR(100) NOT NULL,
    PRIMARY KEY (barrierScenario_fk, law_fk)
);