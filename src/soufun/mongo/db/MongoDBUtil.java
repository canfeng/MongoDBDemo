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
	 * �������ݿ�
	 */
	public  MongoDBUtil(String mongodbConnStr,String dbName) {
		this.mongodbConnStr=mongodbConnStr;//�����ַ�����ֵ

		MongoClientOptions.Builder build = new MongoClientOptions.Builder();  
        //���������������50  
        build.connectionsPerHost(50);  
        //�����ǰ���е�connection����ʹ���У���ÿ��connection�Ͽ�����50���߳��Ŷӵȴ�  
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
	 * �������
	 * 
	 * @param collectionName
	 * @attention ������ʹ��ע������
	 * @note begin modify by �޸��� �޸�ʱ�� �޸�����ժҪ˵�� save��insert������
	 *       save����ʵ�ʾ��Ǹ��ݲ�������,������insert��update����.
	 *       ������������ݶ������,insert�����ᱨ��,��save�����Ǹı�ԭ���Ķ���;
	 *       ��������Ķ��󲻴���,��ô����ִ����ͬ�Ĳ������. ��������ü�����������������������,����ν"�����֮,�����֮".
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
	 * ����
	 * 
	 * @param collectionName
	 * @param source
	 * @param target
	 * @attention ������ʹ��ע������
	 * @note begin modify by �޸��� �޸�ʱ�� �޸�����ժҪ˵��
	 */
	public  boolean update(String collectionName, Bson source, Bson target) {
		MongoCollection<Document> collection =db.getCollection(collectionName);
		UpdateResult result=collection.updateMany(source,target);
		return result.getModifiedCount()>0;
	}
	/**
     * 
        * ɾ��
        * @param collectionName
        * @param obj
        * @attention ������ʹ��ע������ 
        * @note  begin modify by �޸��� �޸�ʱ��   �޸�����ժҪ˵��
     */
    public  boolean delete(String collectionName, Bson delObj) {
    	MongoCollection<Document> collection = db.getCollection(collectionName);
        DeleteResult result=collection.deleteMany(delObj);
        return result.getDeletedCount()>0;
    }
    /**
     * 
        * \brief ��������ѯ
     * @param <T>
        * @param collectionName
        * @param delObj
        * @attention ������ʹ��ע������ 
        * @note  begin modify by �޸��� �޸�ʱ��   �޸�����ժҪ˵��
     */
    public  MongoCursor<Document>  queryWithCondition(String collectionName, Bson condition,int pagesize,int skipNum,Bson orderby) {
        MongoCollection<Document> collection = db.getCollection(collectionName);
        MongoCursor<Document> cur =collection.find(condition).skip(skipNum).limit(pagesize).sort(orderby).iterator(); 
        return cur;
    }
    
    /**
     * 
        *  ��ѯ ������
     * @param <T>
        * @param collectionName
        * @param delObj
        * @attention ������ʹ��ע������ 
        * @note  begin modify by �޸��� �޸�ʱ��   �޸�����ժҪ˵��
     */
    public  MongoCursor<Document>  queryNoOrder(String collectionName, Bson condition,int pagesize,int skipNum) {
        MongoCollection<Document> collection = db.getCollection(collectionName);
        MongoCursor<Document> cur =collection.find(condition).skip(skipNum).limit(pagesize).iterator(); 
        return cur;
    }
    
    /**
     * ��ȡ������
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
     * ��ѯ��������
     * 
     * @attention ������ʹ��ע������
     * @note begin modify by �޸��� �޸�ʱ�� �޸�����ժҪ˵��
     */
    public  MongoCursor<Document> queryAll(String collectionName) {
        MongoCollection<Document> collection =db.getCollection(collectionName);
        // db�α�
        MongoCursor<Document> cur = collection.find().iterator();
        return cur;
       
    }
}
