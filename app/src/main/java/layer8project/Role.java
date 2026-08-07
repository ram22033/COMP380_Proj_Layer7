package layer8project;

/**
 * Defines the account roles available within the Layer7
 * learning management system.
 *
 * A user's role determines the permissions and features they
 * may access within the application. Administrators can access
 * management tools, while regular users access learning content
 * and account features.
 *
 * Responsibilities:
 * - Represent available account roles
 * - Support role-based access control
 * - Distinguish administrators from regular users
 * - Represent restricted or banned accounts
 *
 * Role Values:
 * - Admin: grants access to administrative features
 * - User: grants access to standard learning features
 * - Banned: prevents normal access to the application
 *
 * @author Christopher Sparks
 * @since August 2026
 */
public enum Role {
    USER, ADMIN, BANNED
}