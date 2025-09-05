package ${packageClass}.${srcConfigAuditFolder};

/**
 * Enum for the different audit actions
 */
public enum ${className} {

    CREATE("CREATE"),
    UPDATE("UPDATE"),
    DELETE("DELETE");
    private String value;

    ${className}(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return this.value();
    }

}
