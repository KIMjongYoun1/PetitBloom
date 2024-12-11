package PetitBloom.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

	// 프로젝트내 명
	 private final String uploadDir = "src/main/resources/static/uploads/";
	
	// 외부경로사
	// private final String uploadDir = "/path/to/uploads/";

	// private final String uploadDir = "uploads/";
	 
	 public FileService() {
	        // 디렉토리 생성
	        File uploadPath = new File(uploadDir);
	        if (!uploadPath.exists()) {
	            uploadPath.mkdirs(); // 디렉토리 생성
	        }
	    }

	    // 파일 저장 메서드
	    public String saveImage(MultipartFile file) throws IOException {
	        // 원본 파일명 가져오기
	        String originalFileName = file.getOriginalFilename();
	        // 고유한 파일명 생성
	        String newFileName = UUID.randomUUID() + "_" + originalFileName;
	        // 파일 저장 경로 설정
	        Path filePath = Paths.get(uploadDir, newFileName);

	        // 파일 저장
	        Files.copy(file.getInputStream(), filePath);

	        // 저장된 파일 경로 반환
	        return "/uploads/" + newFileName; // 프론트엔드에서 접근 가능한 경로
	    }
	
}
