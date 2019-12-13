package ua.nure.kn.khmilevoi.usermanagement.agent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ua.nure.kn.khmilevoi.usermanagement.User;
import ua.nure.kn.khmilevoi.usermanagement.db.DaoFactory;
import ua.nure.kn.khmilevoi.usermanagement.db.DatabaseException;

public class RequestServer extends CyclicBehaviour {

	@Override
	public void action() {
		ACLMessage message = myAgent.receive();
		if (message != null) {
			if (message.getPerformative() == ACLMessage.REQUEST) {
				myAgent.send(CreateReply(message));
			} else {
				Collection users = ParseMessage(message);
				((SearchAgent) myAgent).ShowUsers(users);
			}
		} else {
			block();
		}
	}

	private Collection ParseMessage(ACLMessage message) {
		Collection users = new LinkedList();

		String content = message.getContent();
		if (content != null) {
			StringTokenizer tokenizer = new StringTokenizer(content, ";");
			while (tokenizer.hasMoreTokens()) {
				String userInfo = tokenizer.nextToken();
				StringTokenizer innerTokenizer = new StringTokenizer(userInfo, ",");
				String id = innerTokenizer.nextToken();
				String firstName = innerTokenizer.nextToken();
				String lastName = innerTokenizer.nextToken();
				User newUser = new User();
				newUser.setId(new Long(id));
				newUser.setFirstName(firstName);
				newUser.setLastName(lastName);
				newUser.setDateOfBirth(null);
				users.add(newUser);
			}
		}
		return users;
	}

	private ACLMessage CreateReply(ACLMessage message) {
		ACLMessage reply = message.createReply();

		String content = message.getContent();
		StringTokenizer tokenizer = new StringTokenizer(content, ",");
		if (tokenizer.countTokens() == 2) {
			String firstName = tokenizer.nextToken();
			String lastName = tokenizer.nextToken();
			Collection users = null;
			try {
				users = DaoFactory.getInstance().getUserDao().find(firstName, lastName);
			} catch (DatabaseException e) {
				users = new ArrayList(0);
			}

			StringBuffer buffer = new StringBuffer();
			for (Iterator it = users.iterator(); it.hasNext();) {
				User user = (User) it.next();
				buffer.append(user.getId()).append(",");
				buffer.append(user.getFirstName()).append(",");
				buffer.append(user.getLastName()).append(";");
			}
			reply.setContent(buffer.toString());

		}
		return reply;
	}

}
