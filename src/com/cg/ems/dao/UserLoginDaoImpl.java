package com.cg.ems.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.cg.ems.bean.UserLogin;
import com.cg.ems.exception.EMSException;
import com.cg.ems.util.DBConnection;

public class UserLoginDaoImpl implements IUserLoginDao {

	private Logger log = Logger.getLogger("UserDAO");

	@Override
	public UserLogin getUserByName(String userName) throws EMSException {
		UserLogin user = null;
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(IQueryMapper.GET_USER)) {

			preparedStatement.setString(1, userName);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user = new UserLogin();
				user.setUserName(resultSet.getString(1));
				user.setPassword(resultSet.getString(2));
				user.setRole(resultSet.getString(3));
				user.setUserId(resultSet.getString(4));
			}
		} catch (SQLException exception) {
			log.error(exception);
			throw new EMSException("Unable To Fetch Records");
		}
		return user;
	}
}
