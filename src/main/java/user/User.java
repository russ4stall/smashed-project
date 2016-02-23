package user;

public class User {
    private int id;
	private String name;
	private String username;
	private String email;
	private String phone;
	private String website;
	
	public User(int id, String name, String username, String email, String phone, String website) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.phone = phone;
		this.website = website;
	}
	
	public String toString() {
		return "{id: " + id + ", name: " + name + "}";
	}
	
	public int getId() { return id; }
	public String getName() { return name; }
	public String getUsername() { return username; }
	public String getEmail() { return email; }
	public String getPhone() { return phone; }
	public String getWebsite() { return website; }
	
	public void setId(int id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setUsername(String username) { this.username = username; }
	public void setEmail(String email) { this.email = email; }
	public void setPhone(String phone) { this.phone = phone; }
	public void setWebsite(String website) { this.website = website; }
}