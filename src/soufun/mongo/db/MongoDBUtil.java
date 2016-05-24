package soufun.mongo.db;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public class MongoDBUtil {

	//private MongoClient mongoClient = null;
	
//	static String mongodbConnStr = "mongodb://szg_user:123456@localhost:27017/szgDB";
	private  String mongodbConnStr=""; 
	private  MongoDatabase db;
	
	/**
	 * 连接数据库
	 */
	public  MongoDBUtil(String mongodbConnStr,String dbName) {
		this.mongodbConnStr=mongodbConnStr;//连接字符串赋值

		MongoClientOptions.Builder build = new MongoClientOptions.Builder();  
        //与数据最大连接数50  
        build.connectionsPerHost(50);  
        //如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待  
        build.threadsAllowedToBlockForConnectionMultiplier(50);  
        build.connectTimeout(1*60*1000);  
        build.maxWaitTime(2*60*1000);  
        MongoClientOptions options = build.build();  
        MongoClient mongoClient = null;  
		MongoClientURI uri = new MongoClientURI(mongodbConnStr);
		mongoClient = new MongoClient(uri);
		db = mongoClient.getDatabase(dbName);
	}


	/**
	 * 
	 * 添加数据
	 * 
	 * @param collectionName
	 * @attention 方法的使用注意事项
	 * @note begin modify by 修改人 修改时间 修改内容摘要说明 save和insert的区别
	 *       save函数实际就是根据参数条件,调用了insert或update函数.
	 *       如果想插入的数据对象存在,insert函数会报错,而save函数是改变原来的对象;
	 *       如果想插入的对象不存在,那么它们执行相同的插入操作. 这里可以用几个字来概括它们两的区别,即所谓"有则改之,无则加之".
	 */

	public  void add(String collectionName, List<Document> list) {
		MongoCollection<Document> collection = db.getCollection(collectionName);
		collection.insertMany(list);
	}
	
	public  void add(String collectionName, Document doc) {
		MongoCollection<Document> collection = db.getCollection(collectionName);
		collection.insertOne(doc);
	}

	/**
	 * 
	 * 更新
	 * 
	 * @param collectionName
	 * @param source
	 * @param target
	 * @attention 方法的使用注意事项
	 * @note begin modify by 修改人 修改时间 修改内容摘要说明
	 */
	public  boolean update(String collectionName, Bson source, Bson target) {
		MongoCollection<Document> collection =db.getCollection(collectionName);
		UpdateResult result=collection.updateMany(source,target);
		return result.getModifiedCount()>0;
	}
	/**
     * 
        * 删除
        * @param collectionName
        * @param obj
        * @attention 方法的使用注意事项 
        * @note  begin modify by 修改人 修改时间   修改内容摘要说明
     */
    public  boolean delete(String collectionName, Bson delObj) {
    	MongoCollection<Document> collection = db.getCollection(collectionName);
        DeleteResult result=collection.deleteMany(delObj);
        return result.getDeletedCount()>0;
    }
    /**
     * 
        * \brief 带条件查询
     * @param <T>
        * @param collectionName
        * @param delObj
        * @attention 方法的使用注意事项 
        * @note  begin modify by 修改人 修改时间   修改内容摘要说明
     */
    public  MongoCursor<Document>  queryWithCondition(String collectionName, Bson condition,int pagesize,int skipNum,Bson orderby) {
        MongoCollection<Document> collection = db.getCollection(collectionName);
        MongoCursor<Document> cur =collection.find(condition).skip(skipNum).limit(pagesize).sort(orderby).iterator(); 
        return cur;
    }
    
    /**
     * 
        *  查询 不排序
     * @param <T>
        * @param collectionName
        * @param delObj
        * @attention 方法的使用注意事项 
        * @note  begin modify by 修改人 修改时间   修改内容摘要说明
     */
    public  MongoCursor<Document>  queryNoOrder(String collectionName, Bson condition,int pagesize,int skipNum) {
        MongoCollection<Document> collection = db.getCollection(collectionName);
        MongoCursor<Document> cur =collection.find(condition).skip(skipNum).limit(pagesize).iterator(); 
        return cur;
    }
    
    /**
     * 获取总条数
     * @param collectionName
     * @param condition
     * @return
     */
    public  int getTotalCount(String collectionName, Bson condition){
    	MongoCollection<Document> collection = db.getCollection(collectionName);
    	return (int)collection.count(condition);
    }
    
    /**
     * 
     * 查询所有数据
     * 
     * @attention 方法的使用注意事项
     * @note begin modify by 修改人 修改时间 修改内容摘要说明
     */
    public  MongoCursor<Document> queryAll(String collectionName) {
        MongoCollection<Document> collection =db.getCollection(collectionName);
        // db游标
        MongoCursor<Document> cur = collection.find().iterator();
        return cur;
       
    }
}
