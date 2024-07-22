## API Documentation
### **Member**
- Request Mapping: /api/member
#### **SignUp**
- POST /signup
    - Request
        - Method: post
        - Header:
            - Content-Type: application/json
        - Body
          ```json
          {
            "email": "your email",
            "username": "your username",
            "password": "your password",
            "passwordConfirm": "your password",
            "roles": "admin or null"
          }
          ```
    - Response
        - Status: 200
        - Body
          ```json
          {
            "success": true,
            "code": 0,
            "msg": "Success",
            "token": null
          }
          ```

#### **SignIn**
- POST /signin
    - Request
        - Method: post
        - Header:
            - Content-Type: application/json
        - Body
          ```json
          {
            "email": "your email",
            "password": "your password"
          }
          ```
    - Response
        - Status: 200
        - Body
          ```json
          {
            "success": true,
            "code": 0,
            "msg": "Success",
            "token": "your token"
          }
          ```

#### **get**
- GET ?email=youremail
    - Request
        - Method: get
        
    - Response
        - Status: 200
        - Body
          ```json
          {
            "email": "your email",
            "userName": "your username"
          }
          ```

#### **update**
- PUT /update
    - Request
        - Method: put
        - Header:
            - Content-Type: application/json
            - X-AUTH-TOKEN: your token
        - Body
          ```json
          {
            "email": "your email",
            "username": "new username"
          }
          ```
    - Response
        - Status: 200
        - Body
          ```
          프로필이 업데이트 되었습니다.
          ```

#### **update password**
- PATCH /update/password
    - Request
        - Method: patch
        - Header:
            - Content-Type: application/json
            - X-AUTH-TOKEN: your token
        - Body
          ```json
          {
            "email": "your email",
            "password": "your password",
            "newPassword": "new password",
            "newPasswordConfirm": "new password"
          }
          ```
    - Response
        - Status: 200
        - Body
          ```
          비밀번호 변경이 완료되었습니다.
          ```

#### **delete**
- DELETE /delete
    - Request
        - Method: delete
        - Header:
            - Content-Type: application/json
            - X-AUTH-TOKEN: your token
        - Body
          ```json
          {
            "email": "your email",
            "password": "your password"
          }
          ```
    - Response
        - Status: 200
        - Body
          ```
          회원탈퇴 처리가 정상적으로 처리되었습니다.
          ```

<br>

### **Post**
- Request Mapping: /api/post
#### **create**
- POST /create
    - Request
        - Method: post
        - Header:
            - Content-Type: application/json
            - X-AUTH-TOKEN: your token
        - Body
          ```json
          {
            "category": "category",
            "title": "title",
            "contents": "contents"
          }
          ```
    - Response
        - Status: 200

#### **get**
- GET /{id}
    - Request
        - Method: get
        
    - Response
        - Status: 200
        - Body
        ```json
          {
            "id": 1,
            "category": "category",
            "title": "title",
            "contents": "contents",
            "view": 0,
            "recommendation": 0,
            "createdAt": "2024-07-19T04:42:39.726799" 
          }
        ```

#### **update**
- PUT /update
    - Request
        - Method: put
        - Header:
            - Content-Type: application/json
            - X-AUTH-TOKEN: your token
        - Body
          ```json
          {
            "category": "category",
            "title": "title",
            "contents": "contents"
          }
          ```
    - Response
        - Status: 200

#### **delete**
- DELETE /delete
    - Request
        - Method: delete
        - Header:
            - X-AUTH-TOKEN: your token
    - Response
        - Status: 200

#### **recommendation/notrecommendation**
- POST /recommend or notrecommend/{id}
    - Request
        - Method: post
        - Header:
            - X-AUTH-TOKEN: your token
    - Response
        - Status: 200

#### **search**
- GET /search?keyword=keyword&type=type(title, contents, username, titleOrContents)
    - Request
        - Method: get
    - Response
        - Status: 200
        - Body:
          ```json
          [
            {
              "id": 1,
              "category": "category",
              "title": "title",
              "contents": "contents",
              "view": 0,
              "recommendation": 0,
              "createdAt": "2024-07-19T04:42:39.726799"
            },
            {
              "id": 2,
              "category": "category",
              "title": "title2",
              "contents": "contents2",
              "view": 0,
              "recommendation": 0,
              "createdAt": "2024-07-20T17:06:39.654542"
            }
          ]
          ```

### **Comment**
- Request Mapping: /api/comment
#### **create**
- POST /create
    - Request
        - Method: post
        - Header:
            - Content-Type: application/json
            - X-AUTH-TOKEN: your token
        - Body
          ```json
          {
            "contents": "contents"
          }
          ```
    - Response
        - Status: 200

#### **get**
- GET /{post_id}/{comment_id}
    - Request
        - Method: get

    - Response
        - Status: 200
        - Body
        ```json
          
        {
          "id": 1,
          "contents": "contents",
          "recommendation": 0,
          "createdAt": "2024-07-19T04:43:57.598322",
          "updatedAt": null
        }
          
        ```

#### **get all**
- GET /{post_id}
    - Request
        - Method: get

    - Response
        - Status: 200
        - Body
        ```json
        [
          {
            "id": 1,
            "contents": "contents",
            "recommendation": 0,
            "createdAt": "2024-07-19T04:43:57.598322",
            "updatedAt": null
          }
        ]
        ```

#### **update**
- PATCH /update/{post_id}/{comment_id}
    - Request
        - Method: patch
        - Header:
            - Content-Type: application/json
            - X-AUTH-TOKEN: your token
        - Body
          ```json
          {
            "contents": "contents"
          }
          ```
    - Response
        - Status: 200

#### **delete**
- DELETE /delete/{post_id}/{comment_id}
    - Request
        - Method: delete
        - Header:
            - X-AUTH-TOKEN: your token
    - Response
        - Status: 200

#### **recommendation/notrecommendation**
- POST /recommend or notrecommend/{post_id}/{comment_id}
    - Request
        - Method: post
        - Header:
            - X-AUTH-TOKEN: your token
    - Response
        - Status: 200