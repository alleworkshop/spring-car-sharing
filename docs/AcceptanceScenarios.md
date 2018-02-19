# Acceptance scenarios

## User searches for a car nearby

User app sends user id and gps coordination, system returns location of nearest car.

### Acceptance scenario

Given there are cars A(2,2), B(1,1), C(2,2), D(4,4)

And user is at (0,0)

And A, B are available

When user searches for cars nearby by sending his location (lon/lat)

Then system returns [B, A] (sorted by distance)

### Alternative scenarios

#### No cars nearby

Given there are no cars within 10km radius 
When user searches for cars nearby by sending his location (lon/lat)
Then system returns information that there are no cars nearby

#### Cars at the same exact distance

Given there are two cars in exactly 1km distance 

When user searches for cars nearby by sending his location (lon/lat)

Then system returns information about both cars

## User rents a car

### Acceptance scenario

Given there is a free car with id 1

When user rents the car with id 1

Then system does not return this car to other users anymore

### Alternative scenarios

#### Renting a rented car

Given car with id 1 is rented

When user rents the car with id 1

Then system informs user about a failure

## User parks the car for later

### Acceptance scenario

Given user rented a car 1

When user parks the car 1

Then system still does not return this car to other users

## User returns a car

### Acceptance scenario

Given user A rented the car with id 1

When car with id 1 is returned

Then car 1 is available to other users

And user A is charged for the car


### Alternative scenarios

#### Returning a car that wasn't rented

Given car 1 was not rented

When car with id 1 is returned

Then system informs about a failure

## System sends a summary bill

### Acceptance scenario

Given the end of the day

And user 1 rented a car that day

Then system sends information about the summary of billings for that day to the user (ApplicationEventPublisher)

## System calculates cost

### Acceptance scenario

Given user rented a car once
and user drove distance_ km
and user drove for time_ min
and user parked for park_ min
When user asks the system for the cost of this rent
Then system returns the cost of the rent calculated to cost_ PLN
When 5 seconds have passed
and user drove distance2_ km
Then system returns the cost of the rent calculated to cost2_ PLN

### Examples

| distance_ | time_  | park_ | cost_ | distance2_ | cost2_ |
| -----     | -----  | ----- | ----- | -----      | -----  |
| 10		| 7      | 2     | 11.7  | 1          | 13.0   |  
| 0			| 0		 | 0     | 0     | 0          |	0.5    |
| 1			| 1		 | 1     | 1.4	 | 0          |	1.9    |
| 0			| 0		 | 0	 | 0	 | 1	      |	1.3    |
| 1			| 0		 | 0	 | 0.8	 | 0	      |	error  |
| 0			| 0		 | 0	 | 0	 | 1	      |	error  |
| 0			| 1		 | 1	 | 0.6	 | 0	      |	1.1    |
| 0			| 0		 | 1	 | 0.1	 | 0	      |	0.6    |
