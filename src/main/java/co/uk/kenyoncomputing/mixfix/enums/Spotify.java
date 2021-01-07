package co.uk.kenyoncomputing.mixfix.enums;

public enum Spotify {

    CLIENT_ID( "9f8e38fa4a4b4121a8108c36674f4a67"),
    RESPONSE_TYPE("code"),
    SCOPE("user-read-private user-read-email");

    private final String value;

    Spotify(String value) {
        this.value = value;
    };
    public String value() {
        return this.value;
    }
}
