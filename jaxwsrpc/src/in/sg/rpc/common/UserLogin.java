package in.sg.rpc.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import in.sg.rpc.common.exception.UserLoginException;

public class UserLogin {

	public String login(String userName, String password) throws UserLoginException {
		File userDB = new File ("resources/users.txt");
		boolean validUser = false;
		String response = "Incorrect password for ";
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(userDB)));) {
			String line;
			while (null != (line = br.readLine())) {
				if (userName.equalsIgnoreCase(line.split(",")[0])){
					validUser = true;
					if (password.equals(line.split(",")[1])) {
						System.out.println("Login Successful for user " + userName);
					} else {
						System.out.println("Incorrect password for " + userName);
						
						throw new UserLoginException (response + userName);
					}
				}
			}
			if (!validUser) {
				throw new UserLoginException("User " + userName + " does not exists in database");
			} else{
				response = "Welcome " + userName;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new UserLoginException("User database not found");
		} catch (IOException e) {
			e.printStackTrace();
			throw new UserLoginException("Cannot read user details from database");
		}
		return response;
	}

}
