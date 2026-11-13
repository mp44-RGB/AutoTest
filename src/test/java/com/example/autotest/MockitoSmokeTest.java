package com.example.autotest;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Smoke test: proves that Mockito 5.11.0 is available in this Maven/IDEA project.
 * Delete this file once real unit tests are in place.
 */
public class MockitoSmokeTest {

    interface TodoRepository {
        List<String> findByTitle(String keyword);

        int countAll();
    }

    @Test
    public void mockitoIsConfigured() {
        TodoRepository repository = Mockito.mock(TodoRepository.class);
        when(repository.findByTitle("book")).thenReturn(List.of("buy book"));
        when(repository.countAll()).thenReturn(1);

        assertEquals(repository.findByTitle("book"), List.of("buy book"));
        assertEquals(repository.countAll(), 1);

        ArgumentCaptor<String> keyword = ArgumentCaptor.forClass(String.class);
        verify(repository).findByTitle(keyword.capture());
        assertEquals(keyword.getValue(), "book");
    }
}
