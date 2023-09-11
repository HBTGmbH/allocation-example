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


## Task 7 - Platforms and Multi-Projects

* We have a lot of options when it comes to structuring projects, e.g. _mono-repo_ vs. _multi-repo_, _source sets_ vs. _subprojects_
* These options can often be combined, while others benefit each other, e.g. mono-repos are typically structured using multi-projects
  * source sets can be used to structure code that has separate dependency graphs and does not rely on compilation results, e.g. `test` and `integration-test`
  * multi-projects can be used to either [structure a single component](https://docs.gradle.org/current/userguide/multi_project_builds.html), or a [larger software](https://docs.gradle.org/current/userguide/structuring_software_products.html#structure_large_projects) that is kept in a mono-repo
* Multi-projects often include a [platform project](https://docs.gradle.org/current/userguide/java_platform_plugin.html) that is used to define **common dependencies and versions** for all involved projects
* Structuring platforms, domain and service code in separate projects also provides a way to simplify **fixtures code sharing**

Goals:
* Understand the basic multi-project structure
* Understand how platforms work to share dependency versions between project
* Understand what fixtures are and how they can be shared between projects

Your Tasks:
* Compare the original `cfa-gradle` branch and the `cfa-gradle-multi` branch. What is the first difference that meets the eye when looking at the root directory?
* Open the `platform` subproject. What is the difference between this subproject and the others? How is the `build.gradle.kts` different?
* The `platform` subproject is used by both the `domain-model` and the `app` subprojects, while the `domain-model` subproject is itself again used by the `app` subproject. Can you find ot how? (take a look at the `build.gradle.kts` files)
* Test fixtures are a way of fixing test data to share between tests, often by creating a utility class that provides static constructor methods for objects that are used repeatedly. Where are the fixtures in this project, and what might be an advantage of defining them this way?
* The integration tests in the `app` project are not using the fixtures provided by the `domain-model` project, but are using duplicates. Is this necessary? If not, do something about it.
