HOW TO COMPILE AND RUN:
For CMD:
1. Run CreateDB.sql.
2. Put classes111.jar, sdoapi, Populate.java, HW2.java, hw2GUI.java and 4 input files(map.jpg, buildings.xy, students.xy, announcementSystems.xy) into one folder.
3. To compile: javac -classpath path\to\the\directory\classes111.jar;sdoapi -d . Populate.java
	and    javac -classpaht paht\to\the\directory\classes111.jar;sdoapi -d . HW2.java hw2GUI.java
On my computer, that is javac -classpath D:\graduate\CSCI585\HW2_lifeiwan\classes111.jar;sdoapi -d . Populate.java
and javac -classpath D:\graduate\CSCI585\HW2_lifeiwan\classes111.jar;sdoapi -d . HW2.java hw2GUI.java
4. To run: java -classpath path\to\the\directory\classes111.jar;sdoapi;. csci585hw2/Populate buildings.xy students.xy announcementSystems.xy
and	   java -classpath path\to\the\directory\classes111.jar;sdoapi;. csci585hw2/HW2
5. Run DropDB.sql to drop the table.

For Eclipse:
In Properties for csci585hw2, import classes111.jar and sdoapi into the Libraries panel.
Also, include the 4 input files into the project.
The three java files are all under csci585hw2 package.

On my laptop, the DB connection is:
String URL = "jdbc:oracle:thin:@localhost:1521:csci585";
String userName = "SYSTEM";
String password = "123456";


Resolution:
1. Whole Range Query:
Select all the features in each type. Draw them on the panel according to Table 1.

2. Point Query:
For students, select the student which has distance less than 50 to the point. For announcements, select the announcement which has distance no more than max radius + 50, then check the announcement that the distance has to be no more than 50 + its radius. For buildings, select the builing that has anyinteract with the circle centered by the point and 50 as its radius. For each type, use sdo_nn to get the nearest feature type, color it yellow and others green.

3. Range Query:
In range query, after clicking the right button to close a polygon, you can click the right button again to delete the old one and draw a new polygon.
For announcements, select the announcement which has distance to the polygon less than the max announcement radius, then check the distance no more than its radius.
For students, select the students that has anyinteract with the polygon.
For buildings, selcet the buildings that has anyinteract with the polygon.

4. Surrounding Student Query:
When a point is clicked, use sdo_nn to choose the nearest announcement and color it white. due to @189, I use the announcement center.When the submit button is clicked, select the students inside the announcement system circle.

5. Emergency Qeery:
Similar to the first step of surrounding student query, color the nearest announment system white, which meas the system is broken. Then select the students within the circle. For each student, select the announcement system which is different from the broken one and the distance from the announcement center to the student minus the its circle radius is the minimum. If a student is in both circles, both of the distance number is 0.