insert into USER values(1, "abdi@afapa.org", "abdi", "taju", "+251927257963");
insert into USER values(2 , "keti@afapa.org" , "keti", "jundi", "+251927253463");
INSERT INTO USER VALUES(3, 'KETI@AFAPA.ORG', 'KETI', 'JUNDI', '+251927257963');
insert into USERCREDENTIAL values("PBKDF2WithHmacSHA256:2048:c7Y2OCjjCTfDf1Y5AAfdm1EshnsSHWcsKEON/gNVcaY=:Q2a1YFXl7MVTbD7f4T4uL7dzKKKLw+hCNAsgXZFgdY4=", 1),
            ("PBKDF2WithHmacSHA256:2048:oUlKBpgzwpCGZbG/QAzJj/Q/ho375BVOkD1tR9S9M24=:gNxLBT2a5/mSUKtuLPzvGwTjhRxB8Z4Gj/+dyOAG5C0=", 2);

insert into ADMIN values(1), (2);

INSERT INTO ORGANIZATIONALUNIT VALUES(1, 'org'), (2, 'dep'),(3, 'crs');

INSERT INTO ORGANIZATION VALUES(1, '2023-01-21', 'AFAPA', 1);

INSERT INTO DEPARTMENT VALUES(2, '2023-01-21', 'ACADEMICS', 1);

INSERT INTO COURSE VALUES(3, 'CSE3201', 'ADVANCED PROGRAMMING', 2);

INSERT INTO EXAM VALUES(1, 60, 'adavanced programming final exam', '2023-02-13 8:30:00', 1);

INSERT INTO QUESTION VALUES(1, 'what is rmi?', 1), (2, 'what is servelet?', 1), (3, 'which of the following is correct?', 1);

INSERT INTO CHOICE VALUES(1, 'REMOTE METHOD INVOCATION', TRUE, 1), (2, 'REMOTE PROCEDURE CALL', FALSE, 1), (3, 'NONE', FALSE, 1),
    (4, 'SERVER TECH', TRUE, 2), (5, 'REMOTE METHOD INVOCATION', FALSE, 2), (6, 'REMOTE PROCEDURE CALL', FALSE, 2),
    (7, 'YOU ARE DEAD', TRUE, 3), (8, 'YOU ARE ALIVE', FALSE, 3), (9, 'YOU ARE SHIT', TRUE, 3);




