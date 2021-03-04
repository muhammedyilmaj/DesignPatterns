/**
## A class should have only one reason to change
**/
package singleresponsibility;

import java.util.ArrayList;
import java.util.List;

public class ToDoList {
    private final List<String> toDoList = new ArrayList<String>();

    public void addValueToDoList(String value) {
        toDoList.add("" + ("* ") + value);
    }
    public void removeValueFromList(int index) {
        toDoList.remove(index);
    }

    @Override
    public String toString() {
        return String.join(System.lineSeparator(),toDoList);
    }
}
class Main {
    public static void main(String[] args) {
        ToDoList list = new ToDoList();
        list.addValueToDoList("wash my face");
        list.addValueToDoList("prepare a breakfast");
        list.addValueToDoList("start working");
        System.out.println(list);
        list.removeValueFromList(1);
        System.out.println(list);
    }
}