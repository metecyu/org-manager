delete org_org;
INSERT INTO "ORG_ORG" VALUES ('root', 'name', '空军34师', 'y', '1', 1, '', '0', '空军34师');
INSERT INTO "ORG_ORG" VALUES ('2', 'name', '师机关', 'y', '1', 1, '', 'root', '空军34师/师机关');
INSERT INTO "ORG_ORG" VALUES ('3', 'name', '作战科', 'y', '1', 1, '', '2', '空军34师/师机关/作战科');
INSERT INTO "ORG_ORG" VALUES ('4', 'name', '军训科', 'y', '1', 2, '', '2', '空军34师/师机关/军训科');
INSERT INTO "ORG_ORG" VALUES ('5', 'name', '100团', 'y', '1', 2, '', 'root', '空军34师/团领导');

INSERT INTO "ORG_ORG" VALUES ('6', 'name', '团领导', 'y', '1', 1, '', '5', '空军34师/100团/团领导');
INSERT INTO "ORG_ORG" VALUES ('7', 'name', '作训股', 'y', '1', 2, '', '5', '空军34师/100团/作训股');
INSERT INTO "ORG_ORG" VALUES ('8', 'name', '一大队', 'y', '1', 3, '', '5', '空军34师/100团/一大队');
commit;