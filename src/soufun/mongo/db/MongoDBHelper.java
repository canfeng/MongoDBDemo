package soufun.mongo.db;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCursor;

public class MongoDBHelper {

	private MongoDBUtil dbManagerNew = null;

	public MongoDBHelper(String mongoConnStr,String dbName) {
		dbManagerNew = new MongoDBUtil(mongoConnStr,dbName);
	}

	/**
	 * @param <T>
	 * @param collectionName
	 * @param list
	 * @return
	 */
	public <T> void add(String tableName, List<T> list) {
		try {
			List<Document> documents = new ArrayList<Document>();
			for (T t : list) {
				Document doc = entityToDocument(t);
				documents.add(doc);
			}
			dbManagerNew.add(tableName, documents);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���
	 * @param <T>
	 * 
	 * @param tableName
	 *            ����
	 * @param t
	 *            ʵ����
	 */
	public <T> void add(String tableName, T t) {
		try {
			Document doc = entityToDocument(t);
			dbManagerNew.add(tableName, doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param <T>
	 * @param tableName
	 * @param t
	 * @return
	 */
	public <T> boolean delete(String tableName, T t) {
		Bson bson = entityToDocument(t);
		return dbManagerNew.delete(tableName, bson);
	}

	/**
	 * @param <T>
	 * @param tableName
	 * @param t1
	 *            ԭ�ȵ�model
	 * @param t2
	 *            ���ĺ��model
	 * @return
	 */
	public <T> boolean update(String tableName, T t1, T t2) {
		Bson bson1 = entityToDocument(t1);
		Bson bson2 = new Document("$set", entityToDocument(t2));
		return dbManagerNew.update(tableName, bson1, bson2);

	}

	/**
	 * @param <T>
	 * @param tableName
	 *            ����
	 * @param condition
	 *            ��ѯ����
	 * @param pagesize
	 *            ÿҳ����
	 * @param curpage
	 *            ��ǰҳ
	 * @param orderby
	 *            ����
	 * @param cls
	 *            ��ѯ���ص�����
	 * @return
	 */
	private <T> List<T> query(String tableName, T condition, int pagesize, int curpage, Bson orderby, Class<T> cls) {
		try {
			int skipNum = (curpage - 1) * pagesize;
			Bson conBson = new Document();
			MongoCursor<Document> cursor = null;
			if (condition != null) {
				conBson = entityToDocument(condition);
			}
			if (orderby == null) {
				cursor = dbManagerNew.queryNoOrder(tableName, conBson, pagesize, skipNum);
			} else {
				cursor = dbManagerNew.queryWithCondition(tableName, conBson, pagesize, skipNum, orderby);
			}

			List<T> list = new ArrayList<T>();
			while (cursor.hasNext()) {
				Document document = cursor.next();// ת��Ϊ�ĵ�
				T t = documentToEntity(document, cls);
				list.add(t);
			}
			return list;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

	/**
	 * ��ѯ
	 * @param <T>
	 * 
	 * @param inModel
	 * @return
	 */
	public <T> List<T> query(InModel<T> inModel) {
		List<Map<String, String>> orderList = inModel.getOrder();
		Document order = new Document();
		T condition = null;
		if (orderList != null) {
			for (Map<String, String> map : orderList) {
				order.append(map.get("OrderBy"), Integer.parseInt(map.get("UpOrDown")));
			}
		}
		if (inModel.getCondition() == null) {
			try {
				condition = inModel.getClassType().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return query(inModel.getTableName(), condition, inModel.getPagesize(), inModel.getCurpage(), order,
				inModel.getClassType());

	}

	public <T> int getTotalCount(String collectionName, T condition) {
		Document document = null;
		if (condition != null) {
			document = entityToDocument(condition);
		}
		return dbManagerNew.getTotalCount(collectionName, document);
	}

	public <T> List<T> queryAll(String collectionName,Class<T> cls) {
		MongoCursor<Document> cursor= dbManagerNew.queryAll(collectionName);
		List<T> list = new ArrayList<T>();
		while (cursor.hasNext()) {
			Document document = cursor.next();// ת��Ϊ�ĵ�
			T t = documentToEntity(document, cls);
			list.add(t);
		}
		return list;
	}

	/**
	 * ʵ����תDocument
	 * @param <T>
	 * 
	 * @param t
	 *            ʵ����
	 * @return
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public <T> Document entityToDocument(T t) {
		Document doc = null;
		try {
			Class cls = t.getClass();
			Field[] fields = cls.getDeclaredFields();
			doc = new Document();
			for (Field field : fields) {
				String name = field.getName();
				String firstLetter = name.substring(0, 1).toUpperCase();
				String getMethodName = "get" + firstLetter + name.substring(1);
				String setMethodName = "set" + firstLetter + name.substring(1);
				Method getMethod = cls.getMethod(getMethodName, new Class[] {});// ��ȡget����
				Method setMethod = cls.getMethod(setMethodName, new Class[] { field.getType() }); // ��ȡset����
				Object value = getMethod.invoke(t, new Object[] {});
				if (value != null) {
					doc.append(name, value);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * Documentתʵ����
	 * @param <T>
	 * 
	 * @param doc
	 * @param cls
	 * @return
	 */
	public <T> T documentToEntity(Document doc, Class<T> cls) {
		T t = null;
		try {
			t = cls.newInstance();
			Field[] fields = cls.getDeclaredFields();
			for (Field field : fields) {
				String name = field.getName();
				String firstLetter = name.substring(0, 1).toUpperCase();
				String setMethodName = "set" + firstLetter + name.substring(1);
				Method setMethod = cls.getDeclaredMethod(setMethodName, new Class[] { field.getType() });// ��ȡset����
				String typeStr=field.getGenericType().toString();
//				Object value=null;
//				if (typeStr.equals("class java.lang.Integer")) {
//					value=(Integer)doc.get(name);
//				}
				setMethod.invoke(t, new Object[] { doc.get(name) });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

}
