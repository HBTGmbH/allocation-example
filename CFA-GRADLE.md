# CFA - Gradle Examples

## Task 1 - IntelliJ configuration

Open the Gradle view and click the gear icon to go to `Gradle Settings...`.

* Check which JVM is used to run Gradle.
* Check how the project is built and run and how tests are executed.
* Note that the project is configured to use the Gradle wrapper.

## Task 2 - Gradle wrapper version

Find out which version of Gradle we are using.

Now upgrade the wrapper to the latest version by clicking the icon `Execute Gradle Task` to run the following command in the Gradle view:
```
gradle wrapper --gradle-version latest
```


## Task 3 - Dependency management

Goals:
* Understand source sets, configurations, dependencies.
* Understand the Java classpath used in Maven scopes and Gradle configurations.
* Understand what a version conflict is and how Maven and Gradle resolve conflicts.

| Maven Scope | Equivalent Gradle configuration                                                                                                     |
|-------------|-------------------------------------------------------------------------------------------------------------------------------------|
| `compile`   | `api` if the dependency should be exposed to consumers, `implementation` if not                                                     |
| `provided`  | `compileOnly` (note that the provided Maven scope is also available at runtime while the `compileOnly` Gradle configuration is not) |
| `runtime`   | `runtimeOnly`                                                                                                                       |
| `test`      | `testImplementation`                                                                                                                |

Your tasks:

* Have a look at the project's dependencies in the Gradle view `Dependencies` tree. 
* Compare with the declared dependencies in [build.gradle.kts](build.gradle.kts)
* Which dependencies come transitively by depending on `spring-boot-starter-test`?
* Look at [](conflictDemo/build.gradle.kts). Can you spot the version conflict?
* Run the following command to analyze the conflict and resolve it by fixing the version.

```
gradle :conflictDemo:dependencyInsight --configuration runtimeClasspath --dependency org.apache.commons:commons-lang3
```

Gradle documentation:

[Configurations used in Java projects](https://docs.gradle.org/current/userguide/java_plugin.html#tab:configurations)

[Dependency Management](https://docs.gradle.org/current/userguide/core_dependency_management.html)

### Dependency locking

Enable dependency locking in [build.gradle.kts](build.gradle.kts).

Generate the lockfile. It would be checked in to version control.

```
gradle dependencies --write-locks
```

Now upgrade Spring Boot to version 3.1.2 and attempt to run the build.


## (optional) Task 4 - Adding integration tests

### Look at the task tree

The plugin `com.dorongold.task-tree` allows us to have a look at the dependencies between tasks.

Run the following command to display the task tree for the `build` task:

```
gradle build taskTree
```

Compare the build task tree with the `assemble` task tree.

### Run integration tests after unit tests

Have a look at the configuration in [build.gradle.kts](build.gradle.kts).

Make sure that the integration tests are run as part of the `build` task.

Gradle documentation:

[Configuring Java integration tests](https://docs.gradle.org/current/userguide/java_testing.html#sec:configuring_java_integration_tests)


## (optional) Task 5 - The build cache and openapi-generator


## Task 6 - Build a container image

Goals:
* understand options for building (OCI) container images

Your tasks:
* Have a look at either [Jib](https://github.com/GoogleContainerTools/jib/tree/master/jib-gradle-plugin) or the [Spring Boot Gradle Plugin](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/#build-image)
* Get an idea of the differences between the two approaches and the good old plain Dockerfile (see also [Buildpack's comparison](https://buildpacks.io/features/#comparison))
* Try `./gradlew bootBuildImage` and admire the result ... _NOT_. What is the reason for the build error?
* Use the prepared "fix" in [build.gradle.kts](build.gradle.kts) and repeat above statement.
* Look at your locally available Docker images, e.g. on the command line with `docker images`
* Run the newly built image, e.g. on the command line with `docker run -it -p 8080:8080 allocationapplication:0.0.1-SNAPSHOT`
* Navigate to http://localhost:8080
