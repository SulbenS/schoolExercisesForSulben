package Sulben.schoolEx;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * data structure for storing Book data
 *
 * @param id            the unique id of the book
 * @param title         the title of the book
 * @param year          the year the book was published
 * @param numberOfPages the number of pages in the book
 */
@Schema(description = "A book")
public record Book(
    @Schema(description = "Unique ID")
    int id,

    @Schema(description = "Title of the book")
    String title,

    @Schema(description = "Year the book was published")
    int year,

    @Schema(description = "Number of pages in the book")
    int numberOfPages
) {
}
