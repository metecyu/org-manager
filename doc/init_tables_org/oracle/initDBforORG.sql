

-- ----------------------------
-- Table structure for ORG_FUNC
-- ----------------------------
--drop TABLE ORG_FUNC;
CREATE TABLE ORG_FUNC (
ID VARCHAR2(200 BYTE) NOT NULL ,
FUNCNAME VARCHAR2(200 BYTE) NULL ,
ISEFFECT VARCHAR2(200 BYTE) NULL ,
TYPE VARCHAR2(200 BYTE) NULL ,
SORT NUMBER NULL ,
CODE VARCHAR2(200 BYTE) NULL ,
PID VARCHAR2(200 BYTE) NULL ,
FUNCWHOLENAME VARCHAR2(200 BYTE) NULL ,
URL VARCHAR2(200 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of ORG_FUNC
-- ----------------------------
INSERT INTO ORG_FUNC VALUES ('root', '所有功能', 'y', '3', '0', 'root', '0', '所有功能', null);

-- ----------------------------
-- Table structure for ORG_FUNCROLEREL
-- ----------------------------
--drop TABLE ORG_FUNCROLEREL;
CREATE TABLE ORG_FUNCROLEREL (
ID VARCHAR2(200 BYTE) NOT NULL ,
ORG_ID VARCHAR2(200 BYTE) NULL ,
SORT NUMBER NULL ,
FUNCID VARCHAR2(200 BYTE) NULL ,
ROLEID VARCHAR2(200 BYTE) NULL ,
USERID VARCHAR2(200 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of ORG_FUNCROLEREL
-- ----------------------------
INSERT INTO ORG_FUNCROLEREL VALUES ('root', null, null, 'root', 'admin', null);

-- ----------------------------
-- Table structure for ORG_ORG
-- ----------------------------
--drop TABLE ORG_ORG;
CREATE TABLE ORG_ORG (
ID VARCHAR2(200 BYTE) NOT NULL ,
ORGNAME VARCHAR2(200 BYTE) NULL ,
SHORTNAME VARCHAR2(200 BYTE) NULL ,
ISEFFECT VARCHAR2(200 BYTE) NULL ,
TYPE VARCHAR2(200 BYTE) NULL ,
SORT NUMBER NULL ,
CODE VARCHAR2(200 BYTE) NULL ,
PID VARCHAR2(200 BYTE) NULL ,
ORGWHOLENAME VARCHAR2(200 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of ORG_ORG
-- ----------------------------

INSERT INTO ORG_ORG VALUES ('root', 'name', '样例', 'y', '1', '1', null, '0', '样例');

-- ----------------------------
-- Table structure for ORG_ROLE
-- ----------------------------
--drop TABLE ORG_ROLE;
CREATE TABLE ORG_ROLE (
ID VARCHAR2(200 BYTE) NOT NULL ,
ROLENAME VARCHAR2(200 BYTE) NULL ,
ROLESIGN VARCHAR2(200 BYTE) NULL ,
ISEFFECT VARCHAR2(200 BYTE) NULL ,
TYPE VARCHAR2(200 BYTE) NULL ,
SORT NUMBER NULL ,
ORGID VARCHAR2(200 BYTE) NULL ,
DETAILS VARCHAR2(200 BYTE) NULL ,
ROLEGROUP VARCHAR2(200 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of ORG_ROLE
-- ----------------------------

INSERT INTO ORG_ROLE VALUES ('admin', '超级管理员', 'admin', 'y', null, '1', 'root', '非常厉害的较色', null);

-- ----------------------------
-- Table structure for ORG_USER
-- ----------------------------
--drop TABLE ORG_USER;
CREATE TABLE ORG_USER (
ID VARCHAR2(200 BYTE) NOT NULL ,
LOGINID VARCHAR2(200 BYTE) NULL ,
USERNAME VARCHAR2(200 BYTE) NULL ,
ISEFFECT VARCHAR2(200 BYTE) NULL ,
PASSWORD VARCHAR2(200 BYTE) NULL ,
BIRTHDAY DATE NULL ,
MOBILE VARCHAR2(200 BYTE) NULL ,
FJH VARCHAR2(200 BYTE) NULL ,
FPHONE VARCHAR2(200 BYTE) NULL ,
SFZID VARCHAR2(200 BYTE) NULL ,
PYJC VARCHAR2(200 BYTE) NULL ,
PYQC VARCHAR2(200 BYTE) NULL ,
WORKSTATE VARCHAR2(200 BYTE) NULL ,
ORGTYPE VARCHAR2(200 BYTE) NULL ,
DUTY VARCHAR2(200 BYTE) NULL ,
DUTYDETAILS VARCHAR2(200 BYTE) NULL ,
GENDER VARCHAR2(200 BYTE) NULL,
EMAIL VARCHAR2(200 BYTE) NULL
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN ORG_USER.LOGINID IS '111';

-- ----------------------------
-- Records of ORG_USER
-- ----------------------------
INSERT INTO ORG_USER VALUES ('cjgly', 'admin', '超级管理员', 'y', '111111', null, null, null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for ORG_USERORGREL
-- ----------------------------
--drop TABLE ORG_USERORGREL;
CREATE TABLE ORG_USERORGREL (
ID VARCHAR2(200 BYTE) NOT NULL ,
SORT NUMBER NULL ,
ISMAIN VARCHAR2(200 BYTE) NULL ,
USERID VARCHAR2(200 BYTE) NULL ,
ORGID VARCHAR2(200 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of ORG_USERORGREL
-- ----------------------------
INSERT INTO ORG_USERORGREL VALUES ('root', '0', 'y', 'cjgly', 'root');


-- ----------------------------
-- Table structure for ORG_USERROLEREL
-- ----------------------------
--drop TABLE ORG_USERROLEREL;
CREATE TABLE ORG_USERROLEREL (
ID VARCHAR2(200 BYTE) NOT NULL ,
SORT NUMBER NULL ,
USERID VARCHAR2(200 BYTE) NULL ,
ROLEID VARCHAR2(200 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;



-- ----------------------------
-- Records of ORG_USERROLEREL
-- ----------------------------

INSERT INTO ORG_USERROLEREL VALUES ('admin', null, 'cjgly', 'admin');

-- ----------------------------
-- Indexes structure for table ORG_FUNC
-- ----------------------------

CREATE TABLE ORG_DATAROLEREL (
ID VARCHAR2(200 BYTE) NULL,
ORGID VARCHAR2(200 BYTE) NULL,
SORT NUMBER NULL,
ROLEID VARCHAR2(200 BYTE) NULL
)
LOGGING
NOCOMPRESS
NOCACHE
;

INSERT INTO ORG_DATAROLEREL VALUES('root','root',null,'admin');

-- ----------------------------
-- Checks structure for table ORG_FUNC
-- ----------------------------
ALTER TABLE ORG_FUNC ADD CHECK (ID IS NOT NULL);
ALTER TABLE ORG_FUNC ADD CHECK (ID IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table ORG_FUNC
-- ----------------------------
ALTER TABLE ORG_FUNC ADD PRIMARY KEY (ID);

-- ----------------------------
-- Indexes structure for table ORG_FUNCROLEREL
-- ----------------------------

-- ----------------------------
-- Checks structure for table ORG_FUNCROLEREL
-- ----------------------------
ALTER TABLE ORG_FUNCROLEREL ADD CHECK (ID IS NOT NULL);
ALTER TABLE ORG_FUNCROLEREL ADD CHECK (ID IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table ORG_FUNCROLEREL
-- ----------------------------
ALTER TABLE ORG_FUNCROLEREL ADD PRIMARY KEY (ID);

-- ----------------------------
-- Indexes structure for table ORG_ORG
-- ----------------------------

-- ----------------------------
-- Checks structure for table ORG_ORG
-- ----------------------------
ALTER TABLE ORG_ORG ADD CHECK (ID IS NOT NULL);
ALTER TABLE ORG_ORG ADD CHECK (ID IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table ORG_ORG
-- ----------------------------
ALTER TABLE ORG_ORG ADD PRIMARY KEY (ID);

-- ----------------------------
-- Indexes structure for table ORG_ROLE
-- ----------------------------

-- ----------------------------
-- Checks structure for table ORG_ROLE
-- ----------------------------
ALTER TABLE ORG_ROLE ADD CHECK (ID IS NOT NULL);
ALTER TABLE ORG_ROLE ADD CHECK (ID IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table ORG_ROLE
-- ----------------------------
ALTER TABLE ORG_ROLE ADD PRIMARY KEY (ID);

-- ----------------------------
-- Indexes structure for table ORG_USER
-- ----------------------------

-- ----------------------------
-- Checks structure for table ORG_USER
-- ----------------------------
ALTER TABLE ORG_USER ADD CHECK (ID IS NOT NULL);
ALTER TABLE ORG_USER ADD CHECK (ID IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table ORG_USER
-- ----------------------------
ALTER TABLE ORG_USER ADD PRIMARY KEY (ID);

-- ----------------------------
-- Indexes structure for table ORG_USERORGREL
-- ----------------------------

-- ----------------------------
-- Checks structure for table ORG_USERORGREL
-- ----------------------------
ALTER TABLE ORG_USERORGREL ADD CHECK (ID IS NOT NULL);
ALTER TABLE ORG_USERORGREL ADD CHECK (ID IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table ORG_USERORGREL
-- ----------------------------
ALTER TABLE ORG_USERORGREL ADD PRIMARY KEY (ID);

-- ----------------------------
-- Indexes structure for table ORG_USERROLEREL
-- ----------------------------

-- ----------------------------
-- Checks structure for table ORG_USERROLEREL
-- ----------------------------
ALTER TABLE ORG_USERROLEREL ADD CHECK (ID IS NOT NULL);
ALTER TABLE ORG_USERROLEREL ADD CHECK (ID IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table ORG_USERROLEREL
-- ----------------------------
ALTER TABLE ORG_USERROLEREL ADD PRIMARY KEY (ID);
