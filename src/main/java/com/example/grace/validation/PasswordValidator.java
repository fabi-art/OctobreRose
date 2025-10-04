package com.example.grace.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        // Vous pouvez mettre en place des initialisations ici si nécessaire
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return true; // Ne pas valider si le champ est null (la validation @NotBlank s'en chargera)
        }
        // Vérifiez si le mot de passe répond à vos critères de force
        return password.length() >= 8 && // Longueur minimale
                password.matches(".*[A-Z].*") && // Au moins une majuscule
                password.matches(".*[a-z].*") && // Au moins une minuscule
                password.matches(".*[0-9].*") && // Au moins un chiffre
                password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"); // Au moins un caractère spécial
    }
}
