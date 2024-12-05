# Create and populate the database in **SQLite**. 
# You can use the SQLite3 tool which is included with Python, to work with SQLite databases.

# IMPORT PACKAGES =======================================================================================================================================================================

import os
import sqlite3
import re
from simple_term_menu import TerminalMenu
from datetime import datetime
from tabulate import tabulate

# DATABASE CONNECTION ======================================================================================================================

connection = sqlite3.connect("biggles.db")
cursor = connection.cursor()

# TABLE CREATION AND POPULATION FUNCTIONS ================================================================================================================================================

def createTables():
        print("Creating Tables")
        # Create table for pilots - ID, first name, last name
        cursor.execute("CREATE TABLE pilots (pilot_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, first_name TEXT, last_name TEXT)")

        # Create table for route - route id, departure destination, arrival destination
        cursor.execute("CREATE TABLE routes (route_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, departure_destination TEXT, arrival_destination TEXT, FOREIGN KEY (departure_destination) REFERENCES IATA_code (destinations), FOREIGN KEY (arrival_destination) REFERENCES IATA_code (destinations))")
        
        # Create table for flights - flight id, route id, departure time, departure date, arrival time, arrival date, status id
        cursor.execute("CREATE TABLE flights (flight_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, route_id INT, departure_time DATETIME, arrival_time DATETIME, status_id INT, FOREIGN KEY (route_id) REFERENCES routes (route_id))")
       
        # Create table for rosters - ID, pilot ID, flight ID
        cursor.execute("CREATE TABLE roster (roster_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, pilot_id INT, flight_id INT, FOREIGN KEY (pilot_id) REFERENCES pilots (pilot_id), FOREIGN KEY (flight_id) REFERENCES flights (flight_id))")
        
        # Create table for statuses - status id, status
        cursor.execute("CREATE TABLE status (status_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, status TEXT)")

        # Create table for destinations - IATA code, name, city, timezone
        cursor.execute("CREATE TABLE destinations (IATA_code TEXT NOT NULL PRIMARY KEY, name TEXT, city TEXT, timezone DATETIME)")

# Populate pilot table
def populatePilotTable():
        cursor.execute("INSERT INTO pilots VALUES (101, 'Ivor', 'Playne')")
        cursor.execute("INSERT INTO pilots VALUES (102, 'Emma', 'Flynn')")
        cursor.execute("INSERT INTO pilots VALUES (103, 'Cruz', 'Innaltitude')")
        cursor.execute("INSERT INTO pilots VALUES (104, 'Turner', 'Fasihtba-Al-Hussain')")
        cursor.execute("INSERT INTO pilots VALUES (105, 'Harry', 'Long')")
        cursor.execute("INSERT INTO pilots VALUES (106, 'Terry', 'Minal')")
        cursor.execute("INSERT INTO pilots VALUES (107, 'Kerry', 'Sell')")
        cursor.execute("INSERT INTO pilots VALUES (108, 'Herr', 'Miles')")
        cursor.execute("INSERT INTO pilots VALUES (109, 'Bo', 'Ng')")
        cursor.execute("INSERT INTO pilots VALUES (110, 'Connor', 'Chord')")
        cursor.execute("INSERT INTO pilots VALUES (111, 'Jim', 'Bowgette')")
        cursor.execute("INSERT INTO pilots VALUES (100, 'Monsieur', 'Schmitt')")
        return

# Populate roster table
def populateRosterTable():
        cursor.execute("INSERT INTO roster VALUES (100, 100, 111)")
        cursor.execute("INSERT INTO roster VALUES (101, 100, 108)")
        cursor.execute("INSERT INTO roster VALUES (102, 101, 106)")
        cursor.execute("INSERT INTO roster VALUES (103, 102, 106)")
        cursor.execute("INSERT INTO roster VALUES (104, 102, 105)")
        cursor.execute("INSERT INTO roster VALUES (105, 103, 105)")
        cursor.execute("INSERT INTO roster VALUES (106, 104, 105)")
        cursor.execute("INSERT INTO roster VALUES (107, 104, 104)")
        cursor.execute("INSERT INTO roster VALUES (108, 104, 103)")
        cursor.execute("INSERT INTO roster VALUES (109, 105, 103)")
        cursor.execute("INSERT INTO roster VALUES (110, 106, 102)")
        cursor.execute("INSERT INTO roster VALUES (111, 107, 102)")
        cursor.execute("INSERT INTO roster VALUES (112, 108, 101)")
        cursor.execute("INSERT INTO roster VALUES (113, 109, 101)")
        cursor.execute("INSERT INTO roster VALUES (114, 109, 100)")
        return

