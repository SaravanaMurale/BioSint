package com.nutro.biosint.modelresponse;

public class GetClientResponse {

    private String clientDocId;
    private String clientName;

    public GetClientResponse() {
    }

    public GetClientResponse(String clientDocId, String clientName) {
        this.clientDocId = clientDocId;
        this.clientName = clientName;
    }

    public String getClientDocId() {
        return clientDocId;
    }

    public void setClientDocId(String clientDocId) {
        this.clientDocId = clientDocId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
