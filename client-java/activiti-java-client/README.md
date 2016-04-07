# Alfresco Activiti Java Client 

## Introduction
The idea behind this project is to enable developers to interact easily with Alfresco Activiti Public REST API.
To achieve this goal this project provide a single JAR file that can be used in Java or Android applications.

## Important Notice

Alfresco Activiti Java Client is currently in Early Access mode. It evolves as you use them, as you give feedback, and as the developers update and add content. We like to think app development as services that grow and evolve with the involvement of the community.


### Status
**Please note this project is currently under development.**
**The library is still not available in any Maven Repository**


##Usage

###Create Client/Session Object
```java
BPMClient client = new BPMClient.Builder().connect(endpoint, username, password).build();
```

###Create Services
```java
//Admin restricted API to manage tenants, users, groups, endpoints & basic auths
AdmiAPI adminAPI = client.getAdmiAPI();

//Retrieve BPM Suite App information (version number)
AboutAPI aboutAPI = client.getAboutAPI();

//Manage content - Use ProcessInstanceAPI or TaskAPI to manage related content.
ContentAPI contentAPI = client.ContentAPI();

//Retrieve and manage models (process, app, etc...)
ModelsAPI modelsAPI = client.getModelsAPI();

//Retrieve process definitions informations
ProcessDefinitionAPI processDefAPI = client.getProcessDefinitionAPI();

//Manage process instance
ProcessInstanceAPI processAPI = client.getProcessInstanceAPI();

//Retrieve and manage user profile information
ProfileAPI profileAPI = client.getProfileAPI();

//Retrieve runtime app information
RuntimeAppDefinitionAPI runtimeAppAPI = client.getRuntimeAppDefinitionAPI();

//Manage tasks and associated informations (checklist, forms, comments, content...)
TaskAPI taskAPI = client.getTaskAPI()

//Retrieve and manage process & tasks filters defined by the user
UserFiltersAPI userFilterAPI = client.getUserFiltersAPI();

//Retrieve user & group informations (to manage use ApiAdmin)
UserGroupAPI userGroupAPI = client.getUserGroupAPI();
```

###API Response
All API methods can be consumed in 3 differents ways:

**Synchronuously**
```java
Response<AppVersionRepresentation> response = client.getAboutAPI().getAppVersion().execute();
```

**Asynchronuously**
```java
 client.getAboutAPI().getAppVersion().enqueue(new Callback<AppVersionRepresentation>() {
            @Override
            public void onResponse(Response<AppVersionRepresentation> response) {
                //TODO When response is correct
            }

            @Override
            public void onFailure(Throwable t) {
                //TODO When request has failed
            }
        });
```

**Rx Observable/Subscription**
```java
 client.getAboutAPI().getAppVersionObservable()
    .subscribe(appVersion -> System.out.println(appVersion.getFullVersion()));
```


###Create Standalone Task & Complete it (synchronuously)
```java
 // Retrieve TASKS API
 ApiTaskResource api = client.getAPI(ApiTaskResource.class);

 // Create Standalone Task
 TaskRepresentation task = new TaskRepresentation();
 task.setName("Task 1");
 task.setDescription("Description 1");
 Response<TaskRepresentation> response = api.createNewTask(task).execute();

 //Retrieve Task Object
 TaskRepresentation realTask = response.body();

 // Complete the Task
 api.completeTask(realTask.getId()).execute();

```

###Create Standalone Task --> Sub Task --> Sub Task of Sub Task
```java
        TaskAPI taskAPI = bpmClient.getTaskAPI();

        TaskRepresentation representation = new TaskRepresentation();
        representation.setDescription("Description");
        representation.setName("Name");
        representation.setAssignee(new LightUserRepresentation(1L));

        taskAPI.createNewTaskObservable(representation)
                .flatMap(task -> taskAPI.addSubtaskObservable(task.getId(), representation))
                .flatMap(task -> taskAPI.addSubtaskObservable(task.getId(), representation))
                .subscribe(task -> System.out.println("Task & subTask created"));
```


## Versionning

Follow Activiti BPM Suite Version number

##Installation

**IMPORTANT: Currently the library is still not available in any Maven Repository**

###MAVEN

```xml
<dependency>
  <groupId>org.alfresco.client</groupId>
  <artifactId>activiti-java-client</artifactId>
  <version>1.4.0-beta1</version>
</dependency>
```

###GRADLE
```gradle
compile 'org.alfresco.client:activiti-java-client:1.4.0-beta1'
```


##Running tests

Tests require a fresh activiti install and accessible by http://localhost:8080/activiti-app  
Tests can be run as is. Default admin user is used to run the test.

Want to manage other tests ?
Replace all TEST_X in [Client API: ActivitiAPITestCase](activiti-java-client/src/test/java/com/activiti/client/api/ActivitiAPITestCase.java)


