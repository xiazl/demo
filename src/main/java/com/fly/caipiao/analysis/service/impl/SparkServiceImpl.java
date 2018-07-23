package com.fly.caipiao.analysis.service.impl;

import com.fly.caipiao.analysis.service.SparkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.stereotype.Service;

/**
 * @author baidu
 * @date 2018/7/8 下午3:53
 * @description ${TODO}
 **/

@Service("sparkService")
public class SparkServiceImpl implements SparkService {
    private static final Logger LOGGER  = LoggerFactory.getLogger(SparkServiceImpl.class);
    private static final String COLLECTION_NAME = "user";

    @Autowired
    private MongoProperties mongoProperties;


    @Override
    public void load() {
//        String uri = "mongodb://"+mongoProperties.getHost()+ File.separator+
//                mongoProperties.getDatabase()+"."+COLLECTION_NAME;
//        SparkSession sparkSession = SparkSession.builder()
//                .master("local")
//                .appName("MongoSparkConnectorIntro")
//                .config("spark.mongodb.input.uri", uri)
//                .config("spark.mongodb.output.uri", uri)
//                .getOrCreate();
//
//        // Create a JavaSparkContext using the SparkSession's SparkContext object
//        JavaSparkContext jsc = new JavaSparkContext(sparkSession.sparkContext());
//
//        // Load data and infer schema, disregard toDF() name as it returns Dataset
//        Dataset<Row> implicitDS = MongoSpark.load(jsc).toDF();
//        implicitDS.printSchema();
//        implicitDS.show();
//
//        // Load data with explicit schema
//        Dataset<CDNLogEntity> explicitDS = MongoSpark.load(jsc).toDS(CDNLogEntity.class);
//        explicitDS.printSchema();
//        explicitDS.show();
//
//        // Create the temp view and execute the query
//        explicitDS.createOrReplaceTempView("users");
//        Dataset<Row> centenarians = sparkSession.sql("select target_url as name,count(1) as 'count' from data_log" +
//                " group by target_url order by `count` desc");
//        centenarians.show();
//        centenarians.cube();
//        JavaRDD<Row> javaRDD = centenarians.javaRDD();
//        JavaRDD<VisitVO> result = javaRDD.map(new Function<Row,VisitVO>(){
//
//            private static final long serialVersionUID = 1L;
//
//            @Override
//            public VisitVO call(Row row) throws Exception {
//                //返回具体每条记录
//                VisitVO entity = new VisitVO();
//
//                entity.setCount(row.getString(1));
//                entity.setName(row.getString(0));
//
//                return entity;
//            }
//
//        });
//
//        List<VisitVO> list = result.collect();
//
//        // Write the data to the "hundredClub" collection
//        MongoSpark.write(centenarians).option("collection", "hundredClub").mode("overwrite").save();
//
//        // Load the data from the "hundredClub" collection
//        MongoSpark.load(sparkSession, ReadConfig.create(sparkSession).withOption("collection", "hundredClub"), Character.class).show();
//
//        jsc.close();
    }

//    @Override
//    public void save() {
//        String uri = "mongodb://"+mongoProperties.getHost()+ File.separator+
//                mongoProperties.getDatabase()+"."+COLLECTION_NAME;
//        SparkSession spark = SparkSession.builder()
//                .master("local")
//                .appName("MongoSparkConnectorIntro")
//                .config("spark.mongodb.input.uri", uri)
//                .config("spark.mongodb.output.uri", uri)
//                .getOrCreate();
//
//        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
//
//        List<CDNLogEntity> logEntityList = new ArrayList<>();
//        CDNLogEntity entity = new CDNLogEntity();
//        entity.setDateTime("2018-07-09");
//        entity.setReferer("http://test.com");
//        entity.setTargetUrl("http://test.com");
//        entity.setIp("192.168.11.110");
//        entity.setId();
//
//        logEntityList.add(entity);
//
//        // Create a RDD of 10 documents
//        JavaRDD<CDNLogEntity> sparkDocuments  = jsc.parallelize(logEntityList);
//        /*Start Example: Save data from RDD to MongoDB*****************/
//        MongoSpark.save(sparkDocuments , CDNLogEntity.class);
//        /*End Example**************************************************/
//
//        jsc.close();
//    }

    @Override
    public void save() {
//        String uri = "mongodb://"+mongoProperties.getHost()+ File.separator+
//                mongoProperties.getDatabase()+"."+COLLECTION_NAME;
//        SparkSession spark = SparkSession.builder()
//                .master("local")
//                .appName("MongoSparkConnectorIntro")
//                .config("spark.mongodb.input.uri", "mongodb://127.0.0.1/test.user")
//                .config("spark.mongodb.output.uri", "mongodb://127.0.0.1/test.user")
//                .getOrCreate();
//
//        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
//
//        Map<String, String> writeOverrides = new HashMap<String, String>();
//        writeOverrides.put("collection", "spark");
//        writeOverrides.put("writeConcern.w", "majority");
//        WriteConfig writeConfig = WriteConfig.create(jsc).withOptions(writeOverrides);
//
//        // Create a RDD of 10 documents
//        JavaRDD<Document> sparkDocuments = jsc.parallelize(asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)).map
//                (new Function<Integer, Document>() {
//                    public Document call(final Integer i) throws Exception {
//                        return Document.parse("{spark: " + i + "}");
//                    }
//                });
//
//        /*Start Example: Save data from RDD to MongoDB*****************/
//        MongoSpark.save(sparkDocuments, writeConfig);
//
//        jsc.close();
    }

}
