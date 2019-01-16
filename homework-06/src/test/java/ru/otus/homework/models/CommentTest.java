package ru.otus.homework.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class Comment")
class CommentTest
{
    private Comment comment;

    @Test
    @DisplayName("is instantiated with new Comment()")
    void isInstantiatedWithNew() {
        new Comment();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            comment = new Comment();
        }

        @Test
        @DisplayName("default values in Publisher()")
        void defaults()
        {
            assertThat(comment).hasFieldOrPropertyWithValue("id", 0L);
            assertThat(comment).hasFieldOrPropertyWithValue("comment", null);
        }

        @Test
        @DisplayName("Setter and getter for publisherName")
        void testGetSetComment()
        {
            comment.setComment(TEST);
            assertThat(comment).hasFieldOrPropertyWithValue("comment", TEST);
            assertEquals(TEST, comment.getComment());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {

        @BeforeEach
        void createNew()
        {
            comment = new Comment(TEST_ID, TEST_COMMENT_NAME, null);
        }

        @Test
        @DisplayName("initialized values in Comment()")
        void defaults()
        {
            assertThat(comment).hasFieldOrPropertyWithValue("id", TEST_ID);
            assertThat(comment).hasFieldOrPropertyWithValue("comment", TEST_COMMENT_NAME);
        }

        @Test
        @DisplayName("Equals for class Comment and hashCode")
        void testEquals()
        {
            assertNotEquals(new Comment(), comment);
            Comment expected = new Comment(TEST_ID, TEST_COMMENT_NAME, null);
            assertEquals(expected.hashCode(), comment.hashCode());
            assertEquals(expected, comment);
        }

        @Test
        @DisplayName("The length of string from Comment::toString is great than zero")
        void testToString()
        {
            assertTrue(comment.toString().length() > 0);
        }
    }
}