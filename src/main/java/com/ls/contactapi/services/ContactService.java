package com.ls.contactapi.services;

import com.ls.contactapi.domain.Contact;
import com.ls.contactapi.repositories.ContactRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.ls.contactapi.constant.Constant.PHOTO_DIRECTORY;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;

    public Page<Contact>getAllContacts(int page, int size){
        return contactRepository.findAll(PageRequest.of(page, size, Sort.by("name")));
    }
    public Contact getContact(String id){
        return contactRepository.findById(id).orElseThrow(()->new RuntimeException("Contact not found"));
    }
    public Contact createContact(Contact contact){
        return contactRepository.save(contact);
    }
    public void deleteContact(Contact contact){
        contactRepository.delete(contact);
    }
    public String uploadPhoto(String id, MultipartFile file){
        Contact contact = getContact(id);
        String photoURL=photoFunction.apply(id, file);
        contact.setPhotoUrl(photoURL);
        contactRepository.save(contact);
        return photoURL;
    }

    private final Function<String, String> fileExtension = filename -> Optional.of(filename).filter(name -> name.contains("."))
            .map(name-> "."+ name.substring(filename.lastIndexOf(".")+1)).orElse(".png");

    private final BiFunction<String,MultipartFile,String> photoFunction=(id, image)->{
        String fileName = id + fileExtension.apply(image.getOriginalFilename());
        try{
            Path fileStorageLocation = Paths.get(PHOTO_DIRECTORY).toAbsolutePath().normalize();
            if (!Files.exists(fileStorageLocation)){
               Files.createDirectories(fileStorageLocation);
            }
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(fileName), REPLACE_EXISTING);
            return ServletUriComponentsBuilder.fromCurrentContextPath().path("/contacts/images/"+fileName).toString();
        }catch (Exception ex){
            throw new RuntimeException("Unable to save image");
        }
    };
}
