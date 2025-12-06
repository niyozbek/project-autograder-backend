# Critical
[DONE] merge submission_test_results into submissions.
[TEST] assign-roles route.
[DONE] display error message for submission
[] Restrict usage of websocket for students then to specific studentId with Auth.
[DONE] confirm if two websocket connections collide.
# Optimisation (ALL COMPLETED EXCEPT PISTON INTEGRATION)
[DONE] make sure post returns 201 everywhere and location, following convention
[DONE] rename mainService and define its purpose clearly
  - SubmissionMainService → SubmissionExecutionService
  - Added comprehensive JavaDoc
[DONE] spin up piston via docker
  - Docker container configured in compose.local.yaml
  - Implemented PistonClient service using Spring RestTemplate (industry standard)
  - Replaced Piston4j library with direct REST integration
  - Fully self-hosted - no external API dependency
  - Configuration: piston.api.url=http://piston:2000
[DONE] paginated routes return metadata
  - Changed Repository/Service/Controller to return Page<T> instead of List<T>
  - Spring's Page automatically includes totalPages, totalElements, hasNext, hasPrevious
[DONE] sort permission file
  - Make permissions string->importable variable.
[] api/problems/runtimes - fix permission
[] Rename method names in roles controller 
[DONE] - setup/implement email into all users, via this email the user must be able to reset his password if he forgot his password 
   - Using Mailpit (open-source SMTP) for local/dev, can configure any SMTP for production
   - Endpoints: POST /auth/forgot-password, POST /auth/reset-password
# Testing
[] write more tests, 
    [] integration tests
    [] test controllers as unit
    [] test service layer as unit
[] test with frontend

# SOLID Improvements (Elite Backend Quality)
[DONE] WebSocket authorization - restrict students to own submissions
[DONE] WebSocket collision behavior - documented broadcast model
[DONE] Field injection anti-pattern - fixed in SubmissionReceiver
[DONE] WebSocketMessage validation - added @NotNull and @Positive
[DONE] Dead code removal - removed commented Thread.sleep
[DONE] CORS wildcard security fix - restricted to specific origins
[DONE] WebSocket endpoint naming - changed to /ws/submissions
[DONE] HTTP status codes - all POST methods return 201 Created with Location header
  - ProblemsController.createProblem()
  - UsersController.createUser()
  - TestCasesController.addTestCase()
  - SubmissionsController.submitSolution()
[DONE] User creation bug - fixed missing role assignment in UserService.createUser()

# Frontend-Backend Integration Issues (Fixed)
[DONE] WebSocket endpoint mismatch - updated frontend to use /ws/submissions
[DONE] Admin menu visibility - hide from non-admin users (prevents 500 errors)
[DONE] Role-based UI access control - only ADMIN sees admin dropdown

# Security & Authorization
[DONE] Verify all admin routes return 403 (not 500) for unauthorized access
[] Add better error messages for permission denied scenarios
[ALREADY DONE] Consider adding permission-based route guards beyond role checks
[DONE] Jwt token experation will make the right callback
[DONE] isenable column in users table should return access denied 
[] endpoint issues need to be fixed  // before this implementation solution must be agreed by the team lead! 
[DONE] - Update the java from 17 to 21

# Readme
[DONE] update readme

# Question from the 
[] 