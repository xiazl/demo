/** ------------ 映射hbase中已经存在的表  ---------------  */


/** 注意：新版本phoenix做了优化，老版本基于row映射查询不到数据，此处用试图方式映射  */
create table "cdn_logs"(
    id VARCHAR PRIMARY KEY,
    "USER"."IP" VARCHAR,
    "USER"."DATE_TIME" VARCHAR,
    "USER"."REFERER" VARCHAR,
    "USER"."TARGET_URL" VARCHAR,
    "USER"."CREATE_TIME" UNSIGNED_LONG
    )COLUMN_ENCODED_BYTES=0;


/** 二级索引  */
CREATE INDEX REFERER_IDX ON "cdn_logs"("REFERER");