insert into users(id, join_date, name,password, ssn ) values (9001, now(),'User1', 'test1111','1111-22222');
insert into users(id, join_date, name,password, ssn ) values (9002, now(),'User2', 'test2222','1111-33333');
insert into users(id, join_date, name,password, ssn ) values (9003, now(),'User3', 'test3333','1111-44444');


insert into post(description, user_id) values ('My first post',9001);
insert into post(description,  user_id) values ('My second post',9001);
-- jpa에 의해 post와 user 사이에 의존 관계가 성립되고 user (1) <-> pust (n) 의 관계로 설정되어 있다. user에서 기본키가 id이고, post에서 id가 기본키
--user.java에서 posts를 onetomany 로설정, 연결되는 필드명을 user로 함.
--Post에서는 User 빈을 참조하도록 선언되어 있고 이때는 ManyToOne(N:1)로 설정 되었습니다. 이 2개의 설정으로 User:Post의 설정은 1:N으로 설정되며,
--Post에서의 User 컬럼 참조키는 선언된 변수이름(user) + "_" + 기본키(id) = user_id 로 설정 됩니다.
--만약, Post에서의 User user 선언을 userTest로 하신다면, Post 테이블의 컬럼은 user_test_id로 변경될 것입니다.