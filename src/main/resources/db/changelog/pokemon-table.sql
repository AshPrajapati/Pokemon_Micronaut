--liquibase formatted sql

--changeset example-user:pokemon-table

CREATE TABLE `pokemon` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `power` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);

--changeset example-user:added image_url
ALTER TABLE `pokemon`
ADD COLUMN `image_url` VARCHAR(45) NULL AFTER `power`;

--changeset example-user:changed data type of power column
ALTER TABLE `pokemon`
CHANGE COLUMN `power` `power` INT NULL DEFAULT NULL ;

--changeset example-user:delete power column
ALTER TABLE `pokemon`
DROP COLUMN `power`;

--changeset example-user:power table added
CREATE TABLE `power` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);

--changeset example-user:power_column_in_pokemon_table
ALTER TABLE `pokemon`
ADD COLUMN `power` INT NULL AFTER `image_url`;

--changeset example-user:power_fk
ALTER TABLE `pokemon`
ADD INDEX `power_fk_idx` (`power` ASC) VISIBLE;


--changeset example-user:referencing
ALTER TABLE `pokemon`
ADD CONSTRAINT `power_fk`
  FOREIGN KEY (`power`)
  REFERENCES `pokemon`.`power` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
