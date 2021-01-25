package co.uk.kenyoncomputing.mixfix.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorizationCall {
    @JsonProperty("response_type")
    private String responseType;

    @JsonProperty("client_id")
    private String clientId;

    private String scope;

    @JsonProperty("redirect_uri")
    private String redirectURI;
    private String state;

    public AuthorizationCall(String responseType, String clientId, String scope, String redirectURI, String state) {
        this.responseType = responseType;
        this.clientId = clientId;
        this.scope = scope;
        this.redirectURI = redirectURI;
        this.state = state;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRedirectURI() {
        return redirectURI;
    }

    public void setRedirectURI(String redirectURI) {
        this.redirectURI = redirectURI;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
