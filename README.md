MasterKogoAds
=============

A friend and I are interested in starting an advertisement business named Kogo.

This application is one of three applications created to make our lives easier.

This particular application will allow the owner (me) to add, edit, and remove advertisments.
After we make a contract with a business looking to advertise through us, we maintain
the information regarding their advertisement (company name, title, length, plays, etc.) in an XML
File. Upon loading, an XML Parser parses this file and creates video instances containing the information
from the file. These video instances are displayed in a TableView that allows the user to add, edit, and
delete information. Each time an edit is made, an XML Writer writes the changes to the master XML File.

The application also uses this XML Writer to generate data files for drivers (Kogo Employees) that they
will load into their application (KogoDriver) to facilitate playing and keeping track of ads.
After a driver has completed a session, the driver will deliver a data file containing their drive's
information to us. We can then use the XML Parser of this application to read these files and use the
XMLWriter to update the master file. 

Thus you can see that these two applications (MasterKogoAds and KogoDriver) allow for a cycle of
efficient and safe data transport.
