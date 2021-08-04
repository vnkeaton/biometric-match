DROP TABLE IF EXISTS idsl_image;

create table idsl_image (
id varchar(255) not null, 
data longblob, 
file_name varchar(255), 
file_type varchar(255), 
size bigint(12), 
primary key (id)) 
engine=InnoDB;

DROP TABLE IF EXISTS idsl_match_scores;

create table idsl_match_scores (
id bigint auto_increment, 
file_1_id varchar(255),
file_2_id varchar(255),
match_score decimal(22,12),
primary key (id)) 
foreign key (file_1_id) references idsl_image(id)
foreign key (file_2_id) references idsl_image(id)
engine=InnoDB;