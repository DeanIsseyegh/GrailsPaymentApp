# GrailsPaymentApp
A first attempt at using Groovy with the Grails framework

# Version Info
This project was built using:

* Groovy: 2.4
* Grails: 2.5
* Java: 1.8

It should be able to compile/build in any IDE as long as it uses the above.

I used:

* STS/Grails IDE: 3.6.4
* Groovy-Eclipse: 2.9.2

# Setup

This setup assumes STS is used as it is the IDE this project has been built in:

* Download latest version of STS
* Download Grails-Eclipse AND Groovy 2.4 from update site: http://dist.springsource.org/snapshot/GRECLIPSE/e4.4/
* Download Grails 2.5 and go to windows -> preferences -> Groovy -> Grails and add the installation directory there
* Download JDK (not JRE) 1.8 and go to windows -> preferences -> Installer JREs and add the home directory there
* Clone the project
* Import the project as a Grails project
* If there are complaints about the Groovy compiler version then right click project -> properties -> Groovy Compilers and make sure 2.4 is selected. If it does not appear on the list make sure you downloaded it from the update site given in step 2. If you still have issues make sure Groovy 2.4 compiled is configured at a workspace level too.


# Things that could be improved

* Unit tests could be broken up to have less assertions per test (provides more granularity and easier to track down where things went wrong).
* I only found out about integration tests at the very end! They seem awesome and could definitely be used more.
* Front-end sucks.
* 

# Mail Plugin
This project uses the green mail plugin and the grails mail plugin. You should be able to access it from localhost:8080/GrailsPaymentApp/greenmail

More info about the green mail [here!](https://grails.org/plugin/greenmail)
More info about the grails mail plugin [here!](https://grails.org/plugin/mail)
