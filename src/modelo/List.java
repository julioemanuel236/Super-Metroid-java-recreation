package modelo;

public class List<T> {
	
	
	/*
	 * una lista doblemente enlazada que se utililzara mas como cola
	 * debido al disenno que se le dio al videojuego
	 */
	
	private class Node<T>{
		//nodo de la estructura
		private Node next;
		private Node prev;
		private T element;
		
		public Node(T e) {
			this.element = e;			
		}

		public Node(T e,Node<T> prev,Node<T> next) {
			this.element = e;			
			this.prev = prev;
			this.next = next;
		}
		
		public Node<T> getNext() {
			return next;
		}

		public void setNext(Node<T> next) {
			this.next = next;
		}

		public Node<T> getPrev() {
			return prev;
		}

		public void setPrev(Node<T> prev) {
			this.prev = prev;
		}

		public T getElement() {
			return element;
		}

		public void setElement(T element) {
			this.element = element;
		}
				
	}
	
	private Node<T> first;
	private Node<T> last;
	private Node<T> current;
	private int size = 0;
	private int currentPos = -1;
	
	public void add(T element) {
		if(first == null) {
			first = new Node<T>(element);
			first.setNext(first);
			first.setPrev(first);
			last = first;
			currentPos = 0;
			current = first;
		}
		else {
			Node<T> temp = new Node<T>(element,last,first);
			last.setNext(temp);
			last = temp;
			first.prev = last;
		}
		size++;
	}
	
	public void clear() {
		first = last = current = null;
		size = 0;
		currentPos = -1;
	}
	
 	public T get(int index) {
		if(index <0 || index > size)throw new ArrayIndexOutOfBoundsException();
		if(size == 0) {
			return null;
		}
		
		while(currentPos != index) {
			currentPos++;
			current = current.getNext();
			if(currentPos == size)currentPos = 0;
		}
		
		return current.getElement();
	}
	
	public T getNext() {
		if(isEmpty())return null;
		current = current.getNext();
		currentPos++;
		if(currentPos == size)currentPos = 0;
		return current.getElement();
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size==0;
	}

	public void goToStart() {
		current = first;
		currentPos = 0;
	}
	
	public void removeFirst() {
		if(isEmpty())return;		
		size--;		
		if(size == 0) {
			first = last = current = null;
			currentPos = -1;
			return;
		}
		first = first.getNext();
		current = first;
		currentPos=0;
	}
	
	public T poll() {
		if(isEmpty())return null;
		T element = first.getElement();
		removeFirst();
		return element;
	}
	
	public int getCurrentPosition() {
		return currentPos;
	}
	
	public T getCurrent() {
		return current.getElement();
	}
	
	public T getFirst() {
		return first.getElement();
	}
}
