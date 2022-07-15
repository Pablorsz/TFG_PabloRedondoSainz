connect 'jdbc:derby:C:\Users\Pablo\Desktop\Apuntes\WonderfulTFG\gradle-projec\desktop\db;create=true;user=admin;password=admin';

-- Enum
create table TipoCarta 
(
        id INTEGER not null,
        nombre VARCHAR(20) not null unique,
                PRIMARY KEY(id)
);


INSERT INTO TipoCarta (id, nombre) VALUES  
        (1,'ESTRUCTURA'),
        (2,'VEHICULO'),
        (3,'INVESTIGACION'),
        (4,'PROYECTO'),
        (5,'DESCUBRIMIENTO');

-- Enum
create table Recurso(
        id INTEGER not null,
        nombre VARCHAR(20) not null unique,
                PRIMARY KEY (id)
);

INSERT INTO Recurso (id, nombre) VALUES  
        (1,'MATERIAL'),
        (2,'ENERGIA'),
        (3,'CIENCIA'),
        (4,'ORO'),
                (5,'EXPLORACION'),
                (6,'FIANCIERO'),
        (7,'GENERAL'),
        (8,'KRYSTALLIUM');

-- Enum
create table TipoPuntos(
        id INTEGER not null,
        nombre VARCHAR(20) not null unique,
                PRIMARY KEY (id)
);

INSERT INTO TipoPuntos (id, nombre) VALUES
        (0,'PLANOS'),
        (1,'ESTRUCTURA'),
        (2,'VEHICULO'),
        (3,'INVESTIGACION'),
        (4,'PROYECTO'),
        (5,'DESCUBRIMIENTO'),
        (6,'FIANCIEROS'),
        (7,'GENERALES');


create table LineaRecursos(
        id VARCHAR (10),
        material INTEGER,
        energia INTEGER,
        ciencia INTEGER,
        oro INTEGER,
        exploracion INTEGER,
        financieros INTEGER,
        generales INTEGER,
        krystallium INTEGER,
        primary key (id)
);


create table CartaDesarrollo (
        id INTEGER,
        nombre VARCHAR (25),
        copias INTEGER,
        idTipoCarta INTEGER,
        idReciclaje INTEGER,
        --idUso INTEGER,
        recursosConstruccion VARCHAR (10),
        recursosProduccion VARCHAR (10),
        multProduccion INTEGER,
        recursosRecompensa VARCHAR (10),
        puntos INTEGER,
        tipoPuntos INTEGER,
        primary key (id),
        foreign key (idTipoCarta) references TipoCarta(id),
        foreign key (idReciclaje) references Recurso(id),
        --foreign key (idUso) references Uso(id),
        foreign key (recursosConstruccion) references LineaRecursos(id),
        foreign key (recursosProduccion) references LineaRecursos(id),
        foreign key (multProduccion) references TipoCarta(id),
        foreign key (recursosRecompensa) references LineaRecursos(id),
        foreign key (tipoPuntos) references TipoPuntos(id)
);

create table CartaImperio (
        id INTEGER,
        nombre VARCHAR (50),
        recursosProduccion VARCHAR (10),
        puntos INTEGER,
        tipoPuntos INTEGER,
        primary key (id),
        foreign key (recursosProduccion) references LineaRecursos(id),
        foreign key (tipoPuntos) references TipoPuntos(id)
);

create table Escenario (
        id INTEGER,
        nombre VARCHAR (50),
        descripcion VARCHAR (600),
        idCartaImperio INTEGER,
        bronce INTEGER,
        plata INTEGER,
        oro INTEGER,
        primary key (id),
        foreign key (idCartaImperio) references CartaImperio(id)
);

create Table Partida (
        id INTEGER GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1),
        idEscenario INTEGER,
        generales INTEGER,
        financieros INTEGER,
        krystallium INTEGER,
        puntuacionFinal  INTEGER,
        primary key (id),
        foreign key (idEscenario) references Escenario(id)
);

create Table CartasEscenario (
        idEscenario INTEGER,
        idCarta INTEGER,
        foreign key (idEscenario) references Escenario(id),
        foreign key (idCarta) references CartaDesarrollo(id)
);

create Table UsoEnPartida (
        idPartida INTEGER,
        idCarta INTEGER,
        foreign key (idPartida) references Partida(id),
        foreign key (idCarta) references CartaDesarrollo(id)
);

-- Construccion
INSERT INTO LineaRecursos (id,material) VALUES  
        ('c20000',2),
        ('c30000',3);

