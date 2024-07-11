# Trouble Shooting
프로젝트를 진행하면서 발생한 문제점들과 해결법 서술합니다.

1. 사용자의 패스워드를 변경시 isNewPasswordConfirmValid이 작동하지 않는 오류
   1. 증상
      ```
      <전달값>
      Content-Type: application/json
        {
        "email": "test@naver.com",
        "password": "1234",
        "newPassword": "12345",
        "newPasswordConfirm": "12345"
        }
      
      newPassword의 값이 없어 newPassword와 newPasswordConfirm을 비교하는
      isNewPasswordConfirmValid가 작동을 안함
      ```
      2. 원인 및 해결
         - @RequestBody 한개의 어노테이션에 두개의 DTO를 요청해 하나의 DTO만 매핑됨
         - 두개의 DTO를 하나로 통합
         ```java
         <수정전>
         public ResponseEntity<?> updateUserPassword(@RequestBody @Valid
                                                UserLoginInput memberLoginInput,
                                                UserInputPassword memberInputPassword,
                                                Errors errors)
         
         <수정후>
         public ResponseEntity<?> updateUserPassword(@RequestBody @Valid
                                                UserInputPassword memberInputPassword,
                                                Errors errors)
         
         ```
      3. 추가 해결방법
        - 두개의 DTO를 포함하는 단일 DTO를 정의한 뒤, RequestBody 어노테이션으로 매핑한다.
      