# Populate flight table
def populateFlightsTable():
        cursor.execute("INSERT INTO flights VALUES (100, 100, '2024-06-14 12:22', '2024-06-14 16:43', 10)");
        cursor.execute("INSERT INTO flights VALUES (101, 114, '2024-04-04 10:46', '2024-04-04 15:10', 12)");
        cursor.execute("INSERT INTO flights VALUES (102, 113, '2024-11-08 00:54', '2024-11-08 05:37', 14)");
        cursor.execute("INSERT INTO flights VALUES (103, 112, '2024-01-12 23:07', '2024-01-13 04:49', 13)");
        cursor.execute("INSERT INTO flights VALUES (104, 111, '2024-07-25 13:39', '2024-07-25 18:44', 11)");
        cursor.execute("INSERT INTO flights VALUES (105, 110, '2024-05-07 02:13', '2024-05-07 07:56', 15)");
        cursor.execute("INSERT INTO flights VALUES (106, 109, '2024-09-16 19:51', '2024-09-17 00:36', 10)");
        cursor.execute("INSERT INTO flights VALUES (107, 108, '2024-02-21 09:25', '2024-02-21 14:07', 12)");
        cursor.execute("INSERT INTO flights VALUES (108, 107, '2024-12-03 20:38', '2024-12-04 01:24', 14)");
        cursor.execute("INSERT INTO flights VALUES (109, 106, '2024-03-18 16:02', '2024-03-18 21:43', 13)");
        cursor.execute("INSERT INTO flights VALUES (110, 105, '2024-08-30 05:16', '2024-08-30 10:55', 11)");
        cursor.execute("INSERT INTO flights VALUES (111, 104, '2024-10-22 21:33', '2024-10-23 02:14', 15)");
        cursor.execute("INSERT INTO flights VALUES (112, 103, '2024-01-05 07:47', '2024-01-05 12:41', 10)");
        cursor.execute("INSERT INTO flights VALUES (113, 102, '2024-06-19 18:20', '2024-06-19 23:09', 12)");
        cursor.execute("INSERT INTO flights VALUES (114, 101, '2024-11-11 11:56', '2024-11-11 16:38', 14)");
        return

# Populate destination table
def populateDestinationsTable():
        cursor.execute("INSERT INTO destinations VALUES ('LHR', 'London Heathrow Airport', 'London', '+00:00')");
        cursor.execute("INSERT INTO destinations VALUES ('CDG', 'Charles de Gaulle Airport', 'Paris', '+01:00')");
        cursor.execute("INSERT INTO destinations VALUES ('AMS', 'Amsterdam Airport Schiphol', 'Amsterdam', '+01:00')");
        cursor.execute("INSERT INTO destinations VALUES ('FRA', 'Frankfurt Airport', 'Frankfurt', '+01:00')");
        cursor.execute("INSERT INTO destinations VALUES ('MAD', 'Adolfo Suárez Madrid–Barajas Airport', 'Madrid', '+01:00')");
        cursor.execute("INSERT INTO destinations VALUES ('BCN', 'Barcelona–El Prat Airport', 'Barcelona', '+01:00')");
        cursor.execute("INSERT INTO destinations VALUES ('MUC', 'Munich Airport', 'Munich', '+01:00')");
        cursor.execute("INSERT INTO destinations VALUES ('IST', 'Istanbul Airport', 'Istanbul', '+03:00')");
        cursor.execute("INSERT INTO destinations VALUES ('DME', 'Domodedovo International Airport', 'Moscow', '+03:00')");
        cursor.execute("INSERT INTO destinations VALUES ('DXB', 'Dubai International Airport', 'Dubai', '+04:00')");
        cursor.execute("INSERT INTO destinations VALUES ('HND', 'Tokyo Haneda Airport', 'Tokyo', '+09:00')");
        cursor.execute("INSERT INTO destinations VALUES ('PEK', 'Beijing Capital International Airport', 'Beijing', '+08:00')");
        cursor.execute("INSERT INTO destinations VALUES ('HKG', 'Hong Kong International Airport', 'Hong Kong', '+08:00')");
        cursor.execute("INSERT INTO destinations VALUES ('SIN', 'Singapore Changi Airport', 'Singapore', '+08:00')");
        cursor.execute("INSERT INTO destinations VALUES ('SYD', 'Sydney Airport', 'Sydney', '+11:00')");
        return

