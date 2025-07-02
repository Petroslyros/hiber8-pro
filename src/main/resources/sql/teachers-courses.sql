USE hiber8prodb;

INSERT INTO `teachers` (id, firstname, lastname) VALUES
	(2,'Μάκης','Καπέτης'),
	(3,'Μάρκος','Καραμπάτσης'),
	(4,'Παναγιώτης','Μόσχος'),
	(5,'Σοφοκλής','Στουραϊτης');

ALTER TABLE teachers AUTO_INCREMENT = 6;

INSERT INTO `courses` (id, teacher_id, title) VALUES
    (1, 1, "Java"),
	(2, 1, 'C#'),
	(3, 2, 'Python'),
	(4, 3, 'JavaScript'),
	(5, 4, 'TypeScript'),
	(6, 1, 'Scala'),
	(7, 5, 'Go'),
	(8, 5, 'SQL'),
	(9, 3, 'React'),
	(10, 4, 'Angular');

ALTER TABLE courses AUTO_INCREMENT = 11;