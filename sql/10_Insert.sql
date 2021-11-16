-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.12-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table narrative.blueprints
CREATE TABLE IF NOT EXISTS `blueprints` (
  `id` varchar(100) NOT NULL,
  `adoption_rate` decimal(19,2) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `shared_date` datetime DEFAULT NULL,
  `stars` int(11) DEFAULT NULL,
  `story` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `share_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16;

-- Dumping data for table narrative.blueprints: 0 rows
/*!40000 ALTER TABLE `blueprints` DISABLE KEYS */;
/*!40000 ALTER TABLE `blueprints` ENABLE KEYS */;

-- Dumping structure for table narrative.chapters
CREATE TABLE IF NOT EXISTS `chapters` (
  `id` varchar(100) NOT NULL,
  `add_on_instructions` text DEFAULT NULL,
  `choose_one_instructions` text DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `required_instructions` text DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `story_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16;

-- Dumping data for table narrative.chapters: 3 rows
/*!40000 ALTER TABLE `chapters` DISABLE KEYS */;
INSERT INTO `chapters` (`id`, `add_on_instructions`, `choose_one_instructions`, `description`, `name`, `required_instructions`, `state`, `story_id`) VALUES
	('f9215432-8382-40ed-b758-a39557132ab9', '[]', '[{"name":"One-Time Transaction","description":"Set up one-time transaction","fields":[{"fieldName":"oneTimeTransaction","displayName":"One Time Transaction","placeholder":"","dataType":"BOOLEAN","value":"true","required":true,"mask":false,"maskedValue":""}],"state":"INACTIVE","displayFormat":""},{"name":"Scheduled Transaction","description":"Set up scheduled transaction","fields":[{"fieldName":"scheduledTransactionDate","displayName":"Scheduled Transaction Date","placeholder":"","dataType":"DATE","value":"24/12/2021","required":true,"mask":false,"maskedValue":""}],"state":"ACTIVE","displayFormat":""},{"name":"Recurring Transaction","description":"Set up recurring transaction","fields":[{"fieldName":"recurrenceStartRange","displayName":"Start","placeholder":"{today}","dataType":"DATE","value":"","required":true,"mask":false,"maskedValue":""},{"fieldName":"recurrenceEndChoice","displayName":"End","placeholder":"End By, End after, No end date","dataType":"RADIO","value":"","required":true,"mask":false,"maskedValue":""},{"fieldName":"recurrenceEndBy","displayName":"EndBy","placeholder":"","dataType":"DATE","value":"","required":false,"mask":false,"maskedValue":""},{"fieldName":"recurrenceEndAfterOccurences","displayName":"Occurences","placeholder":"1","dataType":"INT","value":"","required":false,"mask":false,"maskedValue":""},{"fieldName":"repeat","displayName":"Repeat","placeholder":"Daily,Weekly,Monthly,Yearly","dataType":"RADIO","value":"","required":true,"mask":false,"maskedValue":""},{"fieldName":"weekly","displayName":"Weekly","placeholder":"S,M,T,W,T,F,S","dataType":"RADIO","value":"","required":false,"mask":false,"maskedValue":""},{"fieldName":"monthly","displayName":"Monthly","placeholder":"","dataType":"DATE","value":"","required":false,"mask":false,"maskedValue":""},{"fieldName":"yearly","displayName":"Yearly","placeholder":"","dataType":"DATE","value":"","required":false,"mask":false,"maskedValue":""}],"state":"INACTIVE","displayFormat":""}]', 'Create a chapter to set up your payment', 'Payment', '[{"name":"Title","description":"Tell us more about this chapter","fields":[{"fieldName":"title","displayName":"Title","placeholder":"title of your chapter","dataType":"STRING","value":"HDB Payments","required":true,"mask":false,"maskedValue":""},{"fieldName":"description","displayName":"Description","placeholder":"description of your chapter","dataType":"STRING","value":"This are for my hdb payments","required":true,"mask":false,"maskedValue":""}],"state":"ACTIVE","displayFormat":""},{"name":"Amount","description":"Let us know the amount you wish to transfer","fields":[{"fieldName":"amount","displayName":"Amount","placeholder":"0","dataType":"DECIMAL","value":"1200","required":true,"mask":false,"maskedValue":""}],"state":"ACTIVE","displayFormat":""},{"name":"To Account","description":"Set up the account you wish to transfer to","fields":[{"fieldName":"toAccount","displayName":"To Account","placeholder":"412-61516-5","dataType":"STRING","value":"","required":true,"mask":true,"maskedValue":"XXX-XXXXX-X"}],"state":"ACTIVE","displayFormat":""}]', 'ACTIVE', NULL),
	('d91862c8-b748-4dfb-9fb5-92b465c6b48e', '[]', '[{"name":"One-Time Transaction","description":"Set up one-time transaction","fields":[{"fieldName":"oneTimeTransaction","displayName":"One Time Transaction","placeholder":"","dataType":"BOOLEAN","value":"true","required":true,"mask":false,"maskedValue":""}],"state":"INACTIVE","displayFormat":""},{"name":"Scheduled Transaction","description":"Set up scheduled transaction","fields":[{"fieldName":"scheduledTransactionDate","displayName":"Scheduled Transaction Date","placeholder":"","dataType":"DATE","value":"25/12/2021","required":true,"mask":false,"maskedValue":""}],"state":"ACTIVE","displayFormat":""},{"name":"Recurring Transaction","description":"Set up recurring transaction","fields":[{"fieldName":"recurrenceStartRange","displayName":"Start","placeholder":"{today}","dataType":"DATE","value":"","required":true,"mask":false,"maskedValue":""},{"fieldName":"recurrenceEndChoice","displayName":"End","placeholder":"End By, End after, No end date","dataType":"RADIO","value":"","required":true,"mask":false,"maskedValue":""},{"fieldName":"recurrenceEndBy","displayName":"EndBy","placeholder":"","dataType":"DATE","value":"","required":false,"mask":false,"maskedValue":""},{"fieldName":"recurrenceEndAfterOccurences","displayName":"Occurences","placeholder":"1","dataType":"INT","value":"","required":false,"mask":false,"maskedValue":""},{"fieldName":"repeat","displayName":"Repeat","placeholder":"Daily,Weekly,Monthly,Yearly","dataType":"RADIO","value":"","required":true,"mask":false,"maskedValue":""},{"fieldName":"weekly","displayName":"Weekly","placeholder":"S,M,T,W,T,F,S","dataType":"RADIO","value":"","required":false,"mask":false,"maskedValue":""},{"fieldName":"monthly","displayName":"Monthly","placeholder":"","dataType":"DATE","value":"","required":false,"mask":false,"maskedValue":""},{"fieldName":"yearly","displayName":"Yearly","placeholder":"","dataType":"DATE","value":"","required":false,"mask":false,"maskedValue":""}],"state":"INACTIVE","displayFormat":""}]', 'Create a chapter to set up your payment', 'Payment', '[{"name":"Title","description":"Tell us more about this chapter","fields":[{"fieldName":"title","displayName":"Title","placeholder":"title of your chapter","dataType":"STRING","value":"HDB Payments","required":true,"mask":false,"maskedValue":""},{"fieldName":"description","displayName":"Description","placeholder":"description of your chapter","dataType":"STRING","value":"This are for my hdb payments","required":true,"mask":false,"maskedValue":""}],"state":"ACTIVE","displayFormat":""},{"name":"Amount","description":"Let us know the amount you wish to transfer","fields":[{"fieldName":"amount","displayName":"Amount","placeholder":"0","dataType":"DECIMAL","value":"1200","required":true,"mask":false,"maskedValue":""}],"state":"ACTIVE","displayFormat":""},{"name":"To Account","description":"Set up the account you wish to transfer to","fields":[{"fieldName":"toAccount","displayName":"To Account","placeholder":"412-61516-5","dataType":"STRING","value":"","required":true,"mask":true,"maskedValue":"XXX-XXXXX-X"}],"state":"ACTIVE","displayFormat":""}]', 'ACTIVE', 'ee74aa21-a759-4402-ae5e-16006fc2597a'),
	('5a6eb04e-1387-4bf4-9326-7e62187fc434', '[]', '[{"name":"One-Time Transaction","description":"Set up one-time transaction","fields":[{"fieldName":"oneTimeTransaction","displayName":"One Time Transaction","placeholder":"","dataType":"BOOLEAN","value":"true","required":true,"mask":false,"maskedValue":""}],"state":"INACTIVE","displayFormat":""},{"name":"Scheduled Transaction","description":"Set up scheduled transaction","fields":[{"fieldName":"scheduledTransactionDate","displayName":"Scheduled Transaction Date","placeholder":"","dataType":"DATE","value":"true","required":true,"mask":false,"maskedValue":""}],"state":"INACTIVE","displayFormat":""},{"name":"Recurring Transaction","description":"Set up recurring transaction","fields":[{"fieldName":"recurrenceStartRange","displayName":"Start","placeholder":"{today}","dataType":"DATE","value":"","required":true,"mask":false,"maskedValue":""},{"fieldName":"recurrenceEndChoice","displayName":"End","placeholder":"End By, End after, No end date","dataType":"RADIO","value":"","required":true,"mask":false,"maskedValue":""},{"fieldName":"recurrenceEndBy","displayName":"EndBy","placeholder":"","dataType":"DATE","value":"","required":false,"mask":false,"maskedValue":""},{"fieldName":"recurrenceEndAfterOccurences","displayName":"Occurences","placeholder":"1","dataType":"INT","value":"","required":false,"mask":false,"maskedValue":""},{"fieldName":"repeat","displayName":"Repeat","placeholder":"Daily,Weekly,Monthly,Yearly","dataType":"RADIO","value":"Weekly","required":true,"mask":false,"maskedValue":""},{"fieldName":"weekly","displayName":"Weekly","placeholder":"S,M,T,W,T,F,S","dataType":"RADIO","value":"M,T,W","required":false,"mask":false,"maskedValue":""},{"fieldName":"monthly","displayName":"Monthly","placeholder":"","dataType":"DATE","value":"","required":false,"mask":false,"maskedValue":""},{"fieldName":"yearly","displayName":"Yearly","placeholder":"","dataType":"DATE","value":"","required":false,"mask":false,"maskedValue":""}],"state":"ACTIVE","displayFormat":""}]', 'Create a chapter to set up your saving', 'Saving', '[{"name":"Title","description":"Tell us more about this chapter","fields":[{"fieldName":"title","displayName":"Title","placeholder":"title of your chapter","dataType":"STRING","value":"Saving for my HDB","required":true,"mask":false,"maskedValue":""},{"fieldName":"description","displayName":"Description","placeholder":"description of your chapter","dataType":"STRING","value":"Monthly money into my hdb","required":true,"mask":false,"maskedValue":""}],"state":"ACTIVE","displayFormat":""},{"name":"Amount","description":"Let us know the amount you wish to transfer","fields":[{"fieldName":"amount","displayName":"Amount","placeholder":"0","dataType":"DECIMAL","value":"1000","required":true,"mask":false,"maskedValue":""}],"state":"ACTIVE","displayFormat":""},{"name":"From Account","description":"Set up the account you wish to transfer from","fields":[{"fieldName":"fromAccount","displayName":"From Account","placeholder":"XXX-XXXXX-X","dataType":"STRING","value":"521-89597-2","required":true,"mask":true,"maskedValue":"XXX-XXXXX-X"}],"state":"ACTIVE","displayFormat":""}]', 'ACTIVE', 'ee74aa21-a759-4402-ae5e-16006fc2597a');
/*!40000 ALTER TABLE `chapters` ENABLE KEYS */;

-- Dumping structure for table narrative.fields
CREATE TABLE IF NOT EXISTS `fields` (
  `id` varchar(100) NOT NULL,
  `data_type` varchar(255) DEFAULT NULL,
  `display_name` varchar(255) DEFAULT NULL,
  `field_name` varchar(255) DEFAULT NULL,
  `mask` bit(1) DEFAULT NULL,
  `masked_value` varchar(255) DEFAULT NULL,
  `placeholder` varchar(255) DEFAULT NULL,
  `required` bit(1) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16;

-- Dumping data for table narrative.fields: 15 rows
/*!40000 ALTER TABLE `fields` DISABLE KEYS */;
INSERT INTO `fields` (`id`, `data_type`, `display_name`, `field_name`, `mask`, `masked_value`, `placeholder`, `required`, `value`) VALUES
	('e2702a87-7271-4880-8ed2-f1293212863d', 'STRING', 'Title', 'title', b'0', '', 'title of your chapter', b'1', ''),
	('e2702a83-7272-4880-8ed2-f1293212863d', 'STRING', 'Description', 'description', b'0', 'this is a sample description', 'description of your chapter', b'1', ''),
	('e2702a83-7273-4880-8ed2-f1293212863d', 'DECIMAL', 'Amount', 'amount', b'0', '0', '0', b'1', ''),
	('e2702a82-7274-4880-8ed2-f1293212863d', 'STRING', 'From Account', 'fromAccount', b'1', 'XXX-XXXXX-X', 'XXX-XXXXX-X', b'1', ''),
	('e2702a81-7275-4880-8ed2-f1293212863d', 'STRING', 'To Account', 'toAccount', b'1', 'XXX-XXXXX-X', 'XXX-XXXXX-X', b'1', ''),
	('e2702a87-7276-4880-8ed2-f1293212863d', 'BOOLEAN', 'One Time Transaction', 'oneTimeTransaction', b'0', '', '', b'1', 'true'),
	('e2702a87-7277-4889-8ed2-f1293212863d', 'DATE', 'Scheduled Transaction Date', 'scheduledTransactionDate', b'0', '', '', b'1', 'true'),
	('e2702a84-7278-4888-8ed2-f1293212863d', 'RADIO', 'Repeat', 'repeat', b'0', '', 'Daily,Weekly,Monthly,Yearly', b'1', ''),
	('e2702a85-7279-4887-8ed2-f1293212863d', 'CHECKBOX', 'Weekly', 'weekly', b'0', '', 'S,M,T,W,T,F,S', b'0', ''),
	('e2702a81-7270-4886-8ed2-f1293212863d', 'DATE', 'Monthly', 'monthly', b'0', '', '', b'0', ''),
	('e2702a82-7276-4881-8ed2-f1293212863d', 'DATE', 'Yearly', 'yearly', b'0', '', '', b'0', ''),
	('e2702a83-7276-4882-8ed2-f1293212863d', 'DATE', 'Start', 'recurrenceStartRange', b'0', '', '{today}', b'1', ''),
	('e2702a87-7276-4883-8ed2-f1293212863d', 'RADIO', 'End', 'recurrenceEndChoice', b'0', '', 'End By, End after, No end date', b'1', ''),
	('e2702a87-7276-4884-8ed2-f1293212863d', 'DATE', 'EndBy', 'recurrenceEndBy', b'0', '', '', b'0', ''),
	('e2702a87-7276-4885-8ed2-f1293212863d', 'INT', 'Occurences', 'recurrenceEndAfterOccurences', b'0', '', '1', b'0', '');
/*!40000 ALTER TABLE `fields` ENABLE KEYS */;

-- Dumping structure for table narrative.genre
CREATE TABLE IF NOT EXISTS `genre` (
  `id` varchar(100) NOT NULL,
  `add_on_instructions` varchar(255) DEFAULT NULL,
  `choose_one_instructions` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `required_instructions` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16;

-- Dumping data for table narrative.genre: 3 rows
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
INSERT INTO `genre` (`id`, `add_on_instructions`, `choose_one_instructions`, `description`, `name`, `required_instructions`) VALUES
	('1325be04-b4d7-4d39-a21d-2260b5c5ff1d', '[]', '["One-Time Transaction","Scheduled Transaction","Recurring Transaction"]', 'Create a chapter to set up your saving', 'Saving', '["Title","Amount","From Account"]'),
	('1555be04-b4d7-4d39-a21d-2260b5c5ff1d', '[]', '["One-Time Transaction","Scheduled Transaction","Recurring Transaction"]', 'Create a chapter to set up your payment', 'Payment', '["Title","Amount","To Account"]'),
	('1565be04-b4d7-4d39-a21d-2260b5c5ff1narratived', '[]', '["One-Time Transaction","Scheduled Transaction","Recurring Transaction"]', 'Create a chapter to set up your transfer', 'Transfer', '["Title","Amount","To Account"]');
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;

-- Dumping structure for table narrative.instructions
CREATE TABLE IF NOT EXISTS `instructions` (
  `id` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `display_format` varchar(255) DEFAULT NULL,
  `fields` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16;

-- Dumping data for table narrative.instructions: 7 rows
/*!40000 ALTER TABLE `instructions` DISABLE KEYS */;
INSERT INTO `instructions` (`id`, `description`, `display_format`, `fields`, `name`) VALUES
	('09fd585c-aa2d-4822-b3d1-744b33e54d24', 'Tell us more about this chapter', '', '["title", "description"]', 'Title'),
	('09fd523c-aa2d-4822-b3d1-744b33e54d24', 'Let us know the amount you wish to transfer', '', '["amount"]', 'Amount'),
	('09fd543c-aa2d-4822-b3d1-744b33e54d24', 'Set up the account you wish to transfer to', '', '["toAccount"]', 'To Account'),
	('09fd553c-aa2d-4822-b3d1-744b33e54d24', 'Set up the account you wish to transfer from', '', '["fromAccount"]', 'From Account'),
	('09fd572c-aa2d-4822-b3d1-744b33e54d24', 'Set up one-time transaction', '', '["oneTimeTransaction"]', 'One-Time Transaction'),
	('09fd541c-aa2d-4822-b3d1-744b33e54d24', 'Set up scheduled transaction', '', '["scheduledTransactionDate"]', 'Scheduled Transaction'),
	('09fd547c-aa2d-4822-b3d1-744b33e54d24', 'Set up recurring transaction', '', '["recurrenceStartRange", "recurrenceEndChoice","recurrenceEndBy","recurrenceEndAfterOccurences","repeat","weekly","monthly","yearly"]', 'Recurring Transaction');
/*!40000 ALTER TABLE `instructions` ENABLE KEYS */;

-- Dumping structure for table narrative.stories
CREATE TABLE IF NOT EXISTS `stories` (
  `id` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `virtual_account` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16;

-- Dumping data for table narrative.stories: 1 rows
/*!40000 ALTER TABLE `stories` DISABLE KEYS */;
INSERT INTO `stories` (`id`, `description`, `icon`, `name`, `user_id`, `virtual_account`) VALUES
	('ee74aa21-a759-4402-ae5e-16006fc2597a', 'This is a story on how I saved for my first HDB!', 'HDB', 'Save my first HDB Story', '1525be04-b4d7-4d39-a21d-2260b5c5ff1d', '375-36830-6');
/*!40000 ALTER TABLE `stories` ENABLE KEYS */;

-- Dumping structure for table narrative.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` varchar(100) NOT NULL,
  `access_code` varchar(255) DEFAULT NULL,
  `master_account` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `roles` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16;

-- Dumping data for table narrative.users: 2 rows
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `access_code`, `master_account`, `name`, `password`, `permission`, `roles`) VALUES
	('1525be04-b4d7-4d39-a21d-2260b5c5ff1d', '0451931', '521-89597-2', 'Kaiyang', '$2a$10$3juMG1SoyF2qlBYX5T0TN.zeZ2LO3SiAqt64ZefrQm69be6mzXFe.', 'ACCESS_USERS', 'ADMIN'),
	('f5fae37d-e9f8-47af-96b7-6c3f8d5b75eb', '0451932', '521-12445-2', 'Johann', '$2a$10$3juMG1SoyF2qlBYX5T0TN.zeZ2LO3SiAqt64ZefrQm69be6mzXFe.', 'ACCESS_USERS', 'ADMIN');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
