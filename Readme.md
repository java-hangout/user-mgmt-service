# user-mgmt-service
# http://localhost:8081/api/users/register
{
"userName": "test_user01",
"password": "password123",
"emailId": "test_user01@example.com",
"firstName": "test",
"lastName": "user01",
"role": "USER",
"status": "ACTIVE",
"departmentId": "department_123"
}

{
"id": "676680b00193037405d80813",
"username": "test_user01",
"password": "$2a$10$OF3G0BLi4mAvVMy2dW/Mse7CYL3Mmv7kfWQr5SmGlLF6B7.zKWVYS",
"emailId": "test_user01@example.com",
"firstName": "test",
"lastName": "user01",
"role": "USER",
"status": "ACTIVE",
"departmentId": "department_123"
}
http://localhost:8081/api/users/fetch/username/test_user01
http://localhost:8081/api/users/fetch/id/6766aba5ae14047b3078e319
http://localhost:8081/api/users/delete/user_001
http://localhost:8081/api/users/update/user_003
{
"userName": "test_user01",
"password": "password123",
"emailId": "test_user01@example.com",
"firstName": "test",
"lastName": "user01",
"role": "USER",
"status": "ACTIVE",
"departmentId": "department_123"
}
{
"id": "user_003",
"userName": "test_user01",
"password": "password123",
"emailId": "test_user01@example.com",
"firstName": "test",
"lastName": "user01",
"role": "USER",
"status": "ACTIVE",
"departmentId": "department_123"
}
http://localhost:8081/api/users/fetch/all
[
{
"id": "user_003",
"userName": "test_user01",
"password": "password123",
"emailId": "test_user01@example.com",
"firstName": "test",
"lastName": "user01",
"role": "USER",
"status": "ACTIVE",
"departmentId": "department_123"
},
{
"id": "user_004",
"userName": "test_user02",
"password": "password123",
"emailId": "test_user01@example.com",
"firstName": "test",
"lastName": "user01",
"role": "USER",
"status": "ACTIVE",
"departmentId": "department_123"
},
{
"id": "user_005",
"userName": "test_user03",
"password": "password123",
"emailId": "test_user01@example.com",
"firstName": "test",
"lastName": "user01",
"role": "USER",
"status": "ACTIVE",
"departmentId": "department_123"
},
{
"id": "user_006",
"userName": "test_user04",
"password": "password123",
"emailId": "test_user01@example.com",
"firstName": "test",
"lastName": "user01",
"role": "USER",
"status": "ACTIVE",
"departmentId": "department_123"
}
]