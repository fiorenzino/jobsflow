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

//private String  input = "{ \"forkedTask1\": { \"width\": 100, \"height\": 100, \"params\": { \"recipe\": \"jpg\" } }, \"forkedTask2\": { \"width\": 200, \"height\": 200, \"params\": { \"recipe\": \"jpg\" } } }";


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
        //InputStream dynamicTasks = ServerDetails.class.getResourceAsStream("data/tasksArray.json");
        ArrayList<CustomTask> dynamiclist = new ArrayList();
        /*Task t1 = new Task();
        t1.setTaskType(WorkflowTask.Type.SIMPLE.toString());
        t1.setWorkflowType(WorkflowTask.Type.SIMPLE.toString());
        t1.setReferenceTaskName("forked_task1");
        t1.setTaskDefName("encode_task1");*/

        CustomTask t1 = new CustomTask();
        t1.setName("encode_task1");
        t1.setTaskReferenceName("forked_task1");
        t1.setType(WorkflowTask.Type.SIMPLE.toString());

      /*  Task t2 = new Task();
        t2.setTaskType(WorkflowTask.Type.SIMPLE.toString());
        t2.setReferenceTaskName("forked_task1");
        t2.setTaskDefName("encode_task1");*/

        CustomTask t2 = new CustomTask();
        t2.setName("encode_task1");
        t2.setTaskReferenceName("forked_task2");
        t2.setType(WorkflowTask.Type.SIMPLE.toString());

        Map<String,Object> mymap1 = new HashMap<String, Object>();
        mymap1.put("1","1");
        mymap1.put("2","2");

        Map<String,Object> mymap = new HashMap<String, Object>();
        mymap.put("forked_task1",mymap1);
        mymap.put("forked_task2",mymap1);

        //t1.setInputData(mymap);
        //t2.setInputData(mymap);
        dynamiclist.add(t1);
        dynamiclist.add(t2);
        result.getOutputData().put("dynamicTasks", dynamiclist);
        //InputStream input = ServerDetails.class.getResourceAsStream("data/tasksInput.json");
        result.getOutputData().put("inputs", mymap);

        return result;
    }

}
