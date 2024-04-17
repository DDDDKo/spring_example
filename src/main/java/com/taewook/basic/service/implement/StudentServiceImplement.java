package com.taewook.basic.service.implement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taewook.basic.dto.Request.Student.PatchStudentRequestDto;
import com.taewook.basic.dto.Request.Student.PostStudentRequestDto;
import com.taewook.basic.dto.Request.Student.SignInRequestDto;
import com.taewook.basic.entity.StudentEntity;
import com.taewook.basic.repository.StudentRepository;
import com.taewook.basic.service.StudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImplement implements StudentService{

    private final StudentRepository studentRepository;

     // PasswordEncoder 인터페이스 :
    // - Spring Security에서 제공해주는 비밀번호를 안전하게 관리하고 검증하도록 도움을 주는 인터페이스
    // - String encode(평문패스워드) : 평문 패스워드를 암호화해서 반환함
    // - boolean matches(평문패스워드, 암호화된패스워드) : 평문 패스워드와 암호화된 패스워드가 같은지 비교 결과를 반환
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    

    @Override
    public ResponseEntity<String> postStudent(PostStudentRequestDto dto) {

        // 패스워드 암호화 작업
        String password = dto.getPassword();
        String encodedPassword = passwordEncoder.encode(password);

        dto.setPassword(encodedPassword);

        // CREATE (SQL : INSERT)
        // 1. Entity 클래스의 인스턴스 생성
        // 2. 생성한 인스턴스를 repository.save() 메서드로 저장
        StudentEntity studentEntity = new StudentEntity(dto);
        // studentEntity.setName(dto.getName());
        // studentEntity.setAge(dto.getAge());
        // studentEntity.setAddress(dto.getAddress());
        // studentEntity.setGraduation(dto.getGraduation());  나중에 너무 많아서 이렇게 사용하지 않고 StudentEntity로 들어가 생성자를 이용함

        // save() : 저장 및 수정(덮어쓰기(primary key를 기준으로))
        studentRepository.save(studentEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body("성공!");
    }

    // UPDATE
    @Override
    public ResponseEntity<String> patchStudent(PatchStudentRequestDto dto) {
        Integer studentNumber = dto.getStudentNumber();
        String address = dto.getAddress();

        // 0. student 테이블에 접근하여 테이블에 해당하는 priamry key를 가지는 레코드가 존재하는지 확인
        boolean isExistedStudent = studentRepository.existsById(studentNumber);
        if(!isExistedStudent) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재하지 않는 학생 입니다.");
        
        // 1. student 테이블로 접근(StudentRepository 사용)
        StudentEntity studentEntity = studentRepository.
        // 2. dto.studentNumber에 해당하는 레코드를 검색
        findById(studentNumber).get();
        // 위 코드는 1줄임 원래 StudentEntity studentEntity = studentRepository.findById(studentNumber).get();

        // 3. 검색된 레코드의 address 값을 dto.address로 변경
        studentEntity.setAddress(address);

        // 4. 변경한 인스턴스를데이터베이스에 저장
        // repository.save() 는 레코드를 생성할 때도 쓰이지만 수정 작업을 할 때도 동일하게 사용됨
        studentRepository.save(studentEntity);
        //-----객체지향프로그래밍언어의 class == RDBMS의 table-----
        //-----객체지향프로그래밍언어의 instance == RDBMS의 record-----
        // student 클래스로 접근
        // dto.studentNumber에 해당하는 인스턴스를 검색
        // 검색된 레코드의 address 값을 dto.address로 변경

        return ResponseEntity.status(HttpStatus.OK).body("성공");
    }

    // DELETE
    @Override
    public ResponseEntity<String> deleteStudent(Integer studentNumber) {

        boolean isExistedStudent = studentRepository.existsById(studentNumber);
        if(!isExistedStudent) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재하지 않는 학생 입니다.");
        // DELETE( SQL : DELETE)
        studentRepository.deleteById(studentNumber);
    
        return ResponseEntity.status(HttpStatus.OK).body("성공!");
    }

    @Override
    public ResponseEntity<String> signIn(SignInRequestDto dto) {
        
        try {
            
            Integer studentNumber = dto.getStudentNumber();
            StudentEntity studentEntity = studentRepository.findByStudentNumber(studentNumber);

            if (studentEntity == null) 
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류");

            // 사용자가 입력한 패스워드와 암호화된 패스워드가 매치되는지 확인
            String password = dto.getPassword();
            String encodedPassword = studentEntity.getPassword();

            boolean isEqualPassword = passwordEncoder.matches(password, encodedPassword);
            if (!isEqualPassword) 
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비밀번호 불일치");

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류");
        }

        return ResponseEntity.status(HttpStatus.OK).body("성공");

    }
    
}
