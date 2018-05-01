import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.task.WorkflowTaskCoordinator;
import com.sun.jersey.api.client.Client;

import javax.ws.rs.core.MediaType;
import java.io.InputStream;

public class ServerDetails {
    public static void main(String args[]) {
        String serverPort = "8080";
        ServerDetails app = new ServerDetails();

        // clean, register tasks and workflow
        app.cleanPreviousRun(serverPort);
        app.registerTaskDef(serverPort);
        app.createWorkflowDef(serverPort);

        //
        //app.startPollingForTasks();

        //start workflow
        app.startWorkflow(serverPort);

        // move this up later
        app.startPollingForTasks();

        app.waitForever();



    }

    private synchronized void waitForever1()  {
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void waitForever() {
        while (true) {
            System.out.println("waiting forever");
            try {
                this.wait(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void registerTaskDef(String port) {
        try {
            Client client = Client.create();
            InputStream stream = ServerDetails.class.getResourceAsStream("/task/inventoryLoadingTasks.json");
            client.resource("http://localhost:" + port + "/api/metadata/taskdefs").type(MediaType.APPLICATION_JSON).post(stream);
            System.out.println("Server and Camera tasks registered !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createWorkflowDef(String port) {
        try {
            Client client = Client.create();
            InputStream stream = ServerDetails.class.getResourceAsStream("workflow/serverDetails.json");
            client.resource("http://localhost:" + port + "/api/metadata/workflow").type(MediaType.APPLICATION_JSON).post(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startWorkflow(String port) {
        try {
            Client client = Client.create();
            String input = "{\"vsomIP\":\"10.20.30.40\",\"vsomName\":\"vsomBangalore\",\"customParam1\":\"param1\",\"customParam2\":\"param2\"}";
            client.resource("http://localhost:" + port + "/api/workflow/serverDetailsLoader").type(MediaType.APPLICATION_JSON).post(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cleanPreviousRun(String port) {
        try {
            Client client = Client.create();
            client.resource("http://localhost:" + port + "/api/metadata/taskdefs/inventory.ServerList").type(MediaType.APPLICATION_JSON).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Client client = Client.create();
            client.resource("http://localhost:" + port + "/api/metadata/taskdefs/inventory.CameraList").type(MediaType.APPLICATION_JSON).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startPollingForTasks() {
        TaskClient client = new TaskClient();
        client.setRootURI("http://localhost:8080/api/");

        CameraListWorker cameraListWorker = new CameraListWorker();
        ServerListWorker serverListWorker = new ServerListWorker();

        WorkflowTaskCoordinator coordinator = new WorkflowTaskCoordinator.Builder()
                .withWorkers(cameraListWorker, serverListWorker)
                .withThreadCount(100)
                .withWorkerQueueSize(400)
                .withSleepWhenRetry(100)
                .withUpdateRetryCount(10)
                .withTaskClient(client)
                //.withWorkerNamePrefix("test-worker-")
                .build();

        coordinator.init();
    }
}

// get servers=> get locations.

