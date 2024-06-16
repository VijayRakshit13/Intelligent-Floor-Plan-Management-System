# Floor Management System

This project implements a Floor Management System that allows users to manage floors, rooms, and bookings within an office environment. It includes functionalities for adding floors and rooms, booking rooms for meetings, and managing these bookings.

## Technologies Used

- Java
- Spring Boot
- Maven (for dependency management)
- Spring Security with JWT Authentication
- MySQL Database
- Postman (for API testing)

## Features

1. **Floor Management:**
   - Add new floors.
   - Retrieve all floors.
   - Update existing floors.
   - Delete floors.
   - Get floors by Floor Number.

2. **Room Management:**
   - Add new rooms to specific floors.
   - Retrieve all rooms.
   - Update existing rooms.
   - Delete rooms.
   - Get rooms in a floor.
   - Get room details by room number

3. **Booking Management:**
   - Book a room for a specified time period.
   - Update existing booking.
   - Show suggested rooms based on the required capacity.
   - Show preferred rooms based on last booking.
   - Cancel existing bookings.
   - Retrieve all bookings.
   - Retrieve bookings by user ID.

4. **Security:**
   - JWT-based authentication and authorization.
   - Admin and member roles with different access permissions.

## Setup Instructions

1. **Clone the repository and run the spring boot application.**
2. **Access the API at http://localhost:8080.**
3. **Use Postman to interact with the API endpoints.**


## List of API Endpoints:

### Authentication

- **POST /api/auth/register**: Register a new user with email, password, name, and role.
- **POST /api/auth/authenticate**: Authenticate and generate a JWT token for accessing protected resources.

### Floors

- **POST /api/floors/add**: Add a new floor with a specified floor number and version.
- **GET /api/floors/all**: Retrieve all floors available in the system.
- **GET /api/floors/{floorNo}**: Retrieve details of a specific floor by its floor number.
- **PUT /api/floors/{floorNo}**: Update details of a specific floor identified by its floor number.
- **DELETE /api/floors/{floorNo}**: Delete a floor with a specific floor number.

### Rooms

- **POST /api/rooms/add**: Create a new room with details like room number, name, and capacity.
- **GET /api/rooms/all**: Retrieve all rooms available in the system.
- **GET /api/rooms/{roomNo}**: Retrieve details of a specific room by its room number.
- **PUT /api/rooms/{roomNo}**: Update details of a specific room identified by its room number.
- **DELETE /api/rooms/{roomNo}**: Delete a room with a specific room number.
- **GET /api/rooms/{floorNo}/rooms**: Retrieve all rooms associated with a specific floor identified by its floor number.

### Bookings

- **POST /api/bookings/book**: Book a room for a specified time period, checking availability and user permissions.
- **POST /api/bookings/available-by-capacity**: Find rooms available based on capacity for a given time range.
- **POST /api/bookings/available-by-roomNo**: Check availability of a specific room for a given time range.
- **POST /api/bookings/suggested-rooms**: Get suggested meeting rooms based on capacity.
- **POST /api/bookings/preferred-room**: Get the preferred meeting room based on the user's last booking.
- **GET /api/bookings/all**: Retrieve all bookings made in the system.
- **GET /api/bookings/user/{bookedBy}**: Retrieve bookings made by a specific user identified by their email.
- **PUT /api/bookings/{bookingId}**: Update details of a specific booking identified by its booking ID.
- **DELETE /api/bookings/{bookingId}**: Cancel a booking identified by its booking ID, ensuring user authorization.
