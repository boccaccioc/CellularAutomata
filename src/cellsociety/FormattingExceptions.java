package cellsociety;

public class FormattingExceptions extends RuntimeException {
   private final String message;

   public FormattingExceptions(String message) {
      this.message = message;
    }

}
