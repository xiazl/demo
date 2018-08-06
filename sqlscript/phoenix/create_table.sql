/** ------------ 映射hbase中已经存在的表  ---------------  */


create table "cdn_logs"(
    id VARCHAR PRIMARY KEY,
    "USER"."IP" VARCHAR,
    "USER"."DATE_TIME" VARCHAR,
    "USER"."REFERER" VARCHAR,
    "USER"."TARGET_URL" VARCHAR,
    "USER"."CREATE_TIME" UNSIGNED_LONG
    )COLUMN_ENCODED_BYTES=0;


/** 二级索引  */
CREATE INDEX IDX_CREATE_TIME ON "cdn_logs"("CREATE_TIME");