INSERT INTO LineaRecursos (id,material,energia) VALUES  
        ('c31000',3,1),
        ('c41000',4,1),
        ('c34000',3,4),
        ('c12000',1,2),
        ('c23000',2,3);


INSERT INTO LineaRecursos (id,material,ciencia) VALUES  
        ('c40100',4,1);

INSERT INTO LineaRecursos (id,material,oro) VALUES  
        ('c40030',4,3),
        ('c30020',3,2),
        ('c20030',2,3),
        ('c30030',3,3),
        ('c50030',5,3);


INSERT INTO LineaRecursos (id,material,exploracion) VALUES  
        ('c30001',3,1);

INSERT INTO LineaRecursos (id,energia) VALUES  
        ('c02000',2),
        ('c03000',3);

INSERT INTO LineaRecursos (id,energia,ciencia) VALUES  
        ('c01200',1,2),
        ('c02200',2,2),
        ('c03100',3,1),
        ('c03200',3,2),
        ('c02400',2,4);

INSERT INTO LineaRecursos (id,energia,oro) VALUES  
        ('c02020',2,2),
        ('c03040',3,4);

INSERT INTO LineaRecursos (id,energia,ciencia,oro) VALUES  
        ('c02120',2,1,2),
        ('c03120',3,1,2),
        ('c01130',1,1,3),
        ('c02130',2,1,3);


INSERT INTO LineaRecursos (id,ciencia) VALUES  
        ('c00300',3),
        ('c00400',4),
        ('c00500',5),
        ('c00700',7),
        ('c00800',8);

INSERT INTO LineaRecursos (id,ciencia,oro) VALUES  
        ('c00120',1,2),
        ('c00210',2,1),
        ('c00410',4,1),
        ('c00320',3,2),
        ('c00420',4,2);

INSERT INTO LineaRecursos (id,oro) VALUES  
        ('c00030',3),
        ('c00050',5);

INSERT INTO LineaRecursos (id,exploracion) VALUES  
        ('c00003',3),
        ('c00004',4),
        ('c00005',5),
        ('c00006',6),   
        ('c00007',7);

INSERT INTO LineaRecursos (id,ciencia,krystallium) VALUES  
        ('c00500k',5,3),
        ('c00700k',7,1);

INSERT INTO LineaRecursos (id,exploracion,krystallium) VALUES  
        ('c00004k',4,1),
        ('c00007k',7,1);

INSERT INTO LineaRecursos (id,material,energia,krystallium) VALUES  
        ('c33000k',3,3,1);

INSERT INTO LineaRecursos (id,energia,ciencia,krystallium) VALUES  
        ('c01400k',1,4,1);

INSERT INTO LineaRecursos (id,energia,ciencia,oro,krystallium) VALUES  
        ('c02220k',2,2,2,1);

INSERT INTO LineaRecursos (id,material,oro,financieros) VALUES  
        ('c20030f',2,3,1);

INSERT INTO LineaRecursos (id,oro,financieros) VALUES  
        ('c00030f',3,2),
        ('c00060f',6,2);

INSERT INTO LineaRecursos (id,oro,krystallium) VALUES  
        ('c00030k',3,1);

INSERT INTO LineaRecursos (id,exploracion,generales) VALUES  
        ('c00005g',5,2),
        ('c00006g',6,1);

INSERT INTO LineaRecursos (id,ciencia,exploracion,generales) VALUES  
        ('c00304g',3,4,1);

-- Produccion
INSERT INTO LineaRecursos (id,material) VALUES  
        ('p10000',1),
        ('p20000',2);

INSERT INTO LineaRecursos (id,energia) VALUES  
        ('p01000',1),
        ('p03000',3),
        ('p04000',4);

INSERT INTO LineaRecursos (id,ciencia) VALUES  
        ('p00100',1),
        ('p00200',2);

INSERT INTO LineaRecursos (id,oro) VALUES  
        ('p00010',1),
        ('p00020',2),
        ('p00030',3);

INSERT INTO LineaRecursos (id,exploracion) VALUES  
        ('p00001',1),
        ('p00002',2),
        ('p00003',3);

INSERT INTO LineaRecursos (id,material,energia) VALUES  
        ('p22000',2,2);

INSERT INTO LineaRecursos (id,material,energia,ciencia) VALUES  
        ('p21100',2,1,1);

INSERT INTO LineaRecursos (id,material,ciencia) VALUES  
        ('p10100',1,1),
        ('p20200',2,2);

