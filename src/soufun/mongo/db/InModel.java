package soufun.mongo.db;

import java.util.List;
import java.util.Map;

public class InModel<T> {

	/**
	 * ����
	 */
	private String tableName;
	/**
	 * ��ѯ����
	 */
	private T condition;
	/**
	 * ��ѯ����
	 */
	private int pagesize=10;
	/**
	 * ��ѯҳ��
	 */
	private int curpage=1;
	/**
	 * ����ʽ
	 * Map<String,String>
	 * ����keyΪOrderBy��map valueΪ����������ֶ���
	 * keyΪUpOrDownd��map��valueΪ"1"��"-1","1"Ϊ����,"-1"Ϊ����
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
