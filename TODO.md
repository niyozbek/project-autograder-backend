[DONE] docker complete with java build.
[DONE] why swagger doesn't have auth button.
[DONE] optimize docker. 
[DONE] set app.jwtSecret and app.jwtExpirationInMinutes in JwtTokenProvider from env
[DONE] upgrade spring boot and dependencies.
[DONE] fix warnings. all correct.
[DONE] improve route directory structure
[DONE] make sure entity crud is located in its own controller, more universal approach.
[DONE] add permission-based rbac instead of role-based, route-based rbac.
    [DONE] hardcode permissions only instead of roles
    [DONE] instead of roles, make sure to have permissions
[CANCELLED] no need for lecturers and students route, users instead.
    we will need such routes for convenience, at least for now.
[DONE] studentId, lecturerId → userId
[DONE] crud for roles to users.
[] add fullname to user table
[] use username and user→id for getting userDetails in JwtAuthenticationFilter.
[] where are the log files?
[] test with frontend
[] merge submission_test_results into submissions.
[] Restrict usage of websocket for students then to specific studentId.
[] confirm if two websocket connections collide.
[] try to spin up piston via docker.
