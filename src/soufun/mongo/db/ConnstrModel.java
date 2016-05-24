package soufun.mongo.db;

/**
 * 连接字符串对象
 * 
 * @author SZG
 *
 */
public class ConnstrModel {

	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 主机名（IP）
	 */
	private String hostName;
	/**
	 * MongoDB服务端口号
	 */
	private int port;
	/**
	 * 数据库名称
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
