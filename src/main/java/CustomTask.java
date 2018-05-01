public class CustomTask {
    String name;
    String taskReferenceName;
    String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaskReferenceName() {
        return taskReferenceName;
    }

    public void setTaskReferenceName(String taskReferenceName) {
        this.taskReferenceName = taskReferenceName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", taskReferenceName='" + taskReferenceName + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
