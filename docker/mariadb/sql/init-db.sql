SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DROP DATABASE IF EXISTS `empresa`;
CREATE DATABASE `empresa`;
USE `empresa`;

DROP TABLE IF EXISTS `departamento`;
CREATE TABLE `departamento` (
    `uuid_departamento` varchar(36) NOT NULL,
    `nombre` varchar(25) NOT NULL,
    `jefe_departamento` varchar(36) NOT NULL,
    `presupuesto` double NOT NULL,
    `presupuesto_anual` double NOT NULL,
    PRIMARY KEY(`uuid_departamento`),
    KEY `jefe_departamento_FK` (`jefe_departamento`),
    CONSTRAINT `jefe_departamento_FK` FOREIGN KEY (`jefe_departamento`)
                                    REFERENCES `programador` (`uuid_programador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla de departamento';

INSERT INTO `departamento` (`uuid_departamento`, `nombre`, `jefe_departamento`, `presupuesto`,
                            `presupuesto_anual`) VALUES
('86e0f53e-04d5-49fb-8ffc-34c6e2c7e455', 'Desarrollo de sistema', '1c311b93-b9f7-4c91-94bc-6938d3bc3499',
 50000.00, 90000.00),
('08174950-6519-47ef-9ceb-eaff8cb66c8b', 'Departamento de redes', '74143cdc-f1f3-4c63-ba08-3eb50d7a516f',
 70000.00, 120000.00),
('b895348f-1852-4d61-920a-5cdbb4b6f7b7', 'Departamento de captación', '46ca0fd4-6daa-421a-b3f8-ce2874dbc715',
 25000.00, 500000.00);


DROP TABLE IF EXISTS `historico_jefes`;
CREATE TABLE `historico_jefes` (
    `uuid_historico` varchar(36) NOT NULL,
    `uuid_programador` varchar(36) NOT NULL,
    `uuid_departamento` varchar(36) NOT NULL,
    `fecha_alta` date NOT NULL,
    `fecha_baja` date,
    PRIMARY KEY(`uuid_historico`),
    KEY `programador_jefe_FK` (`uuid_programador`),
    CONSTRAINT `programador_jefe_FK` FOREIGN KEY (`uuid_programador`)
        REFERENCES `programador` (`uuid_programador`),
    KEY `uuid_departamento_perteneciente_FK` (`uuid_departamento`),
    CONSTRAINT `uuid_departamento_perteneciente_FK` FOREIGN KEY (`uuid_departamento`)
        REFERENCES `departamento` (`uuid_departamento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla de histórico de jefes';


INSERT INTO `historico_jefes` (`uuid_historico`, `uuid_programador`, `uuid_departamento`,
                               `fecha_alta`, `fecha_baja`) VALUES
  ('c9b68bea-5fee-4681-89cf-cd0e20a8e588', '1c311b93-b9f7-4c91-94bc-6938d3bc3499',
   '86e0f53e-04d5-49fb-8ffc-34c6e2c7e455', '2021-10-11', NULL),
  ('58dc30f3-8d8e-46f1-90c7-26c06ad21f38', '74143cdc-f1f3-4c63-ba08-3eb50d7a516f',
   '6ca0fd4-6daa-421a-b3f8-ce2874dbc7152', '2021-10-14', NULL),
  ('66cb775d-badc-4348-a436-d4f9f23aff0a', '46ca0fd4-6daa-421a-b3f8-ce2874dbc715',
   '86e0f53e-04d5-49fb-8ffc-34c6e2c7e455', '2021-10-13', NULL);

DROP TABLE IF EXISTS `programador`;
CREATE TABLE `programador` (
    `uuid_programador` varchar(36) NOT NULL,
    `nombre` varchar (25) NOT NULL,
    `fecha_alta` date NOT NULL,
    `departamento` varchar(36) NOT NULL,
    `tecnologias_dominadas` text NOT NULL,
    `salario` double NOT NULL,
    `es_jefe_departamento` bool NOT NULL,
    `es_jefe_proyecto` bool NOT NULL,
    `es_jefe_activo` bool NOT NULL,
    `password` varchar(25) NOT NULL,
    PRIMARY KEY(`uuid_programador`),
    KEY `departamento_FK` (`departamento`),
    CONSTRAINT `departamento_FK` FOREIGN KEY (`departamento`) REFERENCES departamento (`uuid_departamento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla de programador';


INSERT INTO `programador`(`uuid_programador`, `nombre`, `fecha_alta`, `departamento`,`tecnologias_dominadas`,
                          `salario`, `es_jefe_departamento`, `es_jefe_proyecto`, `es_jefe_activo`, `password`) VALUES
       ('1c311b93-b9f7-4c91-94bc-6938d3bc3499', 'Adrián', '2021-10-11', '86e0f53e-04d5-49fb-8ffc-34c6e2c7e455',
        'java, python', 7000.00, 1, 0, 1, '40bd001563085fc35165329ea1ff5c5ecbdbbeef'),
       ('74143cdc-f1f3-4c63-ba08-3eb50d7a516f', 'Marco', '2021-10-14', 'd6f4c3ee-cbe9-4522-b6f2-6dc2073b625a',
        'java, python', 2000.00, 1, 0, 1, '40bd001563085fc35165329ea1ff5c5ecbdbbeef'),
       ('46ca0fd4-6daa-421a-b3f8-ce2874dbc715', 'Federico', '2021-10-13', '86e0f53e-04d5-49fb-8ffc-34c6e2c7e455',
        'java, python', 1000.00, 1, 0, 1, '40bd001563085fc35165329ea1ff5c5ecbdbbeef'),
       ('ab05d17f-7e4b-4d6b-872d-ea04b9111041', 'Mario', '2021-10-13', '08174950-6519-47ef-9ceb-eaff8cb66c8b',
        'java, kotlin', 2000.00, 0, 1, 1, '40bd001563085fc35165329ea1ff5c5ecbdbbeef'),
       ('5b33ef30-979b-4bb4-9baa-84e92cd291b6', 'Tamara', '2021-10-08', '08174950-6519-47ef-9ceb-eaff8cb66c8b',
        'java, C#', 1000.00, 0, 1, 1, '40bd001563085fc35165329ea1ff5c5ecbdbbeef'),
       ('c0b705bc-8f83-42e6-aae9-b115f6035eff', 'Maria', '2021-10-12', '08174950-6519-47ef-9ceb-eaff8cb66c8b',
        'java, javaScript', 2000.00, 0, 1, 1, '40bd001563085fc35165329ea1ff5c5ecbdbbeef'),
       ('5ee6976a-3f01-411b-aeaf-6dfc7e4785b5', 'Samira', '2021-10-01', 'b895348f-1852-4d61-920a-5cdbb4b6f7b7',
        'java', 3000.00, 0, 0, 0, '40bd001563085fc35165329ea1ff5c5ecbdbbeef'),
       ('e8bedfcb-c14e-4110-8ade-67e503038601', 'Elena', '2021-09-12', 'b895348f-1852-4d61-920a-5cdbb4b6f7b7',
        'html, python', 500.00, 0, 0, 0, '40bd001563085fc35165329ea1ff5c5ecbdbbeef'),
       ('0b3e2cac-6849-4e2a-98f2-f610b182f27d', 'Fátima', '2021-30-12', 'b895348f-1852-4d61-920a-5cdbb4b6f7b7',
        'java, python', 2000.00, 0, 0, 0, '40bd001563085fc35165329ea1ff5c5ecbdbbeef');

DROP TABLE IF EXISTS `ficha`;
CREATE TABLE `ficha` (
     `uuid_ficha` varchar(36) NOT NULL,
     `uuid_programador` varchar(36) NOT NULL,
     `uuid_proyecto` varchar(36) NOT NULL,
     PRIMARY KEY(`uuid_ficha`),
     KEY `uuid_programador_FK` (`uuid_programador`),
     CONSTRAINT `uuid_programador_FK` FOREIGN KEY (`uuid_programador`) REFERENCES `programador` (`uuid_programador`),
     KEY `uuid_proyecto_FK` (`uuid_proyecto`),
     CONSTRAINT `uuid_proyecto_FK` FOREIGN KEY (`uuid_proyecto`) REFERENCES `proyecto` (`uuid_proyecto`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla de ficha';

INSERT INTO `ficha` (`uuid_ficha`, `uuid_programador`, `uuid_proyecto`) VALUES
    ('06362a0b-6927-4190-9c85-df9e8ed2f933', '1c311b93-b9f7-4c91-94bc-6938d3bc3499',
     '4e253552-73a3-4a74-bf1d-6c45db815a91'),
    ('4fc8ca5d-5390-437f-9fe8-f1196adb3781', '74143cdc-f1f3-4c63-ba08-3eb50d7a516f',
     'f74cf06e-8625-4551-8108-f75482cd16fa'),
    ('155b0535-778f-4a64-8dfb-7a7fb09dde9c', '46ca0fd4-6daa-421a-b3f8-ce2874dbc715',
     '03b8bf8d-03f5-4d61-bdcb-7837e65fafe8'),
    ('10a363a4-79a2-4e97-bb10-010e2ca9af13', 'ab05d17f-7e4b-4d6b-872d-ea04b9111041',
     '97a53b2b-dd63-4fc4-9126-4ea3947b940a'),
    ('bae215e0-e089-4039-878d-d1523c50dc23', '5b33ef30-979b-4bb4-9baa-84e92cd291b6',
     '359d985f-9516-4398-b742-4feb526461ca'),
    ('17d80c32-0afb-4c3a-aa26-4c505ca3fc0d', 'c0b705bc-8f83-42e6-aae9-b115f6035eff',
     '65694948-8531-4eda-a06a-8adad84ef256'),
    ('5cd85c93-8cb4-41a6-ae9c-39f720af66f0', '5ee6976a-3f01-411b-aeaf-6dfc7e4785b5',
     '03b8bf8d-03f5-4d61-bdcb-7837e65fafe8'),
    ('2bfda4d3-76a0-419e-8d8c-b5ab4e3d6cc2', 'e8bedfcb-c14e-4110-8ade-67e503038601',
     '65694948-8531-4eda-a06a-8adad84ef256'),
    ('ad7ba017-5fd9-4b1c-8df6-737b0eb09b1e', '0b3e2cac-6849-4e2a-98f2-f610b182f27d',
     '03b8bf8d-03f5-4d61-bdcb-7837e65fafe8');

DROP TABLE IF EXISTS `proyecto`;
CREATE TABLE `proyecto` (
    `uuid_proyecto` varchar(36) NOT NULL,
    `jefe_proyecto` varchar(36) NOT NULL,
    `nombre` varchar(25) NOT NULL,
    `presupuesto` double NOT NULL,
    `fecha_inicio` date NOT NULL,
    `fecha_fin` date NOT NULL,
    `tecnologias_usadas` text NOT NULL,
    `repositorio` varchar(36) NOT NULL,
    `es_acabado` bool NOT NULL,
    `uuid_departamento` varchar(36) NOT NULL,
    PRIMARY KEY(`uuid_proyecto`),
    KEY `uuid_departamento_FK` (`uuid_departamento`),
    CONSTRAINT `uuid_departamento_FK` FOREIGN KEY (`uuid_departamento`) REFERENCES `departamento` (`uuid_departamento`),
    KEY `jefe_proyecto_FK` (`jefe_proyecto`),
    CONSTRAINT `jefe_proyecto_FK` FOREIGN KEY (`jefe_proyecto`) REFERENCES `programador` (`uuid_programador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla de proyecto';

INSERT INTO `proyecto` (`uuid_proyecto`, `jefe_proyecto`, `nombre`, `presupuesto`, `fecha_inicio`, `fecha_fin`,
                        `tecnologias_usadas`, `repositorio`, `es_acabado`, `uuid_departamento`) VALUES
    ('4e253552-73a3-4a74-bf1d-6c45db815a91', 'ab05d17f-7e4b-4d6b-872d-ea04b9111041', 'Videojuego', 50000.00,
     '2021-02-01', '2022-01-12', 'C#', '28377ad9-31d5-4206-9a65-f47828308a35', 1,
     '86e0f53e-04d5-49fb-8ffc-34c6e2c7e455'),
    ('f74cf06e-8625-4551-8108-f75482cd16fa', '5b33ef30-979b-4bb4-9baa-84e92cd291b6', 'Webpage', 30000.00,
     '2021-02-01', '2021-12-12', 'html, css', '0484f1fc-5d53-49d7-ba00-9861abfba760', 1,
     '86e0f53e-04d5-49fb-8ffc-34c6e2c7e455'),
    ('03b8bf8d-03f5-4d61-bdcb-7837e65fafe8', '5b33ef30-979b-4bb4-9baa-84e92cd291b6', 'Videojuego', 50000.00,
     '2021-02-01', '2022-01-12', 'C#', '28377ad9-31d5-4206-9a65-f47828308a35', 1,
     '08174950-6519-47ef-9ceb-eaff8cb66c8b'),
    ('97a53b2b-dd63-4fc4-9126-4ea3947b940a', 'ab05d17f-7e4b-4d6b-872d-ea04b9111041', 'Webpage', 30000.00,
     '2021-02-01', '2021-12-12', 'html, css', '0484f1fc-5d53-49d7-ba00-9861abfba760', 0,
     '08174950-6519-47ef-9ceb-eaff8cb66c8b'),
    ('359d985f-9516-4398-b742-4feb526461ca', 'ab05d17f-7e4b-4d6b-872d-ea04b9111041', 'Videojuego', 50000.00,
     '2021-02-01', '2022-01-12', 'C#', '28377ad9-31d5-4206-9a65-f47828308a35', 0,
     'b895348f-1852-4d61-920a-5cdbb4b6f7b7'),
    ('65694948-8531-4eda-a06a-8adad84ef256', '5b33ef30-979b-4bb4-9baa-84e92cd291b6', 'Webpage', 30000.00,
     '2021-02-01', '2021-12-12', 'html, css', '0484f1fc-5d53-49d7-ba00-9861abfba760', 0,
     'b895348f-1852-4d61-920a-5cdbb4b6f7b7');

DROP TABLE IF EXISTS `tarea`;
CREATE TABLE `tarea` (
     `uuid_tarea` varchar(36) NOT NULL,
     `uuid_programador` varchar(36) NOT NULL,
     `uuid_issue` varchar(36) NOT NULL,
     PRIMARY KEY(`uuid_tarea`),
     KEY `programador_FK` (`uuid_programador`),
     CONSTRAINT `programador_FK` FOREIGN KEY (`uuid_programador`) REFERENCES `programador` (`uuid_programador`),
     KEY `uuid_issue_FK` (`uuid_issue`),
     CONSTRAINT `uuid_issue_FK` FOREIGN KEY (`uuid_issue`) REFERENCES `issue` (`uuid_issue`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla de tarea';

INSERT INTO `tarea` (`uuid_tarea`,`uuid_programador`, `uuid_issue`) VALUES
    ('fa55e2e3-6195-434a-9fb2-1cd8f3bd2a43','ab05d17f-7e4b-4d6b-872d-ea04b9111041',
     '05601a0f-e9d0-4119-859b-235cf28b33a8'),
    ('321c1460-a76b-42b5-b960-248b9cef8144','5b33ef30-979b-4bb4-9baa-84e92cd291b6',
     '82c102f4-e063-4116-9b39-1d7ac1b6ea11'),
    ('a7d89c0a-41b0-4f0f-93ab-89954e795449','c0b705bc-8f83-42e6-aae9-b115f6035eff',
     '0e2616b5-d804-4677-8345-8f093a943c0d');

DROP TABLE IF EXISTS `issue`;
CREATE TABLE `issue` (
     `uuid_issue` varchar(36) NOT NULL,
     `titulo` varchar(25) NOT NULL,
     `texto` varchar(100) NOT NULL,
     `fecha` date NOT NULL,
     `proyecto` varchar(36) NOT NULL,
     `repositorio_asignado` varchar(36) NOT NULL,
     `es_acabado` bool NOT NULL,
     PRIMARY KEY(`uuid_issue`),
     KEY `repositorio_asignado_FK` (`repositorio_asignado`),
     CONSTRAINT `repositorio_asignado_FK` FOREIGN KEY (`repositorio_asignado`)
         REFERENCES `repositorio` (`uuid_repositorio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla de issue';

INSERT INTO `issue` (`uuid_issue`, `titulo`, `texto`, `fecha`,`proyecto`, `repositorio_asignado`, `es_acabado`) VALUES
    ('05601a0f-e9d0-4119-859b-235cf28b33a8', 'Estudio', 'Se hará un estudio profundo del proyecto', '2021-10-01',
     'fdebd291-46b9-4fc5-a4aa-afdfcd434933', '97a53b2b-dd63-4fc4-9126-4ea3947b940a',0),
    ('82c102f4-e063-4116-9b39-1d7ac1b6ea11', 'Analista', 'Se hará un análisis profundo del proyecto', '2021-10-01',
     'fdebd291-46b9-4fc5-a4aa-afdfcd434933', '359d985f-9516-4398-b742-4feb526461ca',0),
    ('0e2616b5-d804-4677-8345-8f093a943c0d', 'Desarrollo', 'Se hará un desarrollo del proyecto', '2021-10-01',
     'd4d71f97-e00e-406f-ad4b-f7be324b9635', '65694948-8531-4eda-a06a-8adad84ef256',0),
    ('d9a4095d-0e59-449d-929e-f37f996cc2e7', 'Desarrollo', 'Se hará un desarrollo del proyecto', '2021-10-02',
     'b1fd232d-0839-4b8a-9aa4-faca952e0665', '65694948-8531-4eda-a06a-8adad84ef256',0);

DROP TABLE IF EXISTS `commit`;
CREATE TABLE `commit` (
    `uuid_commit` varchar(36) NOT NULL,
    `titulo` varchar(25) NOT NULL,
    `texto` varchar(100) NOT NULL,
    `fecha` date NOT NULL,
    `repositorio` varchar(36) NOT NULL,
    `proyecto` varchar(36) NOT NULL,
    `autor_commit` varchar(36) NOT NULL,
    `issue_procedente` varchar(36),
    PRIMARY KEY(`uuid_commit`),
    KEY `proyecto_asociado_FK` (`proyecto`),
    CONSTRAINT `proyecto_asociado_FK` FOREIGN KEY (`proyecto`) REFERENCES `proyecto` (`uuid_proyecto`),
    KEY `autor_commit_FK` (`autor_commit`),
    CONSTRAINT `autor_commit_FK` FOREIGN KEY (`autor_commit`) REFERENCES `programador` (`uuid_programador`),
    KEY `repositorio_asociado_FK` (`repositorio`),
    CONSTRAINT `repositorio_asociado_FK` FOREIGN KEY (`repositorio`) REFERENCES `repositorio` (`uuid_repositorio`),
    KEY `issue_procedente_FK` (`issue_procedente`),
    CONSTRAINT `issue_procedente_FK` FOREIGN KEY (`issue_procedente`) REFERENCES `issue` (`uuid_issue`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla de commit';

INSERT INTO `commit` (`uuid_commit`, `titulo`, `texto`, `fecha`, `repositorio`, `proyecto`, `autor_commit`,
                      `issue_procedente`) VALUES
    ('fbba0a2b-11dc-46c2-949d-96457277f837', 'Parte realizada', 'Se ha realizado una parte del proyecto', '2021-10-02',
     'fbba0a2b-11dc-46c2-949d-96457277f837', '97a53b2b-dd63-4fc4-9126-4ea3947b940a',
     'c0b705bc-8f83-42e6-aae9-b115f6035eff', '82c102f4-e063-4116-9b39-1d7ac1b6ea11'),
    ('07b2f0cb-aedf-4f1c-8314-1b72d5727cf4', 'Parte realizada', 'Se ha realizado una parte del proyecto', '2021-10-01',
     '07b2f0cb-aedf-4f1c-8314-1b72d5727cf4', '359d985f-9516-4398-b742-4feb526461ca',
     '5ee6976a-3f01-411b-aeaf-6dfc7e4785b5', '0e2616b5-d804-4677-8345-8f093a943c0d'),
    ('d37eb266-278b-4f56-b18a-c94ad09c89aa', 'Parte realizada', 'Se ha realizado una parte del proyecto', '2021-10-02',
     'd37eb266-278b-4f56-b18a-c94ad09c89aa', '65694948-8531-4eda-a06a-8adad84ef256',
     'e8bedfcb-c14e-4110-8ade-67e503038601', '05601a0f-e9d0-4119-859b-235cf28b33a8');
DROP TABLE IF EXISTS `repositorio`;
CREATE TABLE `repositorio` (
   `uuid_repositorio` varchar(36) NOT NULL,
   `nombre` varchar(25) NOT NULL,
   `fecha_creacion` date NOT NULL,
   `proyecto` varchar(36) NOT NULL,
   PRIMARY KEY(`uuid_repositorio`),
   KEY `proyecto_FK` (`proyecto`),
                               CONSTRAINT `proyecto_FK` FOREIGN KEY (`proyecto`) REFERENCES `proyecto` (`uuid_proyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla de repositorio';

INSERT INTO `repositorio` (`uuid_repositorio`, `nombre`, `fecha_creacion`, `proyecto`) VALUES
   ('fbba0a2b-11dc-46c2-949d-96457277f837', 'Proyecto videojuego', '2021-10-02',
    '97a53b2b-dd63-4fc4-9126-4ea3947b940a'),
   ('07b2f0cb-aedf-4f1c-8314-1b72d5727cf4', 'Proyecto Webpage', '2021-10-02',
    '359d985f-9516-4398-b742-4feb526461ca'),
   ('d37eb266-278b-4f56-b18a-c94ad09c89aa', 'Proyecto videojuego', '2021-10-01',
    '65694948-8531-4eda-a06a-8adad84ef256');