# Populate route table
def populateRouteTable():
        cursor.execute("INSERT INTO routes VALUES (100, 'SYD', 'LHR')");
        cursor.execute("INSERT INTO routes VALUES (101, 'LHR', 'CDG')");
        cursor.execute("INSERT INTO routes VALUES (102, 'CDG', 'AMS')");
        cursor.execute("INSERT INTO routes VALUES (103, 'AMS', 'FRA')");
        cursor.execute("INSERT INTO routes VALUES (104, 'FRA', 'MAD')");
        cursor.execute("INSERT INTO routes VALUES (105, 'MAD', 'BCN')");
        cursor.execute("INSERT INTO routes VALUES (106, 'BCN', 'MUC')");
        cursor.execute("INSERT INTO routes VALUES (107, 'MUC', 'IST')");
        cursor.execute("INSERT INTO routes VALUES (108, 'IST', 'DME')");
        cursor.execute("INSERT INTO routes VALUES (109, 'DME', 'DXB')");
        cursor.execute("INSERT INTO routes VALUES (110, 'DXB', 'HND')");
        cursor.execute("INSERT INTO routes VALUES (111, 'HND', 'PEK')");
        cursor.execute("INSERT INTO routes VALUES (112, 'PEK', 'HKG')");
        cursor.execute("INSERT INTO routes VALUES (113, 'HKG', 'SIN')");
        cursor.execute("INSERT INTO routes VALUES (114, 'SIN', 'SYD')");
        return

# Populate status table
def populateStatusTable():
        cursor.execute("INSERT INTO status VALUES (10, 'On time')");
        cursor.execute("INSERT INTO status VALUES (11, 'Delayed')");
        cursor.execute("INSERT INTO status VALUES (12, 'Cancelled')");
        cursor.execute("INSERT INTO status VALUES (13, 'Go to gate')");
        cursor.execute("INSERT INTO status VALUES (14, 'Boarding')");
        cursor.execute("INSERT INTO status VALUES (15, 'En-route')");
        cursor.execute("INSERT INTO status VALUES (16, 'Landed')");
        cursor.execute("INSERT INTO status VALUES (17, 'Baggage Removed')");
        cursor.execute("INSERT INTO status VALUES (18, 'Complete')");
        return

# Populate each table with **sample data** (10–15 records per table) to facilitate testing and demonstration.
def populateTables():
        print("Populating Tables")
        populatePilotTable()
        populateRosterTable()
        populateFlightsTable()
        populateRouteTable()
        populateStatusTable()
        populateDestinationsTable()
        return
0
# END OF TABLE CREATION AND POPULATION FUNCTIONS ================================================================================================================================================

# VALIDATION FUNCTIONS ================================================================================================================================================

# User inputs a pilot ID, function returns a valid pilot ID, checking if it's in use or not

def getPilot():
        while True:
                try:
                        viewAllPilots()
                        pilot_id = int(input("Enter the pilot ID from the options above: "))
                        rows = cursor.execute("SELECT * FROM pilots WHERE pilot_id = ?", (pilot_id,)).fetchall()
                        if not rows:
                                print("No such pilot exists")
                                continue
                        return pilot_id
                except ValueError:
                        print("Please enter an integer number")
                        continue

#  Request an IATA code from the user, check that it takes the correct form. If it's for a new destination, check it doesn't already exist; if it's for an existing destination, check that it does exist     

def getDestination(old_new):
        while True:
                
                if old_new == "old":
                        try: 
                                print("Enter one of the following IATA codes for the destination you want to delete: ")
                                rows = cursor.execute("SELECT IATA_code FROM destinations").fetchall()
                                column_names = [description[0] for description in cursor.description]
                                print(tabulate(rows, headers=column_names))
                                dest = str(input ("Enter IATA code: ")).upper()
                                if not (dest.isalpha() and len(dest) == 3):
                                        print("Please enter a three-letter airport code (e.g. JFK)")
                                        continue
                                rows = cursor.execute("SELECT * FROM destinations WHERE IATA_code = ?", (dest,)).fetchall()     
                                if not rows:
                                        print(f"No such destination {dest}")
                                        continue
                        except Exception as e:
                                        print(f"An error occurred: {e}")
                                        break
                elif old_new == "new":
                        try:
                                dest = str(input ("Enter airport IATA code: ")).upper()
                                if not (dest.isalpha() and len(dest) == 3):
                                        print("Please enter a three-letter airport code (e.g. JFK)")
                                        continue
                                rows = cursor.execute("SELECT * FROM destinations WHERE IATA_code = ?", (dest,)).fetchall()     
                                if rows:
                                        print(f"{dest} already exists as a destination")
                                        continue
                        except Exception as e:
                                        print(f"An error occurred: {e}")
                                        break
                else:
                        print("Incorrect argument passsed to getDestination procedure")
                return dest
                        
 # Request a flight ID from the user, check that it takes the correct form and that it exists then return it      

