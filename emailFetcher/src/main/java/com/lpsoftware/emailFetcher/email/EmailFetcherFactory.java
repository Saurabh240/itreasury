package com.lpsoftware.emailFetcher.email;

public class EmailFetcherFactory {

    public static EmailFetcher createEmailFetcher(EmailProvider provider) {
        switch (provider) {
            case GMAIL:
                return new GmailFetcher();
            case OUTLOOK:
                return new OutlookFetcher();
            default:
                throw new IllegalArgumentException("Unsupported provider: " + provider);
        }
    }
}