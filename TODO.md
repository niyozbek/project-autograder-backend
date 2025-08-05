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
[DONE] studentId, lecturerId → userId
[DONE] crud for roles to users.
[DONE] test with frontend
    [DONE] getRuntimes fix
    [DONE] client: enable as variable.
    [DONE] permissions test
[DONE] add fullname to user table, with crud.
[] no need for lecturers and students route, users instead.
    [DONE] roles crud
    [DONE] role create, 
    [DONE] role update with permissions
    [] permissions view
[DONE] something is wrong with exception!!! only 401 is received
[] entity column validations: @Column(nullable = false, unique = true)
[] use username and user→id for getting userDetails in JwtAuthenticationFilter.
[] where are the log files?
[] merge submission_test_results into submissions.
[] Restrict usage of websocket for students then to specific studentId.
[] confirm if two websocket connections collide.
[] try to spin up piston via docker.
