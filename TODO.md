# Critical
[TEST] assign-roles route.
[] Restrict usage of websocket for students then to specific studentId with Auth.
# Optimisation (ALL COMPLETED EXCEPT PISTON INTEGRATION)
[] make sure post returns 201 everywhere and location, following convention
[] paginated routes return metadata
  - Roles Controller for example returning List with pageNo in the param, return page.
  - Changed Repository/Service/Controller to return Page<T> instead of List<T>
  - Spring's Page automatically includes totalPages, totalElements, hasNext, hasPrevious
[] api/problems/runtimes - fix permission
[] Rename method names in roles controller 
# Testing
[] write more tests, 
    [] integration tests
    [] test controllers as unit
    [] test service layer as unit
[] test with frontend

# Security & Authorization
[] Add better error messages for permission denied scenarios
[] endpoint issues need to be fixed  // before this implementation solution must be agreed by the team lead! 

# Readme

# Question from the 
[] 
