DevOps InnovateVA
=================
This repository contains application and infrastructure code to provision an AWS VPC with a Jenkins server that polls this repository and a Tomcat application server that executes the application code.  The application is a simple Spring MVC Java app that is capable of displaying text messages and their senders via the third-party service Twilio.  The application writes to a DynamoDB table in Amazon and the UI displays the values stored in the database.

Chef is used to configure the Jenkins and Tomcat servers, which are both launched as standard Amazon Linux instances.  There are plans to move this external dependency into OpsWorks to more fully integrate with AWS.

To launch the entire stack, create a new CloudFormation stack pointing to cfn/master.template.

