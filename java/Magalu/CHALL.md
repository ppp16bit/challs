**Welcome to our selection process**

**Scenario**
Magalu faces the challenge of building a communication platform. You have been chosen to kick off development for the first sprint.

**Requirements**
- **There must be an endpoint that receives a communication scheduling request (1):**
    - This endpoint must have at least the following fields:kk
        - Date/Time for sending
        - Recipient
        - Message to be delivered
    - The possible communication channels are: email, SMS, push notification, and WhatsApp.
    - At this stage, we need this input channel to handle the sending request, i.e., this endpoint (1). The actual sending will not be developed in this step — you do not need to worry about that.
    - For this sprint, it has been decided that the communication scheduling request will be saved to the database. Therefore, as soon as the scheduling request is received (1), it must be persisted to the database.
    - Think carefully about this database structure. Even though you won't be implementing the actual sending, the structure must already be ready so that your teammate won't need to change anything when developing that feature. The concern at sending time will be to send the message and update the record's status in the database.
- **There must be an endpoint to query the status of a communication scheduling request (2):**
    - The scheduling will be created via endpoint (1), and the query will be performed through this other endpoint.
- **There must be an endpoint to cancel/remove a communication scheduling request.**

**General Notes and Guidelines**
- We have a preference for Java, Python, or Node, but any language may be used — just explain your choice afterward.
- Use one of the following databases:
    - MySQL
    - PostgreSQL
- APIs must follow the RESTful model with JSON format.
- Write unit tests, focusing on a well-organized test suite.
- Follow what you consider to be programming best practices.
- How the database and tables are created is up to you — whether via script, the application itself, or any other means.

Your challenge should preferably be submitted as a public Git repository (GitHub, GitLab, or Bitbucket) with small, well-described commits, or as a compressed file (ZIP or TAR). Your repository must use an open-source license. Do not include any files other than the source code and its documentation. Be careful not to submit images, videos, audio, binaries, etc.

Follow best practices for development, quality, and code governance. Guide the evaluators on how to install, test, and run your code — a README inside the project works great for this.

We will evaluate your challenge according to the position and seniority level you are applying for.

We greatly appreciate your willingness to participate in our selection process and hope you have fun and good luck! :)