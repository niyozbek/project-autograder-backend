#!/bin/bash

# ============================================
# COMPLETE RBAC TESTING GUIDE
# Testing permissions for: ADMIN, LECTURER, STUDENT
# ============================================

BASE_URL="http://localhost:8095"

echo "======================================"
echo "RBAC PERMISSION TESTING"
echo "======================================"

# ============================================
# PART 1: GET TOKENS FOR ALL USERS
# ============================================

echo -e "\n=== STEP 1: Getting JWT Tokens ==="

# 1. Get ADMIN token
echo -e "\n[1/3] Logging in as ADMIN..."
ADMIN_RESPONSE=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin"
  }')

ADMIN_TOKEN=$(echo $ADMIN_RESPONSE | grep -o '"token":"[^"]*' | cut -d'"' -f4)
echo "Admin token: ${ADMIN_TOKEN:0:50}..."

# 2. Get LECTURER token
echo -e "\n[2/3] Logging in as LECTURER (alex)..."
LECTURER_RESPONSE=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "alex",
    "password": "alex"
  }')

LECTURER_TOKEN=$(echo $LECTURER_RESPONSE | grep -o '"token":"[^"]*' | cut -d'"' -f4)
echo "Lecturer token: ${LECTURER_TOKEN:0:50}..."

# 3. Get STUDENT token
echo -e "\n[3/3] Logging in as STUDENT (patrick)..."
STUDENT_RESPONSE=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "patrick",
    "password": "patrick"
  }')

STUDENT_TOKEN=$(echo $STUDENT_RESPONSE | grep -o '"token":"[^"]*' | cut -d'"' -f4)
echo "Student token: ${STUDENT_TOKEN:0:50}..."

# ============================================
# PART 2: TESTING ADMIN PERMISSIONS
# ============================================

echo -e "\n\n======================================"
echo "TESTING ADMIN PERMISSIONS (admin/admin)"
echo "======================================"

# Test 1: Admin accesses user list
echo -e "\n[ADMIN TEST 1] GET /api/users (Should: ✅ 200 OK)"
curl -s -o /dev/null -w "Response Code: %{http_code}\n" \
  -X GET "$BASE_URL/api/users" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# Test 2: Admin creates a problem
echo -e "\n[ADMIN TEST 2] POST /api/problems (Should: ✅ 201 Created)"
curl -s -o /dev/null -w "Response Code: %{http_code}\n" \
  -X POST "$BASE_URL/api/problems" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Test Problem",
    "description": "Admin created problem",
    "difficulty": "EASY"
  }'

# Test 3: Admin disables a user
echo -e "\n[ADMIN TEST 3] PATCH /api/users/2/disable (Should: ✅ 200 OK)"
curl -s -o /dev/null -w "Response Code: %{http_code}\n" \
  -X PATCH "$BASE_URL/api/users/2/disable" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# Test 4: Admin views all submissions
echo -e "\n[ADMIN TEST 4] GET /api/submissions (Should: ✅ 200 OK)"
curl -s -o /dev/null -w "Response Code: %{http_code}\n" \
  -X GET "$BASE_URL/api/submissions" \
  -H "Authorization: Bearer $ADMIN_TOKEN"



# ============================================
# PART 3: TESTING LECTURER PERMISSIONS
# ============================================

echo -e "\n\n======================================"
echo "TESTING LECTURER PERMISSIONS (alex/alex)"
echo "======================================"

# Test 1: Lecturer accesses problems (SHOULD WORK)
echo -e "\n[LECTURER TEST 1] GET /api/problems (Should: ✅ 200 OK)"
curl -s -o /dev/null -w "Response Code: %{http_code}\n" \
  -X GET "$BASE_URL/api/problems" \
  -H "Authorization: Bearer $LECTURER_TOKEN"

# Test 2: Lecturer creates a problem (SHOULD WORK)
echo -e "\n[LECTURER TEST 2] POST /api/problems (Should: ✅ 201 Created)"
curl -s -o /dev/null -w "Response Code: %{http_code}\n" \
  -X POST "$BASE_URL/api/problems" \
  -H "Authorization: Bearer $LECTURER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Lecturer Problem",
    "description": "Created by lecturer",
    "difficulty": "MEDIUM"
  }'

# Test 3: Lecturer views submissions (SHOULD WORK)
echo -e "\n[LECTURER TEST 3] GET /api/submissions (Should: ✅ 200 OK)"
curl -s -o /dev/null -w "Response Code: %{http_code}\n" \
  -X GET "$BASE_URL/api/submissions" \
  -H "Authorization: Bearer $LECTURER_TOKEN"

# Test 4: Lecturer tries to access users (SHOULD FAIL)
echo -e "\n[LECTURER TEST 4] GET /api/users (Should: ❌ 403 Forbidden)"
curl -s -o /dev/null -w "Response Code: %{http_code}\n" \
  -X GET "$BASE_URL/api/users" \
  -H "Authorization: Bearer $LECTURER_TOKEN"

