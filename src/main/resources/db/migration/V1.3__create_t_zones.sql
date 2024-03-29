create table t_zones
(
    zne_id                bigserial,
    zne_name              text,
    zne_level             text,
    zne_modification_date timestamp with time zone
);

insert into t_zones (zne_id, zne_name, zne_level, zne_modification_date)
values (1, 'powiat ropczycko-sędziszowski', 'Y', now()),
       (2, 'powiat łosicki', 'Y', now()),
       (3, 'powiat piaseczyński', 'Y', now()),
       (4, 'powiat radomski', 'Y', now()),
       (5, 'powiat sierpecki', 'Y', now()),
       (6, 'powiat szydłowiecki', 'Y', now()),
       (7, 'powiat węgrowski', 'Y', now()),
       (8, 'powiat gostyniński', 'Y', now()),
       (9, 'powiat grodziski', 'Y', now()),
       (10, 'powiat łukowski', 'Y', now()),
       (11, 'powiat tomaszowski', 'Y', now()),
       (12, 'powiat Chełm', 'Y', now()),
       (13, 'powiat brzeski', 'Y', now()),
       (14, 'powiat Kraków', 'Y', now()),
       (15, 'powiat zgierski', 'Y', now()),
       (16, 'powiat sulęciński', 'Y', now()),
       (17, 'powiat łańcucki', 'Y', now()),
       (18, 'powiat brzeski', 'Y', now()),
       (19, 'powiat ostrzeszowski', 'Y', now()),
       (20, 'powiat Radom', 'Y', now()),
       (21, 'powiat żyrardowski', 'Y', now()),
       (22, 'powiat obornicki', 'Y', now()),
       (23, 'powiat leszczyński', 'Y', now()),
       (24, 'powiat Siedlce', 'Y', now()),
       (25, 'powiat Leszno', 'Y', now()),
       (26, 'powiat kolski', 'Y', now()),
       (27, 'powiat Łomża', 'Y', now()),
       (28, 'powiat rawicki', 'Y', now()),
       (29, 'powiat słupecki', 'Y', now()),
       (30, 'powiat kościerski', 'Y', now()),
       (31, 'powiat włoszczowski', 'Y', now()),
       (32, 'powiat stargardzki', 'Y', now()),
       (33, 'powiat Wrocław', 'Y', now()),
       (34, 'powiat giżycki', 'Y', now()),
       (35, 'powiat mrągowski', 'Y', now()),
       (36, 'powiat głogowski', 'Y', now()),
       (37, 'powiat choszczeński', 'Y', now()),
       (38, 'powiat Sosnowiec', 'Y', now()),
       (39, 'powiat rybnicki', 'Y', now()),
       (40, 'powiat Gliwice', 'Y', now()),
       (41, 'powiat Piekary Śląskie', 'Y', now()),
       (42, 'powiat Jaworzno', 'Y', now()),
       (43, 'powiat inowrocławski', 'Y', now()),
       (44, 'powiat brodnicki', 'Y', now()),
       (45, 'powiat włocławski', 'Y', now()),
       (46, 'powiat mogileński', 'Y', now()),
       (47, 'powiat Toruń', 'Y', now()),
       (48, 'powiat tucholski', 'Y', now()),
       (49, 'powiat raciborski', 'Y', now()),
       (50, 'powiat cieszyński', 'Y', now()),
       (51, 'powiat krośnieński', 'Y', now()),
       (52, 'powiat nyski', 'Y', now()),
       (53, 'powiat Jelenia Góra', 'Y', now()),
       (54, 'powiat zgorzelecki', 'Y', now()),
       (55, 'powiat przasnyski', 'Y', now()),
       (56, 'powiat Ostrołęka', 'Y', now()),
       (57, 'powiat średzki', 'Y', now()),
       (58, 'powiat jarociński', 'Y', now()),
       (59, 'powiat Gdynia', 'Y', now()),
       (60, 'powiat Świnoujście', 'Y', now()),
       (61, 'powiat kamiennogórski', 'Y', now()),
       (62, 'powiat pabianicki', 'Y', now()),
       (63, 'powiat kolneński', 'Y', now()),
       (64, 'powiat Opole', 'Y', now()),
       (65, 'powiat ostródzki', 'Y', now()),
       (66, 'powiat przemyski', 'Y', now()),
       (67, 'powiat Przemyśl', 'Y', now()),
       (68, 'powiat warszawski zachodni', 'Y', now()),
       (69, 'powiat włodawski', 'Y', now()),
       (70, 'powiat Lublin', 'Y', now()),
       (71, 'powiat bocheński', 'Y', now()),
       (72, 'powiat Tarnów', 'Y', now()),
       (73, 'powiat wschowski', 'Y', now()),
       (74, 'powiat gorzowski', 'Y', now()),
       (75, 'powiat międzyrzecki', 'Y', now()),
       (76, 'powiat słubicki', 'Y', now()),
       (77, 'powiat nowotomyski', 'Y', now()),
       (78, 'powiat wągrowiecki', 'Y', now()),
       (79, 'powiat bielski', 'Y', now()),
       (80, 'powiat suwalski', 'Y', now()),
       (81, 'powiat słupski', 'Y', now()),
       (82, 'powiat ostrowiecki', 'Y', now()),
       (83, 'powiat skarżyski', 'Y', now()),
       (84, 'powiat kartuski', 'Y', now()),
       (85, 'powiat częstochowski', 'Y', now()),
       (86, 'powiat pyrzycki', 'Y', now()),
       (87, 'powiat Siemianowice Śląskie', 'Y', now()),
       (88, 'powiat Elbląg', 'Y', now()),
       (89, 'powiat gryfiński', 'Y', now()),
       (90, 'powiat Bytom', 'Y', now()),
       (91, 'powiat złotoryjski', 'Y', now()),
       (92, 'powiat wrocławski', 'Y', now()),
       (93, 'powiat milicki', 'Y', now()),
       (94, 'powiat lubiński', 'Y', now()),
       (95, 'powiat lipnowski', 'Y', now()),
       (96, 'powiat żniński', 'Y', now()),
       (97, 'powiat radziejowski', 'Y', now()),
       (98, 'powiat nakielski', 'Y', now()),
       (99, 'powiat bartoszycki', 'Y', now()),
       (100, 'powiat żywiecki', 'Y', now()),
       (101, 'powiat sokólski', 'Y', now()),
       (102, 'powiat jasielski', 'Y', now()),
       (103, 'powiat głubczycki', 'Y', now()),
       (104, 'powiat nowosądecki', 'Y', now()),
       (105, 'powiat Zielona Góra', 'Y', now()),
       (106, 'powiat Skierniewice', 'Y', now()),
       (107, 'powiat żagański', 'Y', now()),
       (108, 'powiat Gdańsk', 'Y', now()),
       (109, 'powiat lęborski', 'Y', now()),
       (110, 'powiat Łódź', 'Y', now()),
       (111, 'powiat piotrkowski', 'Y', now()),
       (112, 'powiat ostrowski', 'Y', now()),
       (113, 'powiat starogardzki', 'Y', now()),
       (114, 'powiat Poznań', 'Y', now()),
       (115, 'powiat Konin', 'Y', now()),
       (116, 'powiat stalowowolski', 'Y', now()),
       (117, 'powiat Tarnobrzeg', 'Y', now()),
       (118, 'powiat płoński', 'Y', now()),
       (119, 'powiat mławski', 'Y', now()),
       (120, 'powiat siedlecki', 'Y', now()),
       (121, 'powiat garwoliński', 'Y', now()),
       (122, 'powiat lipski', 'Y', now()),
       (123, 'powiat hrubieszowski', 'Y', now()),
       (124, 'powiat kraśnicki', 'Y', now()),
       (125, 'powiat łęczyński', 'Y', now()),
       (126, 'powiat opolski', 'Y', now()),
       (127, 'powiat rycki', 'Y', now()),
       (128, 'powiat Zamość', 'Y', now()),
       (129, 'powiat dąbrowski', 'Y', now()),
       (130, 'powiat krakowski', 'Y', now()),
       (131, 'powiat wieruszowski', 'Y', now()),
       (132, 'powiat żarski', 'Y', now()),
       (133, 'powiat krośnieński', 'Y', now()),
       (134, 'powiat dębicki', 'Y', now()),
       (135, 'powiat namysłowski', 'Y', now()),
       (136, 'powiat czarnkowsko-trzcianecki', 'Y', now()),
       (137, 'powiat Płock', 'Y', now()),
       (138, 'powiat grajewski', 'Y', now()),
       (139, 'powiat starachowicki', 'Y', now()),
       (140, 'powiat konecki', 'Y', now()),
       (141, 'powiat elbląski', 'Y', now()),
       (142, 'powiat nidzicki', 'Y', now()),
       (143, 'powiat nowomiejski', 'Y', now()),
       (144, 'powiat Ruda Śląska', 'Y', now()),
       (145, 'powiat białogardzki', 'Y', now()),
       (146, 'powiat tarnogórski', 'Y', now()),
       (147, 'powiat zawierciański', 'Y', now()),
       (148, 'powiat Chorzów', 'Y', now()),
       (149, 'powiat Katowice', 'Y', now()),
       (150, 'powiat bolesławiecki', 'Y', now()),
       (151, 'powiat Wałbrzych', 'Y', now()),
       (152, 'powiat sępoleński', 'Y', now()),
       (153, 'powiat Włocławek', 'Y', now()),
       (154, 'powiat hajnowski', 'Y', now()),
       (155, 'powiat chrzanowski', 'Y', now()),
       (156, 'powiat wejherowski', 'Y', now()),
       (157, 'powiat Szczecin', 'Y', now()),
       (158, 'powiat pucki', 'Y', now()),
       (159, 'powiat rawski', 'Y', now()),
       (160, 'powiat łowicki', 'Y', now()),
       (161, 'powiat skierniewicki', 'Y', now()),
       (162, 'powiat iławski', 'Y', now()),
       (163, 'powiat niżański', 'Y', now()),
       (164, 'powiat tarnobrzeski', 'Y', now()),
       (165, 'powiat nowodworski', 'Y', now()),
       (166, 'powiat pruszkowski', 'Y', now()),
       (167, 'powiat przysuski', 'Y', now()),
       (168, 'powiat białobrzeski', 'Y', now()),
       (169, 'powiat wyszkowski', 'Y', now()),
       (170, 'powiat biłgorajski', 'Y', now()),
       (171, 'powiat chełmski', 'Y', now()),
       (172, 'powiat parczewski', 'Y', now()),
       (173, 'powiat świdnicki', 'Y', now()),
       (174, 'powiat kutnowski', 'Y', now()),
       (175, 'powiat łódzki wschodni', 'Y', now()),
       (176, 'powiat kolbuszowski', 'Y', now()),
       (177, 'powiat oleski', 'Y', now()),
       (178, 'powiat strzelecki', 'Y', now()),
       (179, 'powiat złotowski', 'Y', now()),
       (180, 'powiat Suwałki', 'Y', now()),
       (181, 'powiat kościański', 'Y', now()),
       (182, 'powiat grodziski', 'Y', now()),
       (183, 'powiat szamotulski', 'Y', now()),
       (184, 'powiat Kielce', 'Y', now()),
       (185, 'powiat staszowski', 'Y', now()),
       (186, 'powiat ełcki', 'Y', now()),
       (187, 'powiat dzierżoniowski', 'Y', now()),
       (188, 'powiat tczewski', 'Y', now()),
       (189, 'powiat kołobrzeski', 'Y', now()),
       (190, 'powiat Koszalin', 'Y', now()),
       (191, 'powiat kłobucki', 'Y', now()),
       (192, 'powiat gliwicki', 'Y', now()),
       (193, 'powiat lubliniecki', 'Y', now()),
       (194, 'powiat Bielsko-Biała', 'Y', now()),
       (195, 'powiat Legnica', 'Y', now()),
       (196, 'powiat grudziądzki', 'Y', now()),
       (197, 'powiat wołowski', 'Y', now()),
       (198, 'powiat toruński', 'Y', now()),
       (199, 'powiat chełmiński', 'Y', now()),
       (200, 'powiat wodzisławski', 'Y', now()),
       (201, 'powiat leski', 'Y', now()),
       (202, 'powiat bieszczadzki', 'Y', now()),
       (203, 'powiat prudnicki', 'Y', now()),
       (204, 'powiat ząbkowicki', 'Y', now()),
       (205, 'powiat makowski', 'Y', now()),
       (206, 'powiat pszczyński', 'Y', now()),
       (207, 'powiat gołdapski', 'Y', now()),
       (208, 'powiat Sopot', 'Y', now()),
       (209, 'powiat rzeszowski', 'Y', now()),
       (210, 'powiat jeleniogórski', 'Y', now()),
       (211, 'powiat świdnicki', 'Y', now()),
       (212, 'powiat opoczyński', 'Y', now()),
       (213, 'powiat człuchowski', 'Y', now()),
       (214, 'powiat ostrowski', 'Y', now()),
       (215, 'powiat Kalisz', 'Y', now()),
       (216, 'powiat strzyżowski', 'Y', now()),
       (217, 'powiat miński', 'Y', now()),
       (218, 'powiat zwoleński', 'Y', now()),
       (219, 'powiat żuromiński', 'Y', now()),
       (220, 'powiat lubelski', 'Y', now()),
       (221, 'powiat proszowicki', 'Y', now()),
       (222, 'powiat wielicki', 'Y', now()),
       (223, 'powiat sieradzki', 'Y', now()),
       (224, 'powiat brzeziński', 'Y', now()),
       (225, 'powiat strzelecko-drezdenecki', 'Y', now()),
       (226, 'powiat świebodziński', 'Y', now()),
       (227, 'powiat leżajski', 'Y', now()),
       (228, 'powiat lubaczowski', 'Y', now()),
       (229, 'powiat chodzieski', 'Y', now()),
       (230, 'powiat międzychodzki', 'Y', now()),
       (231, 'powiat kluczborski', 'Y', now()),
       (232, 'powiat jędrzejowski', 'Y', now()),
       (233, 'powiat gostyński', 'Y', now()),
       (234, 'powiat kazimierski', 'Y', now()),
       (235, 'powiat kaliski', 'Y', now()),
       (236, 'powiat siemiatycki', 'Y', now()),
       (237, 'powiat gdański', 'Y', now()),
       (238, 'powiat szczycieński', 'Y', now()),
       (239, 'powiat łobeski', 'Y', now()),
       (240, 'powiat gryficki', 'Y', now()),
       (241, 'powiat myszkowski', 'Y', now()),
       (242, 'powiat oleśnicki', 'Y', now()),
       (243, 'powiat strzeliński', 'Y', now()),
       (244, 'powiat wąbrzeski', 'Y', now()),
       (245, 'powiat kętrzyński', 'Y', now()),
       (246, 'powiat białostocki', 'Y', now()),
       (247, 'powiat augustowski', 'Y', now()),
       (248, 'powiat wałbrzyski', 'Y', now()),
       (249, 'powiat kłodzki', 'Y', now()),
       (250, 'powiat Tychy', 'Y', now()),
       (251, 'powiat Rybnik', 'Y', now()),
       (252, 'powiat sokołowski', 'Y', now()),
       (253, 'powiat grójecki', 'Y', now()),
       (254, 'powiat lubartowski', 'Y', now()),
       (255, 'powiat puławski', 'Y', now()),
       (256, 'powiat radzyński', 'Y', now()),
       (257, 'powiat Biała Podlaska', 'Y', now()),
       (258, 'powiat Nowy Sącz', 'Y', now()),
       (259, 'powiat Gorzów Wielkopolski', 'Y', now()),
       (260, 'powiat pilski', 'Y', now()),
       (261, 'powiat wysokomazowiecki', 'Y', now()),
       (262, 'powiat buski', 'Y', now()),
       (263, 'powiat śremski', 'Y', now()),
       (264, 'powiat kępiński', 'Y', now()),
       (265, 'powiat turecki', 'Y', now()),
       (266, 'powiat opatowski', 'Y', now()),
       (267, 'powiat Słupsk', 'Y', now()),
       (268, 'powiat pińczowski', 'Y', now()),
       (269, 'powiat koszaliński', 'Y', now()),
       (270, 'powiat bielski', 'Y', now()),
       (271, 'powiat będziński', 'Y', now()),
       (272, 'powiat węgorzewski', 'Y', now()),
       (273, 'powiat bieruńsko-lędziński', 'Y', now()),
       (274, 'powiat policki', 'Y', now()),
       (275, 'powiat polkowicki', 'Y', now()),
       (276, 'powiat Jastrzębie-Zdrój', 'Y', now()),
       (277, 'powiat tatrzański', 'Y', now()),
       (278, 'powiat nowotarski', 'Y', now()),
       (279, 'powiat lwówecki', 'Y', now()),
       (280, 'powiat legionowski', 'Y', now()),
       (281, 'powiat olecki', 'Y', now()),
       (282, 'powiat tomaszowski', 'Y', now()),
       (283, 'powiat zambrowski', 'Y', now()),
       (284, 'powiat bytowski', 'Y', now()),
       (285, 'powiat płocki', 'Y', now()),
       (286, 'powiat pułtuski', 'Y', now()),
       (287, 'powiat sochaczewski', 'Y', now()),
       (288, 'powiat ciechanowski', 'Y', now()),
       (289, 'powiat bialski', 'Y', now()),
       (290, 'powiat krasnostawski', 'Y', now()),
       (291, 'powiat zamojski', 'Y', now()),
       (292, 'powiat tarnowski', 'Y', now()),
       (293, 'powiat bełchatowski', 'Y', now()),
       (294, 'powiat łaski', 'Y', now()),
       (295, 'powiat łęczycki', 'Y', now()),
       (296, 'powiat pajęczański', 'Y', now()),
       (297, 'powiat radomszczański', 'Y', now()),
       (298, 'powiat zduńskowolski', 'Y', now()),
       (299, 'powiat nowosolski', 'Y', now()),
       (300, 'powiat jarosławski', 'Y', now()),
       (301, 'powiat mielecki', 'Y', now()),
       (302, 'powiat Warszawa', 'Y', now()),
       (303, 'powiat krapkowicki', 'Y', now()),
       (304, 'powiat moniecki', 'Y', now()),
       (305, 'powiat kielecki', 'Y', now()),
       (306, 'powiat wrzesiński', 'Y', now()),
       (307, 'powiat gnieźnieński', 'Y', now()),
       (308, 'powiat malborski', 'Y', now()),
       (309, 'powiat lidzbarski', 'Y', now()),
       (310, 'powiat olsztyński', 'Y', now()),
       (311, 'powiat sławieński', 'Y', now()),
       (312, 'powiat działdowski', 'Y', now()),
       (313, 'powiat górowski', 'Y', now()),
       (314, 'powiat sztumski', 'Y', now()),
       (315, 'powiat kwidzyński', 'Y', now()),
       (316, 'powiat Dąbrowa Górnicza', 'Y', now()),
       (317, 'powiat Mysłowice', 'Y', now()),
       (318, 'powiat myśliborski', 'Y', now()),
       (319, 'powiat golubsko-dobrzyński', 'Y', now()),
       (320, 'powiat aleksandrowski', 'Y', now()),
       (321, 'powiat świecki', 'Y', now()),
       (322, 'powiat pleszewski', 'Y', now()),
       (323, 'powiat Krosno', 'Y', now()),
       (324, 'powiat sejneński', 'Y', now()),
       (325, 'powiat sanocki', 'Y', now()),
       (326, 'powiat suski', 'Y', now()),
       (327, 'powiat zielonogórski', 'Y', now()),
       (328, 'powiat wołomiński', 'Y', now()),
       (329, 'powiat mikołowski', 'Y', now()),
       (330, 'powiat goleniowski', 'Y', now()),
       (331, 'powiat Rzeszów', 'Y', now()),
       (332, 'powiat łomżyński', 'Y', now()),
       (333, 'powiat opolski', 'Y', now()),
       (334, 'powiat wałecki', 'Y', now()),
       (335, 'powiat drawski', 'Y', now()),
       (336, 'powiat chojnicki', 'Y', now()),
       (337, 'powiat przeworski', 'Y', now()),
       (338, 'powiat otwocki', 'Y', now()),
       (339, 'powiat kozienicki', 'Y', now()),
       (340, 'powiat janowski', 'Y', now()),
       (341, 'powiat limanowski', 'Y', now()),
       (342, 'powiat miechowski', 'Y', now()),
       (343, 'powiat myślenicki', 'Y', now()),
       (344, 'powiat olkuski', 'Y', now()),
       (345, 'powiat oświęcimski', 'Y', now()),
       (346, 'powiat Piotrków Trybunalski', 'Y', now()),
       (347, 'powiat poddębicki', 'Y', now()),
       (348, 'powiat wieluński', 'Y', now()),
       (349, 'powiat brzozowski', 'Y', now()),
       (350, 'powiat kędzierzyńsko-kozielski', 'Y', now()),
       (351, 'powiat krotoszyński', 'Y', now()),
       (352, 'powiat Białystok', 'Y', now()),
       (353, 'powiat wolsztyński', 'Y', now()),
       (354, 'powiat sandomierski', 'Y', now()),
       (355, 'powiat szczecinecki', 'Y', now()),
       (356, 'powiat świdwiński', 'Y', now()),
       (357, 'powiat piski', 'Y', now()),
       (358, 'powiat Świętochłowice', 'Y', now()),
       (359, 'powiat Zabrze', 'Y', now()),
       (360, 'powiat Olsztyn', 'Y', now()),
       (361, 'powiat braniewski', 'Y', now()),
       (362, 'powiat kamieński', 'Y', now()),
       (363, 'powiat Częstochowa', 'Y', now()),
       (364, 'powiat trzebnicki', 'Y', now()),
       (365, 'powiat bydgoski', 'Y', now()),
       (366, 'powiat oławski', 'Y', now()),
       (367, 'powiat średzki', 'Y', now()),
       (368, 'powiat legnicki', 'Y', now()),
       (369, 'powiat Bydgoszcz', 'Y', now()),
       (370, 'powiat Grudziądz', 'Y', now()),
       (371, 'powiat rypiński', 'Y', now()),
       (372, 'powiat gorlicki', 'Y', now()),
       (373, 'powiat lubański', 'Y', now()),
       (374, 'powiat wadowicki', 'Y', now()),
       (375, 'powiat ostrołęcki', 'Y', now()),
       (376, 'powiat Żory', 'Y', now()),
       (377, 'powiat nowodworski', 'Y', now()),
       (378, 'powiat jaworski', 'Y', now()),
       (379, 'powiat poznański', 'Y', now()),
       (380, 'powiat koniński', 'Y', now());

alter table t_zones
    add constraint pk_t_zones_zne_id
        primary key (zne_id)