INSERT INTO LineaRecursos (id,material,oro) VALUES  
        ('p10010',1,1),
        ('p10020',1,2),
        ('p30010',3,1),
        ('p20020',2,2);

INSERT INTO LineaRecursos (id,material,exploracion) VALUES  
        ('p10001',1,1);

INSERT INTO LineaRecursos (id,energia,exploracion) VALUES  
        ('p02001',2,1);

INSERT INTO LineaRecursos (id,energia,oro) VALUES  
        ('p01010',1,1),
        ('p02010',2,1);

INSERT INTO LineaRecursos (id,ciencia,exploracion) VALUES  
        ('p00101',1,1),
        ('p00102',1,2);

INSERT INTO LineaRecursos (id,oro,exploracion) VALUES  
        ('p00011',1,1);

-- Recompensas
INSERT INTO LineaRecursos (id,generales) VALUES  
        ('r1g',1),
        ('r2g',2);

INSERT INTO LineaRecursos (id,financieros) VALUES  
        ('r1f',1),
        ('r2f',2);

INSERT INTO LineaRecursos (id,krystallium) VALUES  
        ('r1k',1),
        ('r2k',2),
        ('r3k',3);

--Cartas

INSERT INTO CartaDesarrollo (id,nombre, 
        copias, idTipoCarta, idReciclaje, 
        recursosConstruccion, recursosProduccion, multProduccion, recursosRecompensa,
        puntos,tipoPuntos) VALUES  
        (1,'PlantaDeReciclaje',7,1,1,'c20000','p20000',null,null,null,0),
        (2,'Aerogenerador',7,1,2,'c20000','p01000',null,null,null,0),
        (3,'RedDeTransportes',2,1,1,'c30000',null,null,null,1,2),
        (4,'BaseMilitar',6,1,1,'c31000','p10100',null,'r1g',null,0),
        (5,'CentroDeInvestigacion',7,1,3,'c31000','p00200',null,null,null,0),
        (6,'ComplejoIndustrial',6,1,4,'c31000','p10010',null,'r1f',null,0),
        (7,'CentroFinanciero',5,1,4,'c41000','p00020',null,'r1f',null,0),
        (8,'CentralNuclear',5,1,2,'c40100','p03000',null,null,null,0),
        (9,'PlataformaPetrolifera',5,1,2,'c30001','p01010',null,'r1f',null,0),
        (10,'Dirigible',6,2,5,'c02000','p00001',null,null,null,0),
        (11,'LaboratorioAereo',3,2,3,'c03000','p00101',null,null,null,0),
        (12,'Tuneladora',4,2,1,'c12000','p10001',null,null,null,0),
        (13,'BatallonDeTanques',7,2,1,'c12000','p00001',null,'r1g',null,0),
        (14,'Rompehielos',4,2,5,'c03100','p00002',null,null,null,0),
        (15,'EscuadronAereo',2,2,3,'c03200','p00003',null,null,null,0),
        (16,'Submarino',3,2,1,'c23000','p00002',null,'r1g',null,0),
        (17,'Portaaviones',1,2,1,'c34000','p00001',2,'r2g',null,0),
        (18,'Coloso',1,2,1,'c33000k','p00002',null,'r2g',1,2),
        (19,'AnimalesRoboticos',1,3,2,'c01200','p10000',null,'r1g',2,0),
        (20,'Megabomba',1,3,2,'c02200',null,null,'r2g',3,0),
        (21,'Satelites',1,3,5,'c02400','p00002',null,'r1g',3,0),
        (22,'ClonacionHumana',1,3,4,'c00210','p00010',null,'r1f',1,0),
        (23,'Transmutacion',1,3,4,'c00320','p00030',null,'r1k',1,0),
        (24,'Neurociencia',1,3,3,'c00300','p00100',3,null,1,0),
        (25,'AsistenciaRobotica',1,3,1,'c00300','p10000',1,null,1,0),
        (26,'VacunaUniversal',1,3,4,'c00300',null,null,null,1,4),
        (27,'MejorasGeneticas',1,3,3,'c00400',null,null,'r2f',3,0),
        (28,'Supersonar',1,3,5,'c00400','p00001',2,null,1,0),
        (29,'Superordenador',1,3,3,'c00400','p00100',null,null,1,2),
        (30,'InjertosBionicos',1,3,1,'c00500','p20000',null,'r1g',4,0),
        (31,'RealidadVirtual',1,3,4,'c00500','p00010',3,null,2,0),
        (32,'ControlClimatico',1,3,2,'c00500','p02010',null,null,2,0),
        (33,'AutomatasDeSeguridad',1,3,4,'c00410',null,null,null,1,7),
        (34,'Acuicultura',1,3,3,'c00420',null,null,'r1f',1,6),
        (35,'InversorDeGravedad',1,3,3,'c01400k',null,null,'r1f',2,4),
        (36,'TecnologiaDesconocida',1,3,3,'c00700k',null,null,null,3,3),
        (37,'ViajeEnElTiempo',1,3,5,'c00500k',null,null,null,15,0),
        (38,'GeneradorCuantico',1,3,2,'c00500','p03000',null,null,1,2),
        (39,'Teletransporte',1,3,5,'c00800',null,null,'r2k',8,0),
        (40,'Criopreservacion',1,3,4,'c00700',null,null,'r1f',1,6),
        (41,'Supersoldados',1,3,5,'c00700',null,null,'r1g',1,7),
        (42,'ZonaPortuaria',2,4,4,'c00050',null,null,'r2f',2,0),
        (43,'BaseLunar',1,4,5,'c02220k',null,null,'r2g',10,0),
        (44,'TorreGigante',1,4,4,'c20030f',null,null,null,10,0),
        (45,'CongresoMundial',1,4,4,'c00060f',null,null,null,3,4),
        (46,'ExposicionUniversal',1,4,4,'c00030f',null,null,null,1,6),
        (47,'Museo',2,4,5,'c00030',null,null,null,2,5),
        (48,'AscensorEspacial',1,4,2,'c03120',null,null,'r1f',1,6),
        (49,'CanonSolar',1,4,2,'c02130',null,null,'r1g',1,7),
        (50,'MonumentoNacional',1,4,4,'c50030',null,null,null,2,4),
        (51,'CiudadSubterranea',2,4,2,'c30030','p22000',null,'r1k',3,0),
        (52,'BasePolar',1,4,5,'c03040','p00003',null,'r1g',2,5),
        (53,'PresaGigante',1,4,2,'c30020','p04000',null,null,1,0),
        (54,'LaboratorioSecreto',2,4,3,'c20030','p00200',null,'r1k',1,3),
        (55,'CiudadSubmarina',2,4,5,'c02120','p00102',null,null,3,0),
        (56,'ServicioDeInteligencia',2,4,5,'c02020','p00002',null,null,1,0),
        (57,'Universidad',1,4,3,'c00120','p00100',4,null,2,0),
        (58,'CentroDePropaganda',2,4,4,'c00030','p00010',4,'r1g',1,0),
        (59,'TrenMagnetico',1,4,4,'c01130','p00010',1,'r2f',2,0),
        (60,'CiudadCasino',2,4,4,'c03040','p00020',null,'r1f',1,6),
        (61,'Atlantis',1,5,4,'c00007k',null,null,null,2,7),
        (62,'ElCentroDeLaTierra',1,5,5,'c00005g',null,null,null,15,0),
        (63,'DimensionParalela',1,5,5,'c00304g',null,null,'r3k',3,3),
        (64,'FuenteDeLaJuventud',1,5,2,'c00007',null,null,'r3k',1,7),
        (65,'ArcaDeLaAlianza',1,5,5,'c00004',null,null,'r1k',5,0),
        (66,'JardinDeLasHesperides',1,5,5,'c00005',null,null,null,2,4),
        (67,'TumbaDeAlejandro',1,5,4,'c00007',null,null,'r2g',10,0),
        (68,'ReliquiasTemplarias',1,5,4,'c00005','p00020',null,'r2k',3,0),
        (69,'ReinoDeAgartha',1,5,5,'c00004k','p00002',null,null,1,7),
        (70,'Roswell',1,5,3,'c00006','p00100',null,'r1g',1,7),
        (71,'ContinentePerdidoDeMu',1,5,4,'c00006','p00010',null,'r2k',2,5),
        (72,'ElDorado',1,5,4,'c00004','p00030',null,null,3,0),
        (73,'TesoroDeBarbanegra',1,5,4,'c00003','p00011',null,null,2,0),
        (74,'AstronautasAncestrales',1,5,3,'c00006g','p00100',5,'r2k',10,0),
        (75,'LaIslaDeAvalon',1,5,3,'c00005','p00100',null,null,7,0),
        (76,'TrianguloDeLasBermudas',1,5,3,'c00004','p00100',null,'r1k',4,0),
        (77,'MinasDelReySalomon',1,5,4,'c00004','p00010',1,null,2,0),
        (78,'SociedadSecreta',2,4,4,'c00030k',null,null,null,1,6);