# Test 5: Lecturer tries to disable user (SHOULD FAIL)
echo -e "\n[LECTURER TEST 5] PATCH /api/users/3/disable (Should: ❌ 403 Forbidden)"
curl -s -o /dev/null -w "Response Code: %{http_code}\n" \
  -X PATCH "$BASE_URL/api/users/3/disable" \
  -H "Authorization: Bearer $LECTURER_TOKEN"



# ============================================
# PART 4: TESTING STUDENT PERMISSIONS
# ============================================

echo -e "\n\n======================================"
echo "TESTING STUDENT PERMISSIONS (patrick/patrick)"
echo "======================================"

# Test 1: Student views problems (SHOULD WORK)
echo -e "\n[STUDENT TEST 1] GET /api/problems (Should: ✅ 200 OK)"
curl -s -o /dev/null -w "Response Code: %{http_code}\n" \
  -X GET "$BASE_URL/api/problems" \
  -H "Authorization: Bearer $STUDENT_TOKEN"

# Test 2: Student submits solution (SHOULD WORK)
echo -e "\n[STUDENT TEST 2] POST /api/submissions (Should: ✅ 201 Created)"
curl -s -o /dev/null -w "Response Code: %{http_code}\n" \
  -X POST "$BASE_URL/api/submissions" \
  -H "Authorization: Bearer $STUDENT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "problemId": 1,
    "code": "print(\"Hello World\")",
    "language": "python",
    "version": "3.8",
    "filename": "main.py"
  }'

# Test 3: Student views own submissions (SHOULD WORK)
echo -e "\n[STUDENT TEST 3] GET /api/submissions/my (Should: ✅ 200 OK)"
curl -s -o /dev/null -w "Response Code: %{http_code}\n" \
  -X GET "$BASE_URL/api/submissions/own" \
  -H "Authorization: Bearer $STUDENT_TOKEN"

# Test 4: Student tries to create problem (SHOULD FAIL)
echo -e "\n[STUDENT TEST 4] POST /api/problems (Should: ❌ 403 Forbidden)"
curl -s -o /dev/null -w "Response Code: %{http_code}\n" \
  -X POST "$BASE_URL/api/problems" \
  -H "Authorization: Bearer $STUDENT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Student Problem",
    "description": "Should not work",
    "difficulty": "EASY"
  }'

# Test 5: Student tries to access users (SHOULD FAIL)
echo -e "\n[STUDENT TEST 5] GET /api/users (Should: ❌ 403 Forbidden)"
curl -s -o /dev/null -w "Response Code: %{http_code}\n" \
  -X GET "$BASE_URL/api/users" \
  -H "Authorization: Bearer $STUDENT_TOKEN"

# Test 6: Student tries to delete problem (SHOULD FAIL)
echo -e "\n[STUDENT TEST 6] DELETE /api/problems/1 (Should: ❌ 403 Forbidden)"
curl -s -o /dev/null -w "Response Code: %{http_code}\n" \
  -X DELETE "$BASE_URL/api/problems/1" \
  -H "Authorization: Bearer $STUDENT_TOKEN"

# Test 7: Student tries to view all submissions (SHOULD FAIL)
echo -e "\n[STUDENT TEST 7] GET /api/submissions (Should: ❌ 403 Forbidden)"
curl -s -o /dev/null -w "Response Code: %{http_code}\n" \
  -X GET "$BASE_URL/api/submissions" \
  -H "Authorization: Bearer $STUDENT_TOKEN"

# ============================================
# PART 5: TESTING WITHOUT TOKEN
# ============================================

echo -e "\n\n======================================"
echo "TESTING WITHOUT TOKEN (Unauthorized)"
echo "======================================"

# Test 1: Access protected endpoint without token
echo -e "\n[NO TOKEN TEST 1] GET /api/users (Should: ❌ 401 Unauthorized)"
curl -s -o /dev/null -w "Response Code: %{http_code}\n" \
  -X GET "$BASE_URL/api/users"

# Test 2: Access problems without token
echo -e "\n[NO TOKEN TEST 2] GET /api/problems (Should: ❌ 401 Unauthorized)"
curl -s -o /dev/null -w "Response Code: %{http_code}\n" \
  -X GET "$BASE_URL/api/problems"

# Test 3: Try to submit without token
echo -e "\n[NO TOKEN TEST 3] POST /api/submissions (Should: ❌ 401 Unauthorized)"
curl -s -o /dev/null -w "Response Code: %{http_code}\n" \
  -X POST "$BASE_URL/api/submissions" \
  -H "Content-Type: application/json" \
  -d '{
    "problemId": 1,
    "code": "test",
    "language": "python"
  }'

# ============================================
# PART 6: TESTING WITH INVALID TOKEN
# ============================================

echo -e "\n\n======================================"
echo "TESTING WITH INVALID TOKEN"
echo "======================================"

echo -e "\n[INVALID TOKEN TEST] GET /api/users (Should: ❌ 401 Unauthorized)"
curl -s -o /dev/null -w "Response Code: %{http_code}\n" \
  -X GET "$BASE_URL/api/users" \
  -H "Authorization: Bearer invalid_token_12345"

