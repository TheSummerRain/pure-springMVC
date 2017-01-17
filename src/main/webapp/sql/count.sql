

-- mysql 递归查询。
-- http://blog.chinaunix.net/uid-20639775-id-3031821.html
SELECT * from user_info where pId is NOT null;
SELECT b.pId,b.user_id from user_info a INNER JOIN user_info b on a.user_id=b.pId WHERE a.user_id=11

-- set global log_bin_trust_function_creators=1;
-- show variables like 'log_bin_trust_function_creators';

SELECT group_concat(user_id) FROM user_info where FIND_IN_SET(pId,'43')>0;

SELECT getChildLst(11,0,1)
select * from user_info where find_in_set(user_id,getChildLst(11,4,1));
select * from user_info where find_in_set(user_id,getChildLst(129,4,2));


delimiter $$
DROP FUNCTION IF EXISTS getChildLst;
CREATE  FUNCTION `getChildLst`(rootId int,`LEVEL` INT,direction int) RETURNS varchar(1000) 
BEGIN
   DECLARE sTemp VARCHAR(1000);
   DECLARE sTempChd VARCHAR(1000);
	 DECLARE i INT DEFAULT 0 ;
   SET sTemp = '$';
   SET sTempChd =cast(rootId as CHAR);
   IF direction=1 THEN
		up_block:BEGIN
    WHILE sTempChd is not null  DO
				IF `LEVEL`=0 THEN
					SET i=0;
				END IF;
				IF i<=`LEVEL` THEN
					SET sTemp = concat(sTemp,',',sTempChd);
					SELECT group_concat(user_id) INTO sTempChd FROM user_info where FIND_IN_SET(pId,sTempChd)>0;
					SET i=i+1;
				ELSE
				LEAVE up_block;
				END IF;
    END WHILE;
		END up_block;
   ELSEIF direction=2 THEN
		down_block:BEGIN
    WHILE sTempChd is not null  DO
				IF `LEVEL`=0 THEN
					SET i=0;
				END IF;
				IF i<=`LEVEL` THEN
					SET sTemp = concat(sTemp,',',sTempChd);
					SELECT group_concat(pId) INTO sTempChd FROM user_info where  FIND_IN_SET(user_id,sTempChd)>0;
					SET i=i+1;
				ELSE
					LEAVE down_block;
				END IF;
    END WHILE;
		END down_block;
   END IF;
SET sTemp=REPLACE(sTemp,rootId,'');
RETURN sTemp;
END $$
delimiter ;
