Readme.txt

MitchellV1.1
------------

This project is an implementation of Mitchell Claims Web-Service to perform CRUD operations. 

Technologies, Tools Used
-----------------
Java jdk 1.8.0
Glassfish 4.0
EclipseLink 2.5.2
MySQL
SoapUI 5.2.1

Prerequisite
------------
1. An existing server instance in Eclipse.
2. A database instance.
3. A JDBC connection pool on Glassfish connecting to the database instance.
4. A JDBC connection resource using the connection pool. 

Build Application
-----------------
1. Import project from Git to Eclipse. 
2. JDBC connection resource name on server should match with persistence.xml <jta-data-source>
3. Right click on project->Run As->Run on Server->Choose an Existing Server->Next->Finish


Run Application
---------------
1. Open "localhost:4848" 
2. Go to Common Tasks -> Applications -> MitchellV1.1 -> View Endpoint
3. Click on wsdl link to get the web service xml, copy the URL.

Test Application
----------------
1. Open SoapUI.
2. Click File->Import project, navigate to "MitchellV1-1-soapui-project.xml", click Open.
3. Right click on MitchellClaimsImplService, click update definition.
4. Update with latest wsdl URL in Definition URL text box, click OK.
5. Run each test case. 
6. Requests and responses can be validated with xml's in SoapUI_TestCases folder.
