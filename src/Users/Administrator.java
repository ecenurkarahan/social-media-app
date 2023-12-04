package Users;

public class Administrator extends ProfessionalTier{

	private boolean isadministator;
	public Administrator(String nickName, String password, String realName, String surname, String email, int age) {
		super(nickName, password, realName, surname, email, age);
		this.isadministator= true;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isAdmin() {
		// TODO Auto-generated method stub
		return true;
	
}}
