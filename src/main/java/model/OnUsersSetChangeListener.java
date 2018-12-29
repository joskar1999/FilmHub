package main.java.model;

public interface OnUsersSetChangeListener {

    /**
     * Method should be called when new user is created
     * and view should be updated
     *
     * @param user created user
     */
    void onUserCreated(User user);

    /**
     * Method should be called when user is deleted
     * from service and view should be updated
     *
     * @param user removed user
     */
    void onUserRemoved(User user);
}
