package ru.otus.homework.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.Publisher;
import ru.otus.outside.db.JpaDedicatedEntityManagerTest;
import ru.otus.outside.db.JpaSharedEntityManagerTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.otus.outside.utils.TestData.createPublisher1;

@DisplayName("Class PublisherRepositoryJpa")
class PublisherRepositoryJpaTest
{
    private PublisherRepositoryJpa repository;

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            repository = new PublisherRepositoryJpa();
        }

        @Test
        @DisplayName("default values in PublisherRepositoryJpa()")
        void defaults()
        {
            assertThat(repository).hasFieldOrPropertyWithValue("em", null);
        }
    }

    @Nested
    @DisplayName("when mock EntityManager")
    class WhenMock
    {
        private EntityManager entityManager;

        @BeforeEach
        void mockEntityManager()
        {
            entityManager = mock(EntityManager.class);
            repository = new PublisherRepositoryJpa(entityManager);
        }

        @Test
        @DisplayName("Constructor injected values in PublisherRepositoryJpa()")
        void defaults()
        {
            assertThat(repository).hasFieldOrPropertyWithValue("em", entityManager);
        }

        @DisplayName("find by id from table publisher success")
        @Test
        void findById_success()
        {
            Publisher expected = new Publisher();
            expected.setPublisherName("testPublisherName");

            when(entityManager.find(Publisher.class,1L)).thenReturn(expected);

            Publisher publisher = repository.findById(1L);
            assertEquals(expected, publisher);
        }

        @DisplayName("find by id from table publisher return null")
        @Test
        void findById_null()
        {
            when(entityManager.find(Publisher.class,1L)).thenReturn(null);

            Publisher publisher = repository.findById(1L);
            assertNull(publisher);
        }
    }

    @Nested
    @DisplayName("JPA H2 read-only tests for PublisherRepositoryJpa")
    class JpaH2ReadOnlyTests extends JpaSharedEntityManagerTest
    {
        @BeforeEach
        void createNew()
        {
            repository = new PublisherRepositoryJpa(entityManager);
        }

        @Test
        @DisplayName("Constructor injected values in PublisherRepositoryJpa()")
        void defaults()
        {
            assertThat(repository).hasFieldOrPropertyWithValue("em", entityManager);
        }

        @Test
        void findByFirstName_success()
        {
            Publisher expected = createPublisher1();
            List<Publisher> publisherList = repository.findByPublisher(expected.getPublisherName());
            assertTrue(publisherList.contains(createPublisher1()));
        }

        @Test
        void findByFirstName_empty()
        {
            List<Publisher> publisherList = repository.findByPublisher("");
            assertTrue(publisherList.isEmpty());
        }

        @Test
        void findByLastName_success()
        {
            Publisher expected = createPublisher1();
            List<Publisher> publisherList = repository.findByPublisher(expected.getPublisherName());
            assertTrue(publisherList.contains(createPublisher1()));
        }

        @Test
        void findByLastName_empty()
        {
            List<Publisher> publisherList = repository.findByPublisher("");
            assertTrue(publisherList.isEmpty());
        }

        @DisplayName("find all records from table publisher, success")
        @Test
        void findAll_success()
        {
            List<Publisher> publisherList = repository.findAll();
            assertEquals(1, publisherList.size());
            assertTrue(publisherList.contains(createPublisher1()));
        }

        @DisplayName("find all records from empty table publisher, empty list")
        @Test
        void findAll_empty()
        {
            clearAuthorIsbn();
            clearBook();
            clearPublisher();
            List<Publisher> publisherList = repository.findAll();
            assertTrue(publisherList.isEmpty());
        }

        @DisplayName("find by id from table publisher, success")
        @Test
        void findById_success()
        {
            Publisher expected = createPublisher1();
            Publisher publisher = repository.findById(expected.getId());
            assertEquals(expected, publisher);
        }

        @DisplayName("when finding by id from table publisher, id doesn't exist, return null")
        @Test
        void findById_null()
        {
            Publisher publisher = repository.findById(Integer.MAX_VALUE);
            assertNull(publisher);
        }
    }

    @Nested
    @DisplayName("JPA H2 create/update tests for PublisherRepositoryJpa")
    class JpaH2CreateUpdateTests extends JpaDedicatedEntityManagerTest
    {
        @BeforeEach
        void createNew()
        {
            repository = new PublisherRepositoryJpa(entityManager);
        }

        @DisplayName("persists new when save")
        @Test
        void save_persists()
        {
            Publisher publisher = new Publisher();
            publisher.setPublisherName("test");
            runInTransaction(() -> repository.save(publisher));
            assertEquals(publisher, repository.findById(publisher.getId()));
        }

        @DisplayName("merge detached object when save")
        @Test
        void save_megre()
        {
            Publisher publisher = createPublisher1();
            publisher.setPublisherName("test");
            runInTransaction(() -> repository.save(publisher));
            assertEquals(publisher, repository.findById(publisher.getId()));
        }

        @Test
        void delete()
        {
            assertEquals(1, repository.findAll().size());
            clearAuthorIsbn();
            clearBook();
            runInTransaction(() -> repository.delete(1L));
            assertEquals(0, repository.findAll().size());
        }
    }
}