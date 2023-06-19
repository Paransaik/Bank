/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

CREATE TABLE EMPLOYEE
(
    empNo     varchar(8)  NOT NULL,
    nm        varchar(20) NOT NULL,
    passwd    varchar(100)NOT NULL,
    email     varchar(30) NOT NULL,
    birthDt   date,
    enterDt   date,
    retireDt  date,
    deptCd    varchar(4),
    teamCd    varchar(4),
    gradeCd   varchar(2),
    positCd   varchar(2),
    phoneNo   varchar(20),
    addr      varchar(100)
);
insert into EMPLOYEE
values ('1000', '박현철', '1978-01-07', '2005-03-07', NULL, '', '', '10', '', '', '', '');
insert into EMPLOYEE
values ('1001', '홍길동', '1990-04-03', '2013-10-04', NULL, '', '1010', '20', '', '', '', '');
insert into EMPLOYEE
values ('1002', '선동열', '1992-01-02', '2015-11-20', NULL, '', '1010', '20', '', '', '', '');
insert into EMPLOYEE
values ('1003', '임꺾정', '1990-07-02', '2010-02-08', '2022-05-04', '', '2010', '40', '', '', '', '');
insert into EMPLOYEE
values ('1005', '이대호', '1988-01-21', '2019-03-12', NULL, '', '1030', '30', '', '', '', '');
insert into EMPLOYEE
values ('1006', '김시진', '1985-08-03', '2018-04-28', NULL, '', '1030', '10', '', '', '', '');
insert into EMPLOYEE
values ('1008', '최동원', '1991-10-12', '2020-01-31', NULL, '', '1020', '50', '', '', '', '');
insert into EMPLOYEE
values ('1009', '홍길순', '1985-04-03', '2022-07-22', NULL, '', '1010', '30', '', '', '', '');
insert into EMPLOYEE
values ('1010', '정태영', '1997-04-19', '2022-08-22', NULL, '', '1010', '30', '', '', '', '');

CREATE TABLE LAON
(
    empNo         varchar(8) NOT NULL,
    loanDt        date NOT NULL,
    loanAmt       int(10) NOT NULL,
    monthRepayAmt int(10),
    loanRate      float(5),
    loanBalance   int(10),
    repayFrom     date,
    repayTo       date,
    lastRepayDt   date,
    repayYn      varchar(1) NOT NULL default '' '',
    agreeYn      varchar(1) NOT NULL default '' ''
);
insert into LAON
values ('1000', '2022-01-20', '5000000', '400000', '3.00', '5000000', '2022-03-20', '2023-03-20', NULL, 'N');
insert into LAON
values ('1001', '2022-02-17', '6000000', '400000', '3.20', '6000000', '2022-04-20', '2023-06-20', NULL, 'N');
insert into LAON
values ('1002', '2022-01-25', '1000000', '800000', '3.00', '1000000', '2022-03-20', '2022-04-20', NULL, 'N');
insert into LAON
values ('1003', '2020-02-10', '5000000', '350000', '3.00', '0', '2020-03-20', '2021-05-20', NULL, 'Y');
insert into LAON
values ('1004', '2022-03-15', '6000000', '400000', '4.50', '6000000', '2022-04-20', '2023-06-20', NULL, 'N');
insert into LAON
values ('1005', '2022-01-07', '5000000', '600000', '3.50', '5000000', '2022-05-20', '2023-01-20', NULL, 'N');
insert into LAON
values ('1006', '2022-01-15', '5000000', '500000', '2.00', '5000000', '2022-04-20', '2023-01-20', NULL, 'N');
insert into LAON
values ('1007', '2022-01-10', '5000000', '350000', '2.50', '5000000', '2022-05-20', '2023-07-20', NULL, 'N');

CREATE TABLE REPAY
(
    empNo             varchar(8) NOT NULL,
    loanDt            date NOT NULL,
    repayDt           date NOT NULL,
    beforeLoanBalance int(10),
    loanRate          float(5),
    monthRepayAmt     int(10),
    repayRateAmt      int(10),
    repayFrom         date,
    repayTo           date
);