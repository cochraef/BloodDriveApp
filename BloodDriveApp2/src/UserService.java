
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;

public class UserService {
	private static final Random RANDOM = new SecureRandom();
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();
	private DatabaseConnectionService dbService = null;

	public UserService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean useApplicationLogins() {
		return true;
	}
	
	public boolean login(String username, String password) {
		CallableStatement cs = null;
		try {
			cs = this.dbService.getConnection().prepareCall("SELECT Perpasswordsalt, Perpasswordhash FROM [Person] WHERE (username = ?)");
			cs.setString(1, username);
			ResultSet rs = cs.executeQuery();
			rs.next();
			if(this.hashPassword(dec.decode(rs.getString("Perpasswordsalt")), password).equals(rs.getString("Perpasswordhash"))) {
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Login Failed");
				return false;
			}
		} catch (Exception e) {
			if(e.getMessage().equals("The result set has no current row.")) {
				return false;
			}
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean register(String username, String password) {
		byte[] newSalt = this.getNewSalt();
		String newHash = this.hashPassword(newSalt, password);
		CallableStatement cs = null;
		if(username == null || username.equals("")) {
			JOptionPane.showMessageDialog(null, "Username cannot be null or empty.");
			return false;
		}
		if(newSalt == null) {
			//JOptionPane.showMessageDialog(null, "PasswordSalt cannot be null or empty.");
			return false;
		}
		if(newHash == null || newHash.equals("")) {
			//JOptionPane.showMessageDialog(null, "PasswordHash cannot be null or empty.");
			return false;
		}
		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call Register([" + username + "], [" + getStringFromBytes(newSalt) + "], [" + newHash + "])}");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			cs.registerOutParameter(1, Types.INTEGER);
			cs.execute();
			int returnValue = cs.getInt(1);
			if(returnValue == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Username already exists.");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public byte[] getNewSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}
	
	public String getStringFromBytes(byte[] data) {
		return enc.encodeToString(data);
	}

	public String hashPassword(byte[] salt, String password) {

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f;
		byte[] hash = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		}
		return getStringFromBytes(hash);
	}

}
