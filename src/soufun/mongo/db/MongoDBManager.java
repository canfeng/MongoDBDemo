package soufun.mongo.db;

import java.util.List;

public class MongoDBManager<T>{

	private MongoDBHelper<T> dbManager=null;
	private String tableName="";
	/**
	 * @param tableName Ҫ�����ı�������������
	 * @param connstrModel �����ַ�������
	 */
	public MongoDBManager(String tableName,ConnstrModel connstrModel) {
		dbManager=new MongoDBHelper<T>(connstrModel.toString(),connstrModel.getDbName());
		this.tableName=tableName;
	}
	
	/**
	 * ���� ����
	 * @param t
	 */
	public void add(T t){
		dbManager.add(tableName, t);
	}
	/**
	 * ���� ���
	 * @param list
	 */
	public void add(List<T> list){
		dbManager.add(tableName, list);
	}
	
	/**
	 * ����
	 * @param old �����µĶ���ʵ��
	 * @param now ���º�Ķ���ʵ��
	 * @return
	 */
	public boolean update(T old,T now){
		return dbManager.update(tableName, old, old);
	}
	
	/**
	 * ɾ��
	 * @param t
	 * @return
	 */
	public boolean delete(T t){
		return dbManager.delete(tableName, t);
	}
	
	/**
	 * ��ѯ
	 * @param inModel
	 * @return
	 */
	public List<T> query(InModel<T> inModel){
		return dbManager.query(inModel);
	}
	
	/**
	 * ��ȡ����
	 * @param condition ��ѯ����ʵ��
	 * @return
	 */
	public int getTotalCount(T condition){
		return dbManager.getTotalCount(tableName, condition);
	}
	
	/**
	 * ��ѯȫ������
	 * @param cls ʵ������
	 * @return
	 */
	public List<T> queryAll(Class<T> cls){
		return dbManager.queryAll(tableName, cls);
	}
}