def getFlight():
        while True:
                try:
                        viewAllFlights()
                        flight_id = int(input("Enter the flight ID from the options above: "))
                        rows = cursor.execute("SELECT * FROM flights WHERE flight_id = ?", (flight_id,)).fetchall()
                        if not rows:
                                print("No such flight exists")
                                continue
                        return flight_id
                except ValueError:
                        print("Please enter an integer number")
                        continue


 # Request a DATETIME from the user, check that it takes the correct form then return it      

def getDateTime():
    while True:
        try:
            date_input = input("Enter date (YYYY-MM-DD [HH:MM]): ")
            
            try:
                date = datetime.strptime(date_input, "%Y-%m-%d %H:%M")
            except ValueError:
                date = datetime.strptime(date_input, "%Y-%m-%d")
            
            if date.year < 2023 or date.year > 2100:
                print("Year out of range")
                continue
            
            if len(date_input.split()) > 1:
                return date.strftime("%Y-%m-%d %H:%M")
            return date.strftime("%Y-%m-%d")
        
        except ValueError:
            print("Invalid date format. Use YYYY-MM-DD [HH:MM]")

 # Request a status ID from the user, check that it takes the correct form and that it exists then return it      

def getStatus():
        while True:
                try:
                        rows = cursor.execute("SELECT * FROM status").fetchall()
                        column_names = [description[0] for description in cursor.description]
                        print(tabulate(rows, headers=column_names))
                        status_id = int(input("Enter the status ID from the options above: "))
                        rows = cursor.execute("SELECT * FROM status WHERE status_id = ?", (status_id,)).fetchall()
                        if not rows:
                                print("No such status exists")
                                continue
                        status = cursor.execute("SELECT status FROM status WHERE status_id = ?", (status_id,)).fetchall()
                        print("You selected status:" + str(status))
                        return status_id
                except ValueError:
                        print("Please enter an integer number")
                        continue
 
 # Request a route ID from the user, check that it takes the correct form and that it exists then return it      

def getRoute():
        while True:
                try:
                        rows = cursor.execute("SELECT * FROM routes").fetchall()
                        column_names = [description[0] for description in cursor.description]
                        print(tabulate(rows, headers=column_names))
                        route_id = int(input("Enter the route ID from the options above: "))
                        rows = cursor.execute("SELECT * FROM routes WHERE route_id = ?", (route_id,)).fetchall()
                        if not rows:
                                print("No such route exists")
                                continue
                        print("You selected route:" + str(route_id))
                        return route_id
                except ValueError:
                        print("Please enter an integer number")
                        continue

 # Request a string from the user, validate it is an alpha string (with spaces) then return it       

def getString():
        while True:
                string = input("Enter here:")
                if not (string.replace(" ","").isalpha() or len(string)<3):
                        print("Please enter a valid name")
                        continue
                return string
 
 # Request a UTF offset from the user, validate it is taking the correct form then return it       

def getUTCOffset():
    while True:
        try:
            offset = input("Enter UTC offset (±HH:MM): ")
            if bool(re.match(r'^[+-]\d{2}:\d{2}$', offset)):
                    return offset
            else:
                    print("Incorrect format")
                    continue
        except Exception as e:
            print(f"Error {e}")

# END OF VALIDATION FUNCTIONS ===============================================================================================================================================================================================

# FLIGHT FUNCTIONS ==========================================================================================================================================================================================================

 # Let the user add a new flight; ensure that they then add a pilot         

def addFlight():
        route = getRoute()
        print("Enter departure time")
        dep_time = getDateTime()
        print("Enter arrival time")
        arr_time = getDateTime()
        cursor.execute("INSERT INTO flights (route_id, departure_time, arrival_time, status_id) VALUES (?, ?, ?, 10)", (route, dep_time, arr_time,))
        print("Flight added")
        # Display details of the flight just added
        last_inserted_id = cursor.lastrowid
        rows = cursor.execute("SELECT f.flight_id AS ID, r.departure_destination AS 'from', f.departure_time AS dep, r.arrival_destination AS 'to', f.arrival_time AS arr, s.status AS stat FROM flights as f INNER JOIN routes as r on f.route_id=r.route_id LEFT JOIN status as s on f.status_id = s.status_id WHERE f.flight_id = ?", (last_inserted_id,)).fetchall()
        column_names = [description[0] for description in cursor.description]
        print(tabulate(rows, headers=column_names))
        f_id = cursor.execute("SELECT flight_id FROM flights WHERE rowid = ?", (last_inserted_id,)).fetchall()
        print(f"Now add a pilot to flight {cursor.lastrowid}")
        assignPilotToFlight()
        return

