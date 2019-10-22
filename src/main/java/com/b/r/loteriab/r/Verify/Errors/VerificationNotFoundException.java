package com.b.r.loteriab.r.Verify.Errors;

public class VerificationNotFoundException extends Throwable {
    public VerificationNotFoundException() {
        this("Failed to find verification.");
    }

    public VerificationNotFoundException(String message) {
        super(message);
    }
}