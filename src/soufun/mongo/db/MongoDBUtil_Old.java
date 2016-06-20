package soufun.mongo.db;

import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;

public class MongoDBUtil_Old {

	//private MongoClient mongoClient = null;
	static String mongodbConnStr = "mongodb://szg_user:123456@localhost:27017/szgDB";
	private  DB db;
	
	/**
	 * 连接数据库
	 * 
	 * @param name
	 * @param databaseName
	 * @param pswd
	 * @return
	 */
	public  MongoDBUtil_Old() {

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
		db = mongoClient.getDB("szgDB");
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

	public  boolean add(String collectionName, List<DBObject> list) {
		DBCollection collection = db.getCollection(collectionName);
		return collection.insert(list).getN() > 0;
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
	public  boolean update(String collectionName, DBObject source, DBObject target) {
		DBCollection collection =db.getCollection(collectionName);
		/**
		 * true,//如果数据库不存在，是否添加 false//多条修改
		 */
		return collection.update(source, target, true, false).getN()>0;
	}
	/**
     * 
        * \brief 删除
        * @param collectionName
        * @param obj
        * @attention 方法的使用注意事项 
        * @note  begin modify by 修改人 修改时间   修改内容摘要说明
     */
    public  boolean delete(String collectionName, DBObject delObj) {
        DBCollection collection = db.getCollection(collectionName);
        return collection.remove(delObj).getN()>0;  
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
    public  List<DBObject>  queryWithCondition(String collectionName, DBObject condition,int pagesize,int skipNum,DBObject orderby) {
        DBCollection collection = db.getCollection(collectionName);
        DBCursor cur =collection.find(condition).skip(skipNum).limit(pagesize).sort(orderby); 
        return cur.toArray();
    }
    
    /**
     * 获取总条数
     * @param collectionName
     * @param condition
     * @return
     */
    public  int getTotalCount(String collectionName, DBObject condition){
    	DBCollection collection = db.getCollection(collectionName);
    	return collection.find(condition).count();
    }
    
    /**
     * 
     * 查询所有数据
     * 
     * @attention 方法的使用注意事项
     * @note begin modify by 修改人 修改时间 修改内容摘要说明
     */
    public   void queryAll(String collectionName) {
        DBCollection collection =db.getCollection(collectionName);
        // db游标
        DBCursor cur = collection.find();
        System.out.println("数据总条数:" + collection.count());
        while (cur.hasNext()) {
            System.out.println(cur.next());
        }
    }
}
