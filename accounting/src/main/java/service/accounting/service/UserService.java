package service.accounting.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import service.accounting.dto.UserCreateRequest;
import service.accounting.dto.UserEditRequest;
import service.accounting.dto.UserResponse;
import service.sharedlib.dto.CustomPage;

@Service
public interface UserService {

    UserResponse create(UserCreateRequest userRequest);

    CustomPage<UserResponse> search(Pageable pageable);

    UserResponse getById(Long userId);

    UserResponse update(Long userId, UserEditRequest userRequest);
}
