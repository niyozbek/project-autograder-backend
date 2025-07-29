package uk.ac.swansea.autograder.general.enums;

public enum PermissionEnum {
    // User management
    CREATE_USER,
    VIEW_USER,

    // Role management
    CREATE_ROLE,
    VIEW_ROLE,
    ASSIGN_PERMISSION,
    ASSIGN_ROLE,

    // Problem management
    CREATE_PROBLEM,
    UPDATE_PROBLEM,
    UPDATE_OWN_PROBLEM,
    VIEW_PROBLEM,

    // Test case management
    CREATE_TEST_CASE,
    VIEW_TEST_CASE,

    // Submission management
    CREATE_SUBMISSION,
    VIEW_SUBMISSION,
    VIEW_OWN_SUBMISSION,

    // User type management
    CREATE_LECTURER,
    VIEW_LECTURER,
    CREATE_STUDENT,
    VIEW_STUDENT
}