# ============================================
# SUMMARY
# ============================================

echo -e "\n\n======================================"
echo "TEST SUMMARY"
echo "======================================"
echo ""
echo "Expected Results:"
echo "✅ 200 OK       = Allowed (GET/PUT/PATCH/DELETE)"
echo "✅ 201 Created  = Allowed (POST)"
echo "❌ 401 Unauthorized = No/Invalid token"
echo "❌ 403 Forbidden    = Valid token, wrong role"
echo "❌ 404 Not Found    = Resource doesn't exist"
echo ""
echo "Role Capabilities:"
echo "🔴 ADMIN     → Full access to everything"
echo "🟡 LECTURER  → Manage problems & submissions, NO user management"
echo "🟢 STUDENT   → View problems, submit solutions, view own submissions"
echo ""

# ============================================
# INDIVIDUAL COMMAND EXAMPLES
# ============================================

cat << 'EOF'

====================================
INDIVIDUAL CURL COMMANDS
====================================

# Login as Admin
curl -X POST http://localhost:8095/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin"}'

# Login as Lecturer
curl -X POST http://localhost:8095/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"alex","password":"alex"}'

# Login as Student
curl -X POST http://localhost:8095/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"patrick","password":"patrick"}'

# Admin: Get all users
curl -X GET http://localhost:8095/api/users \
  -H "Authorization: Bearer YOUR_ADMIN_TOKEN"

# Admin: Disable user
curl -X PATCH http://localhost:8095/api/users/2/disable \
  -H "Authorization: Bearer YOUR_ADMIN_TOKEN"

# Admin: Enable user
curl -X PATCH http://localhost:8095/api/users/2/enable \
  -H "Authorization: Bearer YOUR_ADMIN_TOKEN"

# Lecturer: Create problem
curl -X POST http://localhost:8095/api/problems \
  -H "Authorization: Bearer YOUR_LECTURER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Two Sum",
    "description": "Find two numbers that add up to target",
    "difficulty": "EASY"
  }'

# Lecturer: Try to access users (should fail with 403)
curl -X GET http://localhost:8095/api/users \
  -H "Authorization: Bearer YOUR_LECTURER_TOKEN"

# Student: View problems
curl -X GET http://localhost:8095/api/problems \
  -H "Authorization: Bearer YOUR_STUDENT_TOKEN"

# Student: Submit solution
curl -X POST http://localhost:8095/api/submissions \
  -H "Authorization: Bearer YOUR_STUDENT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "problemId": 1,
    "code": "def solution():\n    return True",
    "language": "python"
  }'

# Student: View own submissions
curl -X GET http://localhost:8095/api/submissions/my \
  -H "Authorization: Bearer YOUR_STUDENT_TOKEN"

# Student: Try to create problem (should fail with 403)
curl -X POST http://localhost:8095/api/problems \
  -H "Authorization: Bearer YOUR_STUDENT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"title":"Test","description":"Test","difficulty":"EASY"}'

# Without token (should fail with 401)
curl -X GET http://localhost:8095/api/users

====================================
SWAGGER UI TESTING STEPS
====================================

1. Open Swagger UI:
   http://localhost:8095/swagger-ui/index.html

2. Test Login:
   - Find: POST /auth/login
   - Click "Try it out"
   - Enter: {"username":"admin","password":"admin"}
   - Click "Execute"
   - Copy the JWT token from response

3. Authorize in Swagger:
   - Click 🔒 "Authorize" button (top right)
   - Enter: Bearer YOUR_TOKEN_HERE
   - Click "Authorize"
   - Click "Close"

4. Test ADMIN permissions:
   - Try GET /api/users → Should work (200)
   - Try PATCH /api/users/{id}/disable → Should work (200)
   - Try POST /api/problems → Should work (201)

5. Re-authorize with LECTURER token:
   - Logout and login as alex/alex
   - Copy new token
   - Click Authorize and enter new token
   - Try GET /api/users → Should fail (403)
   - Try POST /api/problems → Should work (201)

6. Re-authorize with STUDENT token:
   - Logout and login as patrick/patrick
   - Copy new token
   - Click Authorize and enter new token
   - Try GET /api/problems → Should work (200)
   - Try POST /api/problems → Should fail (403)
   - Try POST /api/submissions → Should work (201)

7. Test without authorization:
   - Click Authorize → Logout
   - Try any protected endpoint → Should fail (401)

====================================
COMMON ISSUES & SOLUTIONS
====================================

Issue: All requests return 401
→ Solution: Make sure token is prefixed with "Bearer "
→ Example: "Bearer eyJhbGciOiJIUzI1NiIs..."

Issue: Token not working
→ Solution: Token might be expired, login again

Issue: Student can access admin endpoints
→ Solution: Check @PreAuthorize annotations in controllers

Issue: Getting 403 but should have access
→ Solution: Check user's role assignment in database

Issue: Can't find endpoints in Swagger
→ Solution: Check SecurityConfig allows Swagger endpoints

EOF
