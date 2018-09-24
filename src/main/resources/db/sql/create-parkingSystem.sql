CREATE TABLE parking_spcification
(
id integer PRIMARY KEY,
spot_type varchar(20),
vehicle_type varchar(20)
);
CREATE TABLE parking_spot 
(
id integer PRIMARY KEY,
parking_spot_number varchar(20),
description varchar(30),
parking_type varchar(20),
parking_spot_type varchar(20),
occupied char
);
CREATE TABLE vehicle
(
id integer PRIMARY KEY,
vehicle_type varchar(20),
vehicle_number varchar(20),
arrival_time timeStamp,
departure_time timeStamp
);
CREATE TABLE ticket
(
id integer PRIMARY KEY,
vehicle_id integer,
parking_spot_id integer,
arrival_time timeStamp,
departure_time timeStamp,
FOREIGN KEY (parking_spot_id) REFERENCES parking_spot(id),
FOREIGN KEY (vehicle_id) REFERENCES vehicle(id)
);


CREATE TABLE vehicle_history
(
id integer,
vehicle_type varchar(20),
vehicle_number varchar(20),
arrival_time timeStamp,
departure_time timeStamp
);
CREATE TABLE ticket_history
(
id integer,
vehicle_id integer,
parking_spot_id integer,
arrival_time timeStamp,
departure_time timeStamp
);

CREATE SEQUENCE spot_id_seq AS integer START WITH 1 INCREMENT BY 1 MAXVALUE 100000 CYCLE;

CREATE SEQUENCE ticket_id_seq AS integer START WITH 1 INCREMENT BY 1 MAXVALUE 100000 CYCLE;

CREATE SEQUENCE vehicle_id_seq AS integer START WITH 1 INCREMENT BY 1 MAXVALUE 100000 CYCLE;

