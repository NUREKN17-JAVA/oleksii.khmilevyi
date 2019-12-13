package ua.nure.kn.khmilevoi.usermanagement.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kn.khmilevoi.usermanagement.User;
import ua.nure.kn.khmilevoi.usermanagement.db.DaoFactory;
import ua.nure.kn.khmilevoi.usermanagement.db.DatabaseException;

public class AddServlet extends EditServlet {

	protected void ShowPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/add.jsp").forward(req, resp);
	}

	protected void ProccesUser(User user) throws DatabaseException {
		DaoFactory.getInstance().getUserDao().create(user);
	}
}