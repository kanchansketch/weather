Jforce task start 10:00PM --- 01:00PM
Challenges:
- No Google search => library and version confusion for some
- Need Weather API and Weather UI libraries and Graph Library

1. Login Page
   Field:
    - username
    - password
    - button
    - registration

2. Registration
   Field:
    - username
    - email
    - password
    - register button
    - login link

3. Home Page (Weather Dashboard)
   Key element:
    - Current Weather Display
        - Location selection
        - Temp, Weather Condition as icon,Wind Speed, Humidity, Visibility.
    - Search Location & Add Location for Quick Access Info
        - Button to add
        - List to select location
    - Weather Forcast as sperate section for same location 7 day
        - Display
            - Temp high/low
            - Weather condition and Icon
    - Weather Map like microsoft map
        - Optional to toggle different weather layer like radar, satellite,temperature
    - Alert via notication for weather for particular location selected

    - User Profile Section
        - Display user details
        - Save locations
        - Logout button

4. Admin Side
    - Manage User
        - View
        - Edit
        - Delete
        - Users Table
            - Usename,Email, Role, Action(Edit,Delete)

    - Weather Data Management
        - Admin Can Update or correct weather data for specific location
        - Data Source used for Weather information can be add or remove

5.System Monitoring
- RealTime performance of Dashboard show matrix like Full Page, Weather refresh,
action wise, Load time or response time overall users on pages.
- Error Logs Display
- View
- Resolve
- Within Retrieval and Display


### Requirement Understood
Challenges
- Weather API
- Weather Information Display Library

#Entity
- User
- username
- name
- email
- role
- password(hash)
- Roles
- USER
- ADMIN

	- Weather_Data
		- temperature
		- wind_speed
		- humidity
		- visibility
		- weather_icon_code
		- weather_icon_url
		- date_time_utc
	
	- Weather_Data_Forcast
		- high_temperature
		- low_temperature
		- weather_icon_code
		- weather_icon_url
		- date_time_utc
		
	- Location
		- location name
		- lat
		- long
		
	- Map_Type
		- name
		- code
		
	- Monitoring_Data
		- component
		- start_timestamp
		- end_timestamp
		- load_time


## Strategy to build challenges
1. Store data in local with updated datasource strategy at runtime with converter


APIS

/users CRUD
- GET
- POST
- PUT
- DELETE

/weather CRUD
- GET
- PUT

/weather_forecast CRUD
- GET
- PUT

/location
- GET
- POST
- PUT

/map
- GET
- POST
- PUT
		
		
	
		
		
	