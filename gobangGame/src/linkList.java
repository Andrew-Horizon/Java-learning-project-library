public class linkList {
    private Node head;
    private int size=1;
    public linkList(){
        head=new Node();
    }

    public int size(){
        return size;
    }

    public void add(Object key,Object value){
        Node newNode=new Node(key,value);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                if(current.next.key==key){
                    current.next.value=value;
                }
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }
    public void delete(Object key){
        if(head==null){
            return;
        }
        if(head.key ==key){
            head=head.next;
            return;
        }
        Node current=head;
        while(current.next!=null){
            if(current.next.key ==key){
                current.next=current.next.next;
                return;
            }
            current=current.next;
        }
        size--;
    }
    public Object find(Object key){
        Node current=head;
        while(current!=null){
            if(current.key ==key){
                return current.value;
            }
            current=current.next;
        }
        return null;
    }
}
class Node{
    public Object key;
    public Object value;
    public Node next;
    public Node(Object key,Object value){
        this.key = key;
        this.value = value;
        this.next=null;
    }
    public Node(){

    }
}
