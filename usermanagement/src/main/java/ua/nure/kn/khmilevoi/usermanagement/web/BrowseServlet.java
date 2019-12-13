package ua.nure.kn.khmilevoi.usermanagement.web;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kn.khmilevoi.usermanagement.User;
import ua.nure.kn.khmilevoi.usermanagement.db.DaoFactory;
import ua.nure.kn.khmilevoi.usermanagement.db.DatabaseException;

public class BrowseServlet extends HttpServlet {

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getParameter("addButton") != null) {
			add(req, resp);
		} else if (req.getParameter("editButton") != null) {
			edit(req, resp);
		} else if (req.getParameter("deleteButton") != null) {
			delete(req, resp);
		} else if (req.getParameter("detailsButton") != null) {
			details(req, resp);
		} else {
			browse(req, resp);
		}

	}

	private void details(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idStr = req.getParameter("id");
		if (idStr == null || idStr.trim().length() == 0) {
			req.setAttribute("error", "You must select a user");
		} else {
			try {
				User user = DaoFactory.getInstance().getUserDao().find(new Long(idStr));
				req.getSession().setAttribute("user", user);
			} catch (DatabaseException e) {
				req.setAttribute("error", "Error" + e.toString());
				req.getRequestDispatcher("/browse.jsp").forward(req, resp);
				return;
			}
			req.getRequestDispatcher("/details.jsp").forward(req, resp);
		}
	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idStr = req.getParameter("id");
		if (idStr == null || idStr.trim().length() == 0) {
			req.setAttribute("error", "You must select a user");
		} else {
			try {
				DaoFactory.getInstance().getUserDao().delete(new Long(idStr));
			} catch (DatabaseException e) {
				req.setAttribute("error", "Error" + e.toString());
				req.getRequestDispatcher("/browse.jsp").forward(req, resp);
				return;
			}
			req.getRequestDispatcher("/browse.jsp").forward(req, resp);
		}
	}

	private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idStr = req.getParameter("id");
		if (idStr == null || idStr.trim().length() == 0) {
			req.setAttribute("error", "You must select a user");
		} else {
			try {
				req.getRequestDispatcher("/browse.jsp").forward(req, resp);
			} catch (ServletException | IOException e) {
				req.setAttribute("error", "Error" + e.toString());
			}
			try {
				User user = DaoFactory.getInstance().getUserDao().find(new Long(idStr));
				req.getSession().setAttribute("user", user);
			} catch (DatabaseException ex) {
				req.setAttribute("error", "Error" + ex.toString());
				req.getRequestDispatcher("/browse.jsp").forward(req, resp);
				return;
			}
			req.getRequestDispatcher("/edit.jsp").forward(req, resp);
		}
	}

	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/add").forward(req, resp);
	}

	private void browse(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		Collection users;
		try {
			users = DaoFactory.getInstance().getUserDao().findAll();
			req.getSession().setAttribute("users", users);
			req.getRequestDispatcher("/browse.jsp").forward(req, resp);
		} catch (DatabaseException e) {
			throw new ServletException(e);
		} catch (IOException e) {
			throw new ServletException(e);
		}
	}
}