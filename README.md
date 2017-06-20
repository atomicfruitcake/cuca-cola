# cuca-cola
An Automated testing tool to utillise the Cucumber framework to run tests inside Docker Containers.

#Introduction

## What is this?
An automated testing suite written in Java using the cucumber testing framework.
More information on cucumber can be found [here](https://cucumber.io/)

## How do I use it
The project can be built using Maven.
#### Maven
If you are using maven to run the tests locally, from your terminal, run:
```
mvn clean install -Denv=LOCAL -Dbrowser=CHROME
```
The project can be built using maven.
#### Maven
If you are using maven to run the tests locally, from your terminal, run:
```
mvn clean install -Denv=LOCAL -Dbrowser=CHROME
```
or to run in Firefox
```
mvn clean install -Denv=LOCAL -Dbrowser=FIREFOX
```
# Test Framework Architecture
This section assumes you have some understanding of Java, OO design patterns, and browser automation with Selenium. 

### Cucumber implementation of Selenium
The Selenium `WebDriver` object is instantiated by the `DriverFactory.java`. This is then passed between the
`pageObjects` where it is used by functions that perform actions on the browser. Those functions used by the `cukeSteps` 
in which execute the cucumber BDD steps.

### pageObjects
pageObjects can be considered a representation of a web page as a java class. It uses the Page Object design pattern which 
you can read about [here](http://toolsqa.com/selenium-webdriver/page-object-pattern-model-page-factory/). The PageFactory
is used to instantiate all elements on a page when the page class object is initialized. All Page Objects must extends their
abstract parent class `Page.java`. This contains the functions to be used by the cukesteps. It can be considered an 
implementation of common functions on pages (e.g. takeScreenshot(), getCurrentURL()) to allow the `cukeSteps` access to
these functions with the page object acting as a de facto interface. Note, this is not an explicit java interface as the
page objects contain non abstract methods that are unique to that page.


### cukeSteps
`cukeSteps` are the interface between the BDD `*.feature` files of cucumber, and the underlying java classes. The step
functions are annotated by their relevant BDD sentence. Let's take the wave currency filter as an example. Here is the
`.*feature` file step which is a standard BDD step definition.
```gherkin
When I click the search button
```

Let's take a look at the cukeStep that executes this.
```java
    @When("^I click the search button$")
    public void i_click_the_search_button() {
        googleHomePage.clickGoogleSearchButton();
    }
```
The `*.feature` file with pick up the annotation and then execute the step when it is called. Note, due to the fact that
we do not `import` any steps into the `.feature` files explicitly, all annotations and functions must be unique, even if
they are protected from accessing each other. E.g. polymorphic cukeSteps are not allowed.

### DriverFactory
The `DriverFactory` is a singleton class where the we build the WebDriver used to execute the tests. It contains the 
switches that read from the Maven input for the specific driver we implement. This allows us to select the browser and
the environment in which the tests will run (e.g. in Docker containers or locally). It also handles that starting and closing
of the driver in its construction.

### Running the Tests with Maven
Apache Maven is used to both build and run the testing suite. This allows us to be more agile in making changes to the
automation framework and tightly couple the build and execution of tests. If you have not used Maven before, please
read the documentation here [here](https://maven.apache.org)

As well as handling dependencies, the `pom.xml` contains the instructions to execute the tests.
```xml
<executions>
	<execution>
		<id>acceptance-test</id>
		<phase>integration-test</phase>
		<goals>
			<goal>test</goal>
		</goals>
		<configuration>
			<forkCount>1</forkCount>
			<reuseForks>false</reuseForks>
			<includes>
				<include>TestsRunner.class</include>
			</includes>
		</configuration>
	</execution>
</executions>
```
This will execute the `TestsRunner.class` as part of the build process once the project has resolved dependencies. The
`TestsRunner.class` can be considered "junit for feature files". Essentially it reads out `.feature` files for a
given annotation (in our case this is `@Tests`), and runs those feature files. It also contains the target location 
for our results. 
