# Some tests for [JSONPlaceholder](https://jsonplaceholder.typicode.com/) #
1. Searching user by username (username is set in `src/test/resources/test.properties`).
1. Fetching all posts written by user (if there are no posts it is logged).
1. Checking the format of email addresses for the comments (if there are no comments it is logged).

## How to run tests: ##
`./gradlew clean test`

## Using Lombok with IntelliJ IDEA: ##
 1. [Install the Lombok Plugin ](https://projectlombok.org/setup/intellij)
 1. Enable annotation processing
    1. Go to Preferences... > Build, Execution, Deployment > Compiler > Annotation Processors
    1. Set check box: 
       - [x] Enable annotation processing
    1. After enabling, run Build -> Rebuild Project 