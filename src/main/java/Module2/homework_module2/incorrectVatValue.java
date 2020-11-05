package Module2.homework_module2;

class IncorrectVatValue extends Throwable {
    private String message;

    public IncorrectVatValue( String message) { this.message = message; }
    public String getMessage() {
        return this.message;
    }
}