# Deletes a flight from the database

def deleteFlight():
        print("Delete Flight")
        deleted_flight = getFlight()
        cursor.execute("DELETE FROM flights WHERE flight_id = ?", (deleted_flight,))
        print("You have deleted flight " + str(deleted_flight))
        return

# Allows user to update a flight's status and times (not route)
def updateFlights():
        print("Please enter the flight ID of the flight you want to update")
        changed_flight = getFlight()
        print("Please enter the new flight status: ")
        stat = getStatus()
        print("Please enter the new departure date and time: ")
        new_dep_time = getDateTime()
        print("Please enter the new arrival date and time: ")
        new_arr_time = getDateTime()
        while True:
                try:
                        cursor.execute( "UPDATE flights SET status_id = ?, departure_time = ?, arrival_time =? WHERE flight_id = ?", (stat, new_dep_time, new_arr_time, changed_flight,))
                        rows = cursor.execute("SELECT f.flight_id AS ID, r.departure_destination AS 'from', f.departure_time AS dep, r.arrival_destination AS 'to', f.arrival_time AS arr, s.status AS stat FROM flights as f INNER JOIN routes as r on f.route_id=r.route_id INNER JOIN status as s on f.status_id = s.status_id WHERE f.flight_id = ?", (changed_flight,)).fetchall()
                        column_names = [description[0] for description in cursor.description]
                        print(column_names)
                        print(rows)
                except Exception as e:
                        print(f"Error! {e}")
                        return 
# Shows users all flights in the database

def viewAllFlights():
        while True:
                try:
                        rows = cursor.execute("SELECT f.flight_id AS ID, r.departure_destination AS 'from', f.departure_time AS dep, r.arrival_destination AS 'to', f.arrival_time AS arr, s.status AS stat FROM flights as f INNER JOIN routes as r on f.route_id=r.route_id INNER JOIN status as s on f.status_id = s.status_id").fetchall()
                        column_names = [description[0] for description in cursor.description]
                        print(tabulate(rows, headers=column_names))
                        return
                except Exception as e:
                        print(f"Error {e}")

# Shows users flights that are arriving to from a particular airport         

def viewFlightsByArrivalLocation():
        while True:
                dest = getDestination("old")
                try: 
                        rows = cursor.execute("SELECT f.flight_id AS ID, r.departure_destination AS 'from', f.departure_time AS dep, r.arrival_destination AS 'to', f.arrival_time AS arr FROM flights as f INNER JOIN routes as r on f.route_id=r.route_id WHERE r.departure_destination = ?", (dest,)).fetchall()     
                        if not rows:
                                print(f"No flights found for destination {dest}")
                                return
                        else:
                                column_names = [description[0] for description in cursor.description]
                                print(tabulate(rows, headers=column_names))
                                return
                except Exception as e:
                                print(f"An error occurred: {e}")
                                break

# Shows users flights that are leaving from a particular departure airport         
def viewFlightsByDepartureLocation():
        print("View Flights by Departure Location")
        
        while True:
                dest = getDestination("old")      
                try:
                        rows = cursor.execute("SELECT f.flight_id AS ID, r.departure_destination AS 'from', f.departure_time AS dep, r.arrival_destination AS 'to', f.arrival_time AS arr FROM flights as f INNER JOIN routes as r on f.route_id=r.route_id WHERE r.departure_destination = ?", (dest,)).fetchall()
                        if not rows:
                                print(f"No flights found for that destination {dest}")
                                return
                        else:
                                column_names = [description[0] for description in cursor.description]
                                print(tabulate(rows, headers=column_names))
                                return
                except Exception as e:
                        print(f"An error occurred: {e}")

# Shows users flights that are leaving on a particular date
#           
def viewFlightsByDepartureDate():
        print("View Flights by Departure Date")
        date = getDateTime()
        while True:
                try:
                        rows = cursor.execute("SELECT f.flight_id AS ID, r.departure_destination AS 'from', f.departure_time AS dep, r.arrival_destination AS 'to', f.arrival_time AS arr, s.status AS stat FROM flights as f INNER JOIN routes as r on f.route_id=r.route_id LEFT JOIN status as s on f.status_id = s.status_id WHERE substr(f.departure_time,1,10) = ?", (date,)).fetchall()
                        if not rows:
                                print("No flights on that date")
                                return
                        column_names = [description[0] for description in cursor.description]
                        print(tabulate(rows, headers=column_names))
                        return
                except Exception as e:
                        print(f"Error {e}")

