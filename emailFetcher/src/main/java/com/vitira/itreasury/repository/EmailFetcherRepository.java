package com.vitira.itreasury.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.stereotype.Repository;

import com.vitira.itreasury.email.EmailClient;
import com.vitira.itreasury.model.Email;

@Repository
public class EmailFetcherRepository {

	public List<String> getAllAttachments() {

		List<String> allAttachments = new ArrayList<>();
		EmailClient client;
		try {
			client = new EmailClient();
			List<Email> emails = client.executeFetch();
			for (Email email : emails) {
				allAttachments.addAll(email.getAttachments());
			}

		} catch (IOException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return allAttachments;

	}

//	private PaymentEntity executeQuery(PaymentRequest request) {
//		// This is a dummy method to simulate a database query
//		PaymentEntity paymentModel = new PaymentEntity();
//		paymentModel.setPaymentId(request.getPaymentId());
//		paymentModel.setAmount(1000.00);
//		paymentModel.setCurrency("INR");
//		return paymentModel;
//	}

}
