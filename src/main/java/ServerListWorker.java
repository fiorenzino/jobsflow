import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskResult.Status;
import org.codehaus.jettison.json.JSONObject;

public class ServerListWorker implements Worker {




    @Override
    public String getTaskDefName() {
        return "inventory.ServerList";
    }


    @Override
    public TaskResult execute(Task task) {
        System.out.println("successfully executed work for Server List");
        TaskResult result = new TaskResult(task);
        result.setStatus(Status.COMPLETED);

        //Register the output of the task
        result.getOutputData().put("outputKey1", "value");
        result.getOutputData().put("oddEven", 1);
        result.getOutputData().put("mod", 4);

        return result;
    }
}
