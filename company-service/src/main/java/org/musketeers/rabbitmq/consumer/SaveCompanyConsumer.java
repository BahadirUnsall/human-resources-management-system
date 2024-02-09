package org.musketeers.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.musketeers.rabbitmq.model.SaveCompanyModel;
import org.musketeers.repository.entity.Company;
import org.musketeers.service.CompanyService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveCompanyConsumer {

    private final CompanyService companyService;

    @RabbitListener(queues = "queue-auth")
    public void createUserFromQueue(SaveCompanyModel model){
        if (!companyService.findByCompanyName(model.getName())) {
            companyService.save(Company.builder()
                    .companyName(model.getName())
                    .build());
        }
    }
}