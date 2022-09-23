# Tarkov-ammo-to-JSON-and-SQL

The program is intended for a full-stack wiki project, so I have required to download the data and then convert it into a local api created in MySQL and Spring.

-----------------------------------------

The data is downloaded thanks to a connection http to the scape from tarkov api which can be found at the following link.

[Tarkov-API](https://tarkov.dev/api/)

Once downloaded the data is saved in a json file and after that the program reads that json file and transforms it into a .sql file ready to be imported into a SQL database.

## To be taken into account

Please note that for the files to be saved correctly you will have to manipulate the "ROUTE" constant located in the TarkovDataSourceUpdater.java file and put between "" the path of your computer where you want to save the files.
