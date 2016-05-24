package soufun.mongo.db;

import java.util.List;

public class MongoDBManager<T>{

	private MongoDBHelper<T> dbManager=null;
	private String tableName="";
	/**
	 * @param tableName 要操作的表名（集合名）
	 * @param connstrModel 连接字符串对象
	 */
	public MongoDBManager(String tableName,ConnstrModel connstrModel) {
		dbManager=new MongoDBHelper<T>(connstrModel.toString(),connstrModel.getDbName());
		this.tableName=tableName;
	}
	
	/**
	 * 新增 单个
	 * @param t
	 */
	public void add(T t){
		dbManager.add(tableName, t);
	}
	/**
	 * 新增 多个
	 * @param list
	 */
	public void add(List<T> list){
		dbManager.add(tableName, list);
	}
	
	/**
	 * 更新
	 * @param old 被更新的对象实体
	 * @param now 更新后的对象实体
	 * @return
	 */
	public boolean update(T old,T now){
		return dbManager.update(tableName, old, old);
	}
	
	/**
	 * 删除
	 * @param t
	 * @return
	 */
	public boolean delete(T t){
		return dbManager.delete(tableName, t);
	}
	
	/**
	 * 查询
	 * @param inModel
	 * @return
	 */
	public List<T> query(InModel<T> inModel){
		return dbManager.query(inModel);
	}
	
	/**
	 * 获取条数
	 * @param condition 查询条件实体
	 * @return
	 */
	public int getTotalCount(T condition){
		return dbManager.getTotalCount(tableName, condition);
	}
	
	/**
	 * 查询全部数据
	 * @param cls 实体类型
	 * @return
	 */
	public List<T> queryAll(Class<T> cls){
		return dbManager.queryAll(tableName, cls);
	}
}