INSERT INTO CartaImperio(id,nombre,recursosProduccion,puntos,tipoPuntos) VALUES 
        (1,'UnionPanafricana','p20200',2,3),
        (2,'FederacionAsiatica','p10020',2,4),
        (3,'ImperioAzteca','p02001',3,5),
        (4,'EstadosNorteamericanos','p30010',1,6),
        (5,'RepublicaDeEuropa','p21100',1,7),
        (6,'HegemoniaDelNorte','p21100',0,0);

INSERT INTO Escenario(id,nombre,descripcion,idCartaImperio,bronce,plata,oro) VALUES  
        (1,'VIAJE AL CENTRO DE LA TIERRA','-¡Oh Líder Supremo, que iluminas tu Imperio y te preocupas por la felicidad de tus humildes súbditos! Acaba de llegar una misiva urgente de nuestro equipo de exploración en el Ártico. ¡Han encontrado un túnel secreto que lleva al centro de la Tierra
         -¡Excelente! Que despliegue un campamento y comiencen la construcción de la tuneladora. Ponga el supersónar a su disposición y que se aseguren de alcanzar el centro de la Tierra antes que nadie, o si no...',6,70,95,115),
        (2,'UN MUNDO MEJOR','-Allá donde miro veo ciudades contaminadas, atestadas de niños enfermos. ¿Cómo puede uno inspirarse cuando tierra, mar y aire están tan llenos de porquería? ¡No puedo permitir que mi imagen inmaculada se vea manchada por algo así! ¡Gran Visir, soluciónelo de inmediato!',6,70,95,115),
        (3,'LA CONQUISTA DEL ESPACIO','El Líder Supremo examina el mapamundi inquieto. Su humor empeora por momentos...
                -¿Por qué no aquí, Gran Visir?
                -Es uno de nuestros protectorados señor.
                -¿Y aquí? ¡Responde, no tengo todo el día!
                -Señor, es una de nuestras marionetas...
                El Líder rompe en dos el mapa y se levanta.
                -¡La Tierra se me ha quedado pequeña! Vamos a mirar más allá. Gran Visir, informa a la división de Desarrollo Espacial de que por fin estamos listos para llevar esperanza y felicidad a otros planetas, y así lo haremos.',6,70,90,105),
        (4,'REGRESO AL FUTURO','-Gran Visir, he derrotado a todos mis enemigos, evitando las trampas que nos tendían, y he superado en ingenio y poder a todos los líderes de la historia. ¿Me queda, acaso, algún obstáculo que vencer?
                Ensimismado en sus cábalas , el Gran Visir musitó:
                -El tiempo, oh, Líder Supremo. Es el tiempo quién pone a todos en nuestro sitio...
                La respuesta del Líder Supremo fue contundente:
                -¡Entonces derrotaré al mismísimo tiempo!',6,65,85,105),
        (5,'EL FIN DEL TIEMPO','El Líder Supremo se mostraba agitado. El Gran Visir pudo percibir un destello en la mirada del Líder, y le recordó a cuando éste era aún joven y ambicioso, y no había visto rival a su altura. De repente encolerizó:
                -¡Estúpidos traidores! ¡Panda de desagradecidos! ¡Están destruyendo todo lo que he hecho por ellos! He dominado tierra, cielo y espacio, pero todavía queda un mundo a mi alcance bajo nuestros pies, ¡y no pararé hasta conquistarlo también! ',6,75,95,120),
        (6,'MEGALOMANiA','-Aunque yo decido quién vive y quién muere según la población son las megacorporaciones la que aparentan ser más poderosas. Gran Visir, hoy pondremos fin a esta desfachatez. Ordena la construcción del mayor monumento en mi honor (quiero decir, del Imperio) y haz que las megacorporaciones asuman el coste.',6,60,85,105),
        (7,'PARTIDA ESTaNDAR','¡Escoge tu propia Carta de Imperio y trata de obter el rango de Líder Supremo!',null,75,95,115);

INSERT INTO CartasEscenario (idEscenario, idCarta) VALUES
        (1,52),
        (1,12),
        (1,28),
        (1,62),
        (2,1),
        (2,2),
        (2,26),
        (2,34),
        (3,15),
        (3,36),
        (3,43),
        (3,54),
        (4,5),
        (4,24),
        (4,37),
        (5,6),
        (5,51),
        (5,55),
        (5,78),
        (6,7),
        (6,50),
        (6,58);
exit;