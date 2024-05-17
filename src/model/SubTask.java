
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package model;

public class SubTask extends Task {
    private Epic epic;

    public SubTask(String name, String description, Epic epic) {
        super(name, description);
        this.epic = epic;
    }

    public Epic getEpic() {
        return this.epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }
}