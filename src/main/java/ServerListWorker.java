import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskResult.Status;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        ArrayList<CustomTask> dynamiclist = new ArrayList();

        Map<String,Object> mymap1 = new HashMap<String, Object>();
        mymap1.put("1","1");
        mymap1.put("2","2");

        Map<String,Object> mymap = new HashMap<String, Object>();

        for(int i=0;i<30;i++){
            CustomTask t1 = new CustomTask();
            t1.setName("encode_task1");
            t1.setTaskReferenceName("forked_task"+i);
            t1.setType(WorkflowTask.Type.SIMPLE.toString());

            mymap.put("forked_task"+i,mymap1);

            dynamiclist.add(t1);
        }

        result.getOutputData().put("dynamicTasks", dynamiclist);
        result.getOutputData().put("inputs", mymap);

        return result;
    }

}
