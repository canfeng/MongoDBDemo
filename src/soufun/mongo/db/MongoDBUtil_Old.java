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
	 * �������ݿ�
	 * 
	 * @param name
	 * @param databaseName
	 * @param pswd
	 * @return
	 */
	public  MongoDBUtil_Old() {

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
		db = mongoClient.getDB("szgDB");
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

	public  boolean add(String collectionName, List<DBObject> list) {
		DBCollection collection = db.getCollection(collectionName);
		return collection.insert(list).getN() > 0;
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
	public  boolean update(String collectionName, DBObject source, DBObject target) {
		DBCollection collection =db.getCollection(collectionName);
		/**
		 * true,//������ݿⲻ���ڣ��Ƿ���� false//�����޸�
		 */
		return collection.update(source, target, true, false).getN()>0;
	}
	/**
     * 
        * \brief ɾ��
        * @param collectionName
        * @param obj
        * @attention ������ʹ��ע������ 
        * @note  begin modify by �޸��� �޸�ʱ��   �޸�����ժҪ˵��
     */
    public  boolean delete(String collectionName, DBObject delObj) {
        DBCollection collection = db.getCollection(collectionName);
        return collection.remove(delObj).getN()>0;  
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
    public  List<DBObject>  queryWithCondition(String collectionName, DBObject condition,int pagesize,int skipNum,DBObject orderby) {
        DBCollection collection = db.getCollection(collectionName);
        DBCursor cur =collection.find(condition).skip(skipNum).limit(pagesize).sort(orderby); 
        return cur.toArray();
    }
    
    /**
     * ��ȡ������
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
     * ��ѯ��������
     * 
     * @attention ������ʹ��ע������
     * @note begin modify by �޸��� �޸�ʱ�� �޸�����ժҪ˵��
     */
    public   void queryAll(String collectionName) {
        DBCollection collection =db.getCollection(collectionName);
        // db�α�
        DBCursor cur = collection.find();
        System.out.println("����������:" + collection.count());
        while (cur.hasNext()) {
            System.out.println(cur.next());
        }
    }
}
