# Software Engineer Challenge - BTG Pactual
## Instructions
1. Read this document carefully before starting the activities.
2. You have 1 day to deliver the work plan (item 1).
3. You have up to 7 calendar days to complete the activities requested here.
   If you are unable to complete all activities, please deliver what was done by the requested date.
4. Create a repository on Github for your project and keep your project public.
5. Upon completing the delivery steps, send an e-mail with the subject "[DESAFIO BTG] - YOUR FULL NAME", to: ****@btgpactual.com" 
6. Feel free to use technologies, frameworks and techniques not mentioned in the activities or replace those you deem necessary. Report the changes and reasons in your report.
7. The application must be delivered "running", with instructions to interact with it.
8. We recommend using Docker (http://www.docker.com) for setting up the environment (MongoDb, RabbitMQ, Web Application, etc.)
   If you choose to use Docker, create a single image with all containers and share it in your final report.
## Scope
Process orders and generate a report.
## Activities
1. Elaborate and deliver a work plan.
   - Create your activities as tasks
   - Estimate hours
2. Create an application in the technology of your choice (JAVA, DOTNET, NODEJS)
3. Model and implement a database (PostgreSQL, MySQL, MongoDB).
4. Create a microservice that consumes data from a RabbitMQ queue and stores the data to be able to list the following information:
   - Total order value
   - Number of orders per customer
   - List of orders placed by customer
Example of the message that must be consumed:
```
   {
       "OrderCode": 1001,
       "ClientCode":1,
       "items": [
           {
               "product": "pencil",
               "quantity": 100,
               "price": 1.10
           },
           {
               "product": "notebook",
               "quantity": 10,
               "price": 1.00
           }
       ]
   }
```
5. Create a REST API that allows querying the following information:
   - Total order value
   - Number of orders per customer
   - List of orders placed by customer
   
6. Technical Report explaining in a summarized way, considering:
   - Work Plan (planned vs. actual)
   - If there is any deviation between the original planning and execution, explain what happened.
   - If the work plan was followed without deviation, comment on the reasons for this result.
   - Technologies used
   - Languages, Versions, IDEs, OSes
   - Architecture diagram
   - Database modeling
   - Solution deployment diagram
   - Infrastructure diagram with cloud resources used (machine, OS, specific products, etc.)
   - Evidence of functional application tests
   - Publish the generated code on your https://github.com/ profile
   - Mention in the report: 
     - Your gitHub profile and the URL(s) where the generated code can be found
     - References used
     - Other items you deem relevant (Framework or testing techniques, methodologies, etc.)
     - If Docker was used to set up the environment, publish the final images on your http://hub.docker.com profile
     - Mention in the report: Your dockerHub profile and the URL(s) where the generated images can be found