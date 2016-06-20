package soufun.mongo.db;

import java.util.List;
import java.util.Map;

public class InModel<T> {

	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 查询条件
	 */
	private T condition;
	/**
	 * 查询条数
	 */
	private int pagesize=10;
	/**
	 * 查询页码
	 */
	private int curpage=1;
	/**
	 * 排序方式
	 * Map<String,String>
	 * 其中key为OrderBy的map value为以其排序的字段名
	 * key为UpOrDownd的map的value为"1"或"-1","1"为正序,"-1"为倒序
	 */
	private List<Map<String, String>> order=null;
	/**
	 * 
	 */
	private Class<T> classType;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public T getCondition() {
		return condition;
	}

	public void setCondition(T condition) {
		this.condition = condition;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getCurpage() {
		return curpage;
	}

	public void setCurpage(int curpage) {
		this.curpage = curpage;
	}

	public List<Map<String, String>> getOrder() {
		return order;
	}

	public void setOrder(List<Map<String, String>> order) {
		this.order = order;
	}

	public Class<T> getClassType() {
		return classType;
	}

	public void setClassType(Class<T> classType) {
		this.classType = classType;
	}

}
