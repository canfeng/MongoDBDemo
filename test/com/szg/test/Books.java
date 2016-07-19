package soufun.mongo.db;

public class Books  {
	public Books(String title,String num){
		this.title=title;
		this.num=num;
	}
	
	public Books() {
		// TODO Auto-generated constructor stub
	}

	private String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	private String num;
	
}
