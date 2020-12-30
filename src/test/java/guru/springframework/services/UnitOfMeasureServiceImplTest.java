package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureRepository repository;

    UnitOfMeasureService service;
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        service = new UnitOfMeasureServiceImpl(repository,unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    void listAllUoms() {
        //given
        Set<UnitOfMeasure> uomCommandSet = new HashSet<>();
        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setId(1L);
        UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
        unitOfMeasure2.setId(2L);
        uomCommandSet.add(unitOfMeasure1);
        uomCommandSet.add(unitOfMeasure2);

        when(repository.findAll()).thenReturn(uomCommandSet);

        //when
        Set<UnitOfMeasureCommand> returnedList = service.listAllUoms();

        //then
        assertEquals(2,returnedList.size());
        verify(repository,times(1)).findAll();
    }
}