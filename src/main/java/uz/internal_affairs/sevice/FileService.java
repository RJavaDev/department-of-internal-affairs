package uz.internal_affairs.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import uz.internal_affairs.dto.FileDto;
import uz.internal_affairs.entity.FileEntity;
import uz.internal_affairs.repository.FileRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {

    private FileRepository fileRepository;

    public FileDto getCitizenFile(Long id){
        Optional<FileEntity> fileOpt = fileRepository.getFileById(id);
        FileDto dto = null;
        if(fileOpt.isPresent()){
            dto = new FileDto();
            BeanUtils.copyProperties(fileOpt.get(), dto);
        }
        return dto;
    }

}
