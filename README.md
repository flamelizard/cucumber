# The Cucumber for Java

Learning project following chapters from book The Cucumber for Java.
Individual commits vaguely match book chapters.
The project aims to build simple ATM for Nice Bank, company that is
favored by 9 out of 10 clients :-)

Features
- simple web gui
- message queues and testing in async environment
- save ATM accounts to MySQL
- inject test data to step definitions/hooks

Technologies
- Cucumber
- simple ORM with Java ActiveJDBC
- Selenium WebDriver
- Liquibase
- Spring DI

## Build project
Start MySQL and create app user and database.
Build and run tests with Maven
```
mvn test
```


## Takeaway

#### ActiveJDBC
Every class that extends Model needs to be instrumented. That means to
do some processing on classes bytecode after compilation.

However the instrumentation can be wiped on recompilation by IDE or
Maven. Make sure to run instrumentation at proper maven stage.

I cannot extend Model subclass as it behaves unexpectedly.
Db table can be associated only with single class. The other class will
fail to locate table even though the table exists.

__Error - failed to determine Model class name, are you sure models have been instrumented?__
- instrumentation run at wrong stage `process-classes`
- change to `process-test-classes` that runs after test class compilation



