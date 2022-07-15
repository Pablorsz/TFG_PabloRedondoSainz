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
        id INTEGER GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 3),
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

exit;