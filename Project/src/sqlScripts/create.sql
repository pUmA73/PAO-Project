CREATE TABLE `auctionsapp_schema`.`user` (
    `userId` INT AUTO_INCREMENT,
    `firstName` VARCHAR(50) NULL,
    `lastName` VARCHAR(50) NULL,
    `email` VARCHAR(50) NULL,
    `password` VARCHAR(60) NULL,
    `rating` INT NULL,
    PRIMARY KEY (`userId`)
);

CREATE TABLE `auctionsapp_schema`.`vehicle` (
    `vehicleId` INT AUTO_INCREMENT,
    `make` VARCHAR(50),
    `model` VARCHAR(50),
    `productionYear` INT,
    `engineCapcaity` DOUBLE,
    `engineConfiguration` VARCHAR(50),
    `power` INT,
    `torque` INT,
    `color` VARCHAR(50),
    `accidentFree` BOOLEAN,
    PRIMARY KEY (`vehicleId`)
);

CREATE TABLE `auctionsapp_schema`.`car` (
    `carId` INT AUTO_INCREMENT,
    `vehicleId` INT,
    `bodyType` VARCHAR(50),
    `gearboxType` VARCHAR(50),
    `driveType` VARCHAR(50),
    PRIMARY KEY (`carId`),
    FOREIGN KEY (`vehicleId`) REFERENCES `vehicle`(`vehicleId`)
);

CREATE TABLE `auctionsapp_schema`.`motorcycle` (
    `motorcycleId` INT AUTO_INCREMENT,
    `vehicleId` INT,
    `category` VARCHAR(50),
    `hasQuickshifter` VARCHAR(50),
    `hasABS` VARCHAR(50),
    PRIMARY KEY (`motorcycleId`),
    FOREIGN KEY (`vehicleId`) REFERENCES `vehicle`(`vehicleId`)
);

CREATE TABLE `auctionsapp_schema`.`auction` (
    `auctionId` INT AUTO_INCREMENT,
    `vehicleId` INT,
    `description` TEXT,
    `startingPrice` DOUBLE,
    `highestBid` DOUBLE,
    `startTime` DATE NOT NULL,
    `finishTime` DATE NOT NULL,
    PRIMARY KEY (`auctionId`),
    FOREIGN KEY (`vehicleId`) REFERENCES `vehicle`(`vehicleId`)
);

CREATE TABLE `auctionsapp_schema`.`offer` (
    `offerId` INT AUTO_INCREMENT,
    `buyerId` INT,
    `auctionId` INT,
    `bid` DOUBLE,
    `offerTime` DATE NOT NULL,
    PRIMARY KEY (`offerId`),
    FOREIGN KEY (`buyerId`) REFERENCES `user`(`userId`),
    FOREIGN KEY (`auctionId`) REFERENCES `auction`(`auctionId`)
);

CREATE TABLE `auctionsapp_schema`.`transaction` (
    `transactionId` INT AUTO_INCREMENT,
    `buyerId` INT,
    `sellerId` INT,
    `vehicleId` INT,
    `finalPrice` DOUBLE,
    PRIMARY KEY (`transactionId`),
    FOREIGN KEY (`buyerId`) REFERENCES `user`(`userId`),
    FOREIGN KEY (`sellerId`) REFERENCES `user`(`userId`),
    FOREIGN KEY (`vehicleId`) REFERENCES `auction`(`auctionId`)
)