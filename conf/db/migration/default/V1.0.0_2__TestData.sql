-- -----------------------------------------------------
-- Data for table `emojiban`.`USER`
-- -----------------------------------------------------
START TRANSACTION;
USE `emojiban`;
INSERT INTO `emojiban`.`USER` (`USER_ID`, `EMAIL`, `NAME`, `CREATED_DATETIME`) VALUES (1, 's.tadokoro0317+emoji1@gmail.com', 'Shunsuke Tadokoro', DEFAULT);
INSERT INTO `emojiban`.`USER` (`USER_ID`, `EMAIL`, `NAME`, `CREATED_DATETIME`) VALUES (2, 's.tadokoro0317+emoji2@gmail.com', 'Haggar', DEFAULT);
INSERT INTO `emojiban`.`USER` (`USER_ID`, `EMAIL`, `NAME`, `CREATED_DATETIME`) VALUES (3, 's.tadokoro0317+emoji3@gmail.com', 'Morizone', DEFAULT);

COMMIT;


-- -----------------------------------------------------
-- Data for table `emojiban`.`EMOJI`
-- -----------------------------------------------------
START TRANSACTION;
USE `emojiban`;
INSERT INTO `emojiban`.`EMOJI` (`EMOJI_ID`, `IMAGE_PATH`, `CREATED_DATETIME`, `USER_ID`) VALUES (1, '1.png', DEFAULT, 1);
INSERT INTO `emojiban`.`EMOJI` (`EMOJI_ID`, `IMAGE_PATH`, `CREATED_DATETIME`, `USER_ID`) VALUES (2, '2.png', DEFAULT, 1);
INSERT INTO `emojiban`.`EMOJI` (`EMOJI_ID`, `IMAGE_PATH`, `CREATED_DATETIME`, `USER_ID`) VALUES (3, '3.png', DEFAULT, 2);
INSERT INTO `emojiban`.`EMOJI` (`EMOJI_ID`, `IMAGE_PATH`, `CREATED_DATETIME`, `USER_ID`) VALUES (4, '4.png', DEFAULT, 3);
INSERT INTO `emojiban`.`EMOJI` (`EMOJI_ID`, `IMAGE_PATH`, `CREATED_DATETIME`, `USER_ID`) VALUES (5, '5.png', DEFAULT, 3);

COMMIT;


-- -----------------------------------------------------
-- Data for table `emojiban`.`NAME`
-- -----------------------------------------------------
START TRANSACTION;
USE `emojiban`;
INSERT INTO `emojiban`.`NAME` (`NAME`, `EMOJI_ID`) VALUES ('swimmy', 1);
INSERT INTO `emojiban`.`NAME` (`NAME`, `EMOJI_ID`) VALUES ('minamisan', 1);
INSERT INTO `emojiban`.`NAME` (`NAME`, `EMOJI_ID`) VALUES ('souichirou', 1);
INSERT INTO `emojiban`.`NAME` (`NAME`, `EMOJI_ID`) VALUES ('totti', 2);
INSERT INTO `emojiban`.`NAME` (`NAME`, `EMOJI_ID`) VALUES ('totti2', 3);
INSERT INTO `emojiban`.`NAME` (`NAME`, `EMOJI_ID`) VALUES ('akiramenai', 4);
INSERT INTO `emojiban`.`NAME` (`NAME`, `EMOJI_ID`) VALUES ('orz', 5);

COMMIT;


-- -----------------------------------------------------
-- Data for table `emojiban`.`EVALUATION`
-- -----------------------------------------------------
START TRANSACTION;
USE `emojiban`;
INSERT INTO `emojiban`.`EVALUATION` (`EVALUATION_ID`, `VALUE`, `EVALUATED_DATETIME`, `EMOJI_ID`) VALUES (1, 'G', DEFAULT, 1);
INSERT INTO `emojiban`.`EVALUATION` (`EVALUATION_ID`, `VALUE`, `EVALUATED_DATETIME`, `EMOJI_ID`) VALUES (2, 'G', DEFAULT, 1);
INSERT INTO `emojiban`.`EVALUATION` (`EVALUATION_ID`, `VALUE`, `EVALUATED_DATETIME`, `EMOJI_ID`) VALUES (3, 'G', DEFAULT, 2);
INSERT INTO `emojiban`.`EVALUATION` (`EVALUATION_ID`, `VALUE`, `EVALUATED_DATETIME`, `EMOJI_ID`) VALUES (4, 'B', DEFAULT, 3);
INSERT INTO `emojiban`.`EVALUATION` (`EVALUATION_ID`, `VALUE`, `EVALUATED_DATETIME`, `EMOJI_ID`) VALUES (5, 'B', DEFAULT, 4);
INSERT INTO `emojiban`.`EVALUATION` (`EVALUATION_ID`, `VALUE`, `EVALUATED_DATETIME`, `EMOJI_ID`) VALUES (6, 'G', DEFAULT, 5);

COMMIT;


-- -----------------------------------------------------
-- Data for table `emojiban`.`DOWNLOAD`
-- -----------------------------------------------------
START TRANSACTION;
USE `emojiban`;
INSERT INTO `emojiban`.`DOWNLOAD` (`DOWNLOAD_ID`, `DOWNLOAD_DATETIME`, `EMOJI_ID`) VALUES (1, DEFAULT, 1);
INSERT INTO `emojiban`.`DOWNLOAD` (`DOWNLOAD_ID`, `DOWNLOAD_DATETIME`, `EMOJI_ID`) VALUES (2, DEFAULT, 1);
INSERT INTO `emojiban`.`DOWNLOAD` (`DOWNLOAD_ID`, `DOWNLOAD_DATETIME`, `EMOJI_ID`) VALUES (3, DEFAULT, 2);
INSERT INTO `emojiban`.`DOWNLOAD` (`DOWNLOAD_ID`, `DOWNLOAD_DATETIME`, `EMOJI_ID`) VALUES (4, DEFAULT, 2);
INSERT INTO `emojiban`.`DOWNLOAD` (`DOWNLOAD_ID`, `DOWNLOAD_DATETIME`, `EMOJI_ID`) VALUES (5, DEFAULT, 4);
INSERT INTO `emojiban`.`DOWNLOAD` (`DOWNLOAD_ID`, `DOWNLOAD_DATETIME`, `EMOJI_ID`) VALUES (6, DEFAULT, 5);
