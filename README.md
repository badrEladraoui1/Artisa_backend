# Artisa Backend

This backend application manages the Artisa platform, including user roles and their associated functionalities.

## Setting Up the Application

### Populating the Database with Roles

To populate your database with the required roles (**ADMIN**, **CLIENT**, **ARTISAN**), follow these steps:

1. Locate the `UserService` class in the codebase.
2. Uncomment the `@PostConstruct` method in `UserService`.
3. Restart the application to automatically insert the roles into the database.

### Required Roles

The following roles will be added to your database:

- **ADMIN**: For administrative users with full system access.
- **CLIENT**: For end-users who use the platform's services.
- **ARTISAN**: For artisans providing services on the platform.

## Next Steps

Once the roles are populated, you can proceed to create users and assign the appropriate roles. Use the relevant API endpoints or admin dashboard (if available) to manage users and their roles.
