delete org_org;
INSERT INTO "ORG_ORG" VALUES ('root', 'name', '�վ�34ʦ', 'y', '1', 1, '', '0', '�վ�34ʦ');
INSERT INTO "ORG_ORG" VALUES ('2', 'name', 'ʦ����', 'y', '1', 1, '', 'root', '�վ�34ʦ/ʦ����');
INSERT INTO "ORG_ORG" VALUES ('3', 'name', '��ս��', 'y', '1', 1, '', '2', '�վ�34ʦ/ʦ����/��ս��');
INSERT INTO "ORG_ORG" VALUES ('4', 'name', '��ѵ��', 'y', '1', 2, '', '2', '�վ�34ʦ/ʦ����/��ѵ��');
INSERT INTO "ORG_ORG" VALUES ('5', 'name', '100��', 'y', '1', 2, '', 'root', '�վ�34ʦ/���쵼');

INSERT INTO "ORG_ORG" VALUES ('6', 'name', '���쵼', 'y', '1', 1, '', '5', '�վ�34ʦ/100��/���쵼');
INSERT INTO "ORG_ORG" VALUES ('7', 'name', '��ѵ��', 'y', '1', 2, '', '5', '�վ�34ʦ/100��/��ѵ��');
INSERT INTO "ORG_ORG" VALUES ('8', 'name', 'һ���', 'y', '1', 3, '', '5', '�վ�34ʦ/100��/һ���');
commit;