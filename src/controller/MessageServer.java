package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import model.Message;

public class MessageServer implements Iterable<Message> {

	private Map<Integer, List<Message>> messages;
	private List<Message> selected;
	
	public MessageServer() {
		selected = new ArrayList<Message>();
		messages = new TreeMap<Integer, List<Message>>();
		
		List<Message> list = new ArrayList<Message>();
		list.add(new Message("Missing Cat", "Have you seen felix anywhere?"));
		list.add(new Message("See you Later", "Are we still meeting in the pub?"));
		list.add(new Message("Galaxy S6", "Edge Display"));
		messages.put(0, list);
		
		list = new ArrayList<Message>();
		list.add(new Message("Nexus 5", "Stock Android"));
		list.add(new Message("Xperia Z3", "Water Resistance, Music"));
		list.add(new Message("LG G4", "Curved Display"));
		messages.put(1, list);
		
		list = new ArrayList<Message>();
		list.add(new Message("Moto X (2016)", "Full of features"));
		messages.put(2, list);
		
		list = new ArrayList<Message>();
		list.add(new Message("Nexus 6", "Bigger display"));
		list.add(new Message("HTC M9", "Nothing new"));
		messages.put(3, list);
		
		list = new ArrayList<Message>();
		list.add(new Message("One Plus One", "value for money"));
		list.add(new Message("Huwawi P8", "Lots to improve."));
		messages.put(4, list);
	}
	
	public void setSelectedServers(Set<Integer> server) {
		selected.clear();
		for(Integer id: server) {
			if (messages.containsKey(id)) {
				List<Message> serverMessages = messages.get(id);
				selected.addAll(serverMessages);
			}
		}
	}
	
	public int getMessageCount() {
		return selected.size();
	}

	@Override
	public Iterator<Message> iterator() {
		return new MessageIterator(selected);
	}
}

class MessageIterator implements Iterator<Message> {
	
	private Iterator<Message> iterator;
	
	public MessageIterator(List<Message> messages) {
		iterator = messages.iterator();
	}
	
	public boolean hasNext() {
		return iterator.hasNext();
	}

	public Message next() {
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return iterator.next();
	}
	
}
