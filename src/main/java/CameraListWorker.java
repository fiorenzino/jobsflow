import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskResult.Status;

public class CameraListWorker implements Worker {
    @Override
    public String getTaskDefName() {
        return "inventory.CameraList";
    }

    @Override
    public TaskResult execute(Task task) {
        System.out.println("successfully executed work for CameraListWorker");
        TaskResult result = new TaskResult(task);
        result.setStatus(Status.COMPLETED);

        //Register the output of the task
        result.getOutputData().put("outputKey1", "value");
        result.getOutputData().put("oddEven", 1);
        result.getOutputData().put("mod", 4);

        return result;
    }
}
