
insert into User(id,name,email,password) values (1,'Mussa Mubarki','mmubarki@gmail.com','123456');
insert into Event(id,title,description,location,eventTime,createdBy) values (2,'Impulse Event Lighting','We are a boutique lighting design production company based ','2627 e la palma ave','2017-01-21 13:00:00',1);
insert into Event(id,title,description,location,eventTime,createdBy) values (3,'Summit Event Productions','Have a Memorable Wedding with help from our experienced Lighting Technicians! Impulse Event Lighting works closely with our customers','516 Cameron St, Placentia','2016-11-21 13:00:00',1);
insert into Event(id,title,description,location,eventTime,createdBy) values (4,'Musssa Event Productions','experienced Lighting Technicians! Impulse Event Lighting works closely with our customers','516 Cameron St, Placentia','2016-12-21 13:00:00',1);
insert into InterestedEvents(userId,eventId) values (1,2);
ALTER SEQUENCE hibernate_sequence RESTART WITH 5;