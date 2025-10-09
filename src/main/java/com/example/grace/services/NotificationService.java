package com.example.grace.services;


import com.example.grace.entities.User;
import com.example.grace.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

//@Service
//public class NotificationService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    // Envoie une notification à tous les utilisateurs d'une ville
//    public void notifyUsersInCity(String ville, String message) {
//        List<User> users = userRepository.findByVilleIgnoreCase(ville);
//
//        for (User user : users) {
//            // Tu peux adapter selon ton système : envoi email, websocket, etc.
//            System.out.println("📢 Notification envoyée à " + user.getPseudo() + " (" + ville + ")");
//            System.out.println(message);
//        }
//    }
//}

import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
public class NotificationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void notifyUsersInCity(String ville, String message) {
        List<User> users = userRepository.findByVilleIgnoreCase(ville);

        for (User user : users) {
            // Envoi vers un topic spécifique à la ville
            messagingTemplate.convertAndSend("/topic/notifications/" + ville, message);
        }
    }
}

