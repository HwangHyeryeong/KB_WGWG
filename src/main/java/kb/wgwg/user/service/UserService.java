package kb.wgwg.user.service;

import kb.wgwg.challenge.domain.ChallengeUser;
import kb.wgwg.user.domain.User;
import kb.wgwg.user.dto.UserDTO.*;
import kb.wgwg.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static kb.wgwg.common.ResponseMessage.NOT_FOUND_USER;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    public UserLoginResponseDTO login(UserLoginRequestDTO dto) {

        User theUser = userRepository.findByEmail(dto.getEmail());
        System.out.println(theUser);
        if(theUser == null || !theUser.getPassword().equals(dto.getPassword())) throw new EntityNotFoundException();

        UserLoginResponseDTO result = modelMapper.map(theUser, UserLoginResponseDTO.class);
        return result;
    }
    
    @Transactional(readOnly = true)
    public UserReadResponseDTO readById(UserReadRequestDTO dto) {
        Optional<User> theUser = userRepository.findById(dto.getUserSeq());
        if (theUser.isEmpty()) throw new EntityNotFoundException();

        UserReadResponseDTO result = modelMapper.map(theUser.get(), UserReadResponseDTO.class);

        return result;
    }

    public boolean checkEmailDup(String email){
        User user = userRepository.findByEmail(email);
        if(user != null) // email이 사용중일 경우
            return true;
        return false;
    }

    public boolean checkNickNameDup(String nickName){
        User user = userRepository.findByNickName(nickName);
        if(user != null) // nickName 이 사용중일 경우
            return true;
        return false;
    }

    public UserReadResponseDTO insertUser(UserInsertRequestDTO dto) {
        User user = modelMapper.map(dto, User.class);
        User savedUser = userRepository.save(user);
        entityManager.flush(); //insertDate
        UserReadResponseDTO result = modelMapper.map(savedUser, UserReadResponseDTO.class);
        return modelMapper.map(result, UserReadResponseDTO.class);
    }

    public void deleteUser(Long id) {
        userRepository.deleteByUserSeq(id);
    }

    public void updatePassword(UserUpdateDTO dto) throws Exception {
        userRepository.updateUserPassword(dto.getPassword(), dto.getUserSeq());
    }

    public List<ReadMyProcessingChallengeResponseDTO> readMyProcessingChallenge(UserReadMyChallengeListRequestDTO dto) {

        User theUser = userRepository.findById(dto.getUserSeq()).orElseThrow(
                () -> new EntityNotFoundException(NOT_FOUND_USER)
        );

        List<ReadMyProcessingChallengeResponseDTO> myChallengeList = theUser.getParticipants().stream()
                .filter(participant -> dto.getStatus().equals(participant.getChallenge().getStatus()))
                .map(ChallengeUser::getChallenge)
                .map(challenge -> modelMapper.map(challenge, ReadMyProcessingChallengeResponseDTO.class))
                .collect(Collectors.toList());

        return myChallengeList;
    }

    public List<ReadMyCompleteChallengeResponseDTO> readMyCompleteChallenge(UserReadMyChallengeListRequestDTO dto) {

        User theUser = userRepository.findById(dto.getUserSeq()).orElseThrow(
                () -> new EntityNotFoundException(NOT_FOUND_USER)
        );

        List<ReadMyCompleteChallengeResponseDTO> myChallengeList = theUser.getParticipants().stream()
                .filter(participant -> dto.getStatus().equals(participant.getChallenge().getStatus()))
                .map(participant -> {
                    ReadMyCompleteChallengeResponseDTO responseDTO = modelMapper.map(participant.getChallenge(),
                            ReadMyCompleteChallengeResponseDTO.class);
                    responseDTO.setIsSuccess(participant.getIsSuccess());
                    return responseDTO;
                })
                .collect(Collectors.toList());

        return myChallengeList;
    }
}