# Allows user to view details of flights that have a given status
def viewFlightsByStatus():
        print("View Flights by Status")
        while True:
                try:
                        stat = getStatus()
                        rows = cursor.execute("SELECT f.flight_id AS ID, r.departure_destination AS 'from', f.departure_time AS dep, r.arrival_destination AS 'to', f.arrival_time AS arr, s.status AS stat FROM flights as f INNER JOIN routes as r on f.route_id=r.route_id LEFT JOIN status as s on f.status_id = s.status_id WHERE s.status_id = ?", (stat,)).fetchall()
                        column_names = [description[0] for description in cursor.description]
                        print(tabulate(rows, headers=column_names))
                        return
                except Exception as e:  
                        print(f"Error {e}")

# END OF FLIGHT FUNCTIONS =====================================================================================================================================================================================================================

# PILOT FUNCTIONS =============================================================================================================================================================================================================================
 
# Add a new pilot    

def addPilot():
        print("Enter Details of New Pilot")
        print("Enter pilot's first name:")
        new_pilot_name = getString()
        print("Enter pilot's last name")
        new_pilot_surname = getString()
        while True:
                try:
                        cursor.execute("INSERT INTO pilots (first_name, last_name) VALUES (?, ?)", (new_pilot_name, new_pilot_surname,))
                        print("Pilot " + new_pilot_name + " " + new_pilot_surname + " added")
                except Exception as e:
                        print(f"Error! {e}")
                        return

# Update an existing pilot

def updatePilot():
        print("Please enter the details to update the pilot's details")

        changed_pilot = getPilot()
        while True:
                try:
                        rows = cursor.execute("SELECT * FROM pilots WHERE pilot_id = ?", (changed_pilot,)).fetchall()
                        print("The pilot's current details are:")
                        column_names = [description[0] for description in cursor.description]
                        print(tabulate(rows, headers=column_names))
                        new_name = input ("New first name: ")
                        new_surname = input ("New surname: ")
                        cursor.execute( "UPDATE pilots SET first_name = ?, last_name = ? WHERE pilot_id = ?", (new_name, new_surname, changed_pilot,))
                        rows = cursor.execute("SELECT * FROM pilots WHERE pilot_id = ?", (changed_pilot,)).fetchall()
                        print("The new pilot's details are:")
                        column_names = [description[0] for description in cursor.description]
                        print(tabulate(rows, headers=column_names))
                except Exception as e:
                        print(f"Error! {e}")
                return 

# Delate a pilot
    
def deletePilot():
        print("Delete Pilot")
        deleted_pilot = getPilot()  
        while True:
                try:
                        cursor.execute("DELETE FROM pilots WHERE pilot_id = ?", (deleted_pilot,))
                        print("You have deleted pilot " + str(deleted_pilot))
                        return
                except Exception as e:
                        print(f"Error! {e}")

# See which flights and routes a given pilot is scheduled to fly on; order them by date

def viewAllPilots():
        print("View All Pilots")
        rows = cursor.execute("SELECT * FROM pilots").fetchall()
        column_names = [description[0] for description in cursor.description]
        print(tabulate(rows, headers=column_names))
        return

def viewRosterByPilot():
        while True:
                print("View an individual pilot's schedule")
                pilot = getPilot()
                try:                        
                        rows = cursor.execute("SELECT flights.departure_time, routes.departure_destination, departures.city, flights.arrival_time, routes.arrival_destination, arrivals.city FROM flights INNER JOIN roster ON flights.flight_id = roster.flight_id INNER JOIN routes ON flights.route_id = routes.route_id LEFT JOIN destinations AS arrivals ON routes.arrival_destination = arrivals.IATA_code LEFT JOIN destinations AS departures ON routes.departure_destination = departures.IATA_code WHERE roster.pilot_id = ? ORDER BY flights.arrival_time ", (pilot,)).fetchall()
                        if not rows:
                                print("This pilot is not scheduled on any flights")
                                return
                        else:
                                column_names = [description[0] for description in cursor.description]
                                print(tabulate(rows, headers=column_names))
                                return
                except Exception as e:
                        print(f"ERROR! {e}")
                        break
        return

# Show the total number of flights each pilot is rostered onto

def viewNoFlightsByPilot():
        print("View number of flights by pilot")
        rows = cursor.execute("SELECT pilots.pilot_id, pilots.first_name, pilots.last_name, COUNT (DISTINCT flights.flight_id) AS no_flights FROM pilots LEFT JOIN roster ON pilots.pilot_id=roster.pilot_id LEFT JOIN flights ON roster.flight_id = flights.flight_id GROUP BY pilots.pilot_id, pilots.first_name, pilots.last_name").fetchall()
        column_names = [description[0] for description in cursor.description]
        print(tabulate(rows, headers=column_names))
        return

