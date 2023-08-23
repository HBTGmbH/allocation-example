package de.hbt.cfa.functionalPrograming;

public class T4_SafeBuilderPattern {

    public static void main(String[] args) {
        final Book book = Book.builder()
                .title("Harry Potter and the Half-Blood Prince")
                .author("J. K. Rowling");
        System.out.println(book);
    }

    record Book(String name, String author) {

        static WithTitle<WithAuthor<Book>> builder() {
            // Currying builder pattern
            return name -> author -> new Book(name, author);
        }

        @FunctionalInterface
        public interface WithTitle<O> {
            O title(String title);
        }

        @FunctionalInterface
        public interface WithAuthor<O> {
            O author(String name);
        }
    }

}
