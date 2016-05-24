package soufun.mongo.db;

/**
 * �����ַ�������
 * 
 * @author SZG
 *
 */
public class ConnstrModel {

	/**
	 * �û���
	 */
	private String userName;
	/**
	 * ����
	 */
	private String password;
	/**
	 * ��������IP��
	 */
	private String hostName;
	/**
	 * MongoDB����˿ں�
	 */
	private int port;
	/**
	 * ���ݿ�����
	 */
	private String dbName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	@Override
	public String toString() {
		return "mongodb://"+userName+":"+password+"@"+hostName+":"+port+"/"+dbName;
	}
}
