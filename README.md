# Jobs Board Spring Boot

## Functionality
Includes:
- Spring Boot Web
- Spring Boot Actuator
- Spring Boot Security (JWT Token Management)
- Spring Boot JPA
- PostgreSQL Database (Docker Compose)
- Keycloak (Docker Compose)

## What this Does
The flow generally is:

- User registers.
- User logs in.
- User gets a JWT token.
- User uses Bearer token in Authorization header.
- User can get, add, update and delete Jobs.


## Authentication Stuff

### Keycloak
- Docker Compose sets up Keycloak.
- Login to Keycloak Admin Console
  - Choose admin/admin
- Create a new realm
  - Name: jobsboard
- Create a client:
  - Clients → Create
  - Client Type: OpenID Connect
  - Client ID: jobsboard-api
  - Root URL: leave blank or http://localhost:8080
  - Click Save
- On the client settings page:
  - Enable Standard Flow Enabled (for web-based login)
  - Enable Direct Access Grants Enabled (for password grant)
  - Save
- Create a user:
  - Go to Users → Add user
  - Username: testuser, Email, etc. → Save
  - Click Credentials tab → set a password (e.g., test123)
  - Turn off temporary password → Save
- Realm roles
  - Create 'admin' role.
  - Create 'general' role.
- Users
  - Select User
  - Role mapping.
  - Select the role for the user.

Once this is complete, keycloak is basically set up.

### Spring Boot Security
From this point:

- Run `docker-compose up` for keycloak in the docker/keycloak directory.
- Start the Application.

Spring Boot security has been set up to use keycloak's JWT tokens for authentication and use the roles to decide on permissions.
The [SecurityConfig](src/main/java/com/chrisp1985/jobsboard_backend/security/SecurityConfig.java) class sets the request matchers
to check that for PUT, DELETE and POST requests, the user needs to have an admin role set, whereas for GET any roles are allowed.
This can be more fine grained for each endpoint by setting `@PreAuthorize("hasRole('admin')")` for example on each of these endpoints.
So SecurityConfig could allow all on a POST, but on specific POSTs you might need an admin role.

### CORS
[Web Config](src/main/java/com/chrisp1985/jobsboard_backend/configuration/WebConfig.java) has been setup to allow access from
the localhost UI. When this is deployed, the CORS settings here would need to change from localhot.