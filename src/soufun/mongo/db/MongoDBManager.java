package soufun.mongo.db;

import java.util.List;

public class MongoDBManager{

	private MongoDBHelper dbManager=null;
	private String tableName="";
	/**
	 * @param tableName Ҫ�����ı�������������
	 * @param connstrModel �����ַ�������
	 */
	public MongoDBManager(String tableName,ConnstrModel connstrModel) {
		dbManager=new MongoDBHelper(connstrModel.toString(),connstrModel.getDbName());
		this.tableName=tableName;
	}
	
	/**
	 * ���� ����
	 * @param <T>
	 * @param t
	 */
	public <T> void add(T t){
		dbManager.add(tableName, t);
	}
	/**
	 * ���� ���
	 * @param <T>
	 * @param list
	 */
	public <T> void add(List<T> list){
		dbManager.add(tableName, list);
	}
	
	/**
	 * ����
	 * @param <T>
	 * @param old �����µĶ���ʵ��
	 * @param now ���º�Ķ���ʵ��
	 * @return
	 */
	public <T> boolean update(T old,T now){
		return dbManager.update(tableName, old, old);
	}
	
	/**
	 * ɾ��
	 * @param <T>
	 * @param t
	 * @return
	 */
	public <T> boolean delete(T t){
		return dbManager.delete(tableName, t);
	}
	
	/**
	 * ��ѯ
	 * @param <T>
	 * @param inModel
	 * @return
	 */
	public <T> List<T> query(InModel<T> inModel){
		return dbManager.query(inModel);
	}
	
	/**
	 * ��ȡ����
	 * @param <T>
	 * @param condition ��ѯ����ʵ��
	 * @return
	 */
	public <T> int getTotalCount(T condition){
		return dbManager.getTotalCount(tableName, condition);
	}
	
	/**
	 * ��ѯȫ������
	 * @param <T>
	 * @param cls ʵ������
	 * @return
	 */
	public <T> List<T> queryAll(Class<T> cls){
		return dbManager.queryAll(tableName, cls);
	}
}