# Assign a pilot to a flight

def assignPilotToFlight():
        print("Assign a pilot to a flight")
        print("First select a flight")
        assigned_flight = getFlight()
        print("Now select a pilot")
        assigned_pilot = getPilot()
        try:
                cursor.execute("INSERT INTO roster (pilot_id, flight_id) VALUES (?, ?)", (assigned_pilot,assigned_flight,))
                print("Pilot " + str(assigned_pilot) + " assigned to flight " + str(assigned_flight))
                lastrowid = cursor.lastrowid
                rows = cursor.execute("SELECT flights.departure_time, routes.departure_destination, departures.city, flights.arrival_time, routes.arrival_destination, arrivals.city FROM flights INNER JOIN roster ON flights.flight_id = roster.flight_id INNER JOIN routes ON flights.route_id = routes.route_id LEFT JOIN destinations AS arrivals ON routes.arrival_destination = arrivals.IATA_code LEFT JOIN destinations AS departures ON routes.departure_destination = departures.IATA_code WHERE roster.rowid = ? ", (lastrowid,)).fetchall()
                column_names = [description[0] for description in cursor.description]
                print(tabulate(rows, headers=column_names))
        except Exception as e:
                print(f"ERROR! {e}")
        return

# END OF PILOT FUNCTIONS =============================================================================================================================================================================================================

# DESTINATION FUNCTIONS ==============================================================================================================================================================================================================

def viewDestinations():
        print("Destinations:")
        rows = cursor.execute("SELECT * FROM destinations").fetchall()
        column_names = [description[0] for description in cursor.description]
        print(tabulate(rows, headers=column_names))
        return

def addDestination():
        print("Enter Details of New Destination")
        print("Enter the IATA code of the new airport")
        new_IATA_code = getDestination("new")
        print("Enter the name of the new airport")
        new_name = getString()
        print("Enter the name of the city")
        new_city = getString()
        new_offset = getUTCOffset()
        cursor.execute("INSERT INTO destinations VALUES (?, ?,?,?)", (new_IATA_code, new_name, new_city, new_offset))
        print("Destination added")
        lastrowid = cursor.lastrowid
        rows = cursor.execute("SELECT * FROM destinations WHERE rowid = ?",(lastrowid,)).fetchall()
        column_names = [description[0] for description in cursor.description]
        print(tabulate(rows, headers=column_names))
        return
        
def updateDestination():
        print("Update a destination's timezone")
        dest = getDestination("old")
        new_offset = getUTCOffset()
        cursor.execute( "UPDATE destinations SET timezone = ? WHERE IATA_code = ?", (new_offset, dest,))
        rows = cursor.execute("SELECT * FROM destinations WHERE IATA_code = ?", (dest,)).fetchall()
        column_names = [description[0] for description in cursor.description]
        print(tabulate(rows, headers=column_names))
        return

def deleteDestination():
        print("Delete Destination")
        deleted_dest = getDestination("old")
        cursor.execute("DELETE FROM destinations WHERE IATA_code = ?", (deleted_dest,))
        print("You have deleted destination " + str(deleted_dest))
        return

# END OF DESTINATION FUNCTIONS ===========================================================================================================================================================

# MENU FUNCTIONS =========================================================================================================================================================================

# Menu when a user selects View Flights from Flights Menu

def viewFlightsMenu():
        options = ["[1] by Arrival location", "[2] by Departure location", "[3] by Departure Date", "[4] by Status", "[5] all Flights", "[6] back"]     
        terminal_menu = TerminalMenu(options, title=" View Flights Menu")
        menu_entry_index = terminal_menu.show()
        print(f"You have selected {options[menu_entry_index]}")
        if (menu_entry_index == 0 ):
                viewFlightsByArrivalLocation()
        elif (menu_entry_index == 1):
                viewFlightsByDepartureLocation()
        elif (menu_entry_index == 2):
                viewFlightsByDepartureDate()
        elif (menu_entry_index == 3):
                viewFlightsByStatus()
        elif (menu_entry_index == 4):
                viewAllFlights()
        elif (menu_entry_index == 5):
                flightsMenu()   
        return

# Menu when a user selects flights from Main Menu

def flightsMenu():
        options = ["[1] add flight", "[2] update flight", "[3] delete flights", "[4] view flights", "[5] back"]
        terminal_menu = TerminalMenu(options, title=" Flights Menu")
        menu_entry_index = terminal_menu.show()
        print(f"You have selected {options[menu_entry_index]}")
        if (menu_entry_index == 0 ):
                addFlight()
        elif (menu_entry_index == 1):
                updateFlights()
        elif (menu_entry_index == 2):
                deleteFlight()
        elif (menu_entry_index == 3):
                viewFlightsMenu()
        elif (menu_entry_index == 4):
                mainMenu()
        return

