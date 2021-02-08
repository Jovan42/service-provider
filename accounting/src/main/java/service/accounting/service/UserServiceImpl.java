package service.accounting.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import service.accounting.domain.User;
import service.accounting.dto.UserCreateRequest;
import service.accounting.dto.UserEditRequest;
import service.accounting.dto.UserResponse;
import service.accounting.repository.UserRepository;
import service.sharedlib.dto.CustomPage;
import service.sharedlib.events.BaseEvent;
import service.sharedlib.exceptions.BadRequestException;
import service.sharedlib.exceptions.NotFoundException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;

    public UserServiceImpl(
            ModelMapper modelMapper,
            UserRepository userRepository,
            KafkaTemplate<String, BaseEvent> kafkaTemplate) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public UserResponse create(UserCreateRequest userRequest) {
        if (userRepository.getByUserName(userRequest.getUserName()).isEmpty()) {
            User user = modelMapper.map(userRequest, User.class);
            user.setCreatedTime(LocalDateTime.now());
            return modelMapper.map(userRepository.save(user), UserResponse.class);
        } else {
            throw new BadRequestException(
                    String.format("Username [%s] is already taken.", userRequest.getUserName()));
        }
    }

    @Override
    public CustomPage<UserResponse> search(Pageable pageable) {
        return new CustomPage<>(
                userRepository
                        .findAll(pageable)
                        .map(user -> modelMapper.map(user, UserResponse.class)));
    }

    @Override
    public UserResponse getById(Long userId) {
        return modelMapper.map(
                userRepository.findById(userId).orElseThrow(NotFoundException::new),
                UserResponse.class);
    }

    @Override
    public UserResponse update(Long userId, UserEditRequest userRequest) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        user.setLastModified(LocalDateTime.now());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }
}
