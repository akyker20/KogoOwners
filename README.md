MasterKogoAds
=============

A friend and I are interested in starting an advertisement business named Kogo.

This application is one of three applications created to make things easier for us, our employees, and our clients.

This particular application will allow the owner (me) to add, edit, and remove advertisments.
After we make a contract with a business looking to advertise through us, we maintain
the information regarding their advertisement (company name, title, length, plays, etc.) in a master XML
File. Upon running this app, an XML Parser parses the master file and creates video instances from the information
it reads. These video instances are displayed in a TableView that allows the user to add, edit, and
delete information. Each time an edit is made, an XML Writer writes the changes to the master XML File. This
way if the user exits the application, the changes are saved.

The application also uses its XML Writer to generate data files for drivers (Kogo Employees) that they
will be able to load into their application (KogoDriver) to facilitate playing and keeping track of ads.
After a driver has completed a session, the driver will deliver us (by email) a data file containing his or her session
information. We can then use the XML Parser of this application to read these files and use the
XMLWriter to update the master file. 

Thus you can see that these two applications (MasterKogoAds and KogoDriver) allow for a cycle of
efficient data modification and transport.