# Menu when a user selects pilots from Main Menu

def pilotsMenu():
        options = ["[1] add pilot", "[2] update pilot", "[3] delete pilot", "[4] view a pilot's schedule", "[5] show number of flights by pilot", "[6] assign pilot to flight", "[7] back"]
        terminal_menu = TerminalMenu(options, title=" Pilots Menu")
        menu_entry_index = terminal_menu.show()
        print(f"You have selected {options[menu_entry_index]}")
        if (menu_entry_index == 0 ):
                addPilot()
        elif (menu_entry_index == 1):
                updatePilot()
        elif (menu_entry_index == 2):
                deletePilot()
        elif (menu_entry_index == 3):
                viewRosterByPilot()
        elif (menu_entry_index == 4):
                viewNoFlightsByPilot()
        elif (menu_entry_index == 5):
                assignPilotToFlight()
        elif (menu_entry_index == 6):
                mainMenu()
        return

# Menu when a user selects destinations from Main Menu

def destinationsMenu():
        options = ["[1] Add", "[2] Update", "[3] Delete", "[4] View", "[5] back"]
        terminal_menu = TerminalMenu(options, title=" Destinations Menu")
        menu_entry_index = terminal_menu.show()
        print(f"You have selected {options[menu_entry_index]}")
        if (menu_entry_index == 0 ):
                addDestination()
        elif (menu_entry_index == 1):
                updateDestination()
        elif (menu_entry_index ==2):
                deleteDestination()
        elif (menu_entry_index == 3):
                viewDestinations()
        elif (menu_entry_index == 4):
                mainMenu()
        return

# Main menu

def mainMenu():
        while True:
                options = ["[1] flights", "[2] pilots", "[3] destinations", "[4] exit"]
                terminal_menu = TerminalMenu(options, title=" Main Menu")
                menu_entry_index = terminal_menu.show()
                print(f"You have selected {options[menu_entry_index]}")
                if (menu_entry_index == 0 ):
                        flightsMenu()
                elif (menu_entry_index == 1):
                        pilotsMenu()
                elif (menu_entry_index == 2):
                        destinationsMenu()
                elif (menu_entry_index == 3):
                        return

# END OF MENU FUNCTIONS ===================================================

# MAIN FUNCTIONS ==========================================================

createTables()
populateTables()
mainMenu()


# 1 - Flights
#       1 Add Flight (END)

# Determine which row number this will be
# Ask for route ID - select from menu
# Ask for departure date - YYYYMMDD
# Ask for departure time - HH:MM
# Default initial status to "on time"
# Insert into database
#       user_input = input("Option: ")
#       cursor.execute("INSERT INTO flights VALUES (flight_id, route_id, departure_time, arrival_time, status_id)");

#       2 Update Flight
#               1 Update Status
#               2 Update Departure Time
#                       cursor.execute( "UPDATE flights SET departure_time = ? WHERE flight_id = ?", (new_departure_time, changed_flight))
#               3 Update Arrival Time
#                       cursor.execute( "UPDATE flights SET arrival_time = ? WHERE flight_id = ?", (new_arrival_time, changed_flight))
#               4 Back        
#       3 View Flights
#               1 Filter by Destination
#               2 Filter by Status
#               3 Filter by Departure Date
#               4 Back
#       4 Back
# 2 - Pilots
#       1 - View Roster by Pilot
#               1 - Pilot 1
#               2 - Pilot 2
#               3 ...
#               4 Back
#       2 - View Number of flights per pilot
#       3 - Back
# 3 - Destinations
#       1 - View Destinations
#       2 - Update Destination
#               1 - Update IATA code
#               2 - Updage Name
#               3 - Update City
#               4 - Update Timezone
#               5 - Back
#       3 - Back


#   - **Add a New Flight**



#   - **View Flights by Criteria**

#       **Flight Retrieval**: Retrieve flights based on multiple criteria, such as destination, status, or departure date.
#   - **Update Flight Information**
#       **Schedule Modification**: Update flight schedules (e.g., change departure time or status).
#   - **Assign Pilot to Flight**
#       **Pilot Assignment**: Assign pilots to flights
#   - **View Pilot Schedule**
#       **retrieve information about pilot schedules.
#       ** or the number of flights assigned to a pilot.
#   - **View/Update Destination Information**
        # **Destination Management**: View and update destination information as required.

        # the number of flights to each destination 
# Ensure the interface displays results clearly and allows users to make changes based on input.