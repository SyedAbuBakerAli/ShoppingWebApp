package com.shopping.app.service.serviceImpl;


import com.shopping.app.dto.TransactionResponse;
import com.shopping.app.entity.OrderEntity;
import com.shopping.app.entity.TransactionEntity;
import com.shopping.app.entity.UserEntity;
import com.shopping.app.repository.OrderRepository;
import com.shopping.app.repository.TransactionRepository;
import com.shopping.app.repository.UserRepository;
import com.shopping.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Random;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TransactionResponse processPayment(String userId, String orderId, Integer amount) {
        Integer orderIdVal = Integer.parseInt(orderId);
        String transactionId = generateTransactionId();
        OrderEntity order = orderRepository.findById(orderIdVal)
                .orElse(null);
        List<TransactionEntity> transactionData = transactionRepository.findByOrderOrderId(orderIdVal);

        if (orderId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "{\n\t\"userId\": " + userId + "," +
                            "\n\t\"orderId\": " + orderId + "," +
                            "\n\t\"transactionId\": \"" + transactionId + "\"," +
                            "\n\t\"status\": \"failed\"," +
                            "\n\t\"description\": \"Payment Failed due to invalid order id\"\n}");
        }

        if(order.getAmount() != amount){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "{\n\t\"userId\": " + userId + "," +
                            "\n\t\"orderId\": " + orderId + "," +
                            "\n\t\"transactionId\": \"" + transactionId + "\"," +
                            "\n\t\"status\": \"failed\"," +
                            "\n\t\"description\": \"Payment Failed as amount is invalid\"\n}");
        }
        for(TransactionEntity transactionStatus: transactionData){
            if(transactionStatus.getStatus().equals("successful")){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "{\n\t\"userId\": " + userId + "," +
                                "\n\t\"orderId\": " + orderId + "," +
                                "\n\t\"transactionId\": \"" + transactionId + "\"," +
                                "\n\t\"status\": \"failed\"," +
                                "\n\t\"description\": \"Order is already paid for\"\n}");
            }
        }

        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        if (elapsedTime > 5 * 60 * 1000) { // Check if payment process took more than 5 minutes
            throw new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT,
                    "{\n\t\"userId\": " + userId + "," +
                            "\n\t\"orderId\": " + orderId + "," +
                            "\n\t\"transactionId\": \"" + transactionId + "\"," +
                            "\n\t\"status\": \"failed\"," +
                            "\n\t\"description\": \"No response from payment server\"\n}");
        }


        UserEntity userEntity = userRepository.findByUserId(userId);
        OrderEntity orderEntity = new OrderEntity();
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionId(transactionId);
        orderEntity.setOrderId(orderIdVal);
        userEntity.setOrder(orderEntity);
        userEntity.setTransaction(transactionEntity);
        userRepository.save(userEntity);

        TransactionEntity transaction = new TransactionEntity();
        transaction.setUserId(userId);
        transaction.setTransactionId(transactionId);
        transaction.setStatus("successful");
        transaction.setOrder(order);

        TransactionEntity createTransaction =   transactionRepository.save(transaction);
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setUserId(createTransaction.getUserId());
        transactionResponse.setOrderId(createTransaction.getOrder().getOrderId());
        transactionResponse.setTransactionId(createTransaction.getTransactionId());
        transactionResponse.setStatus(createTransaction.getStatus());
        return transactionResponse;
    }
    public static String generateTransactionId() {
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000; // Generate a random 6-digit number
        return "tran" + String.format("%06d", randomNumber); // Format the number to have leading zeros if needed
    }
}
