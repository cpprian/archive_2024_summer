package com.main.sams.server.server;

/**
 * RequestType is an enum that defines the different types of requests that can be made to the server.
 * It contains two request that is reserved for the server to use.
 *
 * @author cpprian
 * @version 1.0
 * @since 2023-01-06
 */
public enum RequestType {
    SERVER_ACCEPTED, // only for server
    SERVER_REJECTED, // only for server
    ADD_STUDENT,
    DELETE_STUDENT,
    ADD_GROUP,
    DELETE_GROUP,
    ADD_STUDENT_TO_GROUP,
    DELETE_STUDENT_FROM_GROUP,
    ADD_CLASS_TIME,
    ADD_ATTENDANCE,
    PRINT_GROUP_ATTENDANCE,
    PRINT_ALL_STUDENTS,
    PRINT_ALL_GROUPS,
    PRINT_ALL_CLASS_TIMES,
    PRINT_ALL_ATTENDANCES,
    PRINT_STUDENTS_IN_GROUP,
    PRINT_ALL_GROUPS_OF_STUDENT,
    PRINT_ALL_STUDENT_ATTENDANCES,
    PRINT_ALL_CLASS_TIMES_OF_STUDENT,
    PRINT_ALL_CLASS_TIMES_OF_GROUP,
}
