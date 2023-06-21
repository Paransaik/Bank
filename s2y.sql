--CREATE DATABASE bank;
use bank;

CREATE TABLE  AAA010MT
	(empno varchar(8) NOT NULL, 
	nm nvarchar(20) NOT NULL, 
	birth_dt date, 
	enter_dt date,  
	retire_dt date, 
	dept_cd varchar(4), 
	team_cd varchar(4), 
	grade_cd varchar(2), 
	posit_cd varchar(2), 
	phone_no varchar(20), 
	mphone_no varchar(20), 
	addr varchar(100));

ALTER TABLE  AAA010MT
  ADD CONSTRAINT AAA010MT_PK PRIMARY KEY CLUSTERED ( empno );
CREATE NONCLUSTERED INDEX  AAA010MT_IDX1 ON AAA010MT ( nm );
CREATE NONCLUSTERED INDEX  AAA010MT_IDX2 ON AAA010MT ( grade_cd, empno );
CREATE NONCLUSTERED INDEX  AAA010MT_IDX3 ON AAA010MT ( dept_cd, team_cd, grade_cd, empno );

--* 02. 대출 원장
CREATE TABLE dbo.ABA010MT(
	empno varchar(8) NOT NULL, loan_dt date NOT NULL, loan_amt int NOT NULL
	, month_repay_amt int, loan_rate numeric(5,2), loan_balance int, repay_from date, repay_to date
	, last_repay_dt date, frepay_yn varchar(1) NOT NULL DEFAULT 'N', frepay_dt date);

ALTER TABLE  ABA010MT
  ADD CONSTRAINT ABA010MT_PK PRIMARY KEY CLUSTERED ( empno, loan_dt );
CREATE NONCLUSTERED INDEX  ABA010MT_IDX1 ON ABA010MT ( loan_dt, empno );
 
--* 03. 대출 상환
CREATE TABLE dbo.ABA011DT(
	empno varchar(8) NOT NULL,
	loan_dt date NOT NULL,
	repay_dt date NOT NULL,
	before_loan_balance int NULL,
	loan_rate numeric(5,2) NULL,
	month_repay_amt int NULL,  --* 상환 원금
	repay_rate_amt int NULL,      --* 상환 이자
	repay_from date NULL,         --* 상환 기간 (FROM)
	repay_to    date NULL);         --* 상환 기간 (TO));

ALTER TABLE  ABA011DT
  ADD CONSTRAINT ABA011DT_PK PRIMARY KEY CLUSTERED ( empno, loan_dt, repay_dt );

--* AAA010MT
delete from AAA010MT;
insert into AAA010MT (empno, nm, team_cd, grade_cd) values('1000','박현철', NULL,'10');
insert into AAA010MT (empno, nm, team_cd, grade_cd) values('1001','홍길동','1010','20');
insert into AAA010MT (empno, nm, team_cd, grade_cd) values('1009','홍길순','1010','30');
insert into AAA010MT (empno, nm, team_cd, grade_cd) values('1003','임꺾정','2010','40');
insert into AAA010MT (empno, nm, team_cd, grade_cd) values('1008','최동원','1020','50');
insert into AAA010MT (empno, nm, team_cd, grade_cd) values('1005','이대호','1030','30');
insert into AAA010MT (empno, nm, team_cd, grade_cd) values('1006','김시진','1030','10');
insert into AAA010MT (empno, nm, team_cd, grade_cd) values('1002','선동열','1010','20');

--* ABA010MT
delete from ABA010MT;
insert into ABA010MT (empno, loan_dt, loan_amt, loan_balance, month_repay_amt, loan_rate, repay_from, repay_to ) values('1000','2022-01-20',5000000,5000000,400000,3.0,'2022-03-20','2023-03-20');
insert into ABA010MT (empno, loan_dt, loan_amt, loan_balance, month_repay_amt, loan_rate, repay_from, repay_to ) values('1001','2022-02-17',6000000,6000000,400000,3.2,'2022-04-20','2023-06-20');
insert into ABA010MT (empno, loan_dt, loan_amt, loan_balance, month_repay_amt, loan_rate, repay_from, repay_to ) values('1002','2022-01-25',1000000,1000000,800000,3.0,'2022-03-20','2022-04-20');
insert into ABA010MT (empno, loan_dt, loan_amt, loan_balance, month_repay_amt, loan_rate, repay_from, repay_to ) values('1003','2020-02-10',5000000,5000000,350000,3.0,'2020-03-20','2021-05-20');
insert into ABA010MT (empno, loan_dt, loan_amt, loan_balance, month_repay_amt, loan_rate, repay_from, repay_to ) values('1004','2022-03-15',6000000,6000000,400000,4.5,'2022-04-20','2023-06-20');
insert into ABA010MT (empno, loan_dt, loan_amt, loan_balance, month_repay_amt, loan_rate, repay_from, repay_to ) values('1005','2022-01-07',5000000,5000000,600000,3.5,'2022-05-20','2023-01-20');
insert into ABA010MT (empno, loan_dt, loan_amt, loan_balance, month_repay_amt, loan_rate, repay_from, repay_to ) values('1006','2022-01-15',5000000,5000000,500000,2.0,'2022-04-20','2023-01-20');
insert into ABA010MT (empno, loan_dt, loan_amt, loan_balance, month_repay_amt, loan_rate, repay_from, repay_to ) values('1007','2022-01-10',5000000,5000000,350000,2.5,'2022-05-20','2023-07-20');
update ABA010MT set frepay_yn='Y', loan_balance = 0 where empno='1003' and loan_dt='2020-02-10';

SELECT * FROM AAA010MT;

SELECT * FROM ABA010MT;

SELECT AAA.empno, AAA.nm,
		BBB.loan_dt, BBB.loan_amt, BBB.month_repay_amt, BBB.loan_rate, BBB.loan_balance, BBB.repay_from, BBB.repay_to, frepay_yn, frepay_dt
FROM AAA010MT AAA, ABA010MT BBB
WHERE BBB.empno = AAA.empno