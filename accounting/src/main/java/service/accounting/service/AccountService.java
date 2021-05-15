package service.accounting.service;

public interface AccountService {
    void validateOrder(Long userId, Long accountId, Double price, Long orderId);